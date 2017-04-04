package name.yalsooni.genius.boothelper.classloader;

import name.yalsooni.genius.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * Created by yoon-iljoong on 2015. 12. 31..
 */
public class ClassList {

    private final static String CLASSEXTNAME = ".class";

    private static Map<String, Class<?>> classList = new HashMap<String, Class<?>>();

    public static void put(ClassLoader classLoader, String className ) throws ClassNotFoundException {
        classList.put(className, Class.forName(className, true, classLoader));
    }

    /**
     * jar 파일의 클래스 목록을 맵으로 보관.
     * @param classLoader
     * @param urls
     */
    public static void put(ClassLoader classLoader, URL... urls){

        JarFile jarFile = null;

        for(URL url : urls)
            try {
                jarFile = new JarFile(url.getPath());

                for (Enumeration e = jarFile.entries(); e.hasMoreElements(); ) {
                    String path = e.nextElement().toString().replace("/", ".");
                    if (path.endsWith(CLASSEXTNAME)){
                        put(classLoader, classExtRemove(path));
                    }
                }

            } catch (IOException e) {
                Log.console(e);
                continue;
            } catch (ClassNotFoundException e) {
                Log.console(e);
                continue;
            }
    }

    /**
     * 클래스 확장자를 제거한다.
     * @param className
     * @return
     */
    private static String classExtRemove(String className){
        if(className.endsWith(CLASSEXTNAME)){
            className = className.substring(0, className.lastIndexOf(CLASSEXTNAME));
        }
        return className;
    }

    /**
     * 해당 클래스명의 클래스를 반환한다.
     * @param className
     * @return
     */
    public static Class<?> get(String className){
        return classList.get(className);
    }

    /**
     * 보관된 클래스 맵을 반환한다.
     * @return
     */
    public static Map<String, Class<?>> getClassMap(){
        return classList;
    }
}
