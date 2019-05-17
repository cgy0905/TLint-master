package com.cgy.hupu.module.thread.list;

import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.View;

import com.cgy.hupu.bean.AttendStatusData;
import com.cgy.hupu.bean.Search;
import com.cgy.hupu.bean.SearchData;
import com.cgy.hupu.bean.SearchResult;
import com.cgy.hupu.bean.ThreadListData;
import com.cgy.hupu.bean.ThreadListResult;
import com.cgy.hupu.components.UserStorage;
import com.cgy.hupu.data.ThreadRepository;
import com.cgy.hupu.db.Forum;
import com.cgy.hupu.db.ForumDao;
import com.cgy.hupu.db.Thread;
import com.cgy.hupu.injector.PerActivity;
import com.cgy.hupu.net.forum.ForumApi;
import com.cgy.hupu.net.game.GameApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * @author cgy
 * @description
 * @date 2019/5/16 18:26
 */
@PerActivity
public class ThreadListPresenter implements ThreadListContract.Presenter{


    private String fid;
    private ThreadRepository mThreadRepository;
    private GameApi mGameApi;
    private UserStorage mUserStorage;
    private ForumApi mForumApi;
    private ForumDao mForumDao;

    private boolean isFirst = true;
    private PublishSubject<List<Thread>> mThreadSubject;
    private ThreadListContract.View mThreadListView;
    private List<Thread> threads = new ArrayList<>();

    private String lastTid = "";
    private String lastTamp = "";
    private String type;
    private int pageIndex;
    private int loadType = TYPE_LIST;
    private String key;
    private boolean hasNextPage = true;
    private boolean isAttention = false;

    private static final int TYPE_LIST = 1;
    private static final int TYPE_SEARCH = 2;

    private Subscription mSubscription;

    @Inject
    public ThreadListPresenter(String fid, ThreadRepository threadRepository, GameApi gameApi,
                               UserStorage userStorage, ForumApi forumApi, ForumDao forumDao) {
        this.fid = fid;
        this.mThreadRepository = threadRepository;
        this.mGameApi = gameApi;
        this.mUserStorage = userStorage;
        this.mForumApi = forumApi;
        this.mForumDao = forumDao;
        mThreadSubject = PublishSubject.create();
    }

    @Override
    public void onThreadReceive(String type) {
        mThreadListView.showLoading();
        mThreadListView.onFloatingVisibility(View.VISIBLE);
        this.type = type;
        loadType = TYPE_LIST;
        mThreadRepository.getThreadListObservable(Integer.valueOf(fid), mThreadSubject)
                .subscribe(threads -> {
                    ThreadListPresenter.this.threads = threads;
                    if (threads.isEmpty()) {
                        if (!isFirst) {
                            mThreadListView.onError("数据加载失败");
                        }
                        isFirst = false;
                    } else {
                        mThreadListView.showContent();
                        lastTid = threads.get(threads.size() - 1).getTid();
                        mThreadListView.renderThreads(threads);
                    }
                });
        loadThreadList("");
        getAttendStatus();

    }

    @Override
    public void onStartSearch(String key, int page) {
        if (TextUtils.isEmpty(key)) {
            mThreadListView.showToast("搜索词不能为空");
            return;
        }
        mThreadListView.showLoading();
        mThreadListView.onFloatingVisibility(View.GONE);
        pageIndex = page;
        this.key = key;
        loadSearchList();
    }

    private void loadThreadList(final String last) {
        mSubscription = mThreadRepository.getThreadsList(fid, last, lastTamp, type, mThreadSubject)
                .subscribe(new Action1<ThreadListData>() {
                    @Override
                    public void call(ThreadListData threadListData) {
                        if (threadListData != null && threadListData.result != null) {
                            ThreadListResult data = threadListData.result;
                            lastTamp = data.stamp;
                            hasNextPage = data.nextPage;
                            if (TextUtils.isEmpty(last)) {
                                mThreadListView.onScrollToTop();
                            }
                        }
                        mThreadListView.onRefreshCompleted();
                        mThreadListView.onLoadCompleted(hasNextPage);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (threads.isEmpty()) {
                            mThreadListView.onError("数据加载失败,请重试");
                        } else {
                            mThreadListView.onRefreshCompleted();
                        }
                    }
                });

    }

