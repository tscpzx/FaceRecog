package com.cpzx.facerecog.model;

import java.io.Serializable;

/**
 * created by xwr on 2019/5/10
 */
public class User implements Serializable {
    private int admin_id;
    private int wid;
    private String name;
    private String password;
    private String access_cpfr_token;

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getAccess_cpfr_token() {
        return access_cpfr_token;
    }

    public void setAccess_cpfr_token(String access_cpfr_token) {
        this.access_cpfr_token = access_cpfr_token;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
