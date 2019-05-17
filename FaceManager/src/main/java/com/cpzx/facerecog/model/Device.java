package com.cpzx.facerecog.model;

import java.io.Serializable;

/**
 * created by xwr on 2019/5/17
 */
public class Device implements Serializable {
    private int deviceId;
    private String deviceName;
    private String deviceSn;
    private String lastOnlineTime;//最近上线时间
    private String activateTime;//激活时间
    private int online;//0：不在线;1：在线
    private String registerTime;//注册时间
    private String lastOffineTime;//最近下线时间
    private boolean isCheck;//是否选中

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(String lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public String getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(String activateTime) {
        this.activateTime = activateTime;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getLastOffineTime() {
        return lastOffineTime;
    }

    public void setLastOffineTime(String lastOffineTime) {
        this.lastOffineTime = lastOffineTime;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
