package info.duhovniy.tutorialapp;

import android.app.Application;
import android.content.Context;

import java.util.Collections;
import java.util.List;

import info.duhovniy.tutorialapp.model.ApiCallableInterface;
import info.duhovniy.tutorialapp.model.FragmentModel;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class MyApplication extends Application {

    private Scheduler defaultScheduler;
    private List<FragmentModel> mFragments = Collections.emptyList();
    private ApiCallableInterface apiCallableInterface;

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public Scheduler getDefaultScheduler() {
        if (defaultScheduler == null)
            defaultScheduler = Schedulers.io();
        return defaultScheduler;
    }

    public ApiCallableInterface getApiCallableInterface() {
        if (apiCallableInterface == null)
            apiCallableInterface = ApiCallableInterface.Factory.create();
        return apiCallableInterface;
    }

    public void storeFragments(List<FragmentModel> fragments) {
        mFragments = fragments;
    }

    public List<FragmentModel> restoreFragments() {
        return mFragments;
    }
}
