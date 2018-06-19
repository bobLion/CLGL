package com.bob.android.clgl.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bob.android.clgl.R;
import com.bob.android.clgl.adapter.VehicleNumAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VehicleNumActivity extends AppCompatActivity {

    private ListView mLvVehicle;
    private List<String> vehicleNumList = new ArrayList<>();
    private LinearLayout root;

    private Context mContext;
    private VehicleNumAdapter adapter;
    private final static int TURN_TO_SELECT_VEHICLE_NUM = 1114;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_num);
        root = (LinearLayout)findViewById(R.id.root);
        initStatusBar();
        mContext = this;
        for (int i = 0; i < 10; i++) {
            vehicleNumList.add("沪A1234"+i);
        }

        mLvVehicle = (ListView)findViewById(R.id.lv_vehicle_num);
        if(vehicleNumList.size() != 0){
            adapter = new VehicleNumAdapter(mContext,vehicleNumList);
            mLvVehicle.setAdapter(adapter);
            mLvVehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.putExtra("vehicleNum",vehicleNumList.get(position));
                    setResult(TURN_TO_SELECT_VEHICLE_NUM,intent);
                    finish();
                }
            });
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
}
