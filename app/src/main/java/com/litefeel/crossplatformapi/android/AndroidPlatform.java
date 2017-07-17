package com.litefeel.crossplatformapi.android;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.litefeel.crossplatformapi.android.utils.ActivityHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by litefeel on 2016/5/21.
 */
public class AndroidPlatform {

    private static final String TAG = AndroidPlatform.class.getSimpleName();
    private static ClipboardManager clipboardManager = null;


    public static void init(@NonNull final Activity activity) {
        ActivityHelper.init(activity);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "init runOnUiThread");
                clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            }
        });
    }

    public static void saveToAlbum(String sourefilename) {
        Activity activity = ActivityHelper.getActivity();
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        storageDir = new File(storageDir, activity.getPackageName());
        if (!storageDir.isDirectory()) {
            boolean createDir = storageDir.mkdirs();
            System.out.println("createDir " + createDir);
        }
        int separatorIndex = sourefilename.lastIndexOf('/');
        String name = separatorIndex < 0 ? sourefilename : sourefilename.substring(separatorIndex + 1);

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        File newFile = new File(storageDir, name);
        try {
            inputStream = new FileInputStream(sourefilename);
            System.out.println(storageDir.getAbsolutePath());
            System.out.println(name);

            //newFile.createNewFile();
            outputStream = new FileOutputStream(newFile);
            final int BUFFER_SIZE = 1024 * 1024;
            byte[] buffer = new byte[BUFFER_SIZE]; // 1M
            int size = inputStream.read(buffer, 0, BUFFER_SIZE);
            while (size > 0) {
                outputStream.write(buffer, 0, size);
                size = inputStream.read(buffer, 0, BUFFER_SIZE);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
            inputStream = null;
            outputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (newFile.exists()) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = newFile;
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            activity.sendBroadcast(mediaScanIntent);
        }
    }

    public static void pasteToClipboard(String text) {
        ClipData clipData = ClipData.newPlainText(null, text);
        clipboardManager.setPrimaryClip(clipData);
    }

    public static String copyFromClipboard() {
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

    public static void nativeShareText(String text) {
        if (text == null || text.isEmpty())
            return;
        Intent sendIntent = new Intent();
        sendIntent.setType("text/plain");
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        ActivityHelper.getActivity().startActivity(sendIntent);
    }

    public static void nativeShareImage(String image) {
        nativeShareImage(image, null);
    }

    public static void nativeShareImage(String image, String text) {
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
