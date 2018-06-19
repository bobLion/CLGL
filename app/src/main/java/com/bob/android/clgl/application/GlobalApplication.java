package com.bob.android.clgl.application;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.bob.android.clgl.database.greendao.DaoMaster;
import com.bob.android.clgl.database.greendao.DaoSession;
import com.bob.android.clgl.http.HttpUtils;
import com.bob.android.clgl.http.OKHttpEngine;
import com.bob.android.clgl.login.UserLogin;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.rey.material.app.ThemeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @package com.bob.android.clgl.application
 * @fileName GlobalApplication
 * @Author Bob on 2018/5/15 10:00.
 * @Describe TODO
 */

public class GlobalApplication extends Application {

    private static GlobalApplication application;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public static GlobalApplication getInstance() {
        return application;
    }
    private UserLogin userLogin;
    private List<Activity> activityList = new ArrayList<>();

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //material  UI组件
        ThemeManager.init(this, 2, 0, null);
        HttpUtils.init(new OKHttpEngine());
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5b1df3fd");
        setDatabase();
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "sport-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }
}
