package com.cpzx.facerecog.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpzx.facerecog.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfigActivity extends BaseActivity {

    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_ethrnet)
    Button btnEthrnet;
    @BindView(R.id.btn_wifi)
    Button btnWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {
        tvTitle.setText("网络配置");
    }

    @OnClick({R.id.iv_go_back, R.id.btn_ethrnet, R.id.btn_wifi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.btn_ethrnet:
                goActivity(EthernetActivity.class);
                break;
            case R.id.btn_wifi:
                goActivity(WiFiConfigActivity.class);
                break;
        }
    }
}
