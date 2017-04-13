package name.yalsooni.genius.proxy.execute;


import name.yalsooni.genius.common.definition.Service;
import name.yalsooni.genius.common.definition.annotation.Delegate;
import name.yalsooni.genius.common.definition.annotation.Entry;
import name.yalsooni.genius.proxy.io.Proxy;
import name.yalsooni.genius.util.Log;

/**
 * 011 proxy server.
 * Created by ijyoon on 2017. 4. 12..
 */
@Delegate(projectID = "011", serviceType = Service.RUNTIME)
public class ProxyServer {

    /**
     * 지니어스 어노테이션.
     * 011 : io 방식의 프록시 서버
     * @param sourcePort
     * @param targetIP
     * @param targetPort
     */
    @Entry(arguments = {"listen port : int","target IP : String","target port : int"})
    public void ioBind(int sourcePort, String targetIP, int targetPort){
        Proxy proxy = new Proxy(sourcePort, targetIP, targetPort);
        try {
            proxy.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        proxy.execute();
    }

    /**
     * 실행
     * @param args
     */
    public static void main(String[] args){

        if(args.length !=3){
            Log.console("파라미터 값을 입력하세요.");
            Log.console("1. listen port");
            Log.console("2. target IP");
            Log.console("3. target Port");
            return;
        }

        ProxyServer ps = new ProxyServer();
        ps.ioBind(Integer.parseInt(args[0]),args[1],Integer.parseInt(args[2]));
    }
}
