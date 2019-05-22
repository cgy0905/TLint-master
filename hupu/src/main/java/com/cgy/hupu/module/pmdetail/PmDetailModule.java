package com.cgy.hupu.module.pmdetail;

import dagger.Module;
import dagger.Provides;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/22 9:46
 */
@Module
public class PmDetailModule {

    private String uid;

    public PmDetailModule(String uid) {
        this.uid = uid;
    }

    @Provides
    String provideUid() {
        return uid;
    }
}
