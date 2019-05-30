package com.cpzx.facerecog.model;


/**
 * created by xwr on 2019/5/9
 * 首页功能网格item
 */
public class FunctionItem {
    private int imgId;//图片资源id
    private String note;//注释

    public FunctionItem(int imgId, String note) {
        this.imgId = imgId;
        this.note = note;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
