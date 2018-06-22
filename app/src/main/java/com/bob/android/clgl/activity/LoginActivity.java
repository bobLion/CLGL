package com.bob.android.clgl.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bob.android.clgl.R;
import com.bob.android.clgl.application.GlobalApplication;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.config.AppConfig;
import com.bob.android.clgl.dialog.EasyDialog;
import com.bob.android.clgl.http.HttpCallback;
import com.bob.android.clgl.http.HttpUtils;
import com.bob.android.clgl.login.UserLogin;
import com.bob.android.clgl.util.PreferencesUtils;
import com.bob.android.clgl.util.StringUtils;
import com.bob.android.clgl.util.ToastUtils;
import com.bob.android.clgl.widget.CustomEditText;
import com.rey.material.widget.Button;

import okhttp3.OkHttpClient;


public class LoginActivity extends BaseActivity {

    CustomEditText mEdtLoginName;
    CustomEditText mEdtPswd;
    Button mBtnLogin;
    CheckBox cbAutoLogin;

    private final int REQUEST_FAILURE = 1110;
    private final int REQUEST_SUCCESS = 1111;

    private EasyDialog loadingDialog = null;
    private String password;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REQUEST_FAILURE:
                    Toast.makeText(mContext,"连接失败！",Toast.LENGTH_LONG).show();
                    break;
                case REQUEST_SUCCESS:
                    UserLogin result = (UserLogin) msg.obj;
                    if(null != result.getData()){
                        Toast.makeText(mContext,"登录成功",Toast.LENGTH_LONG).show();
                        GlobalApplication.getInstance().setUserLogin(result);
                        PreferencesUtils.putString(mContext, "username", result.getData().getUserAccount());
                        //点击自动登录
                        if (cbAutoLogin.isChecked()) {
                            PreferencesUtils.putBoolean(mContext, "autoLogin", true);
                            PreferencesUtils.putString(mContext, "password", password);
                        } else {
                            PreferencesUtils.putBoolean(mContext, "autoLogin", false);
                            PreferencesUtils.putString(mContext, "password", "");
                        }

                       /* if(result.getData().getRoleId() .equals(AppConfig.USER_YEWUYUAN)){
                            intent = new Intent(mContext,DistributeCodeActivity.class);
                        }else if(result.getData().getRoleId() .equals(AppConfig.USER_MENWEI)){
                            intent = new Intent(mContext,MainActivity.class);
                        }else if(result.getData().getRoleId() .equals(AppConfig.USER_LINGDAO)){
                            intent = new Intent(mContext,CheckTicketActivity.class);
                        }else{
                            return;
                        }*/
                        Intent intent;
                        switch (result.getData().getRoleId()) {
                            case AppConfig.USER_YEWUYUAN:
                                intent = new Intent(mContext, DistributeCodeActivity.class);
                                break;
                            case AppConfig.USER_MENWEI:
                                intent = new Intent(mContext, MainActivity.class);
                                break;
                            case AppConfig.USER_LINGDAO:
                                intent = new Intent(mContext, CheckTicketActivity.class);
                                break;
                            default:
                                return;
                        }
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(mContext, result.getMsg().toString(),Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                    default:break;
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        mEdtLoginName = (CustomEditText) findViewById(R.id.et_login_name) ;
        mEdtPswd = (CustomEditText)findViewById(R.id.et_login_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        cbAutoLogin = (CheckBox)findViewById(R.id.cb_auto_login);

        mEdtLoginName.setText(PreferencesUtils.getString(mContext, "username"));
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onViewClicked(mBtnLogin);
            }
        });

        if (PreferencesUtils.getBoolean(mContext, "autoLogin")) {
            autoLogin();
        } else {
            cbAutoLogin.setChecked(false);
            mEdtPswd.setText("");
        }
    }

    private void autoLogin() {
        mEdtPswd.setText(PreferencesUtils.getString(mContext, "password"));
    }


    public void onViewClicked(View view) {
        final String userCode = mEdtLoginName.getText().toString().trim();
        if (StringUtils.isBlank(userCode)) {
            ToastUtils.show(mContext, getString(R.string.tip_name_is_empty));
            return;
        }
        final String password = mEdtPswd.getText().toString().trim();
        if (StringUtils.isBlank(password)) {
            ToastUtils.show(mContext, getString(R.string.tip_password_is_empty));
            return;
        }
        loginRequest(userCode, password);
    }


    private void loginRequest(final String userCode, String password) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        HttpUtils.with(this).url(AppConfig.requestUrl(AppConfig.LOGIN_REQUEST))
                .post()
                .addParam("userAccount",userCode)
                .addParam("passWord",password)
                .execute(new HttpCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            UserLogin userLogin = JSON.parseObject(result,UserLogin.class);
                            if(userLogin != null){
                                Message msg = mHandler.obtainMessage();
                                msg.what = REQUEST_SUCCESS;
                                msg.obj = userLogin;
                                mHandler.sendMessage(msg);
                            }else{
                                Message msg = mHandler.obtainMessage();
                                msg.what = REQUEST_FAILURE;
                                mHandler.sendMessage(msg);
                            }
                        }catch (Exception e){
                            Message msg = mHandler.obtainMessage();
                            msg.what = REQUEST_FAILURE;
                            mHandler.sendMessage(msg);
                        }

                    }

                    @Override
                    public void onError(Exception e) {
                        Message msg = mHandler.obtainMessage();
                        msg.what = REQUEST_FAILURE;
                        mHandler.sendMessage(msg);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
