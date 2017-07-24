package com.litefeel.crossplatformapi.android;


import com.litefeel.crossplatformapi.android.clipboard.Clipboard;
import com.litefeel.crossplatformapi.android.utils.ActivityHelper;
import com.unity3d.player.UnityPlayer;

/**
 * Created by litefeel on 2016/5/21.
 */
public class AndroidPlatform {

    private static boolean inited = false;

    public static void init() {
        if (!inited) {
            inited = true;
            ActivityHelper.init(UnityPlayer.currentActivity);
            Clipboard.init();
        }

    }

}
