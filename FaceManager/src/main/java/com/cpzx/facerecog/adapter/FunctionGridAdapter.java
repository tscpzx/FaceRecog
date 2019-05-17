package com.cpzx.facerecog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.model.FunctionItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * created by xwr on 2019/5/9
 * 首页功能网格布局适配器
 */
public class FunctionGridAdapter extends BaseAdapter {
    private Context context;
    private List<FunctionItem> functionItems;
    private Integer[] mIconIds = {
            R.mipmap.device_manager, R.mipmap.person_manager, R.mipmap.record_manager,
            R.mipmap.attend_manager, R.mipmap.inter_config
    };
    private String[] notes = {"设备管理", "人员管理", "记录管理", "考勤管理", "配置网络"};

    public FunctionGridAdapter(Context context) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.grid_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.image.setImageResource(functionItems.get(position).getImgId());
        holder.text.setText(functionItems.get(position).getNote());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.icon)
        ImageView image;
        @BindView(R.id.note)
        TextView text;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
