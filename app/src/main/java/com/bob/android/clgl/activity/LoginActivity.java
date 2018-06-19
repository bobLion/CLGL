package com.bob.android.clgl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.bob.android.clgl.R;
import com.bob.android.clgl.application.GlobalApplication;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.config.AppConfig;
import com.bob.android.clgl.dialog.EasyDialog;
import com.bob.android.clgl.login.UserLogin;
import com.bob.android.clgl.http.HttpCallback;
import com.bob.android.clgl.http.HttpUtils;
import com.bob.android.clgl.util.StringUtils;
import com.bob.android.clgl.util.ToastUtils;
import com.bob.android.clgl.widget.CustomEditText;
import com.rey.material.widget.Button;

import okhttp3.OkHttpClient;


public class LoginActivity extends BaseActivity {

    CustomEditText mEdtLoginName;
    CustomEditText mEdtPswd;
    Button mBtnLogin;

    private final int REQUEST_FAILURE = 1110;
    private final int REQUEST_SUCCESS = 1111;

    private EasyDialog loadingDialog = null;
    private Intent intent ;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case REQUEST_FAILURE:
                    Toast.makeText(mContext,"连接失败！",Toast.LENGTH_LONG).show();
                    break;
                case REQUEST_SUCCESS:
                    Toast.makeText(mContext,"登录成功",Toast.LENGTH_LONG).show();
                    UserLogin result = (UserLogin) msg.obj;
                    GlobalApplication.getInstance().setUserLogin(result);
                    if(result.getData().getRoleId() .equals(AppConfig.USER_YEWUYUAN)){
                        intent = new Intent(mContext,DistributeCodeActivity.class);
                    }else if(result.getData().getRoleId() .equals(AppConfig.USER_MENWEI)){
                        intent = new Intent(mContext,MainActivity.class);
                    }else if(result.getData().getRoleId() .equals(AppConfig.USER_LINGDAO)){
                        intent = new Intent(mContext,CheckTicketActivity.class);
                    }else{
                        return;
                    }
                    startActivity(intent);
                    finish();
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
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onViewClicked(mBtnLogin);
            }
        });
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
                .execute(new HttpCallback<UserLogin>() {
                    @Override
                    public void onSuccess(UserLogin result) {
                        if(null != result.getData()){
                            Message msg = mHandler.obtainMessage();
                            msg.what = REQUEST_SUCCESS;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }else{
                            Message msg = mHandler.obtainMessage();
                            msg.what = REQUEST_FAILURE;
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

    }
}
