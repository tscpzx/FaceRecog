package com.cjw.library.http.rx;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.cjw.library.view.dialog.LoadingDialog;
import com.cjw.library.view.dialog.TipDialog;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 17-2-23.
 */
public abstract class HttpResultSubscriber<T> extends Subscriber<HttpResult<T>> {

  private Context mContext;

  protected HttpResultSubscriber() {}

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
    _onError(e);
  }

  @Override
  public void onNext(HttpResult<T> t) {
//    LogUtil.d(new Gson().toJson(t));
    if (t.code == 1) {
      _onSuccess(t.result);
    } else {
      _onError(new Throwable("error: " + t.message));
    }
  }

  public abstract void _onSuccess(T t);

  public void _onError(final Throwable e) {
    LoadingDialog.close();
    String message = e.getMessage();
    if (TextUtils.isEmpty(message)) {
      message = "连接超时";
    }
    if (mContext != null) TipDialog.show((Activity) mContext, message);
    e.printStackTrace();
  }
}