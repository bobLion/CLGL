package com.bob.android.clgl.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bob.android.clgl.R;
import com.bob.android.clgl.application.GlobalApplication;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.config.AppConfig;
import com.bob.android.clgl.dialog.EasyDialog;
import com.bob.android.clgl.entity.CreatePswdEntity;
import com.bob.android.clgl.entity.DriverEntity;
import com.bob.android.clgl.entity.ReceiverEntity;
import com.bob.android.clgl.entity.ReceiverListEntity;
import com.bob.android.clgl.entity.RemindEntity;
import com.bob.android.clgl.http.HttpCallback;
import com.bob.android.clgl.http.HttpUtils;
import com.bob.android.clgl.login.UserLogin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DistributeCodeActivity extends BaseActivity {


    @BindView(R.id.iv_main_home)
    ImageView ivMainHome;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_remaid_ticket)
    TextView tvRemaidTicket;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.sp_psqd_type)
    Spinner spPswdType;
    @BindView(R.id.btn_create_password)
    Button btnCreatePassword;
    @BindView(R.id.btn_check_ticket)
    Button btnCheckTicket;
    @BindView(R.id.tv_select_driver)
    TextView mTvDriver;
    @BindView(R.id.tv_tip_user)
    TextView mTvTip;

    private String codeType;
    private int receiveId;
    private Context mContext;
    private UserLogin userLogin;
    private final int CREATE_PSWD = 1111;
    private final int SELECT_DRIVER = 1112;
    private final int REMAIND_TICKET = 1113;
    private ReceiverListEntity.DataBean receiverBean;
    private List<String> codeTypeList = new ArrayList<String>();

    private String[] codeStrs = new String[]{"黄色(四分车)", "白色(社会车)", "蓝色(环卫三吨)", "紫色(环卫五吨)"};

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CREATE_PSWD:
                    CreatePswdEntity mCreatePswdEntity = (CreatePswdEntity) msg.obj;
                    if(null != mCreatePswdEntity){
                        if(mCreatePswdEntity.getData().getGenerateState().equals("1")){// 请求成功
                            tvPassword.setText(mCreatePswdEntity.getData().getPwdValue());
                        }else if(mCreatePswdEntity.getData().getGenerateState().equals("2")){//请求失败
                            mTvTip.setVisibility(View.VISIBLE);
                            mTvTip.setText(mCreatePswdEntity.getData().getMsg());
                            tvPassword.setText("000000");
                        }
                    }
                    break;
                case REMAIND_TICKET:
                    RemindEntity remindEntity = (RemindEntity) msg.obj;
                    if(null != remindEntity && null != remindEntity.getData()){
                        tvRemaidTicket.setText(String.valueOf(remindEntity.getData().getTicketNum()));
                    }
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribute_code);
        ButterKnife.bind(this);
        mContext = this;
        initStatusBar();
        initData();
        initView();
    }

    private void initView() {
        if (userLogin.getData().getRoleId() .equals( AppConfig.USER_LINGDAO)) {
            btnCreatePassword.setVisibility(View.GONE);
        } else if (userLogin.getData().getRoleId().equals(AppConfig.USER_YEWUYUAN)) {
            btnCheckTicket.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.btn_create_password)
    public void createPassword(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                findRemindTicket();
            }
        }).run();
        mTvTip.setVisibility(View.GONE);
        if(null != receiverBean){
            receiveId = receiverBean.getReceiveId();
        }
        if(null != receiverBean){
            receiveId = receiverBean.getReceiveId();
        }
        HttpUtils.with(this).url(AppConfig.requestUrl(AppConfig.CREATE_PASSWORD))
                .post()
                .addParam("userId", userLogin.getData().getUserId())
                .addParam("ticketType", codeType)
                .addParam("receiveId", receiveId)
                .execute(new HttpCallback<CreatePswdEntity>() {
                    @Override
                    public void onSuccess(CreatePswdEntity result) {
                        if (null != result.getData()) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = CREATE_PSWD;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    private void initData() {
        userLogin = GlobalApplication.getInstance().getUserLogin();
        codeTypeList.addAll(Arrays.asList(codeStrs));
        ArrayAdapter<String> codeTypeAdapter = new ArrayAdapter<String>(mContext
                , android.R.layout.simple_spinner_item
                , codeTypeList);
        codeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPswdType.setAdapter(codeTypeAdapter);
        spPswdType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        codeType = "14";
                        break;
                    case 1:
                        codeType = "11";
                        break;
                    case 2:
                        codeType = "13";
                        break;
                    case 3:
                        codeType = "12";
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                codeType = "14";
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SELECT_DRIVER:
                if(null != data){
                    ReceiverEntity mReceiverEntity = (ReceiverEntity) data.getSerializableExtra("receiver");
                    if(null != mReceiverEntity){
                        mTvDriver.setText(mReceiverEntity.getData().getReceiveName()
                                +"  ("+ mReceiverEntity.getData().getReceivePhone()+")");
                    }
                    receiverBean = (ReceiverListEntity.DataBean) data.getSerializableExtra("dataBean");
                    if(null != receiverBean){
                        mTvDriver.setText(receiverBean.getReceiveName()
                                +"  ("+receiverBean.getReceivePhone()+")");
                    }
                }
        }
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



    @OnClick(R.id.tv_select_driver)
    public void selectDriver(View view){
        Intent intent = new Intent(mContext,SelectDriverActivity.class);
        startActivityForResult(intent,SELECT_DRIVER);
    }

    private void findRemindTicket(){
        HttpUtils.with(this).url(AppConfig.requestUrl(AppConfig.REMAIND_TICKET))
                .post()
                .addParam("userId",userLogin.getData().getUserId())
                .addParam("ticketType",codeType)
                .execute(new HttpCallback<RemindEntity>() {
                    @Override
                    public void onSuccess(RemindEntity result) {
                        Message msg = mHandler.obtainMessage();
                        msg.what = REMAIND_TICKET;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @Override
    protected boolean onBackQuit() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
