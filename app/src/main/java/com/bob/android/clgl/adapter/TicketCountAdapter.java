package com.bob.android.clgl.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bob.android.clgl.R;
import com.bob.android.clgl.entity.TicketCountEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @package com.bob.android.clgl.adapter
 * @fileName VehicleNumAdapter
 * @Author Bob on 2018/6/10 12:35.
 * @Describe TODO
 */

public class TicketCountAdapter extends BaseAdapter {

    private Context mContext;
    private List<TicketCountEntity.DataBean> ticketDataList = new ArrayList<>();


    public TicketCountAdapter(Context context, List<TicketCountEntity.DataBean> dataBeanList){
        this.mContext = context;
        this.ticketDataList = dataBeanList;
    }
    @Override
    public int getCount() {
        return ticketDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return ticketDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_check_vhicle,null);
            holder.tvTicketType = (TextView)convertView.findViewById(R.id.ticket_type);
            holder.tvUsedTime = (TextView)convertView.findViewById(R.id.tv_use_time);
            holder.tvNum = (TextView)convertView.findViewById(R.id.ticket_num);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        TicketCountEntity.DataBean dataBean = ticketDataList.get(position);
        if(null != dataBean.getTicketType()){
            switch (dataBean.getTicketType()) {
                case "14":
                    bindViewUtil(holder.tvTicketType, "黄色（四分车）");
                    break;
                case "11":
                    bindViewUtil(holder.tvTicketType, "白色（社会车）");
                    break;
                case "13":
                    bindViewUtil(holder.tvTicketType, "蓝色(环卫三吨)");
                    break;
                case "12":
                    bindViewUtil(holder.tvTicketType, "紫色(环卫五吨)");
                    break;
            }

        }
        if(null != dataBean.getCreateTime()){
            bindViewUtil( holder.tvUsedTime,dataBean.getCreateTime().toString());
        }
        bindViewUtil( holder.tvNum,String.valueOf(dataBean.getTicketNum()));
        return convertView;
    }

    private class ViewHolder{
        private TextView tvTicketType,tvNum,tvUsedTime;
    }

    private void bindViewUtil(TextView view,String value){
        if(null != value){
            view.setText(value);
        }else{
            view.setText("");
        }
    }
}
