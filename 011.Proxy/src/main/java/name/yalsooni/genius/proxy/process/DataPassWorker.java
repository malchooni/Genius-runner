package name.yalsooni.genius.proxy.process;

import name.yalsooni.genius.proxy.definition.Code;
import name.yalsooni.genius.proxy.exception.ClientIOException;
import name.yalsooni.genius.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by ijyoon on 2017. 4. 12..
 */
public class DataPassWorker implements Runnable {

    private Socket serverSocket;
    private Socket clientSocket;

    public DataPassWorker(Socket serverSocket, String targetIP, int targetPort) throws ClientIOException {
        this.serverSocket = serverSocket;
        try{
            this.clientSocket = new Socket(targetIP, targetPort);
        }catch (IOException ioe){
            throw new ClientIOException(Code.G_011_0002, ioe);
        }
    }

    public void run() {

        StringBuilder receivePacket = new StringBuilder();
        StringBuilder sendPacket = new StringBuilder();

        InputStream serverInputStream = null;
        OutputStream clientOutputStream = null;

        InputStream clientInputStream = null;
        OutputStream serverOutputStream = null;

        try {
            serverInputStream = serverSocket.getInputStream();
            clientOutputStream = clientSocket.getOutputStream();

            clientInputStream = clientSocket.getInputStream();
            serverOutputStream = serverSocket.getOutputStream();

            byte[] packet = new byte[2048];
            int readIdx = 0;
            boolean running = true;

            while(running){
                do{
                    readIdx = serverInputStream.read(packet);

                    if(readIdx == -1){
                        running = false;
                        break;
                    }

                    clientOutputStream.write(packet, 0, readIdx);
                    receivePacket.append(new String(packet, 0, readIdx));
                }while( readIdx == 2048);

                if(!running){
                    break;
                }

                clientOutputStream.flush();

                Log.console(" -- received packet --");
                Log.console(receivePacket.toString());
                Log.console(" -- --------------- --");

                do{
                    readIdx = clientInputStream.read(packet);

                    if(readIdx == -1){
                        running = false;
                        break;
                    }

                    serverOutputStream.write(packet, 0, readIdx);
                    sendPacket.append(new String(packet, 0, readIdx));
                }while( readIdx == 2048 );

                if(!running){
                    break;
                }

                serverOutputStream.flush();

                Log.console(" -- sent packet --");
                Log.console(sendPacket.toString());
                Log.console(" -- ----------- --");
            }

        } catch (IOException e) {
            e.printStackTrace();
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
