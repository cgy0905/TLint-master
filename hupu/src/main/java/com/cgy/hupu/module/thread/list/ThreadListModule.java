package com.cgy.hupu.module.thread.list;

import dagger.Module;
import dagger.Provides;

/**
 * @author cgy
 * @description 帖子列表的module
 * @date 2019/5/16 17:47
 */
@Module
public class ThreadListModule {

    private String fid;

    public ThreadListModule(String fid) {
        this.fid = fid;
    }

    @Provides
    String provideFid() {
        return fid;
    }
}
