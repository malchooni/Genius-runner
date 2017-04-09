# Genius

단순 반복 작업이나 업무의 효율화를 위한 유틸 제작 프로젝트이다.


## 공통 모듈 설명 및 의존관계

![모듈 의존관계](https://github.com/yalsooni/Genius/blob/master/op/img/readme1.png)

 * `001.Util` - 지니어스에서 자주 사용하는 클래스 모음 
 * `002.BootHelper` - 사용자정의 클래스로더를 구현한 모듈, 특정 라이브러리 디렉토리를 클래스로드.
 * `003.Common` - 지니어스 기반 모듈, 인터페이스 및 어노테이션 정의, 실행 모듈
 
## 어노테이션 정의로 유틸 만들기

 ### Delegate 어노테이션 정의
 
 클래스에 Delegate 어노테이션을 정의하여 해당 클래스가 지니어스 유틸 클래스임을 선언한다.
 
 #### Delegate 어노테이션 속성
 
 > @Delegate(projectID = "099", serviceType = Service.UTIL)
 
 * projectID : 해당 프로젝트 아이디.
 
 ~~~
 예 ) 099
 ~~~
 
 * serviceType : 해당 클래스가 1회성 유틸 서비스인지, 런타임 서비스인지 정의한다.
 
 ~~~
 예) Service.Util
 예) Service.Runtime
 name.yalsooni.genius.common.definition.Service 인터페이스 참조.
 ~~~
 
 ### Entry 어노테이션 정의
 
 Delegate는 서비스의 대표 이름이 되며 기능이 되는 메소드에는 Entry 어노테이션을 선언한다. 한 클래스에 여 러개의 Entry를 사용할 수 있다.
 
 #### Entry 어노테이션 속성
 
 > @Entry(arguments = {"value 1", "value 2"})
 
 * arguments : 인자값들의 설명을 기록한다.
  
  ~~~
  예 ) {"value 1", "value 2"}
  ~~~
 
## 구현 예제

~~~java
package name.yalsooni.genius.test;


import name.yalsooni.genius.common.definition.Service;
import name.yalsooni.genius.common.definition.annotation.Delegate;
import name.yalsooni.genius.common.definition.annotation.Entry;

/**
 * Math
 * Created by yoon-iljoong on 2016. 10. 31..
 */
@Delegate(projectID = "099", serviceType = Service.UTIL)
public class Math {

    /**
     * 더하기
     * @param a
     * @param b
     */
    @Entry(arguments = {"value 1", "value 2"})
    public void sum ( int a, int b ){
        System.out.println(a + " + " + b + " = " + (a+b));
    }

    /**
     * 빼기
     * @param a
     * @param b
     */
    @Entry(arguments = {"value 1", "value 2"})
    public void minus ( int a, int b ){
        System.out.println(a + " - " + b + " = " + (a-b));
    }

    /**
     * print
     */
    @Entry
    public void print(){
        System.out.println("Math Util Service.");
    }
}
~~~
