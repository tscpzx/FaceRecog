package com.cpzx.facerecog.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.presenter.LoginPresenter;
import com.cpzx.facerecog.view.ILoginView;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener {
    @BindView(R.id.name)
    EditText nameEdit;
    @BindView(R.id.password)
    EditText passwordEdit;
    @BindView(R.id.login)
    Button login;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        initData();
        setListen();
    }

    private void initData() {
        this.mLoginPresenter = new LoginPresenter(this);
    }

    private void setListen() {
        login.setOnClickListener(this);
    }

    @Override
    public String getUserName() {
        return nameEdit.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEdit.getText().toString();
    }

    @Override
    public void onLoginSuccess() {
        showToast("登录成功！");
    }

    @Override
    public void onLoginFail() {
        showToast("登录失败！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                mLoginPresenter.login();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mLoginPresenter = null;
    }
}
