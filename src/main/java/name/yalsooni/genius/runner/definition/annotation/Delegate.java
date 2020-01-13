package name.yalsooni.genius.runner.definition.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 서비스 제공 딜리게이트 클래스임을 정의한다.
 * Created by yoon-iljoong on 2016. 10. 31..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Delegate {
    /**
     * 프로젝트 아이디
     */
    String projectID();

    /**
     * 서비스 타입
     * genius.Service 참조
     */
    String serviceType();
}
