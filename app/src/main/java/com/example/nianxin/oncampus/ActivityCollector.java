package com.example.nianxin.oncampus;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * package:com.example.nianxin.oncampus
 * author: nianxin
 * time:2018/4/5 16:00
 * desc: 活动管理器
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity:activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
