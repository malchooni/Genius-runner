package name.yalsooni.genius.runner.definition.property;


import name.yalsooni.genius.runner.definition.ErrCode;

/**
 * 지니어스 속성 값
 * Created by yoon-iljoong on 2016. 10. 31..
 */
public class GeniusProperties {

    private static String SYS_LIB_PATH = "GeniusLibPath";

    /**
     * 어노테이션 라이브러리 경로
     */
    private String annotationLibRootPath;

    public GeniusProperties() throws Exception {
        annotationLibRootPath = System.getProperty(SYS_LIB_PATH, "../lib-genius");
        if( annotationLibRootPath == null || annotationLibRootPath.length() < 1){
            throw new Exception(ErrCode.GR_I001);
        }
    }

    /**
     * 지니어스 jar 경로 반환
     * @return annotationLibRootPath
     */
    public String getAnnotationLibRootPath() {
        return annotationLibRootPath;
    }
}
