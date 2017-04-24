package name.yalsooni.genius.paperfactory.execute;

import name.yalsooni.genius.common.definition.Service;
import name.yalsooni.genius.common.definition.annotation.Delegate;
import name.yalsooni.genius.common.definition.annotation.Entry;
import name.yalsooni.genius.paperfactory.definition.PropertyName;
import name.yalsooni.genius.util.Log;

/**
 * 문자 치환 유틸.
 * 엑셀파일을 기반으로 특정 탬플릿 파일의 단어를 치환시켜 준다.
 *
 * Modified by ijyoon on 2017. 4. 19..
 * Created by yoon-iljoong on 2015. 11. 2..
 */
@Delegate(projectID = "010", serviceType = Service.UTIL)
public class PaperFactory {

    @Entry(arguments = {"PaperFactory property file path : String"})
    public void replace(String propertyFilePath){
        PaperFactoryService pfs = new PaperFactoryService(propertyFilePath);

        try {
            pfs.initialize();
        } catch (Exception e) {
            Log.console(e);
            return;
        }

        try {
            pfs.execute();
        } catch (Exception e) {
            Log.console(e);
            return;
        }


    }

    @Entry
    public void replaceDefault(){
        this.replace(PropertyName.DEFAULT_PROPERTIES_FILE_PATH);
    }

    public static void main(String[] args){
        PaperFactory pf = new PaperFactory();

        if( args.length < 1 || args[0] == null ){
            pf.replaceDefault();
        }else if(args.length == 1){
            pf.replace(args[0]);
        }
    }
}
