package com.chaoshiguanli.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chaoshiguanli.bean.DanHaoJiLu;

import java.util.List;

/**
 * Created by Administrator on 2018/6/6.
 */

public abstract class XiaoShouJiLuAdapter extends BaseAdapter {

     private List<DanHaoJiLu> lists;

    public XiaoShouJiLuAdapter(List<DanHaoJiLu> lists) {
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

    protected abstract View setView(int position, View convertView, ViewGroup parent, List<DanHaoJiLu> listData);
}
