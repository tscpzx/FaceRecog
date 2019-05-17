package com.cpzx.facerecog.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.model.Device;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by xwr on 2019/5/17
 */
public class DeviceListAdapter extends BaseAdapter {
    private Context context;
    private List<Device> deviceList;

    public DeviceListAdapter(Context context, List<Device> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
    }

    @Override
    public int getCount() {
        return  deviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
         ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.device_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Log.d("test",deviceList.get(position).getDeviceName());
        viewHolder.tvDeviceName.setText(deviceList.get(position).getDeviceName());
        viewHolder.tvDeviceSn.setText(deviceList.get(position).getDeviceSn());
        viewHolder.cbChoose.setChecked(deviceList.get(position).isCheck());
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CheckBox checkBox = viewHolder.cbChoose;
//                if (checkBox.isChecked()) {
//                    checkBox.setChecked(false);
//                    deviceList.get(position).setCheck(false);
//                } else {
//                    checkBox.setChecked(true);
//                    deviceList.get(position).setCheck(true);
//                }
//            }
//        });
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_device_name)
        TextView tvDeviceName;
        @BindView(R.id.tv_device_sn)
        TextView tvDeviceSn;
        @BindView(R.id.cb_choose)
        CheckBox cbChoose;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
