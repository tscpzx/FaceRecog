package com.cpzx.facerecog.model;

import com.cpzx.facerecog.model.listener.LoginListener;

/**
 * created by xwr on 2019/5/8
 */
public class LoginMode implements IModel {

    public void login(String name, String password, LoginListener listener) {
        if (listener == null) {
            return;
        }
    }
}
