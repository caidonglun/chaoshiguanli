package com.com.chaoshiguanli.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.chaoshiguanli.bean.ShangPingLeiXing;
import com.daimajia.swipe.SwipeLayout;
import com.chaoshiguanli.adapter.LeiXingAdapter;
import com.example.administrator.chaoshiguanlis.MainActivity;
import com.example.administrator.chaoshiguanlis.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public class ShangPingLeiXing_Fragment extends Fragment {

    private LeiXingAdapter leixing_adapter;
    List<ShangPingLeiXing> datas=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from shangpingleixing where usernumber=?", new String[]{MainActivity.UserName});
        for (;cursor.moveToNext();){
            datas.add(new ShangPingLeiXing(cursor.getString(0),cursor.getString(1),cursor.getString(2)));
        }

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.shang_ping_lei_xing_layout, null);

        Button btn_return_lei_xing = view.findViewById(R.id.btn_return_lei_xing);
        btn_return_lei_xing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().replace(R.id.ll_content,new Four_Fragment()).commit();
            }
        });

        ListView lv_shang_ping_lei_xing = view.findViewById(R.id.lv_shang_ping_lei_xing);
        lv_show_setData();
        lv_shang_ping_lei_xing.setAdapter(leixing_adapter);


        return view;
    }

    private void lv_show_setData() {
        leixing_adapter=new LeiXingAdapter(datas) {
            @Override
            protected View setView(final int position, View convertView, ViewGroup parent, final List<ShangPingLeiXing> listData) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.wipe_lsayout, null);
                if(convertView==null) {
                    convertView= LayoutInflater.from(getActivity()).inflate(R.layout.shang_ping_lei_xing_wipe_lsayout, null);
                    Swipe(convertView,listData,position);
                    TextView tv_list_show,tv_show_date;
                    tv_list_show = convertView.findViewById(R.id.tv_list_show);
                    tv_show_date=convertView.findViewById(R.id.tv_show_date);
                    tv_list_show.setText(listData.get(listData.size() - 1 - position).getLei_xing_ming_cheng());
                    tv_show_date.setText(listData.get(listData.size() - 1 - position).getTian_jia_ri_qi());
                }else{
                    convertView= LayoutInflater.from(getActivity()).inflate(R.layout.shang_ping_lei_xing_wipe_lsayout, null);
                    Swipe(convertView,listData,position);
                    TextView tv_list_show,tv_show_date;
                    tv_list_show = convertView.findViewById(R.id.tv_list_show);
                    tv_show_date=convertView.findViewById(R.id.tv_show_date);
                    tv_list_show.setText(listData.get(listData.size() - 1 - position).getLei_xing_ming_cheng());
                    tv_show_date.setText(listData.get(listData.size() - 1 - position).getTian_jia_ri_qi());
                }
                return convertView;
            }
        };
    }

    private void Swipe(View convertView, final List<ShangPingLeiXing> listData, final int position) {
        SwipeLayout sample1 = convertView.findViewById(R.id.sl_content);

        sample1.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                这里会代替点击事件
//                new AlertDialog.Builder(getActivity()).setTitle(""+listData.get(listData.size()-1-position)).setCancelable(false).setPositiveButton("确定", null).show();
//                Toast.makeText(getActivity(), "Click on surface", Toast.LENGTH_SHORT).show();
//                Log.d(MainActivity.class.getName(), "click on surface");
            }
        });
        sample1.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Toast.makeText(getActivity(), "longClick on surface", Toast.LENGTH_SHORT).show();
//                Log.d(MainActivity.class.getName(), "longClick on surface");
                return true;
            }
        });

        sample1.findViewById(R.id.trash2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.readableDatabase.execSQL("delete from shangpingleixing where usernumber=? and commoditytype=?",new Object[]{MainActivity.UserName,listData.get(listData.size()-1-position).getLei_xing_ming_cheng()});
                listData.remove(listData.size()-1-position);
                leixing_adapter.notifyDataSetChanged();
            }
        });

    }
}
