package com.cpzx.facerecog.model;

import java.io.Serializable;

/**
 * created by xwr on 2019/5/17
 */
public class Device implements Serializable {
    private int device_id;
    private String device_name;
    private String device_sn;
    private String last_online_time;//最近上线时间
    private String activate_time;//激活时间
    private int online;//0：不在线;1：在线
    private String register_time;//注册时间
    private String last_offline_time;//最近下线时间
    private boolean isCheck;//是否选中

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
    }

    public String getLast_online_time() {
        return last_online_time;
    }

    public void setLast_online_time(String last_online_time) {
        this.last_online_time = last_online_time;
    }

    public String getActivate_time() {
        return activate_time;
    }

    public void setActivate_time(String activate_time) {
        this.activate_time = activate_time;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getLast_offline_time() {
        return last_offline_time;
    }

    public void setLast_offline_time(String last_offline_time) {
        this.last_offline_time = last_offline_time;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
