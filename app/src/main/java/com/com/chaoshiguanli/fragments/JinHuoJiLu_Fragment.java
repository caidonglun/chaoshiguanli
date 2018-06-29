package com.com.chaoshiguanli.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoshiguanli.bean.JinHuoJiLu;
import com.chaoshiguanli.adapter.JinHuoJiLuAdapter;
import com.example.administrator.chaoshiguanlis.MainActivity;
import com.example.administrator.chaoshiguanlis.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class JinHuoJiLu_Fragment extends Fragment implements View.OnClickListener {

//    ListView lv_jilu_item;
    View view;
    List<JinHuoJiLu> datas;
    ViewPager vp_content;
    List<View> views;
    PagerAdapter pagerAdapter;
    TextView iv_day,iv_month,iv_year,tv_setDate;
    int mYear=2018,mMonth=1,mDay=1,thisDate=0;
    Button btn_return_ji_lu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String formatDate = sdf.format(date);
        final String[] dates=formatDate.split("-");
        mYear=Integer.parseInt(dates[0]);
        mMonth=(Integer.parseInt(dates[1]));
        mDay=Integer.parseInt(dates[2]);
        Toast.makeText(getActivity(), ""+mYear+" "+mMonth+" "+mDay, Toast.LENGTH_SHORT).show();

        views=new ArrayList<>();
        LayoutInflater li=LayoutInflater.from(getActivity());
        views.add(li.inflate(R.layout.day_layout,null));
        views.add(li.inflate(R.layout.month_layout,null));
        views.add(li.inflate(R.layout.year_layout,null));
        datas=new ArrayList<>();


        Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from jinhuojilu where usernumber=? and adddate=?", new String[]{MainActivity.UserName,formatDate});
        for(;cursor.moveToNext();){
            datas.add(new JinHuoJiLu(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getDouble(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9)));
        }
        Toast.makeText(getActivity(), ""+datas.size(), Toast.LENGTH_SHORT).show();

        view=LayoutInflater.from(getActivity()).inflate(R.layout.jin_huo_ji_lu_layout,null);
//        lv_jilu_item=view.findViewById(R.id.lv_jilu_item);
//        lv_jilu_item.setAdapter(lv_Adapter_set());
//        lv_jilu_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                new AlertDialog.Builder(getActivity()).setTitle("items"+position).setPositiveButton("确定", null).show();
//            }
//        });

        btn_return_ji_lu=view.findViewById(R.id.btn_return_ji_lu);
        btn_return_ji_lu.setOnClickListener(this);
        iv_day=view.findViewById(R.id.iv_day);
        iv_day.setOnClickListener(this);
        iv_month=view.findViewById(R.id.iv_month);
        iv_month.setOnClickListener(this);
        iv_year=view.findViewById(R.id.iv_year);
        iv_year.setOnClickListener(this);
        tv_setDate=view.findViewById(R.id.tv_setDate);
        tv_setDate.setOnClickListener(this);

        vp_content=view.findViewById(R.id.vp_content);
         pagerAdapter=new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }

        };
        vp_content.setOffscreenPageLimit(3);
        vp_content.setAdapter(pagerAdapter);
//        设置默认状态
        vp_content.setCurrentItem(0);
        tv_setDate(0);
        iv_day.setBackgroundColor(Color.parseColor("#daff80"));
        AdapterSet(0);

