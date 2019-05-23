package com.cpzx.facerecog.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * created by xwr on 2019/5/14
 */
public class Person implements Serializable {
    private String person_name;//姓名

    private String emp_number;//工号

    private byte[] header;//头像

    private String deviceIds;

    public Person(String name, String num, byte[] header, String deviceIds) {
        this.person_name = name;
        this.emp_number = num;
        this.header = header;
        this.deviceIds = deviceIds;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getEmp_number() {
        return emp_number;
    }

    public void setEmp_number(String emp_number) {
        this.emp_number = emp_number;
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }
}
