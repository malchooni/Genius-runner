package name.yalsooni.genius.runner.repository;

import name.yalsooni.boothelper.execute.BootHelper;
import name.yalsooni.boothelper.repository.BootHelperRepository;
import name.yalsooni.boothelper.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * annotation lib root를 기반으로 별도의 클래스 로더를 보관한다.
 * Created by yoon-iljoong on 2016. 10. 31..
 */
public class GeniusClassLoader {

    private static URLClassLoader urlClassLoader = null;
    private static URL[] urls = null;

    /**
     * URL 배열을 기반으로 URLClassLoader를 생성한다.
     * @param urls_
     */
    public synchronized static void setUrls(URL[] urls_) {
        urls = urls_;
        BootHelper parentBootHelper = BootHelperRepository.getBootHelper();

        if(parentBootHelper != null){
//            urlClassLoader = new URLClassLoader(urls, parentBootHelper.getClassLoader() );
            urlClassLoader = (URLClassLoader) parentBootHelper.getClassLoader();

            Method addURLMethod = null;

            int loadCnt = 0;

            Log.console(" == JAR File load. == ");

            try {
                addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
                addURLMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.console(e);
            }

            for(URL url : urls_){
                try {
                    addURLMethod.invoke(urlClassLoader, new Object[]{url});
                } catch (IllegalAccessException e) {
                    Log.console(e);
                } catch (InvocationTargetException e) {
                    Log.console(e);
                }
                loadCnt++;

                Log.console(url.toString());
            }

            Log.console(" == "+loadCnt+" JAR File loaded. == ");

        }else{
            urlClassLoader = new URLClassLoader(urls);
        }
    }

    /**
     * URLClassLoader 반환
     * @return
     */
    public static URLClassLoader getUrlClassLoader() {
        return urlClassLoader;
    }

    /**
     * URL Array 반환
     * @return
     */
    public static URL[] getUrls() {
        return urls;
    }
}
