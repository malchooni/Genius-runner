package name.yalsooni.genius.runner.definition.property;


import name.yalsooni.boothelper.util.reader.PropertyReader;
import name.yalsooni.genius.runner.execute.GeniusExecutor;

/**
 * 지니어스 속성 값
 * Created by yoon-iljoong on 2016. 10. 31..
 */
public class GeniusProperties {
    private PropertyReader reader = null;

    /**
     * 자바 시스템프로퍼티명
     */
    private final String JAVA_OPTION_NAME = "GENIUS_META";

    /**
     * 지니어스 기본 프로퍼티 파일 경로
     */
    private final String GENIUS_DEFAULT_PROPERTY_FILEPATH = "../property/genius.meta";
    private final String GENIUS_DEFAULT_LIB_PATH = "../lib-genius";

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

    /**
     * 지니어스 jar 경로 반환
     * @return
     */
    public String getAnnotationLibRootPath() {
        return annotationLibRootPath;
    }

    /**
     * 지너이스 jar 경로 입력
     * @param annotationLibRootPath
     */
    public void setAnnotationLibRootPath(String annotationLibRootPath) {
        this.annotationLibRootPath = annotationLibRootPath;
    }
}
