package com.litefeel.crossplatformapi.android.clipboard;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.litefeel.crossplatformapi.android.utils.ActivityHelper;

/**
 * Created by litefeel on 2017/7/24.
 */

public class Clipboard {

    private static ClipboardManager clipboardManager = null;


    public static void init() {
        final Activity activity = ActivityHelper.getActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            }
        });
    }

    public static void setText(String text) {
        ClipData clipData = ClipData.newPlainText(null, text);
        clipboardManager.setPrimaryClip(clipData);
    }

    public static String getText() {
        if (clipboardManager.hasPrimaryClip()) {
            ClipData clipData = clipboardManager.getPrimaryClip();
            if (clipData.getItemCount() > 0) {
                ClipData.Item item = clipData.getItemAt(0);
                CharSequence text = item.getText();
                if (text != null) {
                    return text.toString();
                }
            }
        }
        return "";
    }
}
