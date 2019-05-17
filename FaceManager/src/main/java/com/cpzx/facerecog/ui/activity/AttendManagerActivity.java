package com.cpzx.facerecog.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpzx.facerecog.R;

import butterknife.BindView;
import butterknife.OnClick;

public class AttendManagerActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_go_back)
    ImageView ivGoBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend);
        init();
    }

    private void init() {
        tvTitle.setText("考勤管理");
    }

    @OnClick({R.id.iv_go_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            default:
                break;
        }
    }
}
