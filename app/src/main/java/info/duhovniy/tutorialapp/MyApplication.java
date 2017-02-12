package info.duhovniy.tutorialapp;

import android.app.Application;
import android.content.Context;

import java.util.Collections;
import java.util.List;

import info.duhovniy.tutorialapp.model.ApiCallable;
import info.duhovniy.tutorialapp.model.FragmentModel;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class MyApplication extends Application {

    private Scheduler defaultScheduler;
    private List<FragmentModel> mFragments = Collections.emptyList();
    private ApiCallable apiCallable;

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    // use for RX implementation
    public Scheduler getDefaultScheduler() {
        if (defaultScheduler == null)
            defaultScheduler = Schedulers.io();
        return defaultScheduler;
    }

    public ApiCallable getApiCallable() {
        if (apiCallable == null)
            apiCallable = ApiCallable.Factory.create();
        return apiCallable;
    }

    public void storeFragments(List<FragmentModel> fragments) {
        mFragments = fragments;
    }

    public List<FragmentModel> restoreFragments() {
        return mFragments;
    }
}
