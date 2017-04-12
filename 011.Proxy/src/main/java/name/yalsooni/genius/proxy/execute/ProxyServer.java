package name.yalsooni.genius.proxy.execute;


import name.yalsooni.genius.common.definition.Service;
import name.yalsooni.genius.common.definition.annotation.Delegate;
import name.yalsooni.genius.common.definition.annotation.Entry;
import name.yalsooni.genius.proxy.io.Proxy;

/**
 * proxy server.
 * Created by ijyoon on 2017. 4. 12..
 */
@Delegate(projectID = "011", serviceType = Service.RUNTIME)
public class ProxyServer {

    /**
     * io 방식의 프록시 서버
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

    public static void main(String[] args){
        ProxyServer ps = new ProxyServer();
        ps.ioBind(7070, "localhost", 8080);
    }
}
