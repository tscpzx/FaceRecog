package com.cpzx.facerecog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.model.Device;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by xwr on 2019/5/17
 */
public class DeviceListAdapter extends BaseAdapter {
    private Context context;
    private List<Device> deviceList;
    private List<Integer> checkBoxIdList;


    public DeviceListAdapter(Context context, List<Device> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
        checkBoxIdList = new ArrayList<>();
    }

    @Override
    public int getCount() {

        return deviceList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.device_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvDeviceName.setText(deviceList.get(position).getDevice_name());
        holder.tvDeviceSn.setText(deviceList.get(position).getDevice_sn());
        holder.cbChoose.setChecked(deviceList.get(position).isCheck());
        final int pos = position;
        final boolean isChecked = deviceList.get(pos).isCheck();
        holder.cbChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deviceList.get(pos).setCheck(!isChecked);
            }
        });
        return convertView;
    }


    static class ViewHolder {
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

    public List<Integer> getCheckBoxIdList() {
        for (Device device : deviceList) {
            if (device.isCheck()) {
                checkBoxIdList.add(device.getDevice_id());
            }
        }
        return checkBoxIdList;
    }
}
