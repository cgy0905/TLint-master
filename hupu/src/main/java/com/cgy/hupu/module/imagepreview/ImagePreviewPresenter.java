package com.cgy.hupu.module.imagepreview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.cgy.hupu.components.okhttp.OkHttpHelper;
import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.utils.ConfigUtil;
import com.cgy.hupu.utils.FormatUtil;
import com.cgy.hupu.utils.StringUtil;
import com.cgy.hupu.utils.ToastUtil;
import com.facebook.cache.common.CacheKey;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.File;
import java.io.InputStream;

import javax.inject.Inject;

import okio.BufferedSink;
import okio.Okio;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/22 14:33
 */
@PerActivity
public class ImagePreviewPresenter implements ImagePreviewContract.Presenter{

    private OkHttpHelper mOkHttpHelper;
    private Context mContext;

    @Inject
    public ImagePreviewPresenter(OkHttpHelper okHttpHelper, Context context) {
        mOkHttpHelper = okHttpHelper;
        mContext = context;
    }

    @Override
    public void saveImage(String url) {
        Observable.just(url)
                .map((Func1<String, InputStream>) s -> getImageBytesFromLocal(Uri.parse(s)))
                .map(in -> {
                    if (in != null) {
                        String fileName = FormatUtil.getFileNameFromUrl(url);
                        File target = new File(ConfigUtil.getPicSavePath(mContext), fileName);
                        if (target.exists()) {
                            return target;
                        }
                        try {
                            BufferedSink sink = Okio.buffer(Okio.sink(target));
                            sink.writeAll(Okio.source(in));
                            sink.close();
                            return target;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                })
                .map(file -> {
                    if (file != null && file.exists()) {
                        return file;
                    }
                    try {
                        mOkHttpHelper.httpDownload(url, file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .doOnNext(file -> {
                    if (file != null && file.exists()) {
                        scanPhoto(file);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    if (file != null && file.exists()) {
                        ToastUtil.showToast("保存成功");
                    } else {
                        ToastUtil.showToast("保存失败，请重试");
                    }
                });
    }

    private InputStream getImageBytesFromLocal(Uri loadUri) {
        if (loadUri == null) {
            return null;
        }
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                .getEncodedCacheKey(ImageRequest.fromUri(loadUri), null);
        try {
            if (ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey)) {
                return ImagePipelineFactory.getInstance()
                        .getMainFileCache()
                        .getResource(cacheKey)
                        .openStream();
            }
            if (ImagePipelineFactory.getInstance().getSmallImageFileCache().hasKey(cacheKey)) {
                return ImagePipelineFactory.getInstance()
                        .getSmallImageFileCache()
                        .getResource(cacheKey)
                        .openStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void scanPhoto(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        mContext.sendBroadcast(mediaScanIntent);
    }

    @Override
    public void copyImagePath(String url) {
        StringUtil.copy(mContext, url);
    }

    @Override
    public void attachView(@NonNull ImagePreviewContract.View view) {

    }

    @Override
    public void detachView() {

    }
}
