package com.bt.arasholding.filloapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.bt.arasholding.filloapp.data.network.model.LoginResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bt.arasholding.filloapp.R;
import com.bt.arasholding.filloapp.ui.base.BaseActivity;
import com.bt.arasholding.filloapp.ui.home.HomeActivity;
import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginMvpPresenter<LoginMvpView> mPresenter;

    @BindView(R.id.et_username)
    TextInputEditText etUsername;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;

    private String newDeviceToken;
    private String deviceName;
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        deviceName = getDeviceName();
        if(deviceName.equals("ZebraTechnologiesTC26"))
        {
            newDeviceToken = "ZebraTC26";
        }
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        newDeviceToken = token;
                        Log.e("newDeviceToken", newDeviceToken);
                        // Log and toast

                        System.out.println(token);
                    }
                });

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(LoginActivity.this);


        setUp();
    }

    @OnClick(R.id.btn_server_login)
    void onServerLoginClick(View v) {
        mPresenter.onServerLoginClick(etUsername.getText().toString(),
                etPassword.getText().toString(),newDeviceToken.toString(),deviceName);
    }

    @Override
    protected void setUp() {

    }
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + "" + model.replace(" ","");
    }
    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString().replace(" ","");
    }
    @Override
    public void openHomeActivity() {
        Intent intent = HomeActivity.getStartIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @OnEditorAction(value = R.id.et_password)
    protected boolean loginUser() {
        mPresenter.onServerLoginClick(etUsername.getText().toString(),
                etPassword.getText().toString(),newDeviceToken.toString(),deviceName.toString());

        return true;
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public String getVersionCode() {
        try {
            int code = getApplicationContext().getPackageManager()
                    .getPackageInfo(getApplicationContext().getPackageName(), 0).versionCode;
            return String.valueOf(code);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void showAlertDialog(LoginResponse response) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle("İşlem Sonucu !");
        alertDialog.setMessage(response.getMessage());
        alertDialog.setIcon(R.drawable.ic_info_black_24dp);

        alertDialog.setPositiveButton("İNDİR", (dialog, which) -> {
            dialog.dismiss();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.getDownloadUri()));

            startActivity(intent);
        });

        alertDialog.show();
    }

}
