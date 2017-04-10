package springmvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 次注解将用该注解的bean 变量 , 变量里边的实例注入进去
 * @author yuezh2   2017年4月7日 下午3:18:56
 *
 */
@Target(value=ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Qualifier {

	String value() default "";
	
}
