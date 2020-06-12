package name.yalsooni.genius.runner.repository;

import name.yalsooni.boothelper.util.Log;

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
     * @param urls_ url list
     */
    public synchronized static void setUrls(URL[] urls_) throws Exception{
        urls = urls_;
        urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();

        Method addURLMethod = null;

        int loadCnt = 0;

        Log.console(" == Genius JAR File load. == ");

        try {
            addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURLMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            Log.console(e);
        }

        for(URL url : urls_){
            addURLMethod.invoke(urlClassLoader, url);
            loadCnt++;
            Log.console(url.toString());
        }

        Log.console(" == "+loadCnt+" Genius JAR File loaded. == ");
    }

    /**
     * URLClassLoader 반환
     * @return URLClassLoader
     */
    public static URLClassLoader getUrlClassLoader() {
        return urlClassLoader;
    }

    /**
     * URL Array 반환
     * @return URL array
     */
    public static URL[] getUrls() {
        return urls;
    }
}
