package name.yalsooni.genius.core.boothelper.util;

import java.io.*;
import java.util.*;

/**
 * 프로퍼티 파일을 읽고 보관한다.
 * @author yoon
 */
public class PropertyReader {

    private Map<String, Properties> propertiesRepository;

    public PropertyReader() {
        propertiesRepository = new HashMap<String, Properties>();
    }

    /**
     * 프로퍼티 파일을 읽고 레파지토리에 보관한다.
     * @param name
     * @param path
     * @return
     * @throws Exception
     */
    public Properties read(String name, String path) throws Exception {
        return this.read(name, new File(path));
    }

    /**
     * 프로퍼티 파일을 읽고 레파지토리에 보관한다.
     * @param name
     * @param path
     * @return
     * @throws Exception
     */
    public Properties read(String name, File path) throws Exception {
        InputStream inStream = new FileInputStream(path);
        return read(name, inStream);
    }

    public Properties read(String name, InputStream inStream) throws Exception {

        if (propertiesRepository.get(name) != null) {
            throw new Exception("already property name : " + name);
        }

        Properties properties = null;

        try {
            properties = new Properties();
            properties.load(inStream);

            this.propertiesRepository.put(name, properties);

        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }

        return properties;
    }

    /**
     * 특정 디렉토리의 프로퍼티 파일을 읽어 보관한다.
     * @param path 읽을 디렉토리의 위치.
     * @param extName 읽을 파일의 확장자.
     * @param includeSubDir 하위 디렉토리 포함 여부.
     * @throws Exception
     */
    public void readDirectory(String path, String extName, boolean includeSubDir) throws Exception {
        ExtFileSearch ext = new ExtFileSearch(extName);
        List<File> targetFiles = ext.getFile(path, includeSubDir);

        Iterator<File> list = targetFiles.iterator();
        File targetFile;
        String name;

        try {
            while(list.hasNext()){
                targetFile = list.next();
                name = targetFile.getName();
                name = name.substring(0, name.lastIndexOf(extName));
                this.read(name, targetFile);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 특정 프로퍼티의 값을 반환한다.
     * @param name 프로퍼티 이름.
     * @param key 해당 프로퍼티의 키.
     * @return 지정된 프로퍼티의 값.
     */
    public String get(String name, String key) {
        return this.propertiesRepository.get(name).getProperty(key);
    }

    /**
     * 특정 프로퍼티를 반환한다.
     * @param name 프로퍼티 이름.
     * @return 프로퍼티.
     */
    public Properties getProperties(String name) {
        return this.propertiesRepository.get(name);
    }

    /**
     * 보관중인 프로퍼티 key값(파일명)을 반환한다.
     * @return 보관중인 프로퍼티 key값.
     */
    public String[] getPropertyNames(){
        Iterator<String> keyIter = propertiesRepository.keySet().iterator();
        List<String> keyList = new ArrayList<String>();

        while (keyIter.hasNext()){
            keyList.add(keyIter.next());
        }

        return keyList.toArray(new String[keyList.size()]);
    }

    /**
     * 파라미터로 시작하는 프로퍼티 key값을 반환한다.
     * @param startsWith
     * @return
     */
    public String[] getPropertyNameStartsWith(String startsWith){
        String[] list = this.getPropertyNames();

        List<String> keyList = new ArrayList<String>();

        for(String name : list){
            if(name.startsWith(startsWith)){
                keyList.add(name);
            }
        }

        return keyList.toArray(new String[keyList.size()]);
    }

    /**
     * 파라미터로 끝나는 프로퍼티 key값을 반환한다.
     * @param endsWith
     * @return
     */
    public String[] getPropertyNameEndsWith(String endsWith){
        String[] list = this.getPropertyNames();
        List<String> keyList = new ArrayList<String>();

        for(String name : list){
            if(name.endsWith(endsWith)){
                keyList.add(name);
            }
        }

        return keyList.toArray(new String[keyList.size()]);
    }

}
