package com.cgy.hupu;

import android.app.Application;

import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.db.User;
import com.cgy.hupu.injector.component.ApplicationComponent;
import com.cgy.hupu.injector.component.DaggerApplicationComponent;
import com.cgy.hupu.injector.module.ApplicationModule;
import com.cgy.hupu.utils.ToastUtil;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadHelper;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;

import javax.inject.Inject;

import okhttp3.OkHttpClient;

/**
 * Created by cgy on 2018/10/10  13:56
 */
public class MyApplication extends Application {

    public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 8;

    @Inject
    UserStorage userStorage;

    @Inject
    OkHttpClient okHttpClient;


    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initComponent();
        initUser();
        FileDownloader.init(this, new FileDownloadHelper.OkHttpClientCustomMaker() {
            @Override
            public OkHttpClient customMake() {
                return okHttpClient;
            }
        });

        initFrescoConfig();
        ToastUtil.register(this);
        LeakCanary.install(this);
    }

    private void initComponent() {
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


    private void initFrescoConfig() {
        final MemoryCacheParams bitmapCacheParams =
                new MemoryCacheParams(MAX_MEMORY_CACHE_SIZE,     // Max total size of elements in the cache
                        Integer.MAX_VALUE,                      // Max entries in the cache
                        MAX_MEMORY_CACHE_SIZE,                  // Max total size of elements in eviction queue
                        Integer.MAX_VALUE,                      // Max length of eviction queue
                        Integer.MAX_VALUE);
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, okHttpClient)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .setBitmapMemoryCacheParamsSupplier(new Supplier<MemoryCacheParams>() {
                    @Override
                    public MemoryCacheParams get() {
                        return bitmapCacheParams;
                    }
                })
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(this).setMaxCacheSize(MAX_MEMORY_CACHE_SIZE).build())
                .setDownsampleEnabled(true)
                .build();

        Fresco.initialize(this, config);
    }

    private void initUser() {
    }


}
