package top.poul.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 约束对象的属性是否可空
 * @author Poul.Y
 * @since 2017年11月7日 下午1:58:31
 */
@Target(value={ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
    
    String msg() default ""; 
    
}
