package com.cgy.hupu.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.cgy.hupu.db.Forum;
import com.cgy.hupu.db.User;
import com.cgy.hupu.db.Thread;
import com.cgy.hupu.db.ThreadInfo;
import com.cgy.hupu.db.ThreadReply;
import com.cgy.hupu.db.ReadThread;
import com.cgy.hupu.db.ImageCache;

import com.cgy.hupu.db.ForumDao;
import com.cgy.hupu.db.UserDao;
import com.cgy.hupu.db.ThreadDao;
import com.cgy.hupu.db.ThreadInfoDao;
import com.cgy.hupu.db.ThreadReplyDao;
import com.cgy.hupu.db.ReadThreadDao;
import com.cgy.hupu.db.ImageCacheDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig forumDaoConfig;
    private final DaoConfig userDaoConfig;
    private final DaoConfig threadDaoConfig;
    private final DaoConfig threadInfoDaoConfig;
    private final DaoConfig threadReplyDaoConfig;
    private final DaoConfig readThreadDaoConfig;
    private final DaoConfig imageCacheDaoConfig;

    private final ForumDao forumDao;
    private final UserDao userDao;
    private final ThreadDao threadDao;
    private final ThreadInfoDao threadInfoDao;
    private final ThreadReplyDao threadReplyDao;
    private final ReadThreadDao readThreadDao;
    private final ImageCacheDao imageCacheDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        forumDaoConfig = daoConfigMap.get(ForumDao.class).clone();
        forumDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        threadDaoConfig = daoConfigMap.get(ThreadDao.class).clone();
        threadDaoConfig.initIdentityScope(type);

        threadInfoDaoConfig = daoConfigMap.get(ThreadInfoDao.class).clone();
        threadInfoDaoConfig.initIdentityScope(type);

        threadReplyDaoConfig = daoConfigMap.get(ThreadReplyDao.class).clone();
        threadReplyDaoConfig.initIdentityScope(type);

        readThreadDaoConfig = daoConfigMap.get(ReadThreadDao.class).clone();
        readThreadDaoConfig.initIdentityScope(type);

        imageCacheDaoConfig = daoConfigMap.get(ImageCacheDao.class).clone();
        imageCacheDaoConfig.initIdentityScope(type);

        forumDao = new ForumDao(forumDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);
        threadDao = new ThreadDao(threadDaoConfig, this);
        threadInfoDao = new ThreadInfoDao(threadInfoDaoConfig, this);
        threadReplyDao = new ThreadReplyDao(threadReplyDaoConfig, this);
        readThreadDao = new ReadThreadDao(readThreadDaoConfig, this);
        imageCacheDao = new ImageCacheDao(imageCacheDaoConfig, this);

        registerDao(Forum.class, forumDao);
        registerDao(User.class, userDao);
        registerDao(Thread.class, threadDao);
        registerDao(ThreadInfo.class, threadInfoDao);
        registerDao(ThreadReply.class, threadReplyDao);
        registerDao(ReadThread.class, readThreadDao);
        registerDao(ImageCache.class, imageCacheDao);
    }
    
    public void clear() {
        forumDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
        threadDaoConfig.getIdentityScope().clear();
        threadInfoDaoConfig.getIdentityScope().clear();
        threadReplyDaoConfig.getIdentityScope().clear();
        readThreadDaoConfig.getIdentityScope().clear();
        imageCacheDaoConfig.getIdentityScope().clear();
    }

    public ForumDao getForumDao() {
        return forumDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ThreadDao getThreadDao() {
        return threadDao;
    }

    public ThreadInfoDao getThreadInfoDao() {
        return threadInfoDao;
    }

    public ThreadReplyDao getThreadReplyDao() {
        return threadReplyDao;
    }

    public ReadThreadDao getReadThreadDao() {
        return readThreadDao;
    }

    public ImageCacheDao getImageCacheDao() {
        return imageCacheDao;
    }

}
