package com.cgy.hupu.module.userprofile;

import dagger.Module;
import dagger.Provides;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/20 13:51
 */
@Module
public class UserProfileModule {

    private String uid;

    public UserProfileModule(String uid) {
        this.uid  = uid;
    }


    @Provides
    String provideUid() {
        return uid;
    }
}