    private void loadSearchList() {
        loadType = TYPE_SEARCH;
        mGameApi.search(key, fid, pageIndex).map(new Func1<SearchData, List<Thread>>() {
            @Override
            public List<Thread> call(SearchData searchData) {
                if (searchData != null) {
                    if (pageIndex == 1) {
                        threads.clear();
                    }
                    SearchResult result = searchData.result;
                    hasNextPage = result.hasNextPage == 1;
                    for (Search search: result.data) {
                        Thread thread = new Thread();
                        thread.setFid(search.fid);
                        thread.setTid(search.id);
                        thread.setLightReply(Integer.valueOf(search.lights));
                        thread.setReplies(search.replies);
                        thread.setUserName(search.username);
                        thread.setTitle(search.title);
                        long time = Long.valueOf(search.addtime);
                        Date date = new Date(time);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        thread.setTime(format.format(date));
                        threads.add(thread);
                    }
                    return threads;
                }
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(threads -> {
                    if (threads == null) {
                        loadThreadError();
                    } else {
                        if (threads.isEmpty()) {
                            mThreadListView.onEmpty();
                        } else {
                            mThreadListView.showContent();
                            mThreadListView.renderThreads(threads);
                            mThreadListView.onRefreshCompleted();
                            mThreadListView.onLoadCompleted(hasNextPage);
                            if (pageIndex == 1) {
                                mThreadListView.onScrollToTop();
                            }
                        }
                    }
                }, throwable -> loadThreadError());
    }

    private void loadThreadError() {
        if (threads.isEmpty()) {
            mThreadListView.onError("数据加载失败");
        } else {
            mThreadListView.showContent();
            mThreadListView.onRefreshCompleted();
            mThreadListView.onLoadCompleted(true);
            mThreadListView.showToast("数据加载失败");
        }
    }

    private void getAttendStatus() {
        mForumApi.getAttentionStatus(fid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(attendStatusData -> {
                    if (attendStatusData != null && attendStatusData.status == 200) {
                        mThreadListView.renderThreadInfo(attendStatusData.forumInfo);
                        isAttention = attendStatusData.attendStatus == 1;
                        mThreadListView.attendStatus(isAttention);
                    }
                }, throwable -> getForumInfo());
    }

    private void getForumInfo() {
        Observable.create((Observable.OnSubscribe<Forum>) subscriber -> {
            List<Forum> forumList = mForumDao.queryBuilder().where(ForumDao.Properties.Fid.eq(fid)).list();
            if (!forumList.isEmpty()) {
                subscriber.onNext(forumList.get(0));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Forum>() {
                    @Override
                    public void call(Forum forum) {
                        if (forum != null && mThreadListView != null) {
                            mThreadListView.renderThreadInfo(forum);
                        }
                    }
                });
    }

    @Override
    public void onAttentionClick() {
        if (isLogin()) {
            if (isAttention) {
                delAttention();
            } else {
                addAttention();
            }
        }
    }

    @Override
    public void onPostClick() {
        if (isLogin()) {
            mThreadListView.showPostThreadUi(fid);
        }
    }

    private boolean isLogin() {
        if (!mUserStorage.isLogin()) {
            mThreadListView.showLoginUi();
            return false;
        }
        return true;
    }

    private void addAttention() {
        mForumApi.addAttention(fid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.status == 200 && result.result == 1) {
                        mThreadListView.showToast("添加关注成功");
                        isAttention = result.status == 200;
                        mThreadListView.attendStatus(isAttention);
                    }
                }, throwable -> mThreadListView.showToast("添加关注失败，请检查网络后重试"));
    }

    private void delAttention() {
        mForumApi.delAttention(fid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    if (result.status == 200 && result.result == 1) {
                        mThreadListView.showToast("取消关注成功");
                        isAttention = result.status != 200;
                        mThreadListView.attendStatus(isAttention);
                    }

                }, throwable -> mThreadListView.showToast("取消关注失败，请检查网络后重试"));

    }

    @Override
    public void onRefresh() {
        mThreadListView.onScrollToTop();
        if (loadType == TYPE_LIST) {
            loadThreadList("");
        } else {
            pageIndex = 1;
            loadSearchList();
        }
    }

    @Override
    public void onReload() {
        mThreadListView.showContent();
        mThreadListView.showLoading();
        if (loadType == TYPE_LIST) {
            loadThreadList(lastTid);
        } else {
            loadSearchList();
        }
    }

    @Override
    public void onLoadMore() {
        if (!hasNextPage) {
            mThreadListView.showToast("没有更多了~");
            mThreadListView.onLoadCompleted(false);
            return;
        }

        if (loadType == TYPE_LIST) {
            loadThreadList(lastTid);
        } else {
            pageIndex++;
            loadSearchList();
        }
    }

    @Override
    public void attachView(@NonNull ThreadListContract.View view) {
        mThreadListView = view;
        mThreadListView.showProgress();
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mThreadListView = null;
    }
}
