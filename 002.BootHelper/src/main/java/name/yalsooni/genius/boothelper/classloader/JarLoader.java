package name.yalsooni.genius.boothelper.classloader;

import name.yalsooni.genius.boothelper.classloader.exception.JARFileNotFoundException;
import name.yalsooni.genius.boothelper.classloader.exception.NotDirectoryException;
import name.yalsooni.genius.boothelper.classloader.util.ClassListUtil;
import name.yalsooni.genius.util.Log;
import name.yalsooni.genius.util.file.ExtFileSearch;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * Created by ijyoon on 2015. 12. 15..
 */
public class JarLoader {

    private final String EXT_NAME = ".jar";

    private ClassLoader parentClassLoader = null;
    private ClassLoader classLoader = null;
    private List<File> jarList = null;

    private ExtFileSearch search = null;

    /**
     * 라이브러리 경로의 모든 Jar 파일을 읽는다.
     * 기본 클래스로더 : SystemClassLoader
     *
     * @param rootLibDirPath 라이브러리 루트 경로
     * @throws NotDirectoryException
     */
    public JarLoader(String rootLibDirPath) throws NotDirectoryException, JARFileNotFoundException {

        this.search = new ExtFileSearch(this.EXT_NAME);
        File rootDir = new File(rootLibDirPath);

        if(!rootDir.isDirectory()){
            throw new NotDirectoryException(rootLibDirPath);
        }

        jarList = search.getFileListIncludeSubDir(rootDir);

        if(1 > jarList.size()){
            throw new JARFileNotFoundException(rootLibDirPath);
        }

    }

    /**
     * 부모 클래스 로더 밑에 새로운 URLClassLoader를 생성 후 자식 클래스로더로 만든다.
     * @param parentClassLoader
     * @return
     */
    public ClassLoader load(ClassLoader parentClassLoader){
        if(parentClassLoader == null){
            throw new NullPointerException("ClassLoader is null.");
        }

        this.parentClassLoader = parentClassLoader;

        Log.console(" == JAR File load. == ");

        URL[] urls = ClassListUtil.getURLArray( jarList );

        Log.console(" == "+urls.length+" JAR File loaded. == ");

        this.classLoader = new URLClassLoader(urls,parentClassLoader);

        return this.classLoader;
    }

    /**
     * SystemClassLoader 에 로드 한다.
     * @return
     */
    public ClassLoader loadSystemClassloader(){
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Method addURLMethod = null;

        try {
            addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
            addURLMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            Log.console(e);
        }

        URL url;
        int loadCnt = 0;

        Log.console(" == JAR File load. == ");

        for(File jarFile: jarList) {
            try {
                url = jarFile.toURI().toURL();
                addURLMethod.invoke(urlClassLoader, new Object[]{url});
                loadCnt++;

                Log.console(url.toString());

            } catch (IllegalAccessException e) {
                Log.console(e);
            } catch (MalformedURLException e) {
                Log.console(e);
            } catch (InvocationTargetException e) {
                Log.console(e);
            }
        }
        Log.console(" == "+loadCnt+" JAR File loaded. == ");

        return this.classLoader;
    }

    /**
     * 클래스 로더를 반환한다.
     * @return
     */
    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    /**
     * 부모 클래스 로더를 반환한다.
     * @return
     */
    public ClassLoader getParentClassLoader() {
        return this.parentClassLoader;
    }

}
