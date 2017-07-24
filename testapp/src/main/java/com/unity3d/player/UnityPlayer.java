package com.unity3d.player;

import android.app.Activity;
import android.util.Log;

/**
 * Created by xiaoqing.zhang on 2017/7/17.
 */

public class UnityPlayer {

    private static final String TAG = UnityPlayer.class.getSimpleName();

    public static void UnitySendMessage(String goName, String method, String value) {
        Log.d(TAG, String.format("UnitySendMessage: %s->%s(%s)", goName, method, value));

    }

    public static Activity currentActivity = null;
}
