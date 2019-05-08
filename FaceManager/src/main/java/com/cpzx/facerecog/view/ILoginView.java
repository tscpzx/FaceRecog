package com.cpzx.facerecog.view;

import android.view.View;

/**
 * created by xwr on 2019/5/8
 */
public interface ILoginView extends IView {
    String getUserName();

    String getPassword();

    void onLoginSuccess();

    void onLoginFail();

}
