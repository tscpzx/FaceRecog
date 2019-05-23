package com.cpzx.facerecog.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cpzx.facerecog.R;
import com.cpzx.facerecog.presenter.LoginPresenter;
import com.cpzx.facerecog.presenter.impl.LoginPresenterImpl;
import com.cpzx.facerecog.view.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {
    @BindView(R.id.et_username)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        initData();
    }

    private void initData() {
        mLoginPresenter = new LoginPresenterImpl(this, this);
    }

    @Override
    public void onLoginSuccess() {
        finish();
        showToast("登录成功");
    }

    @Override
    public void onLoginFail() {
        Log.d("Login", "登录失败!");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mLoginPresenter = null;
    }

    @OnClick({R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            default:
                break;
        }
    }

    private void login() {
        String username = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            showToast("帐号或密码不能为空");
            return;
        }
        mLoginPresenter.login(username, password);
    }
}
