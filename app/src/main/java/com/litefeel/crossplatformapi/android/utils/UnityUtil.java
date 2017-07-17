package com.litefeel.crossplatformapi.android.utils;

import android.util.Log;

import com.unity3d.player.UnityPlayer;

/**
 * Created by litefeel on 2017/7/17.
 */

public class UnityUtil {

    private static final String TAG = UnityUtil.class.getSimpleName();

    public static final String CPAPI_NAME = "CrossPlatformAPI";
    public static final String UI_Alert = "OnUI_AlertCb";

    public static void sendMessage(String method, String value) {
        UnityPlayer.UnitySendMessage(CPAPI_NAME, method, value);
        Log.d(TAG, String.format("UnitySendMessage:%s->%s(%s)", CPAPI_NAME, method, value));
    }
}
