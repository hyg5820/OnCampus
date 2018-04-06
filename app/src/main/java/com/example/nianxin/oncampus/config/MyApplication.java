package com.example.nianxin.oncampus.config;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;

import cn.bmob.v3.Bmob;

/**
 * Created by nianxin on 2018/4/5.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //初始化BmobSDK
        Bmob.initialize(context,"31f93399560206695d915b3c5b77947a");
        //初始化SMSSDK
        MobSDK.init(this);
    }

    public static Context getContext(){
        return context;
    }
}
