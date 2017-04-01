package name.yalsooni.genius.common.definition.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 제공 엔트리(메소드)를 선언한다.
 * Created by yoon-iljoong on 2016. 10. 31..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Entry {
}
