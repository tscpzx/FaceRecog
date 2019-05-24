package com.cpzx.facerecog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.model.WifiBean;

import java.util.List;


public class WifiListAdapter extends BaseAdapter {
    private Context context;
    private List<WifiBean> wifiBeans;

    public WifiListAdapter(Context context, List<WifiBean> wifiBeans) {
        this.context = context;
        this.wifiBeans = wifiBeans;
    }

    @Override
    public int getCount() {
        return wifiBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return wifiBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.wifi_item, null);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.capability = convertView.findViewById(R.id.capability);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(wifiBeans.get(position).getWifiName());
        viewHolder.capability.setText(wifiBeans.get(position).getCapabilities());
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView capability;
    }
}
