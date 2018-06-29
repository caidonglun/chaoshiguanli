package com.chaoshiguanli.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chaoshiguanli.bean.JinHuoJiLu;

import java.util.List;

/**
 * Created by Administrator on 2018/6/6.
 */

public abstract class JinHuoJiLuAdapter extends BaseAdapter {

     private List<JinHuoJiLu> lists;

    public JinHuoJiLuAdapter(List<JinHuoJiLu> lists) {
        this.lists = lists;
    }



    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            convertView=setView(position, convertView, parent,lists);
        return convertView;
    }

    protected abstract View setView(int position, View convertView, ViewGroup parent, List<JinHuoJiLu> listData);
}
