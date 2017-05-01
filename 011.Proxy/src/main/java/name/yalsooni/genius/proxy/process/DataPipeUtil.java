package name.yalsooni.genius.proxy.process;

import name.yalsooni.genius.proxy.definition.Code;
import name.yalsooni.genius.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 데이터 처리기 공통 기능
 * Created by ijyoon on 2017. 4. 13..
 */
public class DataPipeUtil {

    private static final int PACKETSIZE = 4096;

    /**
     * 인풋스트림을 아웃풋스트림으로 패스
     * @param is
     * @param os
     * @return
     * @throws IOException
     */
    public static int streamPass(InputStream is, OutputStream os) throws IOException {
        int readSize = 0;
        byte[] packet = new byte[PACKETSIZE];

        while( (readSize = is.read(packet)) != -1){

            if(readSize == 0){
                Log.console(Code.G_011_0006);
                break;
            }

            os.write(packet, 0, readSize);
            Log.console("["+Thread.currentThread().getName() + "]\n" + new String(packet,0,readSize));
            os.flush();
        }

        return readSize;
    }
}
