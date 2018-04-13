package name.yalsooni.genius.core.boothelper.classloader.util;



import name.yalsooni.genius.core.boothelper.util.Log;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ijyoon on 2016. 4. 10..
 */
public class ClassListUtil {

    public static URL[] getURLArray(List<File> fileList){

        URL url;
        List<URL> urlList = new ArrayList<URL>();

        for(File file : fileList){
            try {
                url = file.toURI().toURL();
                urlList.add(url);
                Log.console(url.toString());

            } catch (MalformedURLException e) {
                Log.console("File name : "+ file.getName(),e);
                continue;
            }
        }

        return urlList.toArray(new URL[urlList.size()]);
    }
}
