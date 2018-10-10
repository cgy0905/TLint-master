package com.cgy.hupu;

import android.app.Application;

import com.facebook.common.util.ByteConstants;

/**
 * Created by cgy on 2018/10/10  13:56
 */
public class MyApplication extends Application {

    public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 8;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
