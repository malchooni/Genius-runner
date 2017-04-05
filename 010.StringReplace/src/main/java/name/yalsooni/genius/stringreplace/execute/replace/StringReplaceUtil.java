package name.yalsooni.genius.stringreplace.execute.replace;


import name.yalsooni.genius.common.definition.Service;
import name.yalsooni.genius.common.definition.annotation.Delegate;
import name.yalsooni.genius.common.definition.annotation.Entry;
import name.yalsooni.genius.stringreplace.replace.StringReplace;
import name.yalsooni.genius.stringreplace.replace.vo.StringReplaceInfo;
import name.yalsooni.genius.util.Log;

/**
 * Created by yoon-iljoong on 2016. 11. 3..
 */
@Delegate(projectID = "011", serviceType = Service.UTIL)
public class StringReplaceUtil {

    @Entry
    public void replaceDefault(){
        this.replace("../property/011_stringreplace.properties");
    }

    @Entry
    public void replace(String propertyPath){
        long startTime = System.currentTimeMillis();

        try{
            StringReplaceInfo info = new StringReplaceInfo();
            info.setPropertyFilePath(propertyPath);

            Log.console(" ** StringPattern Start. **  ");

            StringReplace sp = new StringReplace(info);
            sp.initialize();
            Log.console(" StringPattern initialize done.");

            sp.execute();
            Log.console(" StringPattern execute done.");

        }catch(Exception e){
            Log.console(e.getMessage(), e);
        }finally{
            Log.console(" ** StringPattern End. **  Elasped Time : " + (System.currentTimeMillis() - startTime) + " ms");
        }
    }
}
