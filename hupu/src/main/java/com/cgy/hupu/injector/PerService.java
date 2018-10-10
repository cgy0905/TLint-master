package com.cgy.hupu.injector;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.inject.Scope;

/**
 * Created by cgy on 2018/10/10  16:25
 */
@Scope
@Retention(RUNTIME)
public @interface PerService {
}
