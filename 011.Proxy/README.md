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
~~~

 * 바로 실행 만들기 
 
 argument 값을 shell script에 미리 입력한다.
 
 ex) 011 ioBind 7070,localhost,8080
 
 genius.sh OR genius.cmd 수정.

~~~
java -DMETA_PATH=../property/meta.data \
-cp ../lib/genius-commons/genius001-util.jar\
:../lib/genius-commons/genius002-boothelper.jar \
name.yalsooni.genius.boothelper.Run 011 ioBind 7070,localhost,8080
~~~
