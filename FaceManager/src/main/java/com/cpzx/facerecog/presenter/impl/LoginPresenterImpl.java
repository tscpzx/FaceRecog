package com.cpzx.facerecog.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.cjw.library.http.rx.HttpResult;
import com.cjw.library.http.rx.HttpResultSubscriber;
import com.cjw.library.http.rx.RxDoOnSubscribe;
import com.cjw.library.http.rx.RxSchedulers;
import com.cjw.library.http.rx.RxTrHttpMethod;
import com.cpzx.facerecog.Constant;
import com.cpzx.facerecog.HttpService;
import com.cpzx.facerecog.model.User;
import com.cpzx.facerecog.presenter.LoginPresenter;
import com.cpzx.facerecog.util.SharedPreferenceUtil;
import com.cpzx.facerecog.view.LoginView;

import java.util.HashMap;
import java.util.Map;

import static com.cpzx.facerecog.Constant.isLogin;

/**
 * created by xwr on 2019/5/10
 */
public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private Context context;
    private SharedPreferenceUtil sharedPreferences;

    public LoginPresenterImpl(Context context, LoginView loginView) {
        this.context = context;
        this.loginView = loginView;
        sharedPreferences = SharedPreferenceUtil.getInstance(context);
    }

    @Override
    public void login(final String username, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("name", username);
        map.put("password", password);
        RxTrHttpMethod.getInstance()
                .createService(HttpService.class)
                .login(map)
                .compose(RxSchedulers.<HttpResult<User>>defaultSchedulers())
                .doOnSubscribe(new RxDoOnSubscribe(context))
                .subscribe(new HttpResultSubscriber<User>(context) {
                               @Override
                               public void _onSuccess(User result) {

                                   sharedPreferences.putValues(new SharedPreferenceUtil.ContentValue("adminId", result.getAdmin_id()));
                                   sharedPreferences.putValues(new SharedPreferenceUtil.ContentValue("token", result.getAccess_cpfr_token()));
                                   Log.d("test",result.getAdmin_id()+"********");
                                   Constant.CURRENT_USER = result;
                                   isLogin = true;
                                   loginView.onLoginSuccess();
                               }

                               @Override
                               public void _onError(Throwable e) {
                                   super._onError(e);
                                   loginView.onLoginFail();
                               }
                           }
                );
    }


}
