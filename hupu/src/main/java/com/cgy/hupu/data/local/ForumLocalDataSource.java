package com.cgy.hupu.data.local;

import com.cgy.hupu.data.ForumDataSource;
import com.cgy.hupu.db.Forum;
import com.cgy.hupu.db.ForumDao;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/21 13:43
 */
public class ForumLocalDataSource implements ForumDataSource {

    private final ForumDao mForumDao;

    @Inject
    public ForumLocalDataSource(ForumDao forumDao) {
        mForumDao = forumDao;
    }

    @Override
    public Observable<List<Forum>> getForumList(String forumId) {
        return Observable.create((Observable.OnSubscribe<List<Forum>>) subscriber -> {
            List<Forum> forumList = mForumDao.queryBuilder().where(ForumDao.Properties.ForumId.eq(forumId)).list();
            subscriber.onNext(forumList);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io());
    }

    public void saveForum(Forum forum) {
        mForumDao.queryBuilder()
                .where(ForumDao.Properties.ForumId.eq(forum.getForumId()),
                        ForumDao.Properties.Fid.eq(forum.getFid()))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
        mForumDao.insert(forum);
    }

    @Override
    public Observable<Boolean> removeForum(String fid) {
        return null;
    }
}
