package com.cpzx.facerecog.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.adapter.DeviceListAdapter;
import com.cpzx.facerecog.model.Device;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by xwr on 2019/5/20
 */
public class ListDialog extends Dialog {
    @BindView(R.id.lv_device_list)
    ListView lvDeviceList;
    @BindView(R.id.tv_dialog_cancel)
    TextView tvDialogCancel;
    @BindView(R.id.tv_dialog_sure)
    TextView tvDialogSure;
    private Context context;
    private Activity activity;
    private List<Device> list;
    private DeviceListAdapter adapter;
    private GetClickedDevices getClickedDevices;


    public ListDialog(Context context, List<Device> list, Activity activity, GetClickedDevices getClickedDevices) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.getClickedDevices = getClickedDevices;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_device_lsit);
        ButterKnife.bind(this);
        Window window = getWindow();
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = (int) (d.getWidth() * 0.8);
        wl.height = (int) (d.getHeight() * 0.6);
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
        adapter = new DeviceListAdapter(context, list);
        lvDeviceList.setAdapter(adapter);
        lvDeviceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);//多选模式

    }


    @OnClick({R.id.tv_dialog_cancel, R.id.tv_dialog_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_cancel:
                this.cancel();
                break;
            case R.id.tv_dialog_sure:
                getClickedDevices.getDevices(adapter.getCheckBoxIdList());
                this.cancel();
                break;
        }
    }

    public interface GetClickedDevices {
        void getDevices(List<Integer> devicesIds);
    }
}
