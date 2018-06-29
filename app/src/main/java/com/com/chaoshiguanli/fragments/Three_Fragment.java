package com.com.chaoshiguanli.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoshiguanli.bean.ShangPingKuCun;
import com.daimajia.swipe.SwipeLayout;
import com.example.administrator.chaoshiguanlis.AddActivity;
import com.example.administrator.chaoshiguanlis.MainActivity;
import com.chaoshiguanli.adapter.MyAdapter;
import com.example.administrator.chaoshiguanlis.R;
import com.chaoshiguanli.adapter.ShangPingKuCunAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/6/10.
 */

public class Three_Fragment extends Fragment {

    View view;
    ImageView iv_query_shang_ping,iv_add_shang_ping;
    RelativeLayout ll_gone_content;
    boolean is_ture=true;
    AutoCompleteTextView auto_query_guan_li;
    InputMethodManager inputMethodManager;
    public static  ShangPingKuCunAdapter myAdapter;
    private SwipeLayout sample1;
    public static List<ShangPingKuCun> datas;
    private List<ShangPingKuCun> bei_fen_datas;
    ListView lv_show;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MainActivity.yu_zhi=MainActivity.sharedPreferences.getInt("threshold",-1);
        bei_fen_datas=new ArrayList<>();
        datas=new ArrayList<>();
        Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from kucunbiao where usernumber=?", new String[]{MainActivity.UserName});
//        Toast.makeText(getActivity(), ""+cursor.getCount(), Toast.LENGTH_SHORT).show();
        for (;cursor.moveToNext();){
            boolean or_true=false;
            if(cursor.getInt(9)==1){
                or_true=true;
            }
            datas.add(new ShangPingKuCun(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),cursor.getDouble(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),or_true));
        }

        view=LayoutInflater.from(getActivity()).inflate(R.layout.three_layout,null);
        iv_query_shang_ping=view.findViewById(R.id.iv_query_shang_ping);
        ll_gone_content=view.findViewById(R.id.ll_gone_content);
        auto_query_guan_li=view.findViewById(R.id.auto_query_guan_li);
        lv_show=view.findViewById(R.id.lv_show);
        iv_add_shang_ping=view.findViewById(R.id.iv_add_shang_ping);
        lv_show_setData();
        lv_show.setAdapter(myAdapter);

        iv_add_shang_ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("Id","ThreeFragment");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        iv_query_shang_ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> lists=new ArrayList<>();
                Cursor cursor1 = MainActivity.readableDatabase.rawQuery("select commodityname from kucunbiao where usernumber=?", new String[]{MainActivity.UserName});
                for (;cursor1.moveToNext();){
                    lists.add(cursor1.getString(0));
                }
                ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(),R.layout.auto_items_tv, lists);
                auto_query_guan_li.setAdapter(arrayAdapter);

                if(is_ture) {
//                    Toast.makeText(getActivity(), "进入", Toast.LENGTH_SHORT).show();
                    bei_fen_datas.removeAll(bei_fen_datas);
                    for(int i=0;i<datas.size();i++){
                        bei_fen_datas.add(datas.get(i));
                    }

                    lv_show_setData();
                    lv_show.setAdapter(myAdapter);

                    is_ture=false;
                    ll_gone_content.setVisibility(View.VISIBLE);
                    iv_add_shang_ping.setEnabled(false);
                    auto_query_guan_li.requestFocus();
                    auto_query_guan_li.setFocusable(true);
                    inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                    iv_query_shang_ping.setImageResource(R.mipmap.return_a);
                }else{
                    datas.removeAll(datas);
                    for(int i=0;i<bei_fen_datas.size();i++){
                        datas.add(bei_fen_datas.get(i));
                    }

//                    lv_show_setData();
//                    lv_show.setAdapter(myAdapter);
//                    Toast.makeText(getActivity(), "出去+count"+datas.size(), Toast.LENGTH_SHORT).show();
                    myAdapter.notifyDataSetChanged();
                    iv_add_shang_ping.setEnabled(true);
                    auto_query_guan_li.setText("");
                    inputMethodManager.hideSoftInputFromWindow(auto_query_guan_li.getWindowToken(), 0);
                    is_ture=true;
                    ll_gone_content.setVisibility(View.GONE);
                    iv_query_shang_ping.setImageResource(R.mipmap.query_a);
                }
            }
        });



        auto_query_guan_li.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                Toast.makeText(getActivity(), "进入修改了", Toast.LENGTH_SHORT).show();
                if(auto_query_guan_li.getText().toString().length()>0){
                    datas.removeAll(datas);
                    Cursor cursor1 = MainActivity.readableDatabase.rawQuery("select * from kucunbiao where usernumber=? and commodityname=?", new String[]{MainActivity.UserName, auto_query_guan_li.getText().toString().trim()});
                    for(;cursor1.moveToNext();){
                        boolean or_true=false;
                        if(cursor1.getInt(9)==1){
                            or_true=true;
                        }
                        datas.add(new ShangPingKuCun(cursor1.getString(0),cursor1.getString(1),cursor1.getString(2),cursor1.getInt(3),cursor1.getString(4),cursor1.getDouble(5),cursor1.getString(6),cursor1.getString(7),cursor1.getString(8),or_true));
                    }
                    myAdapter.notifyDataSetChanged();


                    Toast.makeText(getActivity(), "执行查询事件"+cursor1.getCount(), Toast.LENGTH_SHORT).show();
                    //重新设置listView的内容~


                    if(false){
                        inputMethodManager.hideSoftInputFromWindow(auto_query_guan_li.getWindowToken(), 0);
                    }

                }
            }

        });


        return view;
    }

    private void lv_show_setData() {
        myAdapter=new ShangPingKuCunAdapter(datas) {
            @Override
            protected View setView(final int position, View convertView, ViewGroup parent, final List<ShangPingKuCun> listData) {
                if(convertView==null) {
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.three_wipe_lsayout, null);
                    Swipe(convertView,position);
                    TextView tv_list_show = convertView.findViewById(R.id.tv_list_show);
                    tv_list_show.setText(datas.get(datas.size() - 1 - position).getShang_ping_ming_cheng());
                    TextView tv_yu_liang=convertView.findViewById(R.id.tv_yu_liang);
                    tv_yu_liang.setText("数量："+datas.get(datas.size()-1-position).getShang_ping_shu_liang().toString());
                    TextView tv_date=convertView.findViewById(R.id.tv_date);
                    tv_date.setText(listData.get(datas.size() - 1 - position).getGuo_qi_ri_qi());
//                    判断是否为阈值范围
                    if(datas.get(datas.size() - 1 - position).getShang_ping_shu_liang()<=MainActivity.yu_zhi){
                        ColorSet(tv_list_show,tv_yu_liang,tv_date);
                    }else {
                        ColorCancel(tv_list_show,tv_yu_liang,tv_date);
                    }

                }else{
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.three_wipe_lsayout, null);
                    Swipe(convertView,position);
                    TextView tv_list_show = convertView.findViewById(R.id.tv_list_show);
                    tv_list_show.setText(datas.get(datas.size() - 1 - position).getShang_ping_ming_cheng());
                    TextView tv_yu_liang=convertView.findViewById(R.id.tv_yu_liang);
                    tv_yu_liang.setText("数量："+datas.get(datas.size()-1-position).getShang_ping_shu_liang().toString());
                    TextView tv_date=convertView.findViewById(R.id.tv_date);
                    tv_date.setText(datas.get(datas.size() - 1 - position).getGuo_qi_ri_qi());
                    //                    判断是否为阈值范围
                    if(datas.get(datas.size() - 1 - position).getShang_ping_shu_liang()<=MainActivity.yu_zhi){
                        ColorSet(tv_list_show,tv_yu_liang,tv_date);
                    }else{
                        ColorCancel(tv_list_show,tv_yu_liang,tv_date);
                    }
                }
                return convertView;
            }

            private void ColorCancel(TextView tv_list_show, TextView tv_yu_liang, TextView tv_date) {
                tv_list_show.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_yu_liang.setBackgroundColor(Color.parseColor("#ffffff"));
                tv_date.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            private void ColorSet(TextView tv_list_show, TextView tv_yu_liang, TextView tv_date) {
                tv_list_show.setBackgroundColor(Color.parseColor("#fffe93"));
                tv_yu_liang.setBackgroundColor(Color.parseColor("#fffe93"));
                tv_date.setBackgroundColor(Color.parseColor("#fffe93"));
            }
        };
    }

    private void Swipe(View convertView, final int position) {
        sample1 = convertView.findViewById(R.id.sl_content);

        sample1.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                查看商品详情
                View view_show=LayoutInflater.from(getActivity()).inflate(R.layout.shang_ping_guan_li_look_layout,null);
                TextView tv_show_name,tv_tiao_ma_show,tv_sum,tv_lei_xing_xiang_qing,tv_jia_ge_xiang_qing,tv_sheng_can_ri_qi,tv_guo_qi_ri_qi,tv_bei_zhu_xing_xi;
                tv_tiao_ma_show=view_show.findViewById(R.id.tv_tiao_ma_show);
                tv_tiao_ma_show.setText(datas.get(datas.size()-1-position).getShang_ping_tiao_ma());
                tv_show_name=view_show.findViewById(R.id.tv_show_name);
                tv_show_name.setText(datas.get(datas.size()-1-position).getShang_ping_ming_cheng());
                tv_sum=view_show.findViewById(R.id.tv_sum);
                tv_sum.setText(datas.get(datas.size()-1-position).getShang_ping_shu_liang()+"");
                tv_lei_xing_xiang_qing=view_show.findViewById(R.id.tv_lei_xing_xiang_qing);
                tv_lei_xing_xiang_qing.setText(datas.get(datas.size()-1-position).getShang_ping_lei_xing());
                tv_jia_ge_xiang_qing=view_show.findViewById(R.id.tv_jia_ge_xiang_qing);
                tv_jia_ge_xiang_qing.setText(datas.get(datas.size()-1-position).getChu_shou_jia_ge()+"");
                tv_sheng_can_ri_qi=view_show.findViewById(R.id.tv_sheng_can_ri_qi);
                tv_sheng_can_ri_qi.setText(datas.get(datas.size()-1-position).getSheng_can_ri_qi());
                tv_guo_qi_ri_qi=view_show.findViewById(R.id.tv_guo_qi_ri_qi);
                tv_guo_qi_ri_qi.setText(datas.get(datas.size()-1-position).getGuo_qi_ri_qi());
                tv_bei_zhu_xing_xi=view_show.findViewById(R.id.tv_bei_zhu_xing_xi);
                tv_bei_zhu_xing_xi.setText(datas.get(datas.size()-1-position).getBei_zhu_xing_xi());

                new AlertDialog.Builder(getActivity()).setCancelable(false)/*.setTitle("商品详情")*/.setView(view_show).setNegativeButton("确定",null).show();
            }
        });
        sample1.getSurfaceView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), "longClick on surface", Toast.LENGTH_SHORT).show();
                Log.d(MainActivity.class.getName(), "longClick on surface");
                return true;
            }
        });
        sample1.findViewById(R.id.star2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Star", Toast.LENGTH_SHORT).show();

                final String[] sp_id = {""};
                final String[] sp_name = { "" };
                final String[] sp_sum = { "" };
                final String[] new_lei_xing = {""};
                final String[] sp_jia_ge_zheng_shu = { "" };
                final String[] sp_jia_ge_xiao_shu = {""};
                final String[] sp_bei_zhu = { "" };

                View view_set=LayoutInflater.from(getActivity()).inflate(R.layout.three_update_datas,null);
                final EditText et_shang_ping_name,et_shang_ping_num,et_shang_ping_yuan,et_shang_ping_yuan_xiao_shu,et_bei_zhu;
                final Spinner s_jin_huo_lei_xing;
                final TextView et_id_guan_li=view_set.findViewById(R.id.et_id_guan_li);
                et_id_guan_li.setText(datas.get(datas.size()-1-position).getShang_ping_tiao_ma());

                et_shang_ping_name=view_set.findViewById(R.id.et_shang_ping_name);
                et_shang_ping_name.setText(datas.get(datas.size()-1-position).getShang_ping_ming_cheng());

                et_shang_ping_num=view_set.findViewById(R.id.et_shang_ping_num);
                et_shang_ping_num.setText(datas.get(datas.size()-1-position).getShang_ping_shu_liang()+"");

                s_jin_huo_lei_xing=view_set.findViewById(R.id.s_jin_huo_lei_xing);
                List<String> lists=new ArrayList<String>();
                String[] array=getResources().getStringArray(R.array.lei_xing);

//                把自定义类型加入到列表
                for (int i=0;i<array.length;i++){
                    lists.add(array[i]);
                }
                Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from shangpingleixing where usernumber=? ", new String[]{MainActivity.UserName});
                for (;cursor.moveToNext();) {
                    lists.add(cursor.getString(1)+"");
                }

                MyAdapter myAdapte=new MyAdapter(lists) {
                    @Override
                    protected View setView(int position, View convertView, ViewGroup parent, List<String> listData) {
                        if(convertView==null){
                            convertView=LayoutInflater.from(getActivity()).inflate(R.layout.spinner_layout,null);
                            TextView tv_lei_xing=convertView.findViewById(R.id.tv_lei_xing);
                            tv_lei_xing.setText(listData.get(position)+"");
                        }else{
                            TextView tv_lei_xing=convertView.findViewById(R.id.tv_lei_xing);
                            tv_lei_xing.setText(listData.get(position)+"");
                        }

                        return convertView;
                    }
                };

                s_jin_huo_lei_xing.setAdapter(myAdapte);
                int is_num=0;
                for(int i=0;i<lists.size();i++){
                    if(datas.get(datas.size()-1-position).getShang_ping_lei_xing().equals(lists.get(i))){
                        is_num=i;
                    }
                }

                s_jin_huo_lei_xing.setSelection(is_num);
                final List<String> finalLists = lists;
                s_jin_huo_lei_xing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        new_lei_xing[0] = finalLists.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //下面两个一起
                et_shang_ping_yuan=view_set.findViewById(R.id.et_shang_ping_yuan);
                Toast.makeText(getActivity(), ""+datas.get(datas.size() - 1 - position).getChu_shou_jia_ge() + "", Toast.LENGTH_SHORT).show();
                String[] split = (datas.get(datas.size() - 1 - position).getChu_shou_jia_ge() + "").split("\\.");
                Toast.makeText(getActivity(), ""+split.length, Toast.LENGTH_SHORT).show();
                et_shang_ping_yuan.setText(split[0]);
                et_shang_ping_yuan_xiao_shu=view_set.findViewById(R.id.et_shang_ping_yuan_xiao_shu);
                et_shang_ping_yuan_xiao_shu.setText(split[1]);
                et_bei_zhu=view_set.findViewById(R.id.et_bei_zhu);
                et_bei_zhu.setText(datas.get(datas.size() - 1 - position).getBei_zhu_xing_xi());


//                改前数据收集
                final String before_date=""+datas.get(datas.size() - 1 - position).getShang_ping_tiao_ma()+"\n"+datas.get(datas.size() - 1 - position).getShang_ping_ming_cheng()+"\n"+datas.get(datas.size() - 1 - position).getShang_ping_shu_liang()+"\n"+datas.get(datas.size() - 1 - position).getShang_ping_lei_xing()+"\n"+datas.get(datas.size() - 1 - position).getChu_shou_jia_ge()+"\n"+datas.get(datas.size() - 1 - position).getBei_zhu_xing_xi();

                new AlertDialog.Builder(getActivity()).setView(view_set).setCancelable(false).setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sp_id[0] =et_id_guan_li.getText().toString();
                        sp_name[0] =et_shang_ping_name.getText().toString();
                        sp_sum[0] =et_shang_ping_num.getText().toString();
//                        new_lei_xing[0];
                        sp_jia_ge_zheng_shu[0] =et_shang_ping_yuan.getText().toString();
                        sp_jia_ge_xiao_shu[0] =et_shang_ping_yuan_xiao_shu.getText().toString();
                        sp_bei_zhu[0] =et_bei_zhu.getText().toString();

                        String alert_date=sp_id[0]+"\n"+sp_name[0]+"\n"+sp_sum[0]+"\n"+new_lei_xing[0]+"\n"+(sp_jia_ge_zheng_shu[0]+"."+sp_jia_ge_xiao_shu[0])+"\n"+sp_bei_zhu[0];
                        double jia_ge = 0.0;
                        int sum=0;
                        try{
                            sum=Integer.parseInt(sp_sum[0]);
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "数量不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                           jia_ge = Double.parseDouble(sp_jia_ge_zheng_shu[0] + "." + sp_jia_ge_xiao_shu[0]);
                       }catch (Exception e){
                            Toast.makeText(getActivity(), "价格不能为空", Toast.LENGTH_SHORT).show();
                            return;
                       }
                        MainActivity.readableDatabase.beginTransaction();
                        try {
//                      库存表的信息修改后在商品信息表中的数据要更新
                            for(int i=0;i<MainActivity.datas.size();i++){
                                if((datas.get(datas.size()-1-position).getUsernumber().equals(MainActivity.datas.get(i).getYong_hu_bian_hao()))&&(datas.get(datas.size() - 1 - position).getShang_ping_tiao_ma().equals(MainActivity.datas.get(i).getShang_ping_tiao_ma()))){
                                    MainActivity.datas.get(i).setShang_ping_ming_cheng(sp_name[0]);
                                    MainActivity.datas.get(i).setShang_ping_jia_ge(jia_ge);
                                    MainActivity.datas.get(i).setShang_ping_lei_xing(new_lei_xing[0]);
                                    MainActivity.readableDatabase.execSQL("update shangpingxingxi set saleprice=? where usernumber=? and barcode=?",new Object[]{jia_ge,MainActivity.datas.get(i).getYong_hu_bian_hao(),MainActivity.datas.get(i).getShang_ping_tiao_ma()});
                                }
                            }

                            One_fragment.OneFragmentAdapter.notifyDataSetChanged();
//                            MainActivity.datas

                            Date date=new Date();
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
                            String format = sdf.format(date);
                            Toast.makeText(getActivity(), ""+format,Toast.LENGTH_SHORT).show();

//                        修改记录
                            MainActivity.readableDatabase.execSQL("insert  into `xiugaijilu`(`usernumber`,`barcode`,`commodityname`,`preinformation`,`alteredinformation`,dateandtime) values (?,?,?,?,?,?)", new Object[]{MainActivity.UserName, sp_id[0], sp_name[0], before_date, alert_date,format});

//                        修改操作
                            MainActivity.readableDatabase.execSQL("update `kucunbiao` set commodityname=?,quantityofgoods=?,commoditytype=?,saleprice=?,noteinformation=? where barcode=? and usernumber=?", new Object[]{sp_name[0],sum, new_lei_xing[0], jia_ge, sp_bei_zhu[0], sp_id[0], MainActivity.UserName});
                            MainActivity.readableDatabase.setTransactionSuccessful();
                        }finally {
                            MainActivity.readableDatabase.endTransaction();
                        }

                        datas.get(datas.size() - 1 - position).setShang_ping_ming_cheng(sp_name[0]);
                        datas.get(datas.size() - 1 - position).setShang_ping_shu_liang(Integer.parseInt(sp_sum[0]));
                        datas.get(datas.size() - 1 - position).setShang_ping_lei_xing(new_lei_xing[0]);
                        datas.get(datas.size() - 1 - position).setChu_shou_jia_ge(Double.parseDouble(sp_jia_ge_zheng_shu[0]+"."+sp_jia_ge_xiao_shu[0]));
                        datas.get(datas.size() - 1 - position).setBei_zhu_xing_xi(sp_bei_zhu[0]);
                        myAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "结果："+sp_id[0]+" "+sp_name[0]+" "+sp_sum[0]+" "+new_lei_xing[0]+" "+sp_jia_ge_zheng_shu[0]+" "+sp_jia_ge_xiao_shu[0]+" "+sp_bei_zhu[0], Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myAdapter.notifyDataSetChanged();
                    }
                }).show();
            }
        });

        final View finalConvertView = convertView;
        sample1.findViewById(R.id.trash2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.readableDatabase.execSQL("delete from kucunbiao where usernumber=? and barcode=?" ,new Object[]{MainActivity.UserName,datas.get(datas.size() - 1 - position).getShang_ping_tiao_ma()});
                datas.remove(datas.size()-1-position);
                myAdapter.notifyDataSetChanged();
//                setAdapter();
                Toast.makeText(getActivity(), "Trash Bin", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
