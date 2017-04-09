package name.yalsooni.genius.common.repository;

import name.yalsooni.genius.boothelper.execute.BootHelper;
import name.yalsooni.genius.boothelper.repository.BootHelperRepository;

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
            urlClassLoader = new URLClassLoader(urls, parentBootHelper.getClassLoader() );
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
