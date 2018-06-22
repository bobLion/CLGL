package com.bob.android.clgl.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bob.android.clgl.R;
import com.bob.android.clgl.adapter.TicketAdapter;
import com.bob.android.clgl.adapter.VehicleCountAdapter;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.config.AppConfig;
import com.bob.android.clgl.entity.VehicleCountEntity;
import com.bob.android.clgl.http.HttpCallback;
import com.bob.android.clgl.http.HttpUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoadVehicleDataActivity extends BaseActivity {

    @BindView(R.id.iv_main_home)
    ImageView ivMainHome;
    @BindView(R.id.lv_vehicle)
    PullToRefreshListView lvVehicle;
    @BindView(R.id.root)
    LinearLayout root;

    private String startTime = "";
    private String endTime = "";
    private String areaId;
    private int page = 0;
    private int limit = 15;
    private int totalPages = 0;
    private static final int MAX_PAGES = 100;
    private VehicleCountEntity vehicleCountEntity;
    private List<VehicleCountEntity.DataBean> vehicleCountList = new ArrayList<>();
    private List<VehicleCountEntity.DataBean> vehicleList = new ArrayList<>();
    private VehicleCountAdapter adapter;
    private final int GET_DATA_SUCCESS = 1000;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET_DATA_SUCCESS:
                    adapter = new VehicleCountAdapter(mContext, vehicleList);
                    lvVehicle.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    lvVehicle.onRefreshComplete();
                    break;
                    default:break;
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_vehicle_data);
        ButterKnife.bind(this);
        initStatusBar();
        initView();
        initData();
        loadVehicleData(false, false, page, limit);
    }

    private void initView(){
        pullRefreshListView();// 下拉刷新列表
    }

    private void initData(){
        areaId = getIntent().getStringExtra("areaId");
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
    }

    private void pullRefreshListView() {
        //pullRefreshListView.setOnItemClickListener(this);// 注册点击事件
//		pullRefreshListView.setOnCreateContextMenuListener(this);// 注册一个上下文菜单
        lvVehicle.setMode(PullToRefreshBase.Mode.BOTH);
        lvVehicle.setScrollingWhileRefreshingEnabled(false);
        lvVehicle.getLoadingLayoutProxy().setRefreshingLabel("正在加载...");
        lvVehicle.getLoadingLayoutProxy().setReleaseLabel("释放刷新数据");
        lvVehicle.getLoadingLayoutProxy().setPullLabel("拉动刷新数据");
        lvVehicle.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(mContext,
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                if (totalPages > 0) {
                    if (lvVehicle.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END) {
                        page ++;
                        if (page >= totalPages) {
                            Toast.makeText(mContext, "已经到最后一页",
                                    Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    lvVehicle.onRefreshComplete();
                                }
                            }, 500);

                        } else {
                            if (page >= MAX_PAGES) {
                                Toast.makeText(mContext,
                                        "最多允许查看" + MAX_PAGES + "页数据",
                                        Toast.LENGTH_SHORT).show();
                                new Handler().postDelayed(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                lvVehicle.onRefreshComplete();
                                            }
                                        }, 500);
                            } else {
                                loadVehicleData(false, false, page,
                                        limit);
                            }
                        }
                    } else if (lvVehicle.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START) {
                        page = 0;
                        loadVehicleData(false, true, page, limit);
                    }
                } else {
                    if (lvVehicle.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END) {
                        page++;
                        loadVehicleData(false, false, page, limit);
                    } else if (lvVehicle.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START) {
                        page = 0;
                        loadVehicleData(false, true, page, limit);
                    }
                }
            }
        });
        ListView actualListView = lvVehicle.getRefreshableView();
        vehicleList = new ArrayList<VehicleCountEntity.DataBean>();
        adapter = new VehicleCountAdapter(mContext, vehicleList);
        actualListView.setAdapter(adapter);
    }

    /**
     * 查询区域进车量
     * @param showDialog
     * @param fromStart
     * @param pageIndex
     * @param pageSize
     */
    private void loadVehicleData(final boolean showDialog, final boolean fromStart,
                                 int pageIndex, int pageSize) {
        HttpUtils.with(this).url(AppConfig.requestUrl(AppConfig.CHECK_BY_AREA))
                .post()
                .addParam("page",pageIndex)
                .addParam("limit",pageSize)
                .addParam("areaId",areaId)
                .addParam("startDate",startTime)
                .addParam("endDate",endTime)
                .execute(new HttpCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        vehicleCountEntity = JSON.parseObject(result,VehicleCountEntity.class);
                        vehicleCountList = vehicleCountEntity.getData();
//                        if (fromStart) {
                            vehicleList.clear();
//                        }
                        if (null != vehicleCountList && vehicleCountList.size() > 0) {
                            vehicleList.addAll(vehicleCountList);
                        } else {
                            page --;
                            if (page < 0) {
                                page = 0;
                            }
                        }
                        if (vehicleList.size() == 0) {
                            lvVehicle.setVisibility(View.GONE);
                        } else {
                            lvVehicle.setVisibility(View.VISIBLE);
                        }
                        Message msg = mHandler.obtainMessage();
                        msg.what = GET_DATA_SUCCESS;
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

    @OnClick(R.id.iv_main_home)
    public void backHome(View view){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
