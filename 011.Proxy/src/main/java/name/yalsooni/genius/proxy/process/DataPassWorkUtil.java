package name.yalsooni.genius.proxy.process;

import java.io.IOException;

/**
 * 데이터 처리기 공통 기능
 * Created by ijyoon on 2017. 4. 13..
 */
public class DataPassWorkUtil {

    /**
     * 부모 스레드 이름 설정
     * @param dataPassWorker
     * @param currentThreadName
     */
    public static void setParentThreadName(DataPassWorker dataPassWorker, String currentThreadName){
        dataPassWorker.getOutBound().setParentThreadName(currentThreadName);
        dataPassWorker.getInBound().setParentThreadName(currentThreadName);
    }

    /**
     * 송수신 파이프 스레드 시작
     * @param dataPassWorker
     */
    public static void start(DataPassWorker dataPassWorker){
        dataPassWorker.getOutBound().start();
        dataPassWorker.getInBound().start();
    }

    /**
     * monitor wait
     * @param dataPassWorker
     * @throws InterruptedException
     */
    public static void waitWorker(DataPassWorker dataPassWorker) throws InterruptedException {
        Object monitor = dataPassWorker.getMonitor();
        synchronized (monitor){
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                throw e;
            }
        }
    }

    /**
     * 송수신 파이프 닫기
     * @param dataPassWorker
     */
    public static void dataPipeClose(DataPassWorker dataPassWorker){
        dataPassWorker.getOutBound().close();
        dataPassWorker.getInBound().close();
    }

    /**
     * 서버, 클라이언트 소켓 닫기
     * @param dataPassWorker
     */
    public static void socketClose(DataPassWorker dataPassWorker){
        try { dataPassWorker.getServerSocket().close();} catch (IOException e) {}
        try { dataPassWorker.getClientSocket().close();} catch (IOException e) {}
    }
}
