package com.cpzx.facerecog.presenter;

import com.cpzx.facerecog.model.Person;

/**
 * created by xwr on 2019/5/15
 */
public interface AddPersonPresenter {
    void getPhoto(int code);

    void getDeviceList();

    void addPerson(Person person);
}
