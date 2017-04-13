package name.yalsooni.genius.proxy.process;

import name.yalsooni.genius.proxy.definition.Code;
import name.yalsooni.genius.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * Created by ijyoon on 2017. 4. 13..
 */
public class DataPipe extends Thread {

    private String direction;
    private InputStream is;
    private OutputStream os;

    private Object monitor;

    public DataPipe(Object monitor, String direction, InputStream is, OutputStream os) {
        super(direction);
        this.direction = direction;
        this.is = is;
        this.os = os;
        this.monitor = monitor;
    }

    public void setParentThreadName(String parentThreadName) {
        this.setName(parentThreadName + "-" + direction);
    }

    @Override
    public void run() {

        boolean availableStream;

        try {
            while (true) {
                availableStream = DataPipeUtil.dataProcessing(direction, is, os);
                if (!availableStream) {
                    break;
                }
            }
        }catch (SocketException se){
            Log.console("[" + this.getName() + "] " + Code.G_011_0005 + " : " + se.getMessage());
        }catch (IOException ie){
            Log.console("[" + this.getName() + "] " + Code.G_011_0004, ie);
        }finally {
            synchronized (monitor){
                monitor.notify();
            }
        }
    }

    public void close(){
        try {if( is != null) is.close();} catch (IOException e1) {}
        try {if( os != null) os.close();} catch (IOException e1) {}
    }
}
