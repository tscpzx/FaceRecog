package com.cpzx.facerecog.view;

import android.content.Intent;

import com.cpzx.facerecog.model.Device;

import java.util.List;

/**
 * created by xwr on 2019/5/14
 */
public interface AddPersonView {
    void takePhoto(Intent intent, int code);

    void choosePhoto(Intent intent, int code);

    void onAddSuccess();

    void onAddFail();

    void onGetDeviceListSuccess(List<Device> deviceList);

    void onGetDeviceListFail();

}
