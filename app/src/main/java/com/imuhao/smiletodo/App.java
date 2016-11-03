package com.imuhao.smiletodo;

import android.app.Application;
import android.content.Context;

import com.imuhao.smiletodo.model.DaoMaster;
import com.imuhao.smiletodo.model.DaoSession;

/**
 * Created by smile on 16-10-25.
 */

public class App extends Application {
    private static DaoSession daoSession;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "smile.db", null);
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getSession() {
        return daoSession;
    }

}
