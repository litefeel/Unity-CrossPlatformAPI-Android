package com.litefeel.crossplatformapi.android.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import com.litefeel.crossplatformapi.android.utils.ActivityHelper;
import com.litefeel.crossplatformapi.android.utils.StringUtil;
import com.litefeel.crossplatformapi.android.utils.UnityUtil;

/**
 * Created by litefeel on 2017/7/17.
 */

public class UI {

    private static final int YesButton = 1;
    private static final int NoButton = 2;
    private static final int NeutralButton = 3;


    public static void showAlert(final AlertParams param) {
        final int alertId = param.alertId;
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHelper.getActivity());
        builder.setCancelable(false);
        builder.setTitle(param.title);
        builder.setMessage(param.message);
        builder.setPositiveButton(param.yesButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UnityUtil.sendMessage(UnityUtil.UI_Alert, alertId + "|" + YesButton);
            }
        });

        if (!StringUtil.isEmpty(param.noButton)) {
            builder.setNegativeButton(param.noButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    UnityUtil.sendMessage(UnityUtil.UI_Alert, alertId + "|" + NoButton);
                }
            });
        }

        if (!StringUtil.isEmpty(param.neutralButton)) {
            builder.setNeutralButton(param.neutralButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    UnityUtil.sendMessage(UnityUtil.UI_Alert, alertId + "|" + NeutralButton);
                }
            });
        }

        builder.show();
    }
    
    public static void showToast(final String message, final boolean isLong) {
        ActivityHelper.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int duration = isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
                Toast.makeText(ActivityHelper.getAppContext(), message, duration).show();
            }
        });
    }
}
