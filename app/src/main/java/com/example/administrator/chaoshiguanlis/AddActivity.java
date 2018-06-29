package com.example.administrator.chaoshiguanlis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoshiguanli.adapter.MyAdapter;
import com.chaoshiguanli.bean.ShangPingKuCun;
import com.com.chaoshiguanli.fragments.Three_Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.hugo.android.scanner.CaptureActivity;

/**
 * Created by Administrator on 2018/6/10.
 */

public class AddActivity extends Activity {

    public static EditText et_id_guan_li;
    int myear,mmonth,mday;
    Button btn_add_shang_ping_guan_li;
    boolean is_true=false;
    AlertDialog dialog;
    final boolean[] is_run = {true};
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0x111){
                dialog.dismiss();
            }
        }
    };
    Timer timer=new Timer();
    TextView add_tv_sheng_chan_ri_qi;
    EditText et_bei_zhu,et_shang_ping_name,et_shang_ping_num,et_shang_ping_yuan,et_shang_ping_yuan_xiao_shu,et_shang_jin_huo_ping_yuan;
    EditText et_shang_ping_yuan_jin_huo_xiao_shu,et_chang_shang_ming_cheng,et_chang_shang_lian_xi_fang_shi;
    Spinner s_jin_huo_lei_xing;
    List<String> datas;
    String shangpingleixing="";
    String[] array;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas=new ArrayList<>();
        final Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String format = sdf.format(date);
        Toast.makeText(this, ""+format, Toast.LENGTH_SHORT).show();
        String[] split = format.split("-");
        myear=Integer.parseInt(split[0]);
        mmonth=Integer.parseInt(split[1]);
        mday=Integer.parseInt(split[2]);

        array=getResources().getStringArray(R.array.lei_xing);

//                把自定义类型加入到列表
        for (int i=0;i<array.length;i++){
            datas.add(array[i]);
        }
        Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from shangpingleixing where usernumber=? ", new String[]{MainActivity.UserName});
        for (;cursor.moveToNext();) {
            datas.add(cursor.getString(1)+"");
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.add_shang_ping_guan_li);

        et_id_guan_li=findViewById(R.id.et_id_guan_li);
        ImageView iv_shao_ma_ru_ku=findViewById(R.id.iv_shao_ma_ru_ku);
        ImageView ivreturn_activity=findViewById(R.id.ivreturn_activity);
        add_tv_sheng_chan_ri_qi=findViewById(R.id.tv_sheng_chan_ri_qi);
        btn_add_shang_ping_guan_li=findViewById(R.id.btn_add_shang_ping_guan_li);
        final TextView tv_guo_qi_ri_qi=findViewById(R.id.tv_guo_qi_ri_qi);
        et_bei_zhu=findViewById(R.id.et_bei_zhu);
        et_shang_ping_name=findViewById(R.id.et_shang_ping_name);
        et_shang_ping_num=findViewById(R.id.et_shang_ping_num);
        et_shang_jin_huo_ping_yuan=findViewById(R.id.et_shang_jin_huo_ping_yuan);
        et_shang_ping_yuan_jin_huo_xiao_shu=findViewById(R.id.et_shang_ping_yuan_jin_huo_xiao_shu);
        s_jin_huo_lei_xing=findViewById(R.id.s_jin_huo_lei_xing);
        et_shang_ping_yuan=findViewById(R.id.et_shang_ping_yuan);
        et_shang_ping_yuan_xiao_shu=findViewById(R.id.et_shang_ping_yuan_xiao_shu);
        et_chang_shang_ming_cheng=findViewById(R.id.et_chang_shang_ming_cheng);
        et_chang_shang_lian_xi_fang_shi=findViewById(R.id.et_chang_shang_lian_xi_fang_shi);

        MyAdapter myAdapte=new MyAdapter(datas) {
            @Override
            protected View setView(int position, View convertView, ViewGroup parent, List<String> listData) {
                if(convertView==null){
                    convertView=LayoutInflater.from(AddActivity.this).inflate(R.layout.spinner_layout,null);
                }
                    TextView tv_lei_xing=convertView.findViewById(R.id.tv_lei_xing);
                    tv_lei_xing.setText(listData.get(position)+"");

                return convertView;
            }
        };
        s_jin_huo_lei_xing.setAdapter(myAdapte);
        s_jin_huo_lei_xing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddActivity.this, ""+datas.get(position), Toast.LENGTH_SHORT).show();
                shangpingleixing=""+datas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//关闭添加界面
        ivreturn_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Three_Fragment.myAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    AddActivity.this.finish();
                }
                AddActivity.this.finish();
            }
        });


        add_tv_sheng_chan_ri_qi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myear=year;
                        mmonth=month+1;
                        mday=dayOfMonth;
