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
     * 데이터 송수신 객체 생성
     * @param serverSocket 프록시 리슨 소켓
     * @param targetIP 대상 아이피
     * @param targetPort 대상 포트
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

    /**
     * accept socket을 기반으로 타켓으로 정의된 서버에 소켓 연결.
     * 연결 후 패킷에 대한 정보를 출력 한다.
     */
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
            Log.console("["+Thread.currentThread().getName() + "] " +Code.G_011_0004, e);
        } finally {
            try {serverInputStream.close();} catch (IOException e1) {}
            try {clientOutputStream.close();} catch (IOException e1) {}
            try {clientInputStream.close();} catch (IOException e1) {}
            try {serverOutputStream.close();} catch (IOException e1) {}

            try {serverSocket.close();} catch (IOException e1) {}
            try {clientSocket.close();} catch (IOException e1) {}

            Log.console("["+Thread.currentThread().getName() + "] Thread Done.");
        }
    }
}
