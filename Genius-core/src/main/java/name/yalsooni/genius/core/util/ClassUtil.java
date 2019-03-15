package name.yalsooni.genius.core.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * 클래스 관련 유틸
 * Created by yoon-iljoong on 15. 10. 17..
 */
public class ClassUtil {

	/**
	 * 프로퍼티 파일을 읽어 객체를 맵으로 반환한다.
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getInstanceMap(Properties list) throws Exception{
		
		if(list == null) return null;
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Iterator<Object> iter = list.keySet().iterator();
		
		String key = null;
		String className = null;
		while(iter.hasNext()){
			key = (String) iter.next();
			className = list.getProperty(key);
			try {
				result.put(key, this.newInstance(className));
			} catch (Exception e) {
				throw e;
			} 
		}
		
		return result;
	}
	
	/**
	 * 객체를 생성하여 반환한다.
	 * 
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T>T newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class<?> klass = Class.forName(className);
		return (T) klass.newInstance();
	}
	
	
    public Class<?>[] getClass(String packageName) throws ClassNotFoundException {

        List<Class<?>> classList = new ArrayList<Class<?>>();

        String packagePath = "" + packageName.replace('.', '/');

        URL path = Thread.currentThread().getContextClassLoader().getResource("");

        String resource = path + packagePath;
        try {
            resource = URLDecoder.decode(resource, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        resource = resource.substring("file:".length());

        File directory = new File(resource);

        if (directory.exists()) {
            // Get the list of the files contained in the package
            String[] files = directory.list();
            for (int i = 0; i < files.length; i++) {
                // we are only interested in .class files
                if (files[i].endsWith(".class")) {
                    // removes the .class extension
                    classList.add(Class.forName(packageName + '.' + files[i].substring(0, files[i].length() - 6)));
                }
            }
        } else {
            throw new ClassNotFoundException(packageName
                    + " does not appear to be a valid package");
        }


        Class<?>[] classes = new Class[classList.size()];
        classList.toArray(classes);

        return classes;
    }
	
	
    public static void main(String[] args){

        ClassUtil c = new ClassUtil();
        try {
            Class<?>[] classes = c.getClass("genius.rulebreaker.execute");


            for(int i=0; i < classes.length; i++){
                System.out.println(classes[i].getPackage());
                System.out.println(classes[i].getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
