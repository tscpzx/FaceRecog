package com.cpzx.facerecog.model;

import java.util.List;

/**
 * created by xwr on 2019/5/17
 */
public class PageList<T> {
    private int total;
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
