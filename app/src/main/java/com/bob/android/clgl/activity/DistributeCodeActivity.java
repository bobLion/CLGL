package com.bob.android.clgl.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bob.android.clgl.R;
import com.bob.android.clgl.application.GlobalApplication;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.config.AppConfig;
import com.bob.android.clgl.dialog.EasyDialog;
import com.bob.android.clgl.entity.CreatePswdEntity;
import com.bob.android.clgl.entity.DriverEntity;
import com.bob.android.clgl.http.HttpCallback;
import com.bob.android.clgl.http.HttpUtils;
import com.bob.android.clgl.login.UserLogin;
import com.bob.android.clgl.widget.CustomEditText;

import java.util.ArrayList;
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
   /* @BindView(R.id.sp_driver)
    Spinner mSpDriver;*/
    @BindView(R.id.btn_create_password)
    Button btnCreatePassword;
    @BindView(R.id.btn_check_ticket)
    Button btnCheckTicket;
    @BindView(R.id.tv_select_driver)
    TextView mTvDriver;

    private final int CREATE_PSWD = 1111;
    private final int SELECT_DRIVER = 1112;
    private String DRIVER_INFO = "driverList";
    private String codeType;
    private Context mContext;
    private EasyDialog mDialog;
    private UserLogin userLogin;
    private CreatePswdEntity mCreatePswdEntity;
    private List<String> codeTypeList = new ArrayList();
    private List<String> driverNameList = new ArrayList<>();
    private List<DriverEntity> driverEntities = new ArrayList<>();
    private ArrayAdapter<String> driverAdapter;
    private String[] codeStrs = new String[]{"黄色(四分车)", "白色(社会车)", "蓝色(环卫三吨)", "紫色(环卫五吨)"};

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CREATE_PSWD:
                    mCreatePswdEntity = (CreatePswdEntity) msg.obj;
                    tvPassword.setText(mCreatePswdEntity.getData().getPwdValue());
                    tvRemaidTicket.setText(String.valueOf(mCreatePswdEntity.getCount()));
                    break;
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
        HttpUtils.with(this).url(AppConfig.requestUrl(AppConfig.CREATE_PASSWORD))
                .post()
                .addParam("userId", userLogin.getData().getUserId())
                .addParam("ticketType", codeType)
                .addParam("receiveId", 0)
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
        for (int i = 0; i < codeStrs.length; i++) {
            codeTypeList.add(codeStrs[i]);
        }
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
                        codeType = "13";
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

        /*if (driverNameList.size() == 0) {
            driverNameList.add("添加跟车人信息 +");
        } else if (driverNameList.size() > 1) {
            for (int i = 0; i < driverNameList.size() + 1; i++) {
                driverNameList.add(driverEntities.get(i).getDriverName());
            }
        }
        driverAdapter = new ArrayAdapter<String>(mContext
                , android.R.layout.simple_spinner_item
                , driverNameList);
        driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpDriver.setAdapter(driverAdapter);
        mSpDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    dialog();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SELECT_DRIVER:
                if(null != data){
                    driverEntities = GlobalApplication.getInstance().getDaoSession().getDriverEntityDao().loadAll();
                    int position = data.getIntExtra("position",-1);
                    if(position != -1){
                        mTvDriver.setText(driverEntities.get(position ).getDriverName()
                        +"  ("+driverEntities.get(position).getDriverNum()+")");
                    }else{
                        mTvDriver.setText(driverEntities.get(driverEntities.size()-1).getDriverName()
                                +"  ("+driverEntities.get(driverEntities.size()-1).getDriverNum()+")");
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
    @Override
    protected boolean onBackQuit() {
        return true;
    }
}
