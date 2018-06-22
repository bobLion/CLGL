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
import com.bob.android.clgl.entity.VehicleCountEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @package com.bob.android.clgl.adapter
 * @fileName VehicleNumAdapter
 * @Author Bob on 2018/6/10 12:35.
 * @Describe TODO
 */

public class VehicleCountAdapter extends BaseAdapter {

    private Context mContext;
    private List<VehicleCountEntity.DataBean> vehicleDataList = new ArrayList<>();


    public VehicleCountAdapter(Context context, List<VehicleCountEntity.DataBean> dataBeanList){
        this.mContext = context;
        this.vehicleDataList = dataBeanList;
    }
    @Override
    public int getCount() {
        return vehicleDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return vehicleDataList.get(position);
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
            holder.tvVehicleNum = (TextView)convertView.findViewById(R.id.vehicle_num);
            holder.tvVehicleWeight = (TextView)convertView.findViewById(R.id.tv_vehicle_weight);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        VehicleCountEntity.DataBean dataBean = vehicleDataList.get(position);
        if(null != dataBean.getDicDataName()){
            bindViewUtil( holder.tvDepartment,dataBean.getDicDataName());
        }
        if(null != dataBean.getNowTime()){
            bindViewUtil( holder.tvUsedTime,dataBean.getNowTime().toString());
        }
        if(null!= dataBean.getCarNo()){
            bindViewUtil( holder.tvVehicleNum,dataBean.getCarNo().toString());
        }
        if(null != dataBean.getWeights()){
            holder.tvVehicleWeight.setText(dataBean.getWeights());
        }
//        holder.tvDepartment.setText(dataBean.getDicDataName());
//        holder.tvUsedTime.setText(dataBean.getNowTime().toString());
//        holder.tvVehicleNum.setText(dataBean.getCarNo().toString());
        return convertView;
    }

    private class ViewHolder{
        private TextView tvVehicleNum,tvDepartment,tvUsedTime,tvVehicleWeight;
    }

    private void bindViewUtil(TextView view,String value){
        if(null != value){
            view.setText(value);
        }else{
            view.setText("");
        }
    }
}
