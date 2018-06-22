package com.bob.android.clgl.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bob.android.clgl.R;
import com.bob.android.clgl.adapter.DriverAdapter;
import com.bob.android.clgl.application.GlobalApplication;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.config.AppConfig;
import com.bob.android.clgl.entity.DriverEntity;
import com.bob.android.clgl.entity.ReceiverEntity;
import com.bob.android.clgl.entity.ReceiverListEntity;
import com.bob.android.clgl.http.HttpCallback;
import com.bob.android.clgl.http.HttpUtils;
import com.bob.android.clgl.login.UserLogin;
import com.bob.android.clgl.widget.CustomEditText;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectDriverActivity extends BaseActivity {

    @BindView(R.id.lv_select_driver)
    ListView lvSelectDriver;
    @BindView(R.id.tv_tip)
    TextView mTvTip;
    @BindView(R.id.root)
    LinearLayout root;

    private Context mContext;
    private UserLogin userLogin;
    private final int SELECT_DRIVER = 1112;
    private final int SAVE_RECEIVER_SUCESS = 1001;
    private final int SAVE_RECEIVER_FAULT = 1002;
    private final int GET_RECEIVER_SUCESS = 1003;
    private final int GET_RECEIVER_FAULT = 1004;
    private List<ReceiverListEntity.DataBean> receiverEntityList = new ArrayList<>();

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SAVE_RECEIVER_SUCESS:
                    ReceiverEntity mReceiverEntity = (ReceiverEntity) msg.obj;
                    Intent intent = new Intent();
                    intent.putExtra("receiver",(Serializable) mReceiverEntity);
                    setResult(SELECT_DRIVER,intent);
                    finish();
                    break;
                case GET_RECEIVER_SUCESS:
                    ReceiverListEntity result = (ReceiverListEntity)msg.obj;
                    receiverEntityList = result.getData();
                    if(receiverEntityList.size() != 0){
                        mTvTip.setVisibility(View.GONE);
                        lvSelectDriver.setVisibility(View.VISIBLE);
                        DriverAdapter adapter = new DriverAdapter(mContext, receiverEntityList);
                        lvSelectDriver.setAdapter(adapter);
                        lvSelectDriver.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.putExtra("dataBean",(Serializable) receiverEntityList.get(position));
                                setResult(SELECT_DRIVER,intent);
                                finish();
                            }
                        });
                    }else{
                        mTvTip.setVisibility(View.VISIBLE);
                        lvSelectDriver.setVisibility(View.GONE);
                    }
                    break;
                case GET_RECEIVER_FAULT:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_driver);
        ButterKnife.bind(this);
        mContext = this;
        initData();
        initStatusBar();
    }

    public void initData(){
        userLogin = GlobalApplication.getInstance().getUserLogin();
        HttpUtils.with(this).url(AppConfig.requestUrl(AppConfig.GET_RECEIVER))
                .addParam("userId",userLogin.getData().getUserId())
                .post()
                .execute(new HttpCallback<ReceiverListEntity>() {
                    @Override
                    public void onSuccess(ReceiverListEntity result) {
                        if(null != result){
                            Message msg = mHandler.obtainMessage();
                            msg.what = GET_RECEIVER_SUCESS;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @OnClick(R.id.img_add_driver)
    public void addDriver(View view){
        dialog();
    }


    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.dialog_add_driver, null);
        final CustomEditText driverName = (CustomEditText) v.findViewById(R.id.edt_driver_name);
        final CustomEditText driverPhone = (CustomEditText) v.findViewById(R.id.edt_driver_phone);
        Button btn_sure = (Button) v.findViewById(R.id.dialog_btn_sure);
        Button btn_cancel = (Button) v.findViewById(R.id.dialog_btn_cancel);
        builder.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        btn_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DriverEntity driverEntity = new DriverEntity();
                if(isPhone(driverPhone.getText().toString())){
                    driverEntity.setDriverNum(driverPhone.getText().toString().trim());
                    dialog.dismiss();
                }else{
                    return;
                }
                driverEntity.setDriverName(driverName.getText().toString().trim());
                HttpUtils.with(mContext).url(AppConfig.requestUrl(AppConfig.SAVE_RECEIVER))
                        .post()
                        .addParam("receiveName",driverName.getText().toString().trim())
                        .addParam("receivePhone",driverPhone.getText().toString().trim())
                        .addParam("userId",userLogin.getData().getUserId())
                        .execute(new HttpCallback<ReceiverEntity>() {
                            @Override
                            public void onSuccess(ReceiverEntity result) {
                                if(null != result.getData()){
                                    Message msg = mHandler.obtainMessage();
                                    msg.what = SAVE_RECEIVER_SUCESS;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
    }

    private void initStatusBar() {
        //状态栏沉浸
        int statusBarHeight = 60;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height"
                , "dimen"
                , "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        root.setPadding(0, statusBarHeight, 0, 0);
    }

    @Override
    public boolean onBackQuit() {
        return false;
    }

   @OnClick(R.id.iv_main_home)
    public void backHome(View view){
       finish();
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public  boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            Toast.makeText(mContext,"手机号应为11位数",Toast.LENGTH_LONG).show();
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                Toast.makeText(mContext,"请填入正确的手机号",Toast.LENGTH_LONG).show();
            }
            return isMatch;
        }
    }
}
