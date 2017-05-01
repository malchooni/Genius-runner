# 011.Proxy - Service.RUNTIME
 
 송신 수신 패킷을 Sysmtem.out 으로 출력한다.
 
## 1.버전 정보

 *  v1.0 - 2017/04/14 최초 배포
 * [Download](https://github.com/yalsooni/Genius/releases/tag/v1.0)
 
 
## 2.실행하기
 
 * 클래스 참조
 
> [name.yalsooni.genius.proxy.execute.ProxyServer.java](https://github.com/yalsooni/Genius/blob/master/011.Proxy/src/main/java/name/yalsooni/genius/proxy/execute/ProxyServer.java)

 * 바이너리 실행
~~~
$GENIUS_HOME/bin/genius.sh OR genius.cmd 실행
Project ID : 011
Entry List 
 - ioBind(int listen port, String target, int target port)
~~~

 * 바로 실행 만들기 
 
~~~
$GENIUS_HOME/bin/genius.sh 011 ioBind 7070,localhost,8080
$GENIUS_HOME/bin/genius.cmd 011 ioBind 7070,localhost,8080
~~~
