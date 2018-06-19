package com.bob.android.clgl.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bob.android.clgl.R;
import com.bob.android.clgl.adapter.DriverAdapter;
import com.bob.android.clgl.application.GlobalApplication;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.database.greendao.DaoMaster;
import com.bob.android.clgl.database.greendao.DaoSession;
import com.bob.android.clgl.entity.DriverEntity;
import com.bob.android.clgl.widget.CustomEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private DriverAdapter adapter;
    private List<DriverEntity> driverEntityList = new ArrayList<>();
    private final int SELECT_DRIVER = 1112;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_driver);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        initStatusBar();
    }

    public void initView(){
        driverEntityList = GlobalApplication.getInstance().getDaoSession().getDriverEntityDao().loadAll();
        if(driverEntityList.size() != 0){
            mTvTip.setVisibility(View.GONE);
            lvSelectDriver.setVisibility(View.VISIBLE);
            adapter = new DriverAdapter(mContext,driverEntityList);
            lvSelectDriver.setAdapter(adapter);
            lvSelectDriver.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.putExtra("position",position);
                    setResult(SELECT_DRIVER,intent);
                    finish();
                }
            });
        }else{
            mTvTip.setVisibility(View.VISIBLE);
            lvSelectDriver.setVisibility(View.GONE);
        }

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
                dialog.dismiss();
//                Toast.makeText(mContext, "添加跟车人成功" + driverName.getText().toString().trim() + "  " + driverPhone.getText().toString().trim(), Toast.LENGTH_LONG).show();
                DriverEntity driverEntity = new DriverEntity();
                driverEntity.setDriverName(driverName.getText().toString().trim());
                driverEntity.setDriverNum(driverPhone.getText().toString().trim());
                driverEntityList.add(driverEntity);

                GlobalApplication.getInstance().getDaoSession().getDriverEntityDao().insert(driverEntity);
                Intent intent = new Intent();
                setResult(SELECT_DRIVER,intent);
                finish();
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
}
