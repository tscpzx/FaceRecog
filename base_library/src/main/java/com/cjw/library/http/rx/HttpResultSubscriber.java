package com.cjw.library.http.rx;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.cjw.library.view.dialog.LoadingDialog;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 17-2-23.
 */
public abstract class HttpResultSubscriber<T> extends Subscriber<HttpResult<T>> {

    private Context mContext;

    protected HttpResultSubscriber() {
    }

    protected HttpResultSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCompleted() {
        LoadingDialog.close();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        //在这里做全局的错误处理
        if (e instanceof HttpException) {
            // ToastUtils.getInstance().showToast(e.getMessage());
        }
        _onError(-1, e);
    }

    @Override
    public void onNext(HttpResult<T> t) {
//    LogUtil.d(new Gson().toJson(t));
        if (t.code == 0) {
            _onSuccess(t.data);
        } else {
            _onError(t.code, new Throwable("error: " + t.message));
        }
    }

    public abstract void _onSuccess(T t);

    public void _onError(int code, final Throwable e) {
        LoadingDialog.close();
        if (code != 102)  {
            String message = e.getMessage();
            if (TextUtils.isEmpty(message)) {
                message = "连接超时";
            }
            if (mContext != null) //TipDialog.show((Activity) mContext, message);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
}