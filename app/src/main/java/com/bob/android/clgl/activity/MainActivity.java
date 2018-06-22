package com.bob.android.clgl.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.bob.android.clgl.R;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.config.AppConfig;
import com.bob.android.clgl.dialog.EasyDialog;
import com.bob.android.clgl.entity.NewCarEntity;
import com.bob.android.clgl.entity.VehicleEntity;
import com.bob.android.clgl.http.HttpCallback;
import com.bob.android.clgl.http.HttpUtils;
import com.bob.android.clgl.util.Constant;
import com.bob.android.clgl.util.DialogManager;
import com.bob.android.clgl.widget.CustomEditText;
import com.bob.android.clgl.zxing.activity.CaptureActivity;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.rey.material.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_main_home)
    ImageView ivMainHome;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.tip_vehicle_ticket)
    TextView tipVehicleTicket;
    @BindView(R.id.edt_vehicle_ticket)
    CustomEditText edtVehicleTicket;
    @BindView(R.id.img_scan)
    ImageView imgScan;
    @BindView(R.id.edt_vehicle_ticket_type)
    CustomEditText edtVehicleTicketType;
    @BindView(R.id.edt_vehicle_num)
    TextView edtVehicleNum;
    @BindView(R.id.sp_vehicle_type)
    Spinner spVehicleType;
    @BindView(R.id.sp_vehicle_num)
    Spinner spVehicleNum;
    @BindView(R.id.lin_sps)
    LinearLayout linSps;
    @BindView(R.id.lin_vehicle_num)
    LinearLayout linVehicleNum;
    @BindView(R.id.edt_vehicle_department)
    CustomEditText edtVehicleDepartment;
    @BindView(R.id.edt_vehicle_weight)
    CustomEditText edtVehicleWeight;
    @BindView(R.id.edt_vehicle_all_weight)
    CustomEditText edtVehicleAllWeight;
    @BindView(R.id.edt_current_date_time)
    CustomEditText edtCurrentDateTime;
    @BindView(R.id.btn_get_data)
    Button btnGetData;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.switch_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private String qrcode = "";
    private EasyDialog mDialog;
    private int model = 0;//0 语音输入 1 手动输入

    private final static int GET_VEHICLE_INFO = 1111;
    private final static int CONFIRM_SUCCESS = 1112;
    private final static int CONFIRM_FAILURE = 1113;
    private final static int TURN_TO_SELECT_VEHICLE_NUM = 1114;
    /* 语音识别对象 */
    private SpeechRecognizer recognizer;
    //识别出来的句子
    StringBuilder sentence = null;
    private int pwdId = -1;
    private String ticketType = "";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_VEHICLE_INFO:
                    mSwipeRefresh.setRefreshing(false);
                    model = 0;
                    imgScan.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic_black_24dp));
                    NewCarEntity mNewCarEntity = (NewCarEntity) msg.obj;
                    if (null != mNewCarEntity && null!= mNewCarEntity.getState()) {
                        if(mNewCarEntity.getState().equals(AppConfig.RESPONSE_FAILURE)){
                            Toast.makeText(mContext, mNewCarEntity.getMsg(),Toast.LENGTH_LONG).show();
                            mSwipeRefresh.setRefreshing(false);
                            return;
                        }
                        pwdId = mNewCarEntity.getPwdId();
                        ticketType = mNewCarEntity.getJsonStr().getInvoiceType();
                        String cleanWeight = mNewCarEntity.getJsonStr().get净重().substring(0, mNewCarEntity.getJsonStr().get净重().indexOf("."));
                        String allWeight = mNewCarEntity.getJsonStr().get毛重().substring(0, mNewCarEntity.getJsonStr().get毛重().indexOf("."));
                        edtVehicleAllWeight.setText(allWeight);
                        edtVehicleWeight.setText(cleanWeight);
                        edtVehicleNum.setText(mNewCarEntity.getJsonStr().get车号());
                        edtVehicleDepartment.setText(mNewCarEntity.getJsonStr().getAreaName());
                        edtVehicleTicketType.setText(mNewCarEntity.getJsonStr().getInvoiceType());
                        DateFormat fmt = new SimpleDateFormat("yyyyMMddHHmm");
                        String dateStr = fmt.format(System.currentTimeMillis());
                        edtCurrentDateTime.setText(dateStr);
                    }
                    break;
                case CONFIRM_SUCCESS:
                    qrcode = "";
                    pwdId = -1;
                    ticketType = "";
                    setValueNull();
                    model = 0;
                    imgScan.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic_black_24dp));
                    Toast.makeText(mContext, "信息确认成功！", Toast.LENGTH_LONG).show();
                    break;
                case CONFIRM_FAILURE:
                    Toast.makeText(mContext, "信息确认失败！", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;

            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        initStatusBar();

        //初始化语音对象
        recognizer = SpeechRecognizer.createRecognizer(this, mInitListener);
        SpeechSynthesizer speaker = SpeechSynthesizer.createSynthesizer(this, mInitListener);

        //设置听写参数
        recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        //设置为中文
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置为普通话
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin ");
        //设置发音人
        speaker.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置语速
        speaker.setParameter(SpeechConstant.SPEED, "30");
        //设置音量，范围0~100
        speaker.setParameter(SpeechConstant.VOLUME, "200");
        //设置云端
        speaker.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
    }

    //初始化监听器。
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d("TAG", "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(MainActivity.this, "初始化失败，错误码：" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void initView() {
        imgScan.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        linVehicleNum.setOnClickListener(this);
        edtVehicleTicket.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                model = 1;
                imgScan.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));
            }
        });
        mSwipeRefresh.setEnabled(true);
        mSwipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
    }

    // 开始扫码
    private void startQrCode() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        // 二维码扫码
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
            //将扫描出的信息显示出来
            qrcode = scanResult.trim();
            edtVehicleTicket.setText(qrcode.substring(0, qrcode.length() - 1));
            qrcode = edtVehicleTicket.getText().toString();
            @SuppressLint("SimpleDateFormat")
            DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String dateStr = fmt.format(System.currentTimeMillis());
            edtCurrentDateTime.setText(dateStr);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    requestData();
                }
            }).run();
        } else if (requestCode == TURN_TO_SELECT_VEHICLE_NUM ) {
            if(null != data){
                String vehicleNum = data.getStringExtra("vehicleNum");
                edtVehicleNum.setText(vehicleNum);
            }
        }
    }

    private void requestData() {
        qrcode = edtVehicleTicket.getText().toString().trim();
        if(qrcode.equals("")){
            model = 0;
            imgScan.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic_black_24dp));
            Toast.makeText(mContext,"请输入口令密码！",Toast.LENGTH_LONG).show();
            setValueNull();
            mSwipeRefresh.setRefreshing(false);
            return;
        }
        if (null == mDialog) {
            mDialog = DialogManager.getCircularDialog(mContext, true);
        }
        mDialog.show();
        HttpUtils.with(this).url(AppConfig.requestUrl(AppConfig.GET_VEHICLE_INFO))
                .addParam("pwdValue",qrcode)
                .post()
                .execute(new HttpCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                        }
                        NewCarEntity newCarEntity = JSON.parseObject(result, NewCarEntity.class);
                        Message msg = mHandler.obtainMessage();
                        msg.what = GET_VEHICLE_INFO;
                        msg.obj = newCarEntity;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Exception e) {

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
    protected boolean onBackQuit() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_vehicle_num:
                Intent intent = new Intent(MainActivity.this, VehicleNumActivity.class);
                startActivityForResult(intent, TURN_TO_SELECT_VEHICLE_NUM);
                break;
            case R.id.img_scan:
                if(model == 0){
                    recognizer.startListening(recognizerListener);
                    setValueNull();
                }else if(model == 1){
                    qrcode = edtVehicleTicket.getText().toString().trim();
                    requestData();
                }
                break;
            case R.id.btn_confirm:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        uploadInfo();
                    }
                }).run();
                break;
            default:
                break;

        }
    }

    private void uploadInfo() {
        if (null != mDialog && !mDialog.isShowing()) {
            mDialog = DialogManager.getCircularDialog(mContext, true);
        }
        String pwdValue = edtVehicleTicket.getText().toString().trim();
        String vehicleNum = edtVehicleNum.getText().toString().trim();
        String department = edtVehicleDepartment.getText().toString().trim();
        String vehicleAllWeight = edtVehicleAllWeight.getText().toString().trim();
        String vehicleWeight = edtVehicleWeight.getText().toString().trim();
//        String currentTime = edtCurrentDateTime.getText().toString().trim();
        HttpUtils.with(this).url(AppConfig.requestUrl(AppConfig.UPLOAD_VEHICLE_INFO))
                .post()
                .addParam("pwdValue",pwdValue)
                .addParam("pwdId",pwdId)
                .addParam("ticketType",ticketType)
                .addParam("carNo", vehicleNum)
                .addParam("netWeight", vehicleWeight)
                .addParam("roughWeight", vehicleAllWeight)
                .addParam("unit", department)
                .execute(new HttpCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (null != mDialog) {
                    mDialog.dismiss();
                }
                if (result.contains(AppConfig.RESPONSE_FAILURE)) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = CONFIRM_FAILURE;
                    mHandler.sendMessage(msg);
                }else{
                    Message msg = mHandler.obtainMessage();
                    msg.what = CONFIRM_SUCCESS;
                    mHandler.sendMessage(msg);
                }
            }

            @Override
            public void onError(Exception e) {
                if (null != mDialog) {
                    mDialog.dismiss();
                }
                Message msg = mHandler.obtainMessage();
                msg.what = CONFIRM_FAILURE;
            }
        });
    }


    //听写监听器
    private RecognizerListener recognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
        }

        /**
         * 开始录音
         */
        @Override
        public void onBeginOfSpeech() {
            Toast.makeText(MainActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
            edtVehicleTicket.setText("");
        }

        /**
         * 结束录音
         */
        @Override
        public void onEndOfSpeech() {

            //结束录音后，根据识别出来的句子，通过语音合成进行反馈
            Toast.makeText(MainActivity.this, "结束录音", Toast.LENGTH_SHORT).show();
            Pattern pattern = Pattern.compile("[0-9]{1,}");
            if(null!=sentence && !sentence.equals("") && !sentence.equals("。")){
                Matcher matcher = pattern.matcher((CharSequence)sentence.toString());
                boolean result = matcher.matches();
                if(result){
                    edtVehicleTicket.setText(sentence.toString());
                    requestData();
                }else{
                    model = 0;
                    imgScan.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic_black_24dp));
                    Toast.makeText(mContext,"仅支持数字！",Toast.LENGTH_LONG).show();
                }
            }else{
                model = 0;
                imgScan.setImageDrawable(getResources().getDrawable(R.drawable.ic_mic_black_24dp));
                Toast.makeText(mContext,"请使用语音或者手动输入密码！",Toast.LENGTH_LONG).show();
                return;
            }
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String result = recognizerResult.getResultString();
            try {
                //处理json结果
                JSONObject jsonObject = JSONObject.parseObject(result);
                JSONArray words = jsonObject.getJSONArray("ws");
                //拼成句子
                sentence = new StringBuilder("");
                for (int i = 0; i < words.size(); i++) {
                    JSONObject word = words.getJSONObject(i);
                    JSONArray subArray = word.getJSONArray("cw");
                    JSONObject subWord = subArray.getJSONObject(0);
                    String character = subWord.getString("w");
                    sentence.append(character);
                }
                //打印
                Log.e("TAG", sentence.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };


    //语音合成监听器
    private SynthesizerListener synthesizerListener = new SynthesizerListener() {
        /**
         * 开始播放
         */
        @Override
        public void onSpeakBegin() {

        }
        /**
         * 缓冲进度回调
         */
        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }
        /**
         * 暂停播放
         */
        @Override
        public void onSpeakPaused() {

        }
        /**
         * 恢复播放回调接口
         */
        @Override
        public void onSpeakResumed() {

        }

        /**
         * 播放进度回调
         */
        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }
        /**
         * 会话结束回调接口，没有错误时，error为null
         */
        @Override
        public void onCompleted(SpeechError speechError) {

        }

        /**
         * 会话事件回调接口
         */
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    private void setValueNull(){
        edtVehicleTicket.setText("");
        edtVehicleTicketType.setText("");
        edtVehicleNum.setText("");
        edtVehicleDepartment.setText("");
        edtVehicleWeight.setText("");
        edtVehicleAllWeight.setText("");
        edtCurrentDateTime.setText("");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}