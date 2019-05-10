package com.cpzx.facerecog.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpzx.facerecog.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ConfigActivity extends BaseActivity {
    @BindView(R.id.goBack)
    ImageView goback;
    @BindView(R.id.title)
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        init();
    }

    private void init() {
        title.setText("网络配置");
    }

    @OnClick({R.id.goBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goBack:
                finish();
                break;
            default:
                break;
        }
    }
}
