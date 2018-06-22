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
import com.bob.android.clgl.entity.DriverEntity;
import com.bob.android.clgl.entity.TicketEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @package com.bob.android.clgl.adapter
 * @fileName VehicleNumAdapter
 * @Author Bob on 2018/6/10 12:35.
 * @Describe TODO
 */

public class TicketAdapter extends BaseAdapter {

    private Context mContext;
    private List<TicketEntity> usedTicketList = new ArrayList<>();


    public TicketAdapter(Context context, List<TicketEntity> usedTickets){
        this.mContext = context;
        this.usedTicketList = usedTickets;
    }
    @Override
    public int getCount() {
        return usedTicketList.size();
    }

    @Override
    public Object getItem(int position) {
        return usedTicketList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_check_ticket,null);
            holder.tvDepartment = (TextView)convertView.findViewById(R.id.ticket_department);
            holder.tvUsedTime = (TextView)convertView.findViewById(R.id.tv_use_time);
//            holder.tvTicketStatus = (TextView)convertView.findViewById(R.id.tv_ticket_status);
            holder.tvTipTime = (TextView)convertView.findViewById(R.id.tv_tip_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        TicketEntity ticketEntity = usedTicketList.get(position);
        holder.tvDepartment.setText(ticketEntity.getDepartment());
        holder.tvUsedTime.setText(ticketEntity.getUsedTime());
        if(ticketEntity.getStatus().equals("0")){
            holder.tvTicketPswd.setVisibility(View.INVISIBLE);
            holder.tvTicketStatus.setText("未使用");
            holder.tvTicketStatus.setBackground(mContext.getResources().getDrawable(R.color.material_red));
            holder.tvTipTime.setText("生成时间：");
        }else if(ticketEntity.getStatus().equals("1")){
            holder.tvTicketPswd.setText(ticketEntity.getTicketPswd());
            holder.tvTicketStatus.setText("已使用");
            holder.tvTicketStatus.setBackground(mContext.getResources().getDrawable(R.color.material_green));
            holder.tvTipTime.setText("使用时间：");
        }
        return convertView;
    }

    public class ViewHolder{
        private TextView tvTicketPswd,tvDepartment,tvUsedTime,tvTicketStatus,tvTipTime;

    }
}
