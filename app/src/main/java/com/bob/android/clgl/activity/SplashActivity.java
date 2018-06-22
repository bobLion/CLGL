package com.bob.android.clgl.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bob.android.clgl.R;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.config.AppConfig;
import com.bob.android.clgl.dialog.EasyDialog;
import com.bob.android.clgl.util.AppInstallUtils;
import com.bob.android.clgl.util.PermissionsCheckerUtil;

public class SplashActivity extends BaseActivity {

    private Context mContext;
    private Handler handler = new Handler();
    private Runnable splashRunnable = new SplashRunnable();
    private Intent loginIntent;
    //所需权限
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_SETTINGS

    };

    private PermissionsCheckerUtil mPermissionsCheckerUtil; //权限检测器

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        mPermissionsCheckerUtil = new PermissionsCheckerUtil(mContext);;
        loginIntent = new Intent(mContext,LoginActivity.class);
        initPermission();
    }

    private void initPermission() {
        if (mPermissionsCheckerUtil.lacksPermissions(PERMISSIONS)) {
            ActivityCompat.requestPermissions(this
                    , PERMISSIONS, AppConfig.PERMISSION_REQUEST_CODE);
        } else {
            handler.postDelayed(splashRunnable, 2000);
        }
    }

    private class SplashRunnable implements Runnable {
        @Override
        public void run() {
            String sdCardStatus = Environment.getExternalStorageState();
            AppInstallUtils installService = new AppInstallUtils(mContext);
            if (sdCardStatus.equals(Environment.MEDIA_MOUNTED)) {
                startActivity(loginIntent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode
            , @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppConfig.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    handler.postDelayed(splashRunnable, 2000);
                } else {
                    finish();
                }
        }
    }
}
