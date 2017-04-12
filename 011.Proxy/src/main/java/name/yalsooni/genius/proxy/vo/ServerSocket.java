package name.yalsooni.genius.proxy.vo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by ijyoon on 2017. 4. 12..
 */
public class ServerSocket {

    private Socket socket;

    public ServerSocket(Socket socket) {
        this.socket = socket;
    }

    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }
}
