package name.yalsooni.genius.core.boothelper.execute;


import name.yalsooni.genius.core.boothelper.classloader.JarLoader;
import name.yalsooni.genius.core.boothelper.definition.Code;
import name.yalsooni.genius.core.boothelper.definition.PropertyName;
import name.yalsooni.genius.core.boothelper.repository.PropertyRepository;
import name.yalsooni.genius.core.util.Log;
import name.yalsooni.genius.core.util.file.ExtFileSearch;

import java.io.FileNotFoundException;
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

        try{
            if(metaPath != null){
                Log.console("meta file path : " + metaPath);

                try {
                    bootHelper.getPropertyReader().read(PropertyName.META, metaPath);
                } catch (FileNotFoundException e) {
                    throw new Exception(Code.BH_0002,e);
                }
            }else{
                Log.console("meta file name : " + PropertyName.METAFILE_NAME);
                InputStream inputStream = BootHelper.class.getClassLoader().getResourceAsStream( PropertyName.METAFILE_NAME  );
                bootHelper.getPropertyReader().read(PropertyName.META, inputStream);
            }
        }catch (Exception e) {
            throw new Exception(Code.BH_0001,e);
        }
    }

    /**
     * 사용자 프로퍼티 파일 읽기.
     * @param bootHelper
     * @throws Exception
     */
    public static void propertyLoading(BootHelper bootHelper) throws Exception {
        try{
            String propertyRootPath = bootHelper.getPropertyReader().get(PropertyName.META, PropertyName.PROPERTY_ROOT_PATH);

            if(propertyRootPath == null || propertyRootPath.length() < 1) return;

            Log.console("properties load path : " + propertyRootPath);
            PropertyRepository.registerDir(propertyRootPath, PropertyName.PROPERTY_EXT_NAME);
        }catch(Exception e){
            throw new Exception(Code.BH_0003,e);
        }
    }

    /**
     * 클래스 로드
     * @param bootHelper
     * @throws Exception
     */
    public static void classLoad(BootHelper bootHelper) throws Exception {
        try{
            String libRootPath = bootHelper.getPropertyReader().get(PropertyName.META, PropertyName.LIB_ROOT_PATH);
            JarLoader loader = bootHelper.createInstanceJarLoader(libRootPath);
//            loader.load(ClassLoader.getSystemClassLoader());
            loader.loadSystemClassloader();
        }catch (Exception e){
            throw new Exception(Code.BH_0004,e);
        }
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
    public static void classExecute(BootHelper bootHelper) throws Exception {
        try{
            String className = bootHelper.getPropertyReader().get(PropertyName.META, PropertyName.MAIN_CLASS_NAME);

            ClassLoader useClassLoader = bootHelper.getClassLoader();
            Class targetClass = useClassLoader.loadClass(className);
            Object instance = targetClass.newInstance();

            Method mainMethod = targetClass.getDeclaredMethod("main", String[].class);
            mainMethod.invoke(instance, new Object[] {bootHelper.getArgs()});
        }catch (Exception e){
            throw new Exception(Code.BH_0005,e);
        }
    }
}
