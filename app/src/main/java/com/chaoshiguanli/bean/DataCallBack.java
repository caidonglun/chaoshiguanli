package com.chaoshiguanli.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/25.
 */

public class DataCallBack {
    private String userid;
    private List<String> data;

    public DataCallBack(String userid, List<String> data) {
        this.userid = userid;
        this.data = data;
    }

    public DataCallBack() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}


