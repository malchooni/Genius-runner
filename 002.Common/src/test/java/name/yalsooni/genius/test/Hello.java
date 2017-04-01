package name.yalsooni.genius.test;

import genius.rulebreaker.definition.Service;
import genius.rulebreaker.definition.annotation.Delegate;
import genius.rulebreaker.definition.annotation.Entry;

/**
 * 테스트 서비스
 * Created by yoon-iljoong on 2016. 11. 3..
 */
@Delegate(projectID = "098", serviceType = Service.UTIL)
public class Hello {

    @Entry
    public void hi(String name){
        System.out.println(name + " hello.");
    }
}
