package com.cpzx.facerecog.model;

import android.support.annotation.NonNull;

/**
 * wifi对象
 * 集合中按照信号强度排序
 */
public class WifiBean implements Comparable<WifiBean> {
    private String wifiName;//wifi名称
    private String level;//信号强度
    private String capabilities;//加密方式
    private String password;//密码

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int compareTo(@NonNull WifiBean o) {
        int level1 = Integer.parseInt(this.getLevel());
        int level2 = Integer.parseInt(o.getLevel());
        return level1 - level2;
    }
}
