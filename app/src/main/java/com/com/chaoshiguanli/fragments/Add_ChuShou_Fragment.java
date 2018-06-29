package com.com.chaoshiguanli.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoshiguanli.bean.ShangPingXingXi;
import com.example.administrator.chaoshiguanlis.MainActivity;
import com.example.administrator.chaoshiguanlis.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class Add_ChuShou_Fragment extends Fragment implements View.OnClickListener {
    private View view;
    TextView et_id, tv_name,tv_ku_cun_sum_count,tv_shang_ping_lei_xing;
    ImageView remove_sum,add_sum;
    EditText et_sums,et_jia_ge_zheng_shu,et_jia_ge_xiao_shu;
    Button add_sang_pin;
    AutoCompleteTextView auto_query_tv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        List<String> prompt=new ArrayList<>();
        Cursor cursor = MainActivity.readableDatabase.rawQuery("select commodityname from kucunbiao ", null);
        for (;cursor.moveToNext();){
            prompt.add(cursor.getString(0));
        }

        view=LayoutInflater.from(getActivity()).inflate(R.layout.add_chushou_fragment,null);
        ImageView return_iv_btn=view.findViewById(R.id.return_iv_btn);
        return_iv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().replace(R.id.ll_content,new One_fragment()).commit();
            }
        });
//        自动填充文本控件

        auto_query_tv=view.findViewById(R.id.auto_query_tv);
        ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(),R.layout.auto_items_tv, prompt);
        auto_query_tv.setAdapter(arrayAdapter);
//删除字符按钮
        final ImageView iv_auto_clear=view.findViewById(R.id.iv_auto_clear);
        iv_auto_clear.setVisibility(View.GONE);
        iv_auto_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto_query_tv.setText("");
            }
        });