//                        Toast.makeText(AddActivity.this, "" + year + " " + month + " " + dayOfMonth, Toast.LENGTH_SHORT).show();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                        Date format1 = null;
                        try {
                            format1 = simpleDateFormat.parse((year + "-" + (month+1) + "-" + dayOfMonth));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(!(tv_guo_qi_ri_qi.getText().toString().equals("请点击选择"))){
                            String guo_qi_data=tv_guo_qi_ri_qi.getText().toString();
                            String[] split1 = guo_qi_data.split("-");
                            Toast.makeText(AddActivity.this, ""+split1[0]+" "+year, Toast.LENGTH_SHORT).show();
                            if(Integer.parseInt(split1[0])<year){
                                tv_guo_qi_ri_qi.setTextColor(Color.RED);
                                is_true=false;
                                new AlertDialog.Builder(AddActivity.this).setPositiveButton("确定",null).setView(LayoutInflater.from(AddActivity.this).inflate(R.layout.alert_dialog_date_guo_xiao_false,null)).setCancelable(false).show();
                            }
                            Toast.makeText(AddActivity.this, ""+split1[1]+" "+(month+1), Toast.LENGTH_SHORT).show();
                            if(Integer.parseInt(split1[1])<(month+1)){
                                tv_guo_qi_ri_qi.setTextColor(Color.RED);
                                is_true=false;
                                new AlertDialog.Builder(AddActivity.this).setPositiveButton("确定",null).setView(LayoutInflater.from(AddActivity.this).inflate(R.layout.alert_dialog_date_guo_xiao_false,null)).setCancelable(false).show();
                            }
                            Toast.makeText(AddActivity.this, ""+split1[2]+" "+dayOfMonth, Toast.LENGTH_SHORT).show();
                            if(Integer.parseInt(split1[2])<=(dayOfMonth)){
                                tv_guo_qi_ri_qi.setTextColor(Color.RED);
                                is_true=false;
                                new AlertDialog.Builder(AddActivity.this).setPositiveButton("确定",null).setView(LayoutInflater.from(AddActivity.this).inflate(R.layout.alert_dialog_date_guo_xiao_false,null)).setCancelable(false).show();
                            }

                        }

                        add_tv_sheng_chan_ri_qi.setText(""+simpleDateFormat.format(format1));
                    }
                }, myear, mmonth-1, mday);
                datePickerDialog.setTitle("选择生产日期");
                datePickerDialog.setCancelable(false);
                datePickerDialog.show();
            }
        });

//        过期日期
        tv_guo_qi_ri_qi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
