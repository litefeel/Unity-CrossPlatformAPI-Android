package com.litefeel.crossplatformapi.android.album;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.litefeel.crossplatformapi.android.utils.ActivityHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by litefeel on 2017/7/24.
 */

public class Album {

    public static void saveImage(String imagePath) {
        Activity activity = ActivityHelper.getActivity();
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        storageDir = new File(storageDir, activity.getPackageName());
        if (!storageDir.isDirectory()) {
            boolean createDir = storageDir.mkdirs();
            System.out.println("createDir " + createDir);
        }
        int separatorIndex = imagePath.lastIndexOf('/');
        String name = separatorIndex < 0 ? imagePath : imagePath.substring(separatorIndex + 1);

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        File newFile = new File(storageDir, name);
        try {
            inputStream = new FileInputStream(imagePath);
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
}
