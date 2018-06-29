package com.com.chaoshiguanli.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoshiguanli.bean.DanHaoJiLu;
import com.example.administrator.chaoshiguanlis.MainActivity;
import com.example.administrator.chaoshiguanlis.R;
import com.example.administrator.chaoshiguanlis.ShowXiaoShouJiLuActivity;
import com.chaoshiguanli.adapter.XiaoShouJiLuAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class Two_ChuShouJiLu_Fragment extends Fragment implements View.OnClickListener {

//    ListView lv_jilu_item;
    View view;
    List<DanHaoJiLu> datas;
    ViewPager vp_content;
    List<View> views;
    PagerAdapter pagerAdapter;
    TextView iv_day,iv_month,iv_year,tv_setDate;
    int mYear=2018,mMonth=1,mDay=1,thisDate=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MainActivity.yu_zhi=MainActivity.sharedPreferences.getInt("threshold",-1);
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = sdf.format(date);
        String[] dates=formatDate.split("-");
        mYear=Integer.parseInt(dates[0]);
        mMonth=(Integer.parseInt(dates[1]));
        mDay=Integer.parseInt(dates[2]);
        Toast.makeText(getActivity(), ""+mYear+" "+mMonth+" "+mDay, Toast.LENGTH_SHORT).show();

        views=new ArrayList<>();
        LayoutInflater li=LayoutInflater.from(getActivity());
        views.add(li.inflate(R.layout.day_layout,null));
        views.add(li.inflate(R.layout.month_layout,null));
        views.add(li.inflate(R.layout.year_layout,null));

//        Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from danhaojilu where usernumber=?", new String[]{"user"});
        datas=new ArrayList<>();


//        Toast.makeText(getActivity(), ""+cursor.getCount(), Toast.LENGTH_SHORT).show();
//
//        for(;cursor.moveToNext();){
//            datas.add(new DanHaoJiLu(cursor.getString(0),cursor.getDouble(1),cursor.getString(2),cursor.getString(3)));
//        }
//        Collections.reverse(datas);

        view=LayoutInflater.from(getActivity()).inflate(R.layout.two_layout,null);
//        lv_jilu_item=view.findViewById(R.id.lv_jilu_item);
//        lv_jilu_item.setAdapter(lv_Adapter_set());
//        lv_jilu_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                new AlertDialog.Builder(getActivity()).setTitle("items"+position).setPositiveButton("确定", null).show();
//            }
//        });

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
        JiLuSelect();
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
                    iv_day.setBackgroundColor(Color.parseColor("#daff80"));
                    tv_setDate(0);
                    thisDate=0;

                    JiLuSelect();

                    AdapterSet(position);
                }

                if(position==1){
                    iv_month.setBackgroundColor(Color.parseColor("#daff80"));
                    tv_setDate(1);
                    thisDate=1;

                    JiLuSelect();

                    AdapterSet(position);
                }

                if(position==2){
                    iv_year.setBackgroundResource(R.drawable.year_border_true);
                    tv_setDate(2);
                    thisDate=2;

                    JiLuSelect();

                    AdapterSet(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });





        ImageView iv_tu_xing_fen_xi=view.findViewById(R.id.iv_tu_xing_fen_xi);
        iv_tu_xing_fen_xi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().replace(R.id.ll_content,new Two_tu_fragment()).commit();
            }
        });

        return view;
    }

    private void JiLuSelect() {
        datas.removeAll(datas);
        Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from danhaojilu where usernumber=? and dateofsale like ?", new String[]{MainActivity.UserName, "%"+tv_setDate.getText().toString()+"%"});
        for (; cursor.moveToNext(); ) {
            datas.add(new DanHaoJiLu(cursor.getString(0),cursor.getDouble(1),cursor.getString(2),cursor.getString(3)));
        }

        Toast.makeText(getActivity(), "" + datas.size(), Toast.LENGTH_SHORT).show();
    }

    private void AdapterSet(int position) {
        ListView lv_items=views.get(position).findViewById(R.id.lv_jilu_item);
        XiaoShouJiLuAdapter myAdapter = lv_Adapter_set();
        lv_items.setAdapter(myAdapter);
        lv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                打开一activity
                Toast.makeText(getActivity(), "getXiao_shou_dan_hao="+datas.get(position).getXiao_shou_dan_hao(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(), ShowXiaoShouJiLuActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("Id",datas.get(position).getXiao_shou_dan_hao());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private XiaoShouJiLuAdapter lv_Adapter_set() {
        XiaoShouJiLuAdapter myAdapter=new XiaoShouJiLuAdapter(datas) {
            @Override
            protected View setView(int position, View convertView, ViewGroup parent, List<DanHaoJiLu> listData) {
                if(convertView==null){
                    convertView=LayoutInflater.from(getActivity()).inflate(R.layout.dan_hao_ji_lu_list_items,null);
                    DataSetList(convertView,position);

                }else{
                    convertView=LayoutInflater.from(getActivity()).inflate(R.layout.dan_hao_ji_lu_list_items,null);
                    DataSetList(convertView, position);
                }

                return convertView;
            }

            private void DataSetList(View convertView, int position) {
                TextView tv_dan_hao,tv_yu_liang,tv_date;
                tv_dan_hao = convertView.findViewById(R.id.tv_dan_hao);
                tv_yu_liang=convertView.findViewById(R.id.tv_yu_liang);
                tv_date=convertView.findViewById(R.id.tv_date);
                tv_dan_hao.setText(datas.get(position).getXiao_shou_dan_hao());
                tv_yu_liang.setText(""+datas.get(position).getBen_dan_zong_jia_zhi());
                tv_date.setText(datas.get(position).getChu_shou_ri_qi());
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
                            JiLuSelect();
                            AdapterSet(0);
                        }

                        if(thisDate==1){
                            String sMonth=dateToString(mMonth);
                            tv_setDate.setText(mYear+"-"+sMonth);
                            JiLuSelect();
                            AdapterSet(1);
                        }

                        if(thisDate==2){
                            tv_setDate.setText(""+mYear);
                            JiLuSelect();
                            AdapterSet(2);
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
