package com.bob.android.clgl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bob.android.clgl.R;
import com.bob.android.clgl.entity.DriverEntity;
import com.bob.android.clgl.entity.ReceiverListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @package com.bob.android.clgl.adapter
 * @fileName VehicleNumAdapter
 * @Author Bob on 2018/6/10 12:35.
 * @Describe TODO
 */

public class DriverAdapter extends BaseAdapter {

    private Context mContext;
    private List<ReceiverListEntity.DataBean> driverEntityList = new ArrayList<>();


    public DriverAdapter(Context context, List<ReceiverListEntity.DataBean> driverEntities){
        this.mContext = context;
        this.driverEntityList = driverEntities;
    }
    @Override
    public int getCount() {
        return driverEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return driverEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_select_driver,null);
            holder.tvDriverName = (TextView)convertView.findViewById(R.id.tv_driver_name);
            holder.tvDriverPhone = (TextView)convertView.findViewById(R.id.tv_driver_phone);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ReceiverListEntity.DataBean receiver = driverEntityList.get(position);
        holder.tvDriverName.setText(receiver.getReceiveName());
        holder.tvDriverPhone.setText(receiver.getReceivePhone());
        return convertView;
    }

    public class ViewHolder{
        private TextView tvDriverName,tvDriverPhone;

    }
}
