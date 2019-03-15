package name.yalsooni.genius.core.boothelper.repository;



import name.yalsooni.genius.core.util.reader.PropertyReader;

import java.util.Properties;

/**
 * 프로퍼티 보관.
 * Created by ijyoon on 2016. 7. 29..
 */
public class PropertyRepository {

    private static PropertyReader reader = new PropertyReader();

    /**
     * 지정된 프로퍼티를 등록한다.
     * @param name
     * @param filePath
     * @throws Exception
     */
    public static void register(String name, String filePath) throws Exception {
        reader.read(name, filePath);
    }

    /**
     * 하위디렉토리를 포함하는 *.data 파일명의 프로퍼티를 등록한다.
     * @param path
     * @param extName
     * @throws Exception
     */
    public static void registerDir(String path, String extName) throws Exception {
        reader.readDirectory(path, extName, true);
    }

    /**
     * 특정 프로퍼티의 특정 키에 해당하는 값을 반환한다.
     * @param name
     * @param key
     * @return
     */
    public static String get(String name, String key){
        return reader.get(name, key);
    }

    /**
     * 해당 프로퍼티를 반환한다.
     * @param name
     * @return
     */
    public static Properties getProperties(String name){
        return reader.getProperties(name);
    }

    /**
     * 프로퍼티 키값(파일명)을 반환한다.
     * @return
     */
    public static String[] getPropertyNames(){
        return reader.getPropertyNames();
    }

    /**
     * 특정 문자열로 끝나는 프로퍼티 키값(파일명)을 반환한다.
     * @return
     */
    public static String[] getPropertyNameEndsWith(String endsWith){
        return reader.getPropertyNameEndsWith(endsWith);
    }

}
