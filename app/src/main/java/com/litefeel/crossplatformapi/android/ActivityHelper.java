package com.litefeel.crossplatformapi.android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by litefeel on 2017/5/2.
 */

public final class ActivityHelper {
    private static final String TAG = ActivityHelper.class.getSimpleName();

    private static Activity _activity = null;
    private static Context appContext = null;
    private static boolean _init = false;
    private static Application.ActivityLifecycleCallbacks callback = new Callback();

    public static void init(@NonNull Context context) {
        if (_init) return;

        _init = true;
        if (context instanceof Activity) {
            _activity = (Activity) context;
            callback.onActivityCreated(_activity, null);
        }
        appContext = context.getApplicationContext();
        Application app = (Application) appContext;
        app.registerActivityLifecycleCallbacks(callback);
    }

    public static Activity getActivity() {
        return _activity;
    }

    public static Context getAppContext() {
        return appContext;
    }

    static class Callback implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.d(TAG, activity.getClass().getSimpleName() + ":onCreated");
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + ":onStarted");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + ":onResumed");
            _activity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + ":onPaused");
            if (_activity == activity) {
                _activity = null;
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + ":onStopped");
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.d(TAG, activity.getClass().getSimpleName() + ":onSaveInstance");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.d(TAG, activity.getClass().getSimpleName() + ":onDestroyed");
        }
    }
}