//                is_true=true;
                tv_guo_qi_ri_qi.setTextColor(Color.parseColor("#000000"));
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        Toast.makeText(AddActivity.this, "" + year + " " + month + " " + dayOfMonth, Toast.LENGTH_SHORT).show();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                        Date after_date = null;
                        try {
                            after_date = simpleDateFormat.parse((year + "-" + (month+1) + "-" + dayOfMonth));

                        String s = add_tv_sheng_chan_ri_qi.getText().toString();
                            Date befor_date = simpleDateFormat.parse(s);
                            int MaxOrMin=after_date.compareTo(befor_date);
                            if(MaxOrMin<0){
                                Toast.makeText(AddActivity.this, "过小", Toast.LENGTH_SHORT).show();
                                tv_guo_qi_ri_qi.setTextColor(Color.RED);
                                tv_guo_qi_ri_qi.setText("" + simpleDateFormat.format(after_date));
                                is_true=false;
                                new AlertDialog.Builder(AddActivity.this).setPositiveButton("确定",null).setView(LayoutInflater.from(AddActivity.this).inflate(R.layout.alert_dialog_date_guo_xiao_false,null)).setCancelable(false).show();
                                return;
                            }
                            if(MaxOrMin>0){
                                Toast.makeText(AddActivity.this, "正常", Toast.LENGTH_SHORT).show();
                            }
                            if(MaxOrMin==0){
                                tv_guo_qi_ri_qi.setText("" + simpleDateFormat.format(after_date));
                                Toast.makeText(AddActivity.this, "相等", Toast.LENGTH_SHORT).show();
                                tv_guo_qi_ri_qi.setTextColor(Color.RED);
                                is_true=false;
                                new AlertDialog.Builder(AddActivity.this).setPositiveButton("确定",null).setView(LayoutInflater.from(AddActivity.this).inflate(R.layout.alert_dialog_date_guo_xiao_false,null)).setCancelable(false).show();
                                return;
                            }



//                        if((Integer.parseInt(split1[0]))>year){
//                            tv_guo_qi_ri_qi.setTextColor(Color.RED);
//                            is_true=false;
//                            new AlertDialog.Builder(AddActivity.this).setPositiveButton("确定",null).setView(LayoutInflater.from(AddActivity.this).inflate(R.layout.alert_dialog_date_guo_xiao_false,null)).setCancelable(false).show();
//                            Toast.makeText(AddActivity.this, ""+split1[0]+" "+year, Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        if((Integer.parseInt(split1[1]))>(month+1)){
//                            Toast.makeText(AddActivity.this, ""+split1[1]+" "+month+1, Toast.LENGTH_SHORT).show();
//                            new AlertDialog.Builder(AddActivity.this).setPositiveButton("确定",null).setView(LayoutInflater.from(AddActivity.this).inflate(R.layout.alert_dialog_date_guo_xiao_false,null)).setCancelable(false).show();
//                            tv_guo_qi_ri_qi.setTextColor(Color.RED);
//                            is_true=false;
//                            return;
//                        }
//                        if((Integer.parseInt(split1[2]))>=dayOfMonth){
//                            Toast.makeText(AddActivity.this, ""+split1[2]+" "+dayOfMonth, Toast.LENGTH_SHORT).show();
//                            new AlertDialog.Builder(AddActivity.this).setPositiveButton("确定",null).setView(LayoutInflater.from(AddActivity.this).inflate(R.layout.alert_dialog_date_guo_xiao_false,null)).setCancelable(false).show();
//                            tv_guo_qi_ri_qi.setTextColor(Color.RED);
//                            is_true=false;
//                            return;
//                        }
                        } catch (Exception e) {
                            is_run[0] =false;
                            is_true=false;
                            tv_guo_qi_ri_qi.setText("请点击选择");
                            new AlertDialog.Builder(AddActivity.this).setPositiveButton("确定",null).setView(LayoutInflater.from(AddActivity.this).inflate(R.layout.alert_dialog_date_false,null)).show();
                            return;
                        }

                        is_true=true;

//                        if (!is_true){
//                            btn_add_shang_ping_guan_li.setEnabled(true);
//                        }

//                        if (is_run[0]) {
//                            tv_guo_qi_ri_qi.setText("" + simpleDateFormat.format(format1));
//                        }

                        if(is_true) {
                            tv_guo_qi_ri_qi.setText("" + simpleDateFormat.format(after_date));
                        }


                    }
                }, myear, mmonth-1, mday);
                datePickerDialog.setTitle("选择过期日期");
                datePickerDialog.setCancelable(false);
                datePickerDialog.show();
            }
        });

        btn_add_shang_ping_guan_li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              数据变量
                String shang_ping_id="",shang_ping_num="",shang_ping_name="",shang_ping_yuan="",sheng_chan_ri_qi="",guo_qi_ri_qi="",bei_zhu="";
                String chang_shang_ming_cheng="",chang_shang_lian_xi_fang_shi="";
                Double shang_jin_huo_ping_yuan=0.0;


                shang_ping_id = et_id_guan_li.getText().toString().trim();
                shang_ping_name = et_shang_ping_name.getText().toString().trim();
                shang_ping_num=et_shang_ping_num.getText().toString().trim();
                shang_ping_yuan=""+et_shang_ping_yuan.getText().toString().trim()+"."+et_shang_ping_yuan_xiao_shu.getText().toString().trim();
                sheng_chan_ri_qi=add_tv_sheng_chan_ri_qi.getText().toString().trim();
                guo_qi_ri_qi=tv_guo_qi_ri_qi.getText().toString().trim();
                shang_jin_huo_ping_yuan=Double.parseDouble(et_shang_jin_huo_ping_yuan.getText().toString().trim()+"."+et_shang_ping_yuan_jin_huo_xiao_shu.getText().toString().trim());
                bei_zhu=et_bei_zhu.getText().toString().trim();
                chang_shang_ming_cheng=et_chang_shang_ming_cheng.getText().toString().trim();
                chang_shang_lian_xi_fang_shi=et_chang_shang_lian_xi_fang_shi.getText().toString().trim();

