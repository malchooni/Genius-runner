package name.yalsooni.genius.test;

import genius.rulebreaker.definition.Service;
import genius.rulebreaker.definition.annotation.Delegate;
import genius.rulebreaker.definition.annotation.Entry;

/**
 * Genius 2.0 Math Util Service
 * Created by yoon-iljoong on 2016. 10. 31..
 */
@Delegate(projectID = "099", serviceType = Service.UTIL)
public class Math {

    /**
     * 더하기
     * @param a
     * @param b
     */
    @Entry
    public void sum ( int a, int b ){
        System.out.println(a + " + " + b + " = " + (a+b));
    }

    /**
     * 빼기
     * @param a
     * @param b
     */
    @Entry
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
