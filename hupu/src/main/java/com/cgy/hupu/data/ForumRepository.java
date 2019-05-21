package com.cgy.hupu.data;

import com.cgy.hupu.Logger;
import com.cgy.hupu.data.local.ForumLocalDataSource;
import com.cgy.hupu.data.remote.ForumRemoteDataSource;
import com.cgy.hupu.db.Forum;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 13:41
 */
public class ForumRepository implements ForumDataSource {

    private final ForumLocalDataSource mForumLocalDataSource;
    private final ForumRemoteDataSource mForumRemoteDataSource;

    @Inject
    public ForumRepository(ForumLocalDataSource forumLocalDataSource, ForumRemoteDataSource forumRemoteDataSource) {
        mForumLocalDataSource = forumLocalDataSource;
        mForumRemoteDataSource = forumRemoteDataSource;
    }

    @Override
    public Observable<List<Forum>> getForumList(String forumId) {
        Logger.d("getForumList:" + forumId);
        Observable<List<Forum>> remote = mForumRemoteDataSource.getForumList(forumId);
        Observable<List<Forum>> local = mForumLocalDataSource.getForumList(forumId);

        Observable<List<Forum>> remoteWithLocalUpdate = remote.doOnNext(forums -> {
            if (forums != null) {
                for (Forum forum : forums) {
                    mForumLocalDataSource.saveForum(forum);
                }
            }
        });
        if (forumId.equals("0")) {  //我的论坛强制刷新
            return remoteWithLocalUpdate;
        }
        return Observable.concat(local, remoteWithLocalUpdate).first(forums -> forums != null && !forums.isEmpty());
    }

    @Override
    public Observable<Boolean> removeForum(String fid) {
        return null;
    }
}
