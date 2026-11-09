package javaSE.Annotations.Examples.ex2.ex1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TransactionLimit {
    double limit() default 1000.0;
    String description() default "Standard transaction limit";
}