package com.cgy.hupu.injector;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;
import javax.inject.Singleton;

/**
 * Created by cgy on 2018/10/11  9:26
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {

}
