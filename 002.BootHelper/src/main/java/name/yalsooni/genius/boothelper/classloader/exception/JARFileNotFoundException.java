package name.yalsooni.genius.boothelper.classloader.exception;

/**
 * Created by ijyoon on 2015. 12. 16..
 */
public class JARFileNotFoundException extends Exception{

    public JARFileNotFoundException(String dirPath){
        super("JAR file not found in '"+dirPath+"'");
    }
}
