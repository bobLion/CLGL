package com.bob.android.clgl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bob.android.clgl.R;
import com.bob.android.clgl.application.GlobalApplication;
import com.bob.android.clgl.base.BaseActivity;
import com.bob.android.clgl.login.UserLogin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckTicketActivity extends BaseActivity {

    @BindView(R.id.btn_used_ticket)
    Button btnUsedTicket;
    @BindView(R.id.un_used_ticket)
    Button unUsedTicket;
    @BindView(R.id.btn_remained_ticket)
    Button btnRemainedTicket;
    @BindView(R.id.lv_tickets)
    ListView lvTickets;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.sp_department)
    Spinner spDepartment;
    @BindView(R.id.sp_ticket_type)
    Spinner spTicketType;
    private UserLogin userLogin;
    @BindView(R.id.tv_select)
    TextView mTvTip;

    private List<String> codeTypeList = new ArrayList();
    private List<String> departmentList = new ArrayList<>();
    private String[] codeStrs = new String[]{"黄色(四分车)", "白色(社会车)", "蓝色(环卫三吨)",
            "紫色(环卫五吨)"};
    private String[] department = new String[]{"长白","新江湾城","延吉","定海","五角场","四平",
            "控江","大桥","平凉","江浦","殷行","五镇","五街"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ticket);
        ButterKnife.bind(this);
        mContext = this;
        initStatusBar();
        initData();
    }

    @OnClick({R.id.btn_used_ticket, R.id.un_used_ticket, R.id.btn_remained_ticket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_used_ticket:
                break;
            case R.id.un_used_ticket:
                break;
            case R.id.btn_remained_ticket:
                break;
        }
    }

    public void initData(){
        lvTickets.setVisibility(View.GONE);
        mTvTip.setVisibility(View.VISIBLE);
        userLogin = GlobalApplication.getInstance().getUserLogin();
        for (int i = 0; i < codeStrs.length; i++) {
            codeTypeList.add(codeStrs[i]);
        }
        for (String str : department) {
            departmentList.add(str);
        }

        ArrayAdapter<String> codeTypeAdapter = new ArrayAdapter<String>(mContext
                , android.R.layout.simple_spinner_item
                , codeTypeList);
        codeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTicketType.setAdapter(codeTypeAdapter);

        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(mContext
                , android.R.layout.simple_spinner_item
                , departmentList);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartment.setAdapter(departmentAdapter);

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