//                是否为空
                if (shang_ping_id.equals("")||shang_ping_name.equals("")||shang_ping_num.equals("")||shang_ping_yuan.equals("")||sheng_chan_ri_qi.equals("")||guo_qi_ri_qi.equals("")||shang_jin_huo_ping_yuan.equals("")){
                    is_true=false;
                }else {

//                是否有数量问题
                    try {
                        if (Integer.parseInt(shang_ping_num) < 1) {
                            et_shang_ping_yuan.setTextColor(Color.RED);
                            is_true = false;
                        }
                    } catch (Exception e) {

                    }

                }

//验证成功加入数据库
                if(is_true){
//                    数据库事件

                    Three_Fragment.datas.add(new ShangPingKuCun(MainActivity.UserName,shang_ping_id,shang_ping_name,Integer.parseInt(shang_ping_num),shangpingleixing,Double.parseDouble(shang_ping_yuan),sheng_chan_ri_qi,guo_qi_ri_qi,bei_zhu,false));
                    Three_Fragment.myAdapter.notifyDataSetChanged();

                    MainActivity.readableDatabase.beginTransaction();
                    try {

//                    添加库存记录
                        MainActivity.readableDatabase.execSQL("insert  into `kucunbiao`(`usernumber`,`barcode`,`commodityname`,`quantityofgoods`,`commoditytype`,`saleprice`,`dateofmanufacture`,`expirationdate`,`noteinformation`,`whetherithasexpired`) values (?,?,?,?,?,?,?,?,?,?)", new Object[]{MainActivity.UserName, shang_ping_id, shang_ping_name, Integer.parseInt(shang_ping_num), shangpingleixing, Double.parseDouble(shang_ping_yuan), sheng_chan_ri_qi, guo_qi_ri_qi, bei_zhu, false});

//                    添加记录插入
                        Date date = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String formatDate = simpleDateFormat.format(date);
                        MainActivity.readableDatabase.execSQL("insert  into `jinhuojilu`(`usernumber`,`barcode`,`commodityname`,`quantityofgoods`,`purchaseprice`,`commoditytype`,`noteinformation`,`vendorname`,`contactinformation`,`adddate`,isupdate) values (?,?,?,?,?,?,?,?,?,?,?)", new Object[]{MainActivity.UserName, shang_ping_id, shang_ping_name, Integer.parseInt(shang_ping_num), shang_jin_huo_ping_yuan, shangpingleixing, bei_zhu, chang_shang_ming_cheng, chang_shang_lian_xi_fang_shi, formatDate,0});

                        MainActivity.readableDatabase.setTransactionSuccessful();
                        MainActivity.readableDatabase.endTransaction();
                    }catch (Exception e){
                        MainActivity.readableDatabase.endTransaction();
                    }

                    AlertDialog.Builder adb=new AlertDialog.Builder(AddActivity.this);

                    adb.setView(LayoutInflater.from(AddActivity.this).inflate(R.layout.alert_dialog_view_true,null));
                    adb.setCancelable(false);
                     dialog =adb.show();
                    TimerTask timerTask=new TimerTask() {
                        @Override
                        public void run() {
                            Message m=new Message();
                            m.what=0x111;
                            handler.sendMessage(m);
                        }
                    };
                    timer.schedule(timerTask,2000);
                }else{
                    AlertDialog.Builder adb=new AlertDialog.Builder(AddActivity.this);

                    adb.setView(LayoutInflater.from(AddActivity.this).inflate(R.layout.alert_dialog_view_false,null));
                    adb.setCancelable(false);
                    dialog =adb.show();
                    TimerTask timerTask=new TimerTask() {
                        @Override
                        public void run() {
                            Message m=new Message();
                            m.what=0x111;
                            handler.sendMessage(m);
                        }
                    };
                    timer.schedule(timerTask,2000);
                }
            }
        });

        iv_shao_ma_ru_ku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Intent intent=new Intent(AddActivity.this, CaptureActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("Id","AddActivity");
        intent.putExtras(bundle);
        startActivity(intent);
            }
        });

    }
}
