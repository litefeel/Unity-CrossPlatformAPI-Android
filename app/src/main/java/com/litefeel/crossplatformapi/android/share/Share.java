package com.litefeel.crossplatformapi.android.share;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.litefeel.crossplatformapi.android.utils.ActivityHelper;

import java.io.File;

/**
 * Created by litefeel on 2017/7/24.
 */

public class Share {

    private static final String TAG = Share.class.getSimpleName();

    public static void shareText(String text) {
        if (text == null || text.isEmpty())
            return;
        Intent sendIntent = new Intent();
        sendIntent.setType("text/plain");
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        ActivityHelper.getActivity().startActivity(sendIntent);
    }

    public static void shareImage(String image, String text) {
        File file = new File(image);
        Uri imageUri = file.exists() ? Uri.fromFile(file) : Uri.parse(image);
        Log.d(TAG, "nativeShareImage:" + image);
        Log.d(TAG, "nativeShareImage uri" + imageUri.toString());
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        if (text != null && !text.isEmpty()) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        }
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        ActivityHelper.getActivity().startActivity(Intent.createChooser(shareIntent, "send"));
    }
}
