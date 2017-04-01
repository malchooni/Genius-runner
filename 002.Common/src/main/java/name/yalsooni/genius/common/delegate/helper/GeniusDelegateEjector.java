package name.yalsooni.genius.common.delegate.helper;

import genius.rulebreaker.definition.annotation.Delegate;
import genius.rulebreaker.definition.annotation.Entry;
import genius.rulebreaker.definition.property.GeniusProperties;
import genius.rulebreaker.delegate.vo.DelegateDTO;
import genius.rulebreaker.delegate.vo.EntryDTO;
import genius.rulebreaker.repository.DelegateList;
import genius.rulebreaker.repository.GeniusClassLoader;
import name.yalsooni.boothelper.classloader.ClassList;
import name.yalsooni.boothelper.classloader.util.ClassListUtil;
import name.yalsooni.common.util.file.ExtFileSearch;
import name.yalsooni.common.util.support.Log;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 * 지정된 디렉토리의 JAR파일의 클래스를 검색하여 Delegate 추출한다.
 * Created by yoon-iljoong on 2016. 10. 31..
 */
public class GeniusDelegateEjector {

    private ExtFileSearch fileSearch = new ExtFileSearch(".jar");

    public DelegateList eject(GeniusProperties gProperties){

        DelegateList result = new DelegateList();
        GeniusClassLoader.setUrls(ClassListUtil.getURLArray( fileSearch.getFileList(gProperties.getAnnotationLibRootPath())));
        ClassList.put(GeniusClassLoader.getUrlClassLoader(), GeniusClassLoader.getUrls());
        this.classAnalyzer(ClassList.getClassMap(), result);

        return result;
    }

    /**
     * 클래스 검색
     * @param classMap
     * @param delegateList
     */
    private void classAnalyzer(Map<String, Class<?>> classMap, DelegateList delegateList){

        Iterator<String> keys = classMap.keySet().iterator();

        while(keys.hasNext()){
            String className = keys.next();
            try {
                Class klass = Class.forName(className, false, GeniusClassLoader.getUrlClassLoader());
                this.delegateEjector(klass, delegateList);
            } catch (ClassNotFoundException e) {
                Log.console(e);
                continue;
            }
        }
    }

    /**
     * 딜리게이트 추출 (Delegate 어노테이션 클래스)
     * @param klass
     * @param delegateList
     */
    private void delegateEjector(Class klass, DelegateList delegateList){
        if(klass.isAnnotationPresent(Delegate.class)){
            Delegate delegate = (Delegate) klass.getAnnotation(Delegate.class);

            DelegateDTO delegateDTO = new DelegateDTO(delegate.projectID());
            delegateDTO.setKlass(klass);
            delegateDTO.setServiceType(delegate.serviceType());

            Log.console("Loading Delegate * Project ID : " + delegate.projectID() + ", Service Name : "+delegateDTO.getName()+", Service Type : " + delegate.serviceType());
            this.entryEjector(delegateDTO, klass, delegateList);
            delegateList.addDelegate(delegateDTO);
            Log.console("["+delegateDTO.getId() + "] "+delegateDTO.getName()+ " loaded.");
        }
    }

    /**
     * 엔트리 추출 (Entry 어노테이션 메소드)
     * @param delegateDTO
     * @param klass
     * @param delegateList
     */
    private void entryEjector(DelegateDTO delegateDTO, Class klass, DelegateList delegateList){
        Method[] methods = klass.getMethods();

        for(Method method : methods){
            Entry entry = method.getAnnotation(Entry.class);
            if(entry != null){
                EntryDTO entryDTO = new EntryDTO(method);
                delegateDTO.addEntry(entryDTO);
                Log.console("\t - "+entryDTO.toString());
            }
        }
    }
}