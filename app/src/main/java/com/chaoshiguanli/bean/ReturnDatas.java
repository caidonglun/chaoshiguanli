package com.chaoshiguanli.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/24.
 */

public class ReturnDatas<T> {
    private String userid;
    private List<T> data;
    private String is_success;

    @Override
    public String toString() {
        return "ReturnDatas{" +
                "userid='" + userid + '\'' +
                ", data=" + data +
                ", is_success='" + is_success + '\'' +
                '}';
    }

    public ReturnDatas() {
    }

    public ReturnDatas(String userid, List<T> data, String is_success) {
        this.userid = userid;
        this.data = data;
        this.is_success = is_success;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getIs_success() {
        return is_success;
    }

    public void setIs_success(String is_success) {
        this.is_success = is_success;
    }
}
