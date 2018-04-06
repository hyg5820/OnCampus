package com.example.nianxin.oncampus.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nianxin.oncampus.R;

public class SucessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucess);
    }

    /**
     * 启动SucessActivity
     * @author nianxin
     * @time 2018/4/5 16:32
     */
    public static void actionStart(Context context){
        Intent intent = new Intent(context,SucessActivity.class);
        context.startActivity(intent);
    }
}
