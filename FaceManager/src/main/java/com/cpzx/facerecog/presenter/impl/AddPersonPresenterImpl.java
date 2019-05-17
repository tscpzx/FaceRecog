package com.cpzx.facerecog.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.cjw.library.http.rx.HttpResult;
import com.cjw.library.http.rx.HttpResultSubscriber;
import com.cjw.library.http.rx.RxDoOnSubscribe;
import com.cjw.library.http.rx.RxSchedulers;
import com.cjw.library.http.rx.RxTrHttpMethod;
import com.cpzx.facerecog.HttpService;
import com.cpzx.facerecog.model.Device;
import com.cpzx.facerecog.model.PageList;
import com.cpzx.facerecog.model.Person;
import com.cpzx.facerecog.model.User;
import com.cpzx.facerecog.presenter.AddPersonPresenter;
import com.cpzx.facerecog.util.HttpResultUtil;
import com.cpzx.facerecog.util.ImageUtil;
import com.cpzx.facerecog.util.SharedPreferenceUtil;
import com.cpzx.facerecog.view.AddPersonView;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cpzx.facerecog.Constant.CHOOSE_PHOTO;
import static com.cpzx.facerecog.Constant.IMAGE_FILE_NAME;
import static com.cpzx.facerecog.Constant.TAKE_PHOTO;

/**
 * created by xwr on 2019/5/15
 */
public class AddPersonPresenterImpl implements AddPersonPresenter {
    private Context context;
    private AddPersonView addPersonView;
    private SharedPreferenceUtil sharedPreferenceUtil;

    public AddPersonPresenterImpl(Context context, AddPersonView addPersonView) {
        this.context = context;
        this.addPersonView = addPersonView;
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(context);
    }

    @Override
    public void getPhoto(int code) {
        if (code == TAKE_PHOTO) {//拍照
            //启动相机
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");// 设置action
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
            addPersonView.takePhoto(intent, TAKE_PHOTO);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            addPersonView.choosePhoto(intent, CHOOSE_PHOTO);
        }

    }

    @Override
    public void getDeviceList() {
        String token = sharedPreferenceUtil.getString("token");
        Map<String, String> map = new HashMap<>();
        map.put("access_cpfr_token", token);
        RxTrHttpMethod.getInstance()
                .createService(HttpService.class)
                .deviceList(map)
                .compose(RxSchedulers.<HttpResult<PageList<Device>>>defaultSchedulers())
                .doOnSubscribe(new RxDoOnSubscribe(context))
                .subscribe(new HttpResultUtil<PageList<Device>>(context) {
                               @Override
                               public void _onSuccess(PageList<Device> result) {
                                   addPersonView.onGetDeviceListSuccess(result.getList());
                               }

                               @Override
                               public void _onError(int code, Throwable e) {
                                   super._onError(code, e);
                                   addPersonView.onGetDeviceListFail();
                               }
                           }
                );
    }

    @Override
    public void addPerson(Person person) {
        Map<String, String> map = new HashMap<>();
        map.put("person_name", person.getName());
        map.put("emp_number", person.getNum());
        map.put("file", ImageUtil.bitmapToBase64(person.getHeader()));
        RxTrHttpMethod.getInstance()
                .createService(HttpService.class)
                .addPerson(map)
                .compose(RxSchedulers.<HttpResult<JsonObject>>defaultSchedulers())
                .doOnSubscribe(new RxDoOnSubscribe(context))
                .subscribe(new HttpResultUtil<JsonObject>(context) {
                               @Override
                               public void _onSuccess(JsonObject result) {
                                   addPersonView.onAddSuccess();
                               }

                               @Override
                               public void _onError(int code, Throwable e) {
                                   super._onError(code, e);
                                   addPersonView.onAddFail();
                               }
                           }
                );
    }

}
