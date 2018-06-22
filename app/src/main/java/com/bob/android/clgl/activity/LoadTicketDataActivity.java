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
import com.bob.android.clgl.adapter.TicketCountAdapter;
import com.bob.android.clgl.adapter.VehicleCountAdapter;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.config.AppConfig;
import com.bob.android.clgl.entity.TicketCountEntity;
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

public class LoadTicketDataActivity extends BaseActivity {

    @BindView(R.id.iv_main_home)
    ImageView ivMainHome;
    @BindView(R.id.lv_tickets)
    PullToRefreshListView lvTickets;
    @BindView(R.id.root)
    LinearLayout root;

    private String areaId;
    private String ticketType;
    private int page = 0;
    private int limit = 15;
    private int totalPages = 0;
    private static final int MAX_PAGES = 100;
    private TicketCountEntity ticketCountEntity;
    private List<TicketCountEntity.DataBean> ticketCountList = new ArrayList<>();
    private List<TicketCountEntity.DataBean> ticketList = new ArrayList<>();
    private TicketCountAdapter adapter;
    private final int GET_DATA_SUCCESS = 1000;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_DATA_SUCCESS:
                    adapter = new TicketCountAdapter(mContext, ticketList);
                    lvTickets.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    lvTickets.onRefreshComplete();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_ticket);
        ButterKnife.bind(this);
        initStatusBar();
        initView();
        initData();
        loadTicketData(false, false, page, limit);
    }

    private void initView(){
        pullRefreshListView();// 下拉刷新列表
    }

    private void initData(){
        areaId = getIntent().getStringExtra("areaId");
        ticketType = getIntent().getStringExtra("ticketType");
    }

    private void pullRefreshListView() {
        //pullRefreshListView.setOnItemClickListener(this);// 注册点击事件
//		pullRefreshListView.setOnCreateContextMenuListener(this);// 注册一个上下文菜单
        lvTickets.setMode(PullToRefreshBase.Mode.BOTH);
        lvTickets.setScrollingWhileRefreshingEnabled(false);
        lvTickets.getLoadingLayoutProxy().setRefreshingLabel("正在加载...");
        lvTickets.getLoadingLayoutProxy().setReleaseLabel("释放刷新数据");
        lvTickets.getLoadingLayoutProxy().setPullLabel("拉动刷新数据");
        lvTickets.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
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
                    if (lvTickets.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END) {
                        page ++;
                        if (page >= totalPages) {
                            Toast.makeText(mContext, "已经到最后一页",
                                    Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    lvTickets.onRefreshComplete();
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
                                                lvTickets.onRefreshComplete();
                                            }
                                        }, 500);
                            } else {
                                loadTicketData(false, false, page,
                                        limit);
                            }
                        }
                    } else if (lvTickets.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START) {
                        page = 0;
                        loadTicketData(false, true, page, limit);
                    }
                } else {
                    if (lvTickets.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END) {
                        page++;
                        loadTicketData(false, false, page, limit);
                    } else if (lvTickets.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START) {
                        page = 0;
                        loadTicketData(false, true, page, limit);
                    }
                }
            }
        });
        ListView actualListView = lvTickets.getRefreshableView();
        ticketList = new ArrayList<TicketCountEntity.DataBean>();
        adapter = new TicketCountAdapter(mContext, ticketList);
        actualListView.setAdapter(adapter);
    }

    /**
     * 查询区域票据数量
     *
     * @param showDialog
     * @param fromStart
     * @param pageIndex
     * @param pageSize
     */
    private void loadTicketData(final boolean showDialog, final boolean fromStart,
                                int pageIndex, int pageSize) {
        HttpUtils.with(this).url(AppConfig.requestUrl(AppConfig.CHECK_TICKET))
                .post()
                .addParam("page", pageIndex)
                .addParam("limit", pageSize)
                .addParam("areaId", areaId)
                .addParam("invoiceType", ticketType)
                .execute(new HttpCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        ticketCountEntity = JSON.parseObject(result,TicketCountEntity.class);
                        ticketCountList = ticketCountEntity.getData();
//                        if (fromStart) {
                        ticketList.clear();
//                        }
                        if (null != ticketCountList && ticketCountList.size() > 0) {
                            ticketList.addAll(ticketCountList);
                        } else {
                            page --;
                            if (page < 0) {
                                page = 0;
                            }
                        }
                        if (ticketList.size() == 0) {
                            lvTickets.setVisibility(View.GONE);
                        } else {
                            lvTickets.setVisibility(View.VISIBLE);
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
