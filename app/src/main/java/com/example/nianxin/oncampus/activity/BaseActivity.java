package com.example.nianxin.oncampus.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.nianxin.oncampus.ActivityCollector;

/**
 * package:com.example.nianxin.oncampus.activity
 * author: nianxin
 * time:2018/4/5 15:57
 * desc: 所有活动的父类
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取当前实例的类名
        Log.d("BaseActivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
