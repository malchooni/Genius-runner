package name.yalsooni.genius.runner.definition.property;


import name.yalsooni.boothelper.util.reader.PropertyReader;
import name.yalsooni.genius.runner.definition.ErrCode;
import name.yalsooni.genius.runner.execute.GeniusExecutor;

/**
 * 지니어스 속성 값
 * Created by yoon-iljoong on 2016. 10. 31..
 */
public class GeniusProperties {

    /**
     * 어노테이션 라이브러리 경로
     */
    private String annotationLibRootPath;

    public GeniusProperties() throws Exception {
        PropertyReader reader = new PropertyReader();

        String genius_property = System.getProperty("GENIUS_META", "../property/genius.meta");
        reader.read(GeniusExecutor.GENIUS, genius_property);
        java.util.Properties properties = reader.getProperties(GeniusExecutor.GENIUS);

        if(properties == null){
            throw new Exception(ErrCode.GR_I001);
        }

        String libRootPath = properties.getProperty("ANNOTATION.LIB.ROOT.PATH");
        if( libRootPath == null || libRootPath.length() < 1){
            throw new Exception(ErrCode.GR_I002);
        }
        this.annotationLibRootPath = libRootPath;
    }

    /**
     * 지니어스 jar 경로 반환
     * @return annotationLibRootPath
     */
    public String getAnnotationLibRootPath() {
        return annotationLibRootPath;
    }
}
