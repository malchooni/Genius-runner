package name.yalsooni.genius.proxy.exception;

import java.io.IOException;

/**
 * 송신 IOException
 * Created by ijyoon on 2017. 4. 12..
 */
public class ClientIOException extends IOException {

    public ClientIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
