package com.example.administrator.chaoshiguanlis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoshiguanli.adapter.ShowXiaoShowJiLuAdapter;
import com.chaoshiguanli.bean.XiaoShouJiLu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/19.
 */

public class ShowXiaoShouJiLuActivity extends Activity {

    Button btn_return_ji_lu;
    ListView lv_show_xiang_xi_shang_ping_ji_lu;
    List<XiaoShouJiLu> datas;
    String dan_hao_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        datas=new ArrayList<>();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xiao_shou_dan_hao_xiang_qing);
        btn_return_ji_lu=findViewById(R.id.btn_return_ji_lu);

        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            dan_hao_id = bundle.getString("Id");
        }catch (Exception e){
e.printStackTrace();
        }

        Cursor cursorTest = MainActivity.readableDatabase.rawQuery("select * from xiaoshoujilu where usernumber=?", new String[]{MainActivity.UserName});
        Toast.makeText(this, "cursorTest="+cursorTest.getCount(), Toast.LENGTH_SHORT).show();
        Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from xiaoshoujilu where usernumber=? and saleno=?", new String[]{MainActivity.UserName, dan_hao_id});
        for (;cursor.moveToNext();){
            datas.add(new XiaoShouJiLu(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getString(5),cursor.getDouble(6),cursor.getString(7)));
        }

        btn_return_ji_lu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowXiaoShouJiLuActivity.this.finish();
            }
        });
        lv_show_xiang_xi_shang_ping_ji_lu=findViewById(R.id.lv_show_xiang_xi_shang_ping_ji_lu);

        ShowXiaoShowJiLuAdapter showXiaoShowJiLuAdapter=new ShowXiaoShowJiLuAdapter(datas) {
            @Override
            protected View setView(int position, View convertView, ViewGroup parent, List<XiaoShouJiLu> listData) {
                TextView tv_ji_lu_name,tv_ji_lu_num,tv_ji_lu_jia_ge;
                if(convertView==null){
                    convertView= LayoutInflater.from(ShowXiaoShouJiLuActivity.this).inflate(R.layout.xiao_shou_ji_lu_items,null);

                }

                tv_ji_lu_name=convertView.findViewById(R.id.tv_ji_lu_name);
                tv_ji_lu_num=convertView.findViewById(R.id.tv_ji_lu_num);
                tv_ji_lu_jia_ge=convertView.findViewById(R.id.tv_ji_lu_jia_ge);
                tv_ji_lu_name.setText(datas.get(position).getShang_ping_ming_cheng());
                tv_ji_lu_num.setText(datas.get(position).getShang_ping_shu_liang()+"");
                tv_ji_lu_jia_ge.setText(datas.get(position).getChu_shou_jia_ge()+"");


                return convertView;
            }
        };
        lv_show_xiang_xi_shang_ping_ji_lu.setAdapter(showXiaoShowJiLuAdapter);
        lv_show_xiang_xi_shang_ping_ji_lu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View inflate = LayoutInflater.from(ShowXiaoShouJiLuActivity.this).inflate(R.layout.shang_ping_chu_shou_ji_lu_layout, null);
                TextView tv_tiao_ma_show,tv_show_name,tv_sum,tv_lei_xing_xiang_qing,tv_ji_lu_jia_ge,tv_chu_shou_ji_lu_date;
                tv_tiao_ma_show=inflate.findViewById(R.id.tv_tiao_ma_show);
                tv_show_name=inflate.findViewById(R.id.tv_show_name);
                tv_sum=inflate.findViewById(R.id.tv_sum);
                tv_lei_xing_xiang_qing=inflate.findViewById(R.id.tv_lei_xing_xiang_qing);
                tv_ji_lu_jia_ge=inflate.findViewById(R.id.tv_ji_lu_jia_ge);
                tv_chu_shou_ji_lu_date=inflate.findViewById(R.id.tv_chu_shou_ji_lu_date);

                tv_tiao_ma_show.setText(datas.get(position).getShang_ping_tiao_ma());
                tv_show_name.setText(datas.get(position).getShang_ping_ming_cheng());
                tv_sum.setText(datas.get(position).getShang_ping_shu_liang()+"");
                tv_lei_xing_xiang_qing.setText(datas.get(position).getShang_ping_lei_xing());
                tv_ji_lu_jia_ge.setText(datas.get(position).getChu_shou_jia_ge()+"");
                tv_chu_shou_ji_lu_date.setText(datas.get(position).getChu_shou_ri_qi());

                new AlertDialog.Builder(ShowXiaoShouJiLuActivity.this).setView(inflate).setPositiveButton("确定",null).show();
            }
        });


    }
}
