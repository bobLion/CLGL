package com.bob.android.clgl.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bob.android.clgl.R;
import com.bob.android.clgl.adapter.VehicleNumAdapter;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.config.AppConfig;
import com.bob.android.clgl.dialog.EasyDialog;
import com.bob.android.clgl.entity.AreaEntity;
import com.bob.android.clgl.entity.UnitEntity;
import com.bob.android.clgl.entity.VehicleNumEntity;
import com.bob.android.clgl.http.HttpCallback;
import com.bob.android.clgl.http.HttpUtils;
import com.bob.android.clgl.util.DialogManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VehicleNumActivity extends BaseActivity {

    @BindView(R.id.iv_main_home)
    ImageView ivMainHome;
    @BindView(R.id.sp_area)
    Spinner spArea;
    @BindView(R.id.sp_unit)
    Spinner spUnit;

    private ListView mLvVehicle;
    private LinearLayout root;
    private Context mContext;
    private VehicleNumAdapter adapter;
    private final static int TURN_TO_SELECT_VEHICLE_NUM = 1114;
    private final static int AREA_ENTITY_LIST = 1110;
    private final static int UNIT_ENTITY_LIST = 1111;
    private final static int GET_VEHICLE_NUM = 1112;
    private List<AreaEntity.DataBean> areaEntityList = new ArrayList<>();
    private List<String> areaNameList = new ArrayList<>();

    private List<UnitEntity.DataBean> unitEntityList = new ArrayList<>();
    private List<String> unitNameList = new ArrayList<>();

    private  List<VehicleNumEntity.DataBean> vehicleNumList = new ArrayList<>();
    private String areaId;
    private String unitId;
    private EasyDialog mDialog;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AREA_ENTITY_LIST:
                    String result = (String) msg.obj;
                    AreaEntity areaEntity = JSON.parseObject(result, AreaEntity.class);
                    if (null != areaEntity.getData()) {
                        areaEntityList = areaEntity.getData();
                        String[] area = new String[areaEntityList.size() + 1];
                        for (int i = 0; i < areaEntityList.size(); i++) {
                            area[i] = areaEntityList.get(i).getDicDataName();
                        }
                        area[areaEntityList.size()] = "请选择";
                        areaNameList.addAll(Arrays.asList(area));
                        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(mContext
                                , android.R.layout.simple_spinner_item
                                , areaNameList);
                        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spArea.setAdapter(areaAdapter);
                        spArea.setSelection(areaEntityList.size());
                        spArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != areaEntityList.size()) {
                                    areaId = areaEntityList.get(position).getDicDataValue();
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
                case UNIT_ENTITY_LIST:
                        String unitStr = (String) msg.obj;
                        UnitEntity unitEntity = JSON.parseObject(unitStr, UnitEntity.class);
                        if (null != unitEntity.getData()) {
                            unitEntityList = unitEntity.getData();
                            String[] unit = new String[unitEntityList.size() + 1];
                            for (int i = 0; i < unitEntityList.size(); i++) {
                                unit[i] = unitEntityList.get(i).getDicDataName();
                            }
                            unit[unitEntityList.size()] = "请选择";
                            unitNameList.addAll(Arrays.asList(unit));

                            ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(mContext
                                    , android.R.layout.simple_spinner_item
                                    , unitNameList);
                            unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spUnit.setAdapter(unitAdapter);
                            spUnit.setSelection(unitEntityList.size());
                            spUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position != unitEntityList.size()) {
                                        unitId = unitEntityList.get(position).getDicDataValue();
                                    } else {
                                        unitId = "";
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    unitId = "";
                                }
                            });
                        }
                    break;
                case GET_VEHICLE_NUM:
                    vehicleNumList = (List<VehicleNumEntity.DataBean>)msg.obj;
                    if(null != vehicleNumList && vehicleNumList.size() != 0){
                        setVehicleAdapter(vehicleNumList);
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_num);
        ButterKnife.bind(this);
        root = (LinearLayout) findViewById(R.id.root);
        mContext = this;
        initStatusBar();
        initData();

    }

    private void setVehicleAdapter(final List<VehicleNumEntity.DataBean> vehicleNumList) {
        mLvVehicle = (ListView) findViewById(R.id.lv_vehicle_num);
        if (vehicleNumList.size() != 0) {
            adapter = new VehicleNumAdapter(mContext, vehicleNumList);
            mLvVehicle.setAdapter(adapter);
            mLvVehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.putExtra("vehicleNum", vehicleNumList.get(position).getCarno());
                    setResult(TURN_TO_SELECT_VEHICLE_NUM, intent);
                    finish();
                }
            });
        }
    }

    private void initData() {
        getAreaDictionary();
        getUnitDictionary();
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

    private void getUnitDictionary() {
        HttpUtils.with(mContext).url(AppConfig.requestUrl(AppConfig.GET_UNIT))
                .get()
                .execute(new HttpCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (null != mDialog) {
                            mDialog.dismiss();
                        }
                        if (null != result) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = UNIT_ENTITY_LIST;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

    }

    private void getVehicleListFromAreaId(){
        HttpUtils.with(mContext).url(AppConfig.requestUrl(AppConfig.GET_CAR_NUM_BY_AREA_ID))
                .post()
                .addParam("areaId",areaId)
                .execute(new HttpCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        VehicleNumEntity vehicleNumEntity = JSON.parseObject(result,VehicleNumEntity.class);
                        if(null != vehicleNumEntity.getData()){
                            Message msg = mHandler.obtainMessage();
                            msg.what = GET_VEHICLE_NUM;
                            msg.obj = vehicleNumEntity.getData();
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    private void getVehicleListFromUnitId(){
        HttpUtils.with(mContext).url(AppConfig.requestUrl(AppConfig.GET_CAR_NUM_BY_UNIT))
                .post()
                .addParam("company",unitId)
                .execute(new HttpCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        VehicleNumEntity vehicleNumEntity = JSON.parseObject(result,VehicleNumEntity.class);
                        if(null != vehicleNumEntity.getData()) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = GET_VEHICLE_NUM;
                            msg.obj = vehicleNumEntity.getData();
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @OnClick(R.id.btn_find_vehicle_num)
    public void findVehicle(View view){
        if(areaId.equals("") && unitId.equals("")){
            Toast.makeText(mContext,"请至少选择一项查找条件",Toast.LENGTH_LONG).show();
            return;
        }
        if(!areaId.equals("")){
            getVehicleListFromAreaId();
        }
        if(!unitId.equals("")){
            getVehicleListFromUnitId();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean onBackQuit() {
        return false;
    }

    @OnClick(R.id.iv_main_home)
    public void backHome(View view){
        finish();
    }

}
