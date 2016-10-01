package info.duhovniy.tutorialapp;

import android.app.Application;
import android.content.Context;

import java.util.Collections;
import java.util.List;

import info.duhovniy.tutorialapp.model.FragmentModel;

public class MyApplication extends Application {


    //private Scheduler defaultScheduler;
    private List<FragmentModel> mFragments = Collections.emptyList();

    public static MyApplication get(Context contex) {
        return (MyApplication) contex.getApplicationContext();
    }

//    public Scheduler getDefaultScheduler() {
//        if(defaultScheduler == null)
//            defaultScheduler = Schedulers.io();
//        return defaultScheduler;
//    }

    public void storeFragments(List<FragmentModel> fragments) {
        mFragments = fragments;
    }

    public List<FragmentModel> restoreFragments() {
        return mFragments;
    }
}
