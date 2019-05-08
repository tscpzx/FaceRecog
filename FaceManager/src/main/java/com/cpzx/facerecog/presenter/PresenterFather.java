package com.cpzx.facerecog.presenter;

import com.cpzx.facerecog.model.IModel;
import com.cpzx.facerecog.view.IView;

import java.lang.ref.WeakReference;

/**
 * created by xwr on 2019/5/8
 */
public class PresenterFather {
    protected IModel mIModel;
    protected WeakReference<IView> mViewReference;
}
