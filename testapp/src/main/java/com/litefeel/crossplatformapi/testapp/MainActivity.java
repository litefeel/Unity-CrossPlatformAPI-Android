package com.litefeel.crossplatformapi.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.litefeel.crossplatformapi.android.AndroidPlatform;
import com.litefeel.crossplatformapi.android.share.Share;
import com.litefeel.crossplatformapi.android.ui.UI;
import com.litefeel.crossplatformapi.android.ui.AlertParams;
import com.unity3d.player.UnityPlayer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UnityPlayer.currentActivity = this;
        AndroidPlatform.init();


        Button shareTextBtn = (Button) findViewById(R.id.shareTextBtn);
        shareTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AndroidPlatform.nativeShareText(new String[]{"this is native share text!"});
                Share.shareText("this is share only text!");
            }
        });


        findViewById(R.id.shareImageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Share.shareImage("android.resource://" + getPackageName()
                        + "/mipmap/" + "ic_launcher", null);
            }
        });

        findViewById(R.id.shareImageAndTextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Share.shareImage("android.resource://" + getPackageName()
                        + "/mipmap/" + "ic_launcher", "this is share image and text");
            }
        });

        findViewById(R.id.alertBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertParams params = new AlertParams();
                params.title = "this is title";
                params.message = "this is message";
                params.yesButton = "OK";
                //params.neutralButton = "Neutral";
                params.noButton = "No";
                UI.showAlert(params);
            }
        });


        findViewById(R.id.toastBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UI.showToast("this is toast", false);
            }
        });
    }
}
