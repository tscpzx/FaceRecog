package com.cpzx.facerecog.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpzx.facerecog.R;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonManagerActivity extends BaseActivity {
    @BindView(R.id.add_person)
    Button add;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.iv_go_back)
    ImageView goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_manager);
        init();
    }

    private void init() {
        title.setText("人员管理");
    }

    @OnClick({R.id.add_person, R.id.iv_go_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                finish();
                break;
            case R.id.add_person:
                goActivity(AddPersonActivity.class);
                break;
            default:
                break;
        }


    }
}
