package name.yalsooni.genius.boothelper.execute;


import name.yalsooni.genius.boothelper.classloader.JarLoader;
import name.yalsooni.genius.boothelper.classloader.exception.JARFileNotFoundException;
import name.yalsooni.genius.boothelper.classloader.exception.NotDirectoryException;
import name.yalsooni.genius.boothelper.definition.Code;
import name.yalsooni.genius.boothelper.definition.PropertyName;
import name.yalsooni.genius.boothelper.repository.PropertyRepository;
import name.yalsooni.genius.util.Log;
import name.yalsooni.genius.util.file.ExtFileSearch;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * BootHelper Util
 * Created by ijyoon on 2016. 7. 29..
 */
public class BootHelperUtil {

    private static ExtFileSearch JARFILE_SEARCH = new ExtFileSearch(".jar");

    /**
     * 부트헬퍼 메타 파일 읽기.
     * @param bootHelper
     * @throws Exception
     */
    public static void metaLoading(BootHelper bootHelper) throws Exception {
        String metaPath = System.getProperty("META_PATH");

        if(metaPath != null){
            Log.console("meta file path : " + metaPath);

            try {
                bootHelper.getPropertyReader().read(bootHelper.META, metaPath);
            } catch (FileNotFoundException e) {
                throw new Exception(Code.BH_0001,e);
            } catch (IOException e) {
                throw e;
            } catch (Exception e) {
                throw e;
            }
        }else{
            Log.console("meta resource name : " + PropertyName.METAFILE_NAME);
            InputStream inputStream = BootHelper.class.getClassLoader().getResourceAsStream( PropertyName.METAFILE_NAME  );
            bootHelper.getPropertyReader().read(bootHelper.META, inputStream);
        }
    }

    /**
     * 사용자 프로퍼티 파일 읽기.
     * @param bootHelper
     */
    public static void propertyLoading(BootHelper bootHelper) throws Exception {
        String propertyRootPath = bootHelper.getPropertyReader().get(bootHelper.META, bootHelper.PROPERTY_ROOT_PATH);

        if(propertyRootPath == null || propertyRootPath.length() < 1) return;

        PropertyRepository.registerDir(propertyRootPath, bootHelper.PROPERTY_EXT_NAME);
    }

    /**
     * 클래스 로드
     * @param bootHelper
     * @throws NotDirectoryException
     * @throws JARFileNotFoundException
     */
    public static void classLoad(BootHelper bootHelper) throws NotDirectoryException, JARFileNotFoundException {
        String libRootPath = bootHelper.getPropertyReader().get(bootHelper.META, bootHelper.LIB_ROOT_PATH);
        JarLoader loader = bootHelper.createInstanceJarLoader(libRootPath);
        loader.load(ClassLoader.getSystemClassLoader());
    }

    /**
     * 클래스 실행
     * @param bootHelper
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void classExecute(BootHelper bootHelper) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String className = bootHelper.getPropertyReader().get(bootHelper.META, bootHelper.MAIN_CLASS_NAME);

        ClassLoader useClassLoader = bootHelper.getClassLoader();
        Class targetClass = useClassLoader.loadClass(className);
        Object instance = targetClass.newInstance();

        Method mainMethod = targetClass.getDeclaredMethod("main", String[].class);
        mainMethod.invoke(instance, new Object[] {bootHelper.getArgs()});
    }
}
