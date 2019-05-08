package com.cpzx.facerecog.presenter;

import com.cpzx.facerecog.model.LoginMode;
import com.cpzx.facerecog.view.ILoginView;
import com.cpzx.facerecog.view.IView;

import java.lang.ref.WeakReference;

/**
 * created by xwr on 2019/5/8
 */
public class LoginPresenter  extends  PresenterFather{
    public LoginPresenter(ILoginView loginView){
        this.mIModel = new LoginMode();
        this.mViewReference = new WeakReference<IView>(loginView);
    }
    public void login(){
        if (mIModel!=null&& mViewReference!=null&&mViewReference.get()!=null){
            ILoginView loginView = (ILoginView) mViewReference.get();
            String  name = loginView.getUserName();
            String password = loginView.getPassword();
        }
    }
}
