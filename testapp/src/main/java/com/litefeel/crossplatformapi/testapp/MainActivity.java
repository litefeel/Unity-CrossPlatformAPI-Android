package com.litefeel.crossplatformapi.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.litefeel.crossplatformapi.android.AndroidPlatform;
import com.litefeel.crossplatformapi.android.ui.UI;
import com.litefeel.crossplatformapi.android.ui.AlertParams;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidPlatform.init(this);


        Button shareTextBtn = (Button) findViewById(R.id.shareTextBtn);
        shareTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AndroidPlatform.nativeShareText(new String[]{"this is native share text!"});
                AndroidPlatform.nativeShareText("this is share only text!");
            }
        });


        findViewById(R.id.shareImageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidPlatform.nativeShareImage("android.resource://" + getPackageName()
                        + "/mipmap/" + "ic_launcher");
            }
        });

        findViewById(R.id.shareImageAndTextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidPlatform.nativeShareImage("android.resource://" + getPackageName()
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
    }
}
