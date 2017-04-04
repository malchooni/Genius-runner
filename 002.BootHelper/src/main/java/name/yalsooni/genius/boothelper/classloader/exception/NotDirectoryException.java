package name.yalsooni.genius.boothelper.classloader.exception;

/**
 * Created by ijyoon on 2015. 12. 16..
 */
public class NotDirectoryException extends Exception{

    public NotDirectoryException(String dirPath){
        super("'" + dirPath + "' is not directory.");
    }
}
