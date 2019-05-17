package com.cpzx.facerecog.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * created by xwr on 2019/5/14
 */
public class Person implements Serializable {
    private String person_name;//姓名

    private String emp_number;//工号

    private Bitmap header;//头像

    public Person(String name, String num, Bitmap header) {
        this.person_name = name;
        this.emp_number = num;
        this.header = header;
    }

    public String getName() {
        return person_name;
    }

    public void setName(String name) {
        this.person_name = name;
    }

    public String getNum() {
        return emp_number;
    }

    public void setNum(String num) {
        this.emp_number = num;
    }

    public Bitmap getHeader() {
        return header;
    }

    public void setHeader(Bitmap header) {
        this.header = header;
    }
}