//设置选项卡的选择事件
        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                titleSetBackground();
                if(position==0){
                    tv_setDate(0);
                    thisDate=0;
                    iv_day.setBackgroundColor(Color.parseColor("#daff80"));

                    replaceAdapter(position);
                }

                if(position==1){
                    iv_month.setBackgroundColor(Color.parseColor("#daff80"));
                    tv_setDate(1);
                    thisDate=1;

                    replaceAdapter(position);
                }

                if(position==2){
                    iv_year.setBackgroundResource(R.drawable.year_border_true);
                    tv_setDate(2);
                    thisDate=2;

                    replaceAdapter(position);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    private void replaceAdapter(int position) {
        switch (position) {
            case 0:
            datas.removeAll(datas);
            Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from jinhuojilu where usernumber=? and adddate=?", new String[]{MainActivity.UserName, tv_setDate.getText().toString()});
            for (; cursor.moveToNext(); ) {
                datas.add(new JinHuoJiLu(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getDouble(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9)));
            }
            Toast.makeText(getActivity(), "" + datas.size(), Toast.LENGTH_SHORT).show();
            AdapterSet(position);

                break;

            case 1:
                datas.removeAll(datas);
                Cursor cursor1 = MainActivity.readableDatabase.rawQuery("select * from jinhuojilu where usernumber=? and adddate like ?", new String[]{MainActivity.UserName,"%"+tv_setDate.getText().toString()+"%"});
                for(;cursor1.moveToNext();){
                    datas.add(new JinHuoJiLu(cursor1.getString(0),cursor1.getString(1),cursor1.getString(2),cursor1.getInt(3),cursor1.getDouble(4),cursor1.getString(5),cursor1.getString(6),cursor1.getString(7),cursor1.getString(8),cursor1.getString(9)));
                }
                Toast.makeText(getActivity(), ""+datas.size(), Toast.LENGTH_SHORT).show();
                AdapterSet(position);
                break;

            case 2:
                datas.removeAll(datas);
                Cursor cursor2 = MainActivity.readableDatabase.rawQuery("select * from jinhuojilu where usernumber=? and adddate like ?", new String[]{MainActivity.UserName,"%"+tv_setDate.getText().toString()+"%"});
                for(;cursor2.moveToNext();){
                    datas.add(new JinHuoJiLu(cursor2.getString(0),cursor2.getString(1),cursor2.getString(2),cursor2.getInt(3),cursor2.getDouble(4),cursor2.getString(5),cursor2.getString(6),cursor2.getString(7),cursor2.getString(8),cursor2.getString(9)));
                }
                Toast.makeText(getActivity(), ""+datas.size(), Toast.LENGTH_SHORT).show();
                AdapterSet(position);
                break;
        }
    }

    private void AdapterSet(int position) {
        ListView lv_items=views.get(position).findViewById(R.id.lv_jilu_item);
        JinHuoJiLuAdapter myAdapter = lv_Adapter_set();
        lv_items.setAdapter(myAdapter);
        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View view_jin_huo_ji_lu=LayoutInflater.from(getActivity()).inflate(R.layout.shang_ping_add_ji_lu_layout,null);
                TextView tv_tiao_ma_show,tv_show_name,tv_sum,tv_jin_huo_jia_ge,tv_lei_xing_xiang_qing,tv_bei_zhu_xing_xi,tv_chang_shang_ming_cheng,tv_lian_xi_fang_shi,tv_add_date;
                tv_tiao_ma_show = view_jin_huo_ji_lu.findViewById(R.id.tv_tiao_ma_show);
                tv_show_name=view_jin_huo_ji_lu.findViewById(R.id.tv_show_name);
                tv_sum=view_jin_huo_ji_lu.findViewById(R.id.tv_sum);
                tv_jin_huo_jia_ge=view_jin_huo_ji_lu.findViewById(R.id.tv_jin_huo_jia_ge);
                tv_lei_xing_xiang_qing=view_jin_huo_ji_lu.findViewById(R.id.tv_lei_xing_xiang_qing);
                tv_bei_zhu_xing_xi=view_jin_huo_ji_lu.findViewById(R.id.tv_bei_zhu_xing_xi);
                tv_chang_shang_ming_cheng=view_jin_huo_ji_lu.findViewById(R.id.tv_chang_shang_ming_cheng);
                tv_lian_xi_fang_shi=view_jin_huo_ji_lu.findViewById(R.id.tv_lian_xi_fang_shi);
                tv_add_date=view_jin_huo_ji_lu.findViewById(R.id.tv_add_date);
                tv_tiao_ma_show.setText(datas.get(datas.size()-1-position).getShang_ping_tiao_ma());
                tv_show_name.setText(datas.get(datas.size()-1-position).getShang_ping_ming_cheng());
                tv_sum.setText(""+datas.get(datas.size()-1-position).getShang_ping_shu_liang());
                tv_jin_huo_jia_ge.setText(datas.get(datas.size()-1-position).getJin_huo_jia_ge()+"");
                tv_lei_xing_xiang_qing.setText(datas.get(datas.size()-1-position).getShang_ping_lei_xing());
                tv_bei_zhu_xing_xi.setText(datas.get(datas.size()-1-position).getBei_zhu_lei_xing());
                tv_chang_shang_ming_cheng.setText(datas.get(datas.size()-1-position).getChang_shang_ming_cheng());
                tv_lian_xi_fang_shi.setText(datas.get(datas.size()-1-position).getLian_xi_fang_shi());
                tv_add_date.setText(datas.get(datas.size()-1-position).getTian_jia_ri_qi());


                new AlertDialog.Builder(getActivity()).setView(view_jin_huo_ji_lu).setPositiveButton("确定",null).show();
            }
        });

    }

    private JinHuoJiLuAdapter lv_Adapter_set() {
        JinHuoJiLuAdapter myAdapter=new JinHuoJiLuAdapter(datas) {
            @Override
            protected View setView(int position, View convertView, ViewGroup parent, List<JinHuoJiLu> listData) {
                if(convertView==null){
                    convertView=LayoutInflater.from(getActivity()).inflate(R.layout.jilu_list_items,null);
                    TextView tv_name=convertView.findViewById(R.id.tv_list_show);
                    TextView tv_yu_liang=convertView.findViewById(R.id.tv_yu_liang);
                    TextView tv_date=convertView.findViewById(R.id.tv_date);
                    tv_name.setText(listData.get(listData.size()-1-position).getShang_ping_ming_cheng());
                    tv_yu_liang.setText("数量"+listData.get(listData.size()-1-position).getShang_ping_shu_liang());
                    tv_date.setText(listData.get(listData.size()-1-position).getTian_jia_ri_qi());
                }else{
                    convertView=LayoutInflater.from(getActivity()).inflate(R.layout.jilu_list_items,null);
                    TextView tv_name=convertView.findViewById(R.id.tv_list_show);
                    TextView tv_yu_liang=convertView.findViewById(R.id.tv_yu_liang);
                    TextView tv_date=convertView.findViewById(R.id.tv_date);
                    tv_name.setText(listData.get(listData.size()-1-position).getShang_ping_ming_cheng());
                    tv_yu_liang.setText("数量"+listData.get(listData.size()-1-position).getShang_ping_shu_liang());
                    tv_date.setText(listData.get(listData.size()-1-position).getTian_jia_ri_qi());
                }


                return convertView;
            }
        };
        return myAdapter;
    }

    private void titleSetBackground() {
        iv_day.setBackgroundColor(Color.parseColor("#ffffff"));
        iv_month.setBackgroundColor(Color.parseColor("#ffffff"));
        iv_year.setBackgroundResource(R.drawable.year_border_false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_return_ji_lu:
                getActivity().getFragmentManager().beginTransaction().replace(R.id.ll_content,new Four_Fragment()).commit();
                break;

            case  R.id.iv_day:
//                dateGet();
                vp_content.setCurrentItem(0);
                tv_setDate(0);
                thisDate=0;
                AdapterSet(0);
                break;

            case  R.id.iv_month:
                vp_content.setCurrentItem(1);
                tv_setDate(1);
                thisDate=1;
                AdapterSet(1);
                break;

            case R.id.iv_year:
                vp_content.setCurrentItem(2);
                tv_setDate(2);
                thisDate=2;
                AdapterSet(2);
                break;

            case R.id.tv_setDate:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Toast.makeText(getActivity(), ""+year+" "+(month+1)+" "+dayOfMonth, Toast.LENGTH_SHORT).show();
                        mDay=dayOfMonth;
                        mMonth=month+1;
                        mYear=year;

                        if(thisDate==0){
                            String sMonth=dateToString(mMonth);
                            String sDay=dateToString(mDay);
                            tv_setDate.setText(mYear+"-"+sMonth+"-"+sDay);

                            replaceAdapter(0);

                        }

                        if(thisDate==1){
                            String sMonth=dateToString(mMonth);
                            tv_setDate.setText(mYear+"-"+sMonth);

                            replaceAdapter(1);

                        }

                        if(thisDate==2){
                            tv_setDate.setText(""+mYear);

                            replaceAdapter(2);

                        }
                    }
                }, mYear, mMonth-1, mDay);
                datePickerDialog.setTitle("选择日期");
                datePickerDialog.show();

        }
    }

    private void tv_setDate(int position) {
        if(position==0){
            tv_setDate.setText(mYear+"-"+dateToString(mMonth)+"-"+dateToString(mDay));
        }

        if(position==1){
            tv_setDate.setText(mYear+"-"+dateToString(mMonth));
        }

        if(position==2){

            tv_setDate.setText(mYear+"");
        }

    }




    private String dateToString(int MonthOrDay) {
        String smonth;
        if(MonthOrDay<10){
            smonth="0"+MonthOrDay;
        }else{
            smonth=""+MonthOrDay;
        }
        return smonth;
    }
}
