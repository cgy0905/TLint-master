package com.cgy.hupu.injector.module;

import android.content.Context;

import com.cgy.hupu.db.DaoMaster;
import com.cgy.hupu.db.DaoSession;
import com.cgy.hupu.db.ForumDao;
import com.cgy.hupu.db.ImageCacheDao;
import com.cgy.hupu.db.ReadThreadDao;
import com.cgy.hupu.db.ThreadDao;
import com.cgy.hupu.db.ThreadInfoDao;
import com.cgy.hupu.db.ThreadReplyDao;
import com.cgy.hupu.db.UserDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cgy on 2018/10/11  11:34
 */
@Module
public class DBModule {

    @Provides
    @Singleton
    DaoMaster.DevOpenHelper provideDevOpenHelper(Context context) {
        return new DaoMaster.DevOpenHelper(context, "TLint.db", null);
    }

    @Provides
    @Singleton
    DaoMaster provideDaoMaster(DaoMaster.DevOpenHelper helper) {
        return new DaoMaster(helper.getWritableDatabase());
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession(DaoMaster master) {
        return master.newSession();
    }

    @Provides
    @Singleton
    UserDao getUserDao(DaoSession session) {
        return session.getUserDao();
    }

    @Provides
    @Singleton
    ForumDao getForumDao(DaoSession session) {
        return session.getForumDao();
    }

    @Provides
    @Singleton
    ThreadDao getThreadDao(DaoSession session) {
        return session.getThreadDao();
    }

    @Provides
    @Singleton
    ReadThreadDao getReadThreadDao(DaoSession session) {
        return session.getReadThreadDao();
    }

    @Provides
    @Singleton
    ThreadInfoDao getThreadInfoDao(DaoSession session) { return session.getThreadInfoDao(); }

    @Provides
    @Singleton
    ThreadReplyDao getThreadReplyDao(DaoSession session) { return session.getThreadReplyDao(); }

    @Provides
    @Singleton
    ImageCacheDao getImageCacheDao(DaoSession session) {
        return session.getImageCacheDao();
    }
}
