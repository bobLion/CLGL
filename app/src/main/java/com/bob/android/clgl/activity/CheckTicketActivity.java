package com.bob.android.clgl.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bob.android.clgl.R;
import com.bob.android.clgl.application.GlobalApplication;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.config.AppConfig;
import com.bob.android.clgl.dialog.EasyDialog;
import com.bob.android.clgl.entity.AreaEntity;
import com.bob.android.clgl.entity.RemindEntity;
import com.bob.android.clgl.http.HttpCallback;
import com.bob.android.clgl.http.HttpUtils;
import com.bob.android.clgl.login.UserLogin;
import com.bob.android.clgl.util.DialogManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckTicketActivity extends BaseActivity {

    @BindView(R.id.btn_remained_ticket)
    Button btnRemainedTicket;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.sp_department)
    Spinner spDepartment;
    @BindView(R.id.sp_ticket_type)
    Spinner spTicketType;
    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    private String ticketType;

    private EasyDialog mDialog;
    private UserLogin mUserLogin;
    private final int REMAIND_TICKET = 1110;
    private final int AREA_ENTITY_LIST = 1113;
    private List<String> codeTypeList = new ArrayList<String>();
    private List<String> departmentNameList = new ArrayList<>();

    private String areaId;
    private Calendar calendar;
    private String endTime = "";
    private String startTime = "";
    private DatePickerDialog dialog;
    private List<AreaEntity.DataBean> areaEntyList = new ArrayList<>();
    private String[] codeStrs = new String[]{"请选择", "黄色(四分车)", "白色(社会车)"
            , "蓝色(环卫三吨)", "紫色(环卫五吨)"};


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AREA_ENTITY_LIST:
                    String result = (String) msg.obj;
                    AreaEntity areaEntity = JSON.parseObject(result, AreaEntity.class);
                    if (null != areaEntity.getData()) {
                        areaEntyList = areaEntity.getData();
                        String[] department = new String[areaEntyList.size() + 1];
                        for (int i = 0; i < areaEntyList.size(); i++) {
                            department[i] = areaEntyList.get(i).getDicDataName();
                        }
                        department[areaEntyList.size()] = "请选择";
                        /*for (int i = 0; i < department.length; i++) {
                            departmentNameList.add(department[i]);
                        }*/
                        departmentNameList.addAll(Arrays.asList(department));

                        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(mContext
                                , android.R.layout.simple_spinner_item
                                , departmentNameList);
                        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spDepartment.setAdapter(departmentAdapter);
                        spDepartment.setSelection(areaEntyList.size());
                        spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != areaEntyList.size()) {
                                    areaId = areaEntyList.get(position).getDicDataValue();
                                } else {
                                    areaId = "";
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                areaId = "";
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ticket);
        ButterKnife.bind(this);
        mContext = this;
        initStatusBar();
        initData();
    }


    @OnClick({R.id.btn_vehicle_data, R.id.btn_ticket_data, R.id.btn_remained_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_vehicle_data:
                if(startTime.equals("")){
                    if(areaId .equals("")){
                        Toast.makeText(mContext,"请选择查询区域", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Toast.makeText(mContext,"请选择开始时间", Toast.LENGTH_LONG).show();
                    return;
                }
                if(endTime.equals("")){
                    Toast.makeText(mContext,"请选择结束时间", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(mContext,LoadVehicleDataActivity.class);
                intent.putExtra("startTime",startTime);
                intent.putExtra("endTime",startTime);
                intent.putExtra("areaId",areaId);
                startActivity(intent);
                break;
            case R.id.btn_ticket_data:
                if(areaId .equals("")){
                    Toast.makeText(mContext,"请选择查询区域", Toast.LENGTH_LONG).show();
                    return;
                }
                if(ticketType.equals("")){
                    Toast.makeText(mContext,"请选择票据种类", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent ticketIntent = new Intent(mContext,LoadTicketDataActivity.class);
                ticketIntent.putExtra("ticketType",ticketType);
                ticketIntent.putExtra("areaId",areaId);
                startActivity(ticketIntent);
                break;
            case R.id.btn_remained_ticket:
                break;
        }
    }

    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAreaDictionary();
            }
        }).run();
        mUserLogin = GlobalApplication.getInstance().getUserLogin();
        /*for (int i = 0; i < codeStrs.length; i++) {
            codeTypeList.add(codeStrs[i]);
        }*/
        codeTypeList.addAll(Arrays.asList(codeStrs));

        ArrayAdapter<String> codeTypeAdapter = new ArrayAdapter<String>(mContext
                , android.R.layout.simple_spinner_item
                , codeTypeList);
        codeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTicketType.setAdapter(codeTypeAdapter);
        spTicketType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ticketType = "";
                        break;
                    case 1:
                        ticketType = "14";
                        break;
                    case 2:
                        ticketType = "11";
                        break;
                    case 3:
                        ticketType = "13";
                        break;
                    case 4:
                        ticketType = "12";
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ticketType = "";
            }
        });
    }


    private void getAreaDictionary() {
        if (null == mDialog) {
            mDialog = DialogManager.getCircularDialog(mContext, true);
        }
        mDialog.show();
        HttpUtils.with(this).url(AppConfig.requestUrl(AppConfig.GET_AREA_DICTIONARY))
                .get()
                .execute(new HttpCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                        }
                        if (null != result) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = AREA_ENTITY_LIST;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

    }

    private void checkRemaind() {
        HttpUtils.with(this).url(AppConfig.requestUrl(AppConfig.REMAIND_TICKET))
                .post()
                .addParam("userId", mUserLogin.getData().getUserId())
                .addParam("ticketType", ticketType)
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

    @OnClick(R.id.lin_start_time)
    public void setStartTimeClick(View view){
        setStartTime();
    }

    @OnClick(R.id.lin_end_time)
    public void setEndTimeClick(View view){
        setEndTime();
    }

    private void setStartTime() {
        calendar = Calendar.getInstance();
        dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view,
                                  int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                String mMM="";
                String mDD="";
                int mMonthOfYear=monthOfYear+1;
                if (mMonthOfYear<10){
                    mMM="0"+mMonthOfYear;
                }else{
                    mMM=""+mMonthOfYear;
                }
                if (dayOfMonth<10){
                    mDD="0"+dayOfMonth;
                }else{
                    mDD=""+dayOfMonth;
                }
                tvStartTime.setText(year  + mMM +  mDD);
                startTime = tvStartTime.getText().toString().trim();
            }
        }, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void setEndTime() {
        calendar = Calendar.getInstance();
        dialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view,
                                  int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                String mMM="";
                String mDD="";
                int mMonthOfYear=monthOfYear+1;
                if (mMonthOfYear<10){
                    mMM="0"+mMonthOfYear;
                }else{
                    mMM=""+mMonthOfYear;
                }
                if (dayOfMonth<10){
                    mDD="0"+dayOfMonth;
                }else{
                    mDD=""+dayOfMonth;
                }
                tvEndTime.setText(year + mMM +  mDD);
                endTime = tvEndTime.getText().toString().trim();
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                try {
                    Date startDate = df.parse(endTime);
                    Date endDate = df.parse(endTime);
                    if (endDate.getTime() < startDate.getTime()) {
                        Toast.makeText(mContext, "开始时间大于结束时间", Toast.LENGTH_SHORT).show();
                        tvEndTime.setText("yyyMMdd");
                        endTime = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        dialog.show();
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
    protected boolean onBackQuit() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
