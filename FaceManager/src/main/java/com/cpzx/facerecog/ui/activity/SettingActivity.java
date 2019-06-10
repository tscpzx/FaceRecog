package com.cpzx.facerecog.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xwr
 * @date 2019/5/28
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.btn_logout)
    Button btnLogout;
    SharedPreferenceUtil sharedPreferenceUtil;
    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        tvTitle.setText("设置");
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
    }

    @OnClick({R.id.btn_logout,R.id.iv_go_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                showLogoutDialog();
                break;
            case R.id.iv_go_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void showLogoutDialog() {
        //创建AlertDialog的构造器的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("提示");
        builder.setMessage("确定退出当前账号吗？"); //设置内容
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
                goActivity(LoginActivity.class);
                finish();
                sharedPreferenceUtil.clear();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }

}
