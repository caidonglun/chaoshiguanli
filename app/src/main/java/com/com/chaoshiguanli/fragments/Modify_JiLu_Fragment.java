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

import com.chaoshiguanli.bean.XiuGaiJiLu;
import com.example.administrator.chaoshiguanlis.MainActivity;
import com.example.administrator.chaoshiguanlis.R;
import com.chaoshiguanli.adapter.XiuGaiJiLuAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class Modify_JiLu_Fragment extends Fragment implements View.OnClickListener {

//    ListView lv_jilu_item;
    View view;
    List<XiuGaiJiLu> datas;
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


        Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from xiugaijilu where usernumber=? and dateandtime like ?", new String[]{MainActivity.UserName,"%"+formatDate+"%"});
        for(;cursor.moveToNext();){
            datas.add(new XiuGaiJiLu(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
        }
        Toast.makeText(getActivity(), ""+datas.size(), Toast.LENGTH_SHORT).show();

        view=LayoutInflater.from(getActivity()).inflate(R.layout.modify_ji_lu_layout,null);

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
            Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from xiugaijilu where usernumber=? and dateandtime like ?", new String[]{MainActivity.UserName, "%"+tv_setDate.getText().toString()+"%"});
            for (; cursor.moveToNext(); ) {
                datas.add(new XiuGaiJiLu(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
            }
            Toast.makeText(getActivity(), "" + datas.size(), Toast.LENGTH_SHORT).show();
            AdapterSet(position);

                break;

            case 1:
                datas.removeAll(datas);
                Cursor cursor1 = MainActivity.readableDatabase.rawQuery("select * from xiugaijilu where usernumber=? and dateandtime like ?", new String[]{MainActivity.UserName,"%"+tv_setDate.getText().toString()+"%"});
                for(;cursor1.moveToNext();){
                    datas.add(new XiuGaiJiLu(cursor1.getString(0),cursor1.getString(1),cursor1.getString(2),cursor1.getString(3),cursor1.getString(4),cursor1.getString(5)));
                }
                Toast.makeText(getActivity(), ""+datas.size(), Toast.LENGTH_SHORT).show();
                AdapterSet(position);
                break;

            case 2:
                datas.removeAll(datas);
                Cursor cursor2 = MainActivity.readableDatabase.rawQuery("select * from xiugaijilu where usernumber=? and dateandtime like ?", new String[]{MainActivity.UserName,"%"+tv_setDate.getText().toString()+"%"});
                for(;cursor2.moveToNext();){
                    datas.add(new XiuGaiJiLu(cursor2.getString(0),cursor2.getString(1),cursor2.getString(2),cursor2.getString(3),cursor2.getString(4),cursor2.getString(5)));
                }
                Toast.makeText(getActivity(), ""+datas.size(), Toast.LENGTH_SHORT).show();
                AdapterSet(position);
                break;
        }
    }

    private void AdapterSet(int position) {
        ListView lv_items=views.get(position).findViewById(R.id.lv_jilu_item);
        XiuGaiJiLuAdapter myAdapter = lv_Adapter_set();
        lv_items.setAdapter(myAdapter);
//        点击查看修改记录详情
        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int supper_position=datas.size()-1-position;
                View view_modify=LayoutInflater.from(getActivity()).inflate(R.layout.shang_ping_modify_ji_lu_layout,null);
                final Button tv_before_title,tv_after_title;
                final ViewPager vp_before_after_content;
                final List<View> views= new ArrayList<>();
                PagerAdapter pagerAdapter;
                LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
                views.add(layoutInflater.inflate(R.layout.show_xiu_gai_xin_xi_layout,null));
                views.add(layoutInflater.inflate(R.layout.show_xiu_gai_xin_xi_layout,null));
                tv_before_title = view_modify.findViewById(R.id.tv_before_title);
                tv_after_title=view_modify.findViewById(R.id.tv_after_title);
                vp_before_after_content=view_modify.findViewById(R.id.vp_before_after_content);

                ColorSet(tv_before_title,tv_after_title);
                tv_before_title.setBackgroundColor(Color.parseColor("#99cc00"));
                vp_before_after_content.setCurrentItem(0);

                tv_before_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ColorSet(tv_before_title,tv_after_title);
                        tv_before_title.setBackgroundColor(Color.parseColor("#99cc00"));
                        vp_before_after_content.setCurrentItem(0);
                    }


                });
                tv_after_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ColorSet(tv_before_title,tv_after_title);
                        tv_after_title.setBackgroundColor(Color.parseColor("#99cc00"));
                        vp_before_after_content.setCurrentItem(1);
                    }
                });


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
                vp_before_after_content.setAdapter(pagerAdapter);
                //                默认还有一次
                showDataSet(0,datas.get(supper_position).getGai_qian_xing_xi(),views);

                vp_before_after_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if(position==0){
                            ColorSet(tv_before_title,tv_after_title);
                            tv_before_title.setBackgroundColor(Color.parseColor("#99cc00"));
                            showDataSet(position,datas.get(supper_position).getGai_qian_xing_xi(), views);

                        }

                        if(position==1){
                            ColorSet(tv_before_title,tv_after_title);
                            tv_after_title.setBackgroundColor(Color.parseColor("#99cc00"));

                            showDataSet(position,datas.get(supper_position).getGai_hou_xing_xi(), views);

                        }

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

                new AlertDialog.Builder(getActivity()).setView(view_modify).setPositiveButton("确定",null).show();
            }

            private void showDataSet(int position, String gai_qian_xing_xi, List<View> views) {
//                        String gai_qian_xing_xi = datas.get(supper_position).getGai_qian_xing_xi();
                List<String> strings= new ArrayList<>();
                String[] split= gai_qian_xing_xi.split("\\n");
                Collections.addAll(strings, split);
                if(strings.size()<6){
                    strings.add(" ");
                }
//                Toast.makeText(getActivity(), ""+strings.size(), Toast.LENGTH_SHORT).show();
                TextView tv_id,tv_name,tv_num,tv_show_lei_xing,tv_jia_ge,tv_bei_zhu;
                tv_id = views.get(position).findViewById(R.id.tv_id);
                tv_name= views.get(position).findViewById(R.id.tv_name);
                tv_num= views.get(position).findViewById(R.id.tv_num);
                tv_show_lei_xing=views.get(position).findViewById(R.id.tv_show_lei_xing);
                tv_jia_ge= views.get(position).findViewById(R.id.tv_jia_ge);
                tv_bei_zhu= views.get(position).findViewById(R.id.tv_bei_zhu);
                tv_id.setText(strings.get(0));
                tv_name.setText(strings.get(1));
                tv_num.setText(strings.get(2));
                tv_show_lei_xing.setText(strings.get(3));
                tv_jia_ge.setText(strings.get(4));
                tv_bei_zhu.setText(strings.get(5));
            }

            private void ColorSet(Button tv_before_title, Button tv_after_title) {
                tv_before_title.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_after_title.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

    }

    private XiuGaiJiLuAdapter lv_Adapter_set() {
        return new XiuGaiJiLuAdapter(datas) {
            @Override
            protected View setView(int position, View convertView, ViewGroup parent, List<XiuGaiJiLu> listData) {
                if(convertView==null){
                    convertView=LayoutInflater.from(getActivity()).inflate(R.layout.modify_jilu_list_items,null);
                    TextView tv_name=convertView.findViewById(R.id.tv_list_show);
                    TextView tv_date=convertView.findViewById(R.id.tv_date);
                    tv_name.setText(listData.get(listData.size()-1-position).getShang_ping_ming_cheng());
                    tv_date.setText(listData.get(listData.size()-1-position).getXiu_gai_shi_jian());
                }else{
//                    convertView=LayoutInflater.from(getActivity()).inflate(R.layout.jilu_list_items,null);
                    TextView tv_name=convertView.findViewById(R.id.tv_list_show);
                    TextView tv_date=convertView.findViewById(R.id.tv_date);
                    tv_name.setText(listData.get(listData.size()-1-position).getShang_ping_ming_cheng());
                    tv_date.setText(listData.get(listData.size()-1-position).getXiu_gai_shi_jian());
                }

                return convertView;
            }
        };
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
