package com.bt.arasholding.filloapp.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.bt.arasholding.filloapp.R;

import dmax.dialog.SpotsDialog;

public class CommonUtils {

    public static String Cropper(String str) {
        String strOut = str;

        if (str.length() > 30)
            strOut = str.substring(0, 20) + "...";

        return strOut;
    }

    public static String getAppVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return String.valueOf(info.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ProgressDialog showLoadingDialog(Context context) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        return progressDialog;
    }

    public static AlertDialog showLoadingDialogWithMessage(Context context, String message) {

        android.app.AlertDialog alertDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage(message)
                .setCancelable(false)
                .build();

        alertDialog.show();

        return alertDialog;
    }
}
