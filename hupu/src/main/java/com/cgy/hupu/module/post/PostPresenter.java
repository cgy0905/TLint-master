package com.cgy.hupu.module.post;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cgy.hupu.Constants;
import com.cgy.hupu.Logger;
import com.cgy.hupu.bean.UploadData;
import com.cgy.hupu.bean.UploadInfo;
import com.cgy.hupu.net.forum.ForumApi;
import com.cgy.hupu.utils.SettingPrefUtil;
import com.cgy.hupu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cgy on 2019/4/29 15:47 .
 */
public class PostPresenter implements PostContract.Presenter {

    private ForumApi mForumApi;
    private Context mContext;

    @Inject
    public PostPresenter(ForumApi forumApi, Context context) {
        mForumApi = forumApi;
        mContext = context;
    }

    private Subscription mSubscription;
    private PostContract.View mPostView;
    private ArrayList<String> paths = new ArrayList<>();
    private int uploadCount = 0;

    @Override
    public void checkPermission(int type, String fid, String tid) {
        mSubscription = mForumApi.checkPermission(fid, tid, type == Constants.TYPE_POST ? "threadPublish" : "threadReply")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permissionData -> {
                    if (permissionData != null) {
                        if (permissionData.error != null) {
                            mPostView.renderError(permissionData.error);
                        } else if (SettingPrefUtil.isNeedExam(mContext)) {
                            mPostView.renderExam(permissionData.exam);
                        }
                    }
                }, throwable -> throwable.printStackTrace());
    }

    @Override
    public void parse(ArrayList<String> paths) {
        this.paths = paths;
    }

    @Override
    public void comment(String tid, String fid, String pid, String content) {
        mPostView.showLoading();
        if (paths != null && !paths.isEmpty()) {
            List<String> images = new ArrayList<>();
            mSubscription = Observable.from(paths)
                    .flatMap((Func1<String, Observable<UploadData>>) s -> mForumApi.upload(s))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UploadData>() {
                        @Override
                        public void onStart() {
                            uploadCount = 0;
                            images.clear();
                        }

                        @Override
                        public void onCompleted() {
                            uploadCount++;
                            if (uploadCount == paths.size()) {
                                addReply(tid, fid, pid, content, images);
                            }
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            uploadCount++;
                            if (uploadCount == paths.size()) {
                                addReply(tid, fid, pid, content, images);
                            }
                        }

                        @Override
                        public void onNext(UploadData uploadData) {
                            if (uploadData != null) {
                                for (UploadInfo info : uploadData.files) {
                                    images.add(info.requestUrl);
                                }
                            }
                        }
                    });
        } else {
            addReply(tid, fid, pid, content, null);
        }

    }

    private void addReply(String tid, String fid, String pid, String content, List<String> images) {
        StringBuilder builder = new StringBuilder(content);
        if (images != null) {
            for (String url :
                    images) {
                builder.append("<br><br><img src=\"").append(url).append("\"<br><br>");
            }
        }
        Logger.d("builder :" + builder.toString());
        mSubscription = mForumApi.addReplyByApp(tid, fid, pid, builder.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    mPostView.hideLoading();
                    if (data != null) {
                        if (data.error != null) {
                            ToastUtil.showToast(data.error.text);
                        } else if (data.status == 200) {
                            ToastUtil.showToast("发送成功~");
                            mPostView.postSuccess();
                        }
                    } else {
                        ToastUtil.showToast("您的网络有问题,请检查后重试");
                    }
                }, throwable -> {
                    mPostView.hideLoading();
                    ToastUtil.showToast("您的网络有问题,请检查后重试");
                });

    }

    @Override
    public void post(String fid, String content, String title) {
        mPostView.showLoading();
        if (paths != null && !paths.isEmpty()) {
            List<String> images = new ArrayList<>();
            mSubscription = Observable.from(paths)
                    .flatMap((Func1<String, Observable<UploadData>>) s -> mForumApi.upload(s)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UploadData>() {
                        @Override
                        public void onStart() {
                            uploadCount = 0;
                            images.clear();
                        }

                        @Override
                        public void onCompleted() {
                            uploadCount++;
                            if (uploadCount == paths.size()) {
                                addPost(fid, content, title, images);
                            }
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            uploadCount++;
                            if (uploadCount == paths.size()) {
                                addPost(fid, content, title, images);
                            }
                        }

                        @Override
                        public void onNext(UploadData uploadData) {
                            if (uploadData != null) {
                                for (UploadInfo info : uploadData.files) {
                                    images.add(info.requestUrl);
                                }
                            }
                        }
                    });
        } else {
            addPost(fid, content, title, null);
        }
    }

    private void addPost(String fid, String content, String title, List<String> images) {
        StringBuilder builder = new StringBuilder(content);
        if (images != null) {
            for (String url : images) {
                builder.append("<br><br><img src= \"").append(url).append("\"><br><br>");
            }
        }
        mSubscription = mForumApi.addThread(title, builder.toString(), fid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    mPostView.hideLoading();
                    if (data != null) {
                        if (data.error != null) {
                            ToastUtil.showToast(data.error.text);
                        } else if (data.status == 200) {
                            ToastUtil.showToast("发送成功~");
                            mPostView.postSuccess();
                        }
                    } else {
                        ToastUtil.showToast("您的网络有问题,请检查后重试");
                    }
                }, throwable -> ToastUtil.showToast("您的网络有问题,请检查后重试"));


    }

    @Override
    public void attachView(@NonNull PostContract.View view) {
        mPostView = view;
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mPostView = null;
    }
}
