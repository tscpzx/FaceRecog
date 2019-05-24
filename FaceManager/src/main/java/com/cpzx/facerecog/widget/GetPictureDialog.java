package com.cpzx.facerecog.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.cpzx.facerecog.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by xwr on 2019/5/20
 */
public abstract class GetPictureDialog extends Dialog {
    @BindView(R.id.tv_take_photo)
    TextView tvTakePhoto;
    @BindView(R.id.tv_take_pic)
    TextView tvTakePic;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    private Context context;


    public GetPictureDialog(Context context) {
        super(context, R.style.DialogTheme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_camera);
        ButterKnife.bind(this);
        setViewLocation();
        setCanceledOnTouchOutside(true);

    }

    @OnClick({R.id.tv_take_photo, R.id.tv_take_pic, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_take_photo:
                takePhoto();
                this.cancel();
                break;
            case R.id.tv_take_pic:
                choosePic();
                this.cancel();
                break;
            case R.id.tv_cancel:
                this.cancel();
                break;
        }
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation() {
        Activity activity = (Activity) context;
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }

    public abstract void takePhoto();

    public abstract void choosePic();
}
