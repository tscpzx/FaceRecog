package com.cpzx.facerecog.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.model.FunctionItem;
import com.cpzx.facerecog.ui.activity.DeviceManagerActivity;
import com.cpzx.facerecog.ui.activity.PersonManagerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * created by xwr on 2019/5/9
 * 首页功能网格布局适配器
 */
public class GridAdapter extends BaseAdapter {
    private Context context;
    private List<FunctionItem> functionItems;
    private Integer[] mIconIds = {
            R.mipmap.device_manager, R.mipmap.person_manager, R.mipmap.record_manager,
            R.mipmap.attend_manager, R.mipmap.inter_config
    };
    private String[] notes = {"设备管理", "人员管理", "记录详情", "考勤管理", "配置网络"};
    public GridAdapter(Context context) {
        this.context = context;
        functionItems = new ArrayList<>();
        for (int i = 0; i < mIconIds.length; i++) {
            FunctionItem functionItem = new FunctionItem(mIconIds[i], notes[i]);
            functionItems.add(functionItem);
        }
    }

    @Override
    public int getCount() {
        return functionItems.size();
    }

    @Override
    public Object getItem(int position) {
        return functionItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
            holder = new ViewHolder();
            holder.image = view.findViewById(R.id.icon);
            holder.text = view.findViewById(R.id.note);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.image.setImageResource(functionItems.get(position).getImgId());
        holder.text.setText(functionItems.get(position).getNote());
        return view;
    }

    class ViewHolder {
        ImageView image;
        TextView text;
    }

}
