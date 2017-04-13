package name.yalsooni.genius.proxy.process;

import name.yalsooni.genius.proxy.definition.Code;
import name.yalsooni.genius.proxy.definition.Direction;
import name.yalsooni.genius.proxy.exception.ClientIOException;
import name.yalsooni.genius.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 스트림 데이터 처리기
 * Created by ijyoon on 2017. 4. 12..
 */
public class DataPassWorker implements Runnable {

    private Socket serverSocket;
    private Socket clientSocket;

    /**
     *
     * @param serverSocket
     * @param targetIP
     * @param targetPort
     * @throws ClientIOException
     */
    public DataPassWorker(Socket serverSocket, String targetIP, int targetPort) throws ClientIOException {
        this.serverSocket = serverSocket;
        try{
            this.clientSocket = new Socket(targetIP, targetPort);
        }catch (IOException ioe){
            throw new ClientIOException(Code.G_011_0002, ioe);
        }
    }

    public void run() {

        InputStream serverInputStream = null;
        OutputStream clientOutputStream = null;

        InputStream clientInputStream = null;
        OutputStream serverOutputStream = null;

        try {
            serverInputStream = serverSocket.getInputStream();
            clientOutputStream = clientSocket.getOutputStream();

            clientInputStream = clientSocket.getInputStream();
            serverOutputStream = serverSocket.getOutputStream();

            boolean availableStream = true;

            while(true){
                availableStream = DataPassWorkUtil.dataProcessing(Direction.RECEIVED, serverInputStream, clientOutputStream);
                if(!availableStream){
                    break;
                }

                availableStream = DataPassWorkUtil.dataProcessing(Direction.SENT, clientInputStream, serverOutputStream);
                if(!availableStream){
                    break;
                }
            }

        } catch (IOException e) {
            Log.console(Code.G_011_0004, e);
        } finally {
            try {serverInputStream.close();} catch (IOException e1) {}
            try {clientOutputStream.close();} catch (IOException e1) {}
            try {clientInputStream.close();} catch (IOException e1) {}
            try {serverOutputStream.close();} catch (IOException e1) {}

            try {serverSocket.close();} catch (IOException e1) {}
            try {clientSocket.close();} catch (IOException e1) {}

            Log.console("Thread Done.");
        }
    }
}
