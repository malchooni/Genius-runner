package name.yalsooni.genius.proxy.process;

import name.yalsooni.genius.proxy.definition.Code;
import name.yalsooni.genius.proxy.definition.Direction;
import name.yalsooni.genius.proxy.exception.ClientIOException;
import name.yalsooni.genius.util.Log;

import java.io.IOException;
import java.net.Socket;

/**
 * 스트림 데이터 처리기
 * Created by ijyoon on 2017. 4. 12..
 */
public class DataPassWorker implements Runnable {

    private Socket serverSocket;
    private Socket clientSocket;

    private DataPipe outBound;
    private DataPipe inBound;

    private Object monitor = new Object();

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
            this.clientSocket   = new Socket(targetIP, targetPort);
            this.outBound       = new DataPipe(monitor, Direction.RECEIVED, serverSocket.getInputStream(), clientSocket.getOutputStream());
            this.inBound        = new DataPipe(monitor, Direction.SENT, clientSocket.getInputStream(), serverSocket.getOutputStream());
        }catch (IOException ioe){
            throw new ClientIOException(Code.G_011_0002, ioe);
        }
    }

    /**
     * accept socket을 기반으로 타켓으로 정의된 서버에 소켓 연결.
     * 연결 후 패킷에 대한 정보를 출력 한다.
     */
    public void run() {
        DataPassWorkUtil.setParentThreadName(this, Thread.currentThread().getName());
        DataPassWorkUtil.start(this);

        try {
            DataPassWorkUtil.waitWorker(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DataPassWorkUtil.dataPipeClose(this);

        Log.console("["+Thread.currentThread().getName()+"] Thread Done.");
    }

    /**
     * 송신 파이프 반환
     * @return
     */
    public DataPipe getOutBound() {
        return outBound;
    }

    /**
     * 수신 파이프 반환
     * @return
     */
    public DataPipe getInBound() {
        return inBound;
    }

    /**
     * 모니터 객체 반환
     * @return
     */
    public Object getMonitor() {
        return monitor;
    }

    /**
     * 로컬 서버 소켓 반환.
     * @return
     */
    public Socket getServerSocket() {
        return serverSocket;
    }

    /**
     * 클라이언트 소켓 반환
     * @return
     */
    public Socket getClientSocket() {
        return clientSocket;
    }
}
