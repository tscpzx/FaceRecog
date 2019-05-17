package com.cpzx.facerecog.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.cjw.library.http.rx.HttpResultSubscriber;
import com.cpzx.facerecog.ui.activity.LoginActivity;

/**
 * created by xwr on 2019/5/17
 */
public abstract class HttpResultUtil<T> extends HttpResultSubscriber<T> {
    private Context context;

    protected HttpResultUtil() {
    }

    protected HttpResultUtil(Context context) {
        super(context);
        this.context = context;
    }

    public abstract void _onSuccess(T t);
    @Override
    public void _onError(int code, Throwable e) {
        super._onError(code, e);
        if (code == 102) {
            Toast.makeText(context, "身份过期，请重新登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
    }
}