//        自动填充文本事件
        auto_query_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                数据库事件

                if(auto_query_tv.getText().toString().length()>0){
                    iv_auto_clear.setVisibility(View.VISIBLE);
                }else{
                    iv_auto_clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                Cursor cursor1 = MainActivity.readableDatabase.rawQuery("select usernumber,barcode,commodityname,quantityofgoods,commoditytype,saleprice from kucunbiao where usernumber=? and commodityname like ?", new String[]{MainActivity.UserName, "%" + auto_query_tv.getText().toString() + "%"});
                if (cursor1.getCount()==1) {
                    cursor1.moveToFirst();
                    et_id.setText(cursor1.getString(1));
                    tv_name.setText(cursor1.getString(2));
                    tv_shang_ping_lei_xing.setText(cursor1.getString(4));
                    tv_ku_cun_sum_count.setText(cursor1.getInt(3)+"");

                    double jia_ge=cursor1.getDouble(5);
                    String jia_ges=String.valueOf(jia_ge);
                    String[] split = jia_ges.split("\\.");
                    et_jia_ge_zheng_shu.setText(""+split[0]);
                    et_jia_ge_xiao_shu.setText("" + split[1]);

//                数据库事件

                    Toast.makeText(getActivity(), "搜索成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        查询按钮
        ImageView iv_query=view.findViewById(R.id.iv_query);

        et_id = view.findViewById(R.id.et_id);
        tv_name = view.findViewById(R.id.tv_name);
        tv_ku_cun_sum_count=view.findViewById(R.id.tv_ku_cun_sum_count);
        remove_sum=view.findViewById(R.id.remove_sum);
        add_sum=view.findViewById(R.id.add_sum);
        et_sums=view.findViewById(R.id.et_sums);

        tv_shang_ping_lei_xing=view.findViewById(R.id.tv_shang_ping_lei_xing);
        et_jia_ge_zheng_shu=view.findViewById(R.id.et_jia_ge_zheng_shu);
        et_jia_ge_xiao_shu=view.findViewById(R.id.et_jia_ge_xiao_shu);

        add_sang_pin=view.findViewById(R.id.add_sang_pin);

        iv_query.setOnClickListener(this);
        remove_sum.setOnClickListener(this);
        add_sum.setOnClickListener(this);
        add_sang_pin.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_query:
//                查询数据库需要
                Cursor cursor1 = MainActivity.readableDatabase.rawQuery("select usernumber,barcode,commodityname,quantityofgoods,commoditytype,saleprice from kucunbiao where usernumber=? and commodityname like ?", new String[]{MainActivity.UserName, "%" + auto_query_tv.getText().toString() + "%"});
                if (cursor1.getCount()==1) {
                    cursor1.moveToFirst();
                    et_id.setText(cursor1.getString(1));
                    tv_name.setText(cursor1.getString(2));
                    tv_shang_ping_lei_xing.setText(cursor1.getString(4));
                    tv_ku_cun_sum_count.setText(cursor1.getInt(3)+"");

                    double jia_ge=cursor1.getDouble(5);
                    String jia_ges=String.valueOf(jia_ge);
                    String[] split = jia_ges.split("\\.");
                    et_jia_ge_zheng_shu.setText(""+split[0]);
                    et_jia_ge_xiao_shu.setText("" + split[1]);

                    Toast.makeText(getActivity(), "搜索成功！", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.add_sang_pin:
//                修改数据库事件
                if(et_id.getText().toString().length()>0) {
                    if (Integer.parseInt(tv_ku_cun_sum_count.getText().toString()) > 0) {
                        Date date = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String format = simpleDateFormat.format(date);

                        try {
                            MainActivity.readableDatabase.beginTransaction();
                            MainActivity.readableDatabase.execSQL("update kucunbiao set quantityofgoods=quantityofgoods-? where usernumber=? and barcode=?", new Object[]{Integer.parseInt(et_sums.getText().toString()), MainActivity.UserName, et_id.getText().toString()});

                            MainActivity.readableDatabase.execSQL("insert  into `shangpingxingxi`(`usernumber`,`barcode`,`commodityname`,`quantityofgoods`,`commoditytype`,`saleprice`,`adddate`) values (?,?,?,?,?,?,?)", new Object[]{MainActivity.UserName, et_id.getText().toString(), tv_name.getText().toString(), Integer.parseInt(et_sums.getText().toString()), tv_shang_ping_lei_xing.getText().toString(), Double.parseDouble(et_jia_ge_zheng_shu.getText().toString() + "." + et_jia_ge_xiao_shu.getText().toString()), format});

                            MainActivity.datas.add(new ShangPingXingXi(MainActivity.UserName, et_id.getText().toString(), tv_name.getText().toString(), Integer.parseInt(et_sums.getText().toString()), tv_shang_ping_lei_xing.getText().toString(), Double.parseDouble(et_jia_ge_zheng_shu.getText().toString() + "." + et_jia_ge_xiao_shu.getText().toString()), format));

                            One_fragment.sumAndZongJiaZhi();
                            One_fragment.OneFragmentAdapter.notifyDataSetChanged();

                            MainActivity.readableDatabase.setTransactionSuccessful();
                            MainActivity.readableDatabase.endTransaction();
//                                将基本信息保存到shangpingxingxi表中

                        } catch (Exception e) {
                            MainActivity.readableDatabase.endTransaction();
                        }

                        Toast.makeText(getActivity(), "添加商品成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "添加商品失败！数量不足~", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "添加商品失败！请先搜索商品~", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.remove_sum:
                if (Integer.parseInt(et_sums.getText().toString()) > 1) {
                    et_sums.setText((Integer.parseInt(et_sums.getText().toString()) - 1) + "");
                }
                break;

            case R.id.add_sum:
                if (Integer.parseInt(et_sums.getText().toString()) < Integer.parseInt(tv_ku_cun_sum_count.getText().toString())) {
                    et_sums.setText((Integer.parseInt(et_sums.getText().toString()) + 1) + "");
                }
                break;
        }
    }
}
