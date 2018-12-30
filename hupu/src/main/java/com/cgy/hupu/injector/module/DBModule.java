package com.cgy.hupu.injector.module;

import android.content.Context;

import com.cgy.hupu.db.DaoMaster;
import com.cgy.hupu.db.DaoSession;
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

}
