package com.bob.android.clgl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bob.android.clgl.R;
import com.bob.android.clgl.activity.VehicleNumActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @package com.bob.android.clgl.adapter
 * @fileName VehicleNumAdapter
 * @Author Bob on 2018/6/10 12:35.
 * @Describe TODO
 */

public class VehicleNumAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> vehicleNunList = new ArrayList<>();


    public VehicleNumAdapter(Context context,List<String> vehicleNunList){
        this.mContext = context;
        this.vehicleNunList = vehicleNunList;
    }
    @Override
    public int getCount() {
        return vehicleNunList.size();
    }

    @Override
    public Object getItem(int position) {
        return vehicleNunList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_vehicle_num,null);
            holder.mTvVehicleNum = (TextView)convertView.findViewById(R.id.tv_vhicle_num);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String vehicleNum = vehicleNunList.get(position);
        holder.mTvVehicleNum.setText(vehicleNum);
        return convertView;
    }

    public class ViewHolder{
        private TextView mTvVehicleNum;

    }
}
