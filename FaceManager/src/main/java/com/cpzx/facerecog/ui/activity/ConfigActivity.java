package com.cpzx.facerecog.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpzx.facerecog.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xwr
 * @date 2019/5/28
 */
public class ConfigActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_ethernet)
    RelativeLayout rlEthernet;
    @BindView(R.id.rl_wifi)
    RelativeLayout rlWifi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        tvTitle.setText("网络配置");
    }

    @OnClick({R.id.iv_go_back, R.id.rl_ethernet, R.id.rl_wifi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.rl_ethernet:
                goActivity(EthernetActivity.class);
                break;
            case R.id.rl_wifi:
                goActivity(WiFiConfigActivity.class);
                break;
        }
    }
}
