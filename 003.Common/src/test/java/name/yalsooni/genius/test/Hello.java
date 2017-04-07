package name.yalsooni.genius.test;


import name.yalsooni.genius.common.definition.Service;
import name.yalsooni.genius.common.definition.annotation.Delegate;
import name.yalsooni.genius.common.definition.annotation.Entry;

/**
 * 테스트 서비스ø
 * Created by yoon-iljoong on 2016. 11. 3..
 */
@Delegate(projectID = "098", serviceType = Service.UTIL)
public class Hello {

    @Entry(arguments = {"your name"})
    public void hi(String name){
        System.out.println(name + " hello.");
    }
}
