package name.yalsooni.genius.common.definition.property;

import genius.rulebreaker.execute.GeniusExecutor;
import name.yalsooni.common.util.support.PropertyReader;

/**
 * 지니어스 속성 값
 * Created by yoon-iljoong on 2016. 10. 31..
 */
public class GeniusProperties {
    private PropertyReader reader = null;

    private final String JAVA_OPTION_NAME = "GENIUS_META";

    /**
     * 지니어스 기본 프로퍼티 파일 경로
     */
    private final String GENIUS_DEFAULT_PROPERTY_FILEPATH = "../property/genius.meta";
    private final String GENIUS_DEFAULT_LIB_PATH = "../lib/genius";

    /**
     * 어노테이션 라이브러리 경로
     */
    private final String ANNOTATION_LIB_ROOT_PATH = "ANNOTATION.LIB.ROOT.PATH";
    private String annotationLibRootPath = null;

    public GeniusProperties() throws Exception {
        this.reader = new PropertyReader();

        String genius_property = System.getProperty(JAVA_OPTION_NAME);

        if(genius_property != null){
            this.reader.read(GeniusExecutor.GENIUS, genius_property);
            java.util.Properties properties = this.reader.getProperties(GeniusExecutor.GENIUS);
            setAnnotationLibRootPath(properties.getProperty(ANNOTATION_LIB_ROOT_PATH));
        }else{
            setAnnotationLibRootPath(GENIUS_DEFAULT_LIB_PATH);
        }
    }

    public String getAnnotationLibRootPath() {
        return annotationLibRootPath;
    }

    public void setAnnotationLibRootPath(String annotationLibRootPath) {
        this.annotationLibRootPath = annotationLibRootPath;
    }
}
