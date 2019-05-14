package com.cpzx.facerecog.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpzx.facerecog.R;

import butterknife.BindView;
import butterknife.OnClick;

public class AddPersonActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.num)
    EditText num;
    @BindView(R.id.choose_pic)
    LinearLayout choosePicture;
    @BindView(R.id.goBack)
    ImageView goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        init();
    }

    private void init() {
        title.setText("添加用户");
    }


    @OnClick({R.id.choose_pic, R.id.goBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_pic:

                break;
            case R.id.goBack:
                finish();
                break;
            default:
                break;
        }
    }

}
