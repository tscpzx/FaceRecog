package com.cpzx.facerecog.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.cjw.library.http.rx.HttpResult;
import com.cjw.library.http.rx.RxDoOnSubscribe;
import com.cjw.library.http.rx.RxSchedulers;
import com.cjw.library.http.rx.RxTrHttpMethod;
import com.cpzx.facerecog.Constant;
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
        if (code == TAKE_PHOTO) {
            File cameraSavePath = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
            try {
                if (cameraSavePath.exists()) {
                    cameraSavePath.delete();
                }
                cameraSavePath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Uri uri;
            //启动相机
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");// 设置action
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, "com.cpzx.facerecog.fileprovider", cameraSavePath);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(cameraSavePath);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            addPersonView.takePhoto(intent, TAKE_PHOTO);
        } else {
            //打开相册
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            addPersonView.choosePhoto(intent, CHOOSE_PHOTO);
        }

    }

    @Override
    public void getDeviceList() {
        Map<String, String> map = new HashMap<>();
        RxTrHttpMethod.getInstance()
                .createService(HttpService.class)
                .deviceList(Constant.CURRENT_USER.getAccess_cpfr_token(), map)
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
        map.put("person_name", person.getPerson_name());
        map.put("emp_number", person.getEmp_number());
        map.put("device_ids", person.getDeviceIds());
        map.put("file", ImageUtil.bytesToBase64(person.getHeader()));
        RxTrHttpMethod.getInstance()
                .createService(HttpService.class)
                .addPerson(sharedPreferenceUtil.getString("token"), map)
                .compose(RxSchedulers.<HttpResult<User>>defaultSchedulers())
                .doOnSubscribe(new RxDoOnSubscribe(context))
                .subscribe(new HttpResultUtil<User>(context) {
                               @Override
                               public void _onSuccess(User result) {
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
