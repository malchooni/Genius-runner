package name.yalsooni.genius.proxy.process;

import name.yalsooni.genius.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 데이터 처리기 공통 기능
 * Created by ijyoon on 2017. 4. 13..
 */
public class DataPassWorkUtil {

    private static final int PACKETSIZE = 2048;

    /**
     * 데이터 전송 및 출력
     * @param direction
     * @param is
     * @param os
     * @return available stream
     * @throws IOException
     */
    public static boolean dataProcessing (String direction, InputStream is, OutputStream os) throws IOException {

        String data = streamPass(is, os);

        if(data == null){
            return false;
        }

        Log.console("["+Thread.currentThread().getName() + "] ************ "+direction+" packet ************");
        Log.console("["+Thread.currentThread().getName() + "]\n"+data);
        Log.console("["+Thread.currentThread().getName() + "] *****************************************");

        return true;
    }


    /**
     * 인풋스트림을 아웃풋스트림으로 패스
     * @param is
     * @param os
     * @return
     * @throws IOException
     */
    private static String streamPass(InputStream is, OutputStream os) throws IOException {
        StringBuilder packetString = new StringBuilder();
        int readIdx = 0;

        byte[] packet = new byte[PACKETSIZE];

        do{
            readIdx = is.read(packet);

            if(readIdx == -1){
                return null;
            }

            os.write(packet, 0, readIdx);
            packetString.append(new String(packet, 0, readIdx));
        }while( readIdx == PACKETSIZE);

        os.flush();

        return packetString.toString();
    }
}
