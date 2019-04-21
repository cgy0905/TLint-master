package com.example.retrofit_proxy;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by cgy on 2018/10/31.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface GET {
    String value() default "";
}
