package name.yalsooni.genius.common.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;

/**
 * properties 파일에 한글이 포함되어있는 경우 사용
 * @author Lee Seul Gi
 */
public class PropertyResourceBundleReader {

    private Map<String, PropertyResourceBundle> resourceBundleRepository;

    public PropertyResourceBundleReader() {
        resourceBundleRepository = new HashMap<String, PropertyResourceBundle>();
    }

    public PropertyResourceBundle read(String name, String path) throws Exception {

        if (resourceBundleRepository.get(name) != null) {
            throw new Exception("이미 존재하는 프로퍼티 이름입니다. : " + name);
        }

        try {
            File propertiesFile = new File(path);
            FileReader fr = new FileReader(propertiesFile);

            PropertyResourceBundle resourceBundle = new PropertyResourceBundle(fr);

            this.resourceBundleRepository.put(name, resourceBundle);

            return resourceBundle;
        } catch (FileNotFoundException e) {
            throw new Exception("파일이 존재하지 않습니다. : " + name);
        }
    }

    public String get(String name, String key) {
        return this.resourceBundleRepository.get(name).getString(key);
    }

    public PropertyResourceBundle getResourceBundle(String name) {
        return this.resourceBundleRepository.get(name);
    }
}
