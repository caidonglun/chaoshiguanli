package com.com.chaoshiguanli.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoshiguanli.bean.User;
import com.example.administrator.chaoshiguanlis.LoginActivity;
import com.example.administrator.chaoshiguanlis.MainActivity;
import com.example.administrator.chaoshiguanlis.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/12.
 */

public class Four_Fragment extends Fragment {

    View view;
    TextView tv_user_title;
    ImageView iv_user_setting;
    EditText et_ni_cheng;
    RadioButton cb_nv;
    boolean is_sex = false;
    Button btn_query_add_shang_ping_ji_lu, btn_set_yu_zhi, btn_set_type, btn_type_query, btn_out, btn_query_modify_ji_lu, btn_data_RollBACK;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x111:
                    try {
                        JSONObject jsonObject = new JSONObject(return_datas);
                        if (jsonObject.getString("result").equals("success")) {
                            Toast.makeText(getActivity(), "修改成功！", Toast.LENGTH_SHORT).show();
                            MainActivity.editor.putString("nickname", et_ni_cheng.getText().toString());
                            MainActivity.editor.putBoolean("sex", is_sex);
                            MainActivity.editor.commit();
                        } else if (jsonObject.getString("result").equals("fail")) {
                            Toast.makeText(getActivity(), "修改失败！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "服务器错误！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 0x112:
                    Toast.makeText(getActivity(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                    break;

                case 0x113:
//                    alertDialog.dismiss();
//                    try {
//                        JSONObject jsonObject = new JSONObject(return_datas);
//                        if (jsonObject.getString("result").equals("success")) {
//                            String data = jsonObject.getString("data");
////                            Toast.makeText(getActivity(), "datadddd"+data, Toast.LENGTH_SHORT).show();
////                            所有书籍集合
//                            JSONObject jsonObject1 = new JSONObject(data);
//
////                            单号记录数据获取
//                            List<DanHaoJiLu> danHaoJiLus = new ArrayList<>();
//
//                            try {
//                                JSONArray jsonArray = new JSONArray(jsonObject1.getString("danHaoJiLus"));
////                            Toast.makeText(getActivity(), "danHaoJiLus"+danHaoJiLu, Toast.LENGTH_SHORT).show();
//                                try {
//                                    MainActivity.readableDatabase.beginTransaction();
//                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                        Toast.makeText(getActivity(), "次数："+jsonArray.length(), Toast.LENGTH_SHORT).show();
//                                        JSONObject jsonObject2 = new JSONObject(jsonArray.getString(i));
//                                        danHaoJiLus.add(new DanHaoJiLu(jsonObject2.getString("usernumber"), jsonObject2.getDouble("totalvalue"), jsonObject2.getString("Saleno"), jsonObject2.getString("dateofsale")));
//                                        MainActivity.readableDatabase.execSQL("insert  into `danhaojilu`(`usernumber`,`totalvalue`,`Saleno`,`dateofsale`,isupdate) values (?,?,?,?,?)", new Object[]{jsonObject2.getString("usernumber"), jsonObject2.getDouble("totalvalue"), jsonObject2.getString("Saleno"), jsonObject2.getString("dateofsale"),1});
//                                    }
//                                    MainActivity.readableDatabase.setTransactionSuccessful();
//                                    MainActivity.readableDatabase.endTransaction();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    MainActivity.readableDatabase.endTransaction();
//                                }
//                                Toast.makeText(getActivity(), "danHaoJiLus===" + danHaoJiLus, Toast.LENGTH_SHORT).show();
//                            } catch (Exception e) {
//                                Log.i("cdl", "handleMessage: 没有danHaoJiLus");
//                            }
//
//                            try {
////                            进货记录
//                                List<JinHuoJiLu> jinHuoJiLus = new ArrayList<>();
//
//                                JSONArray jsonArray1 = new JSONArray(jsonObject1.getString("jinHuoJiLus"));
//                                try {
//                                    MainActivity.readableDatabase.beginTransaction();
//                                    for (int i = 0; i < jsonArray1.length(); i++) {
//                                        Toast.makeText(getActivity(), "次数："+jsonArray1.length(), Toast.LENGTH_SHORT).show();
//                                        JSONObject jsonObject2 = new JSONObject(jsonArray1.getString(i));
//                                        jinHuoJiLus.add(new JinHuoJiLu(jsonObject2.getString("usernumber"), jsonObject2.getString("barcode"), jsonObject2.getString("commodityname"), jsonObject2.getInt("quantityofgoods"), jsonObject2.getDouble("purchaseprice"), jsonObject2.getString("commoditytype"), jsonObject2.getString("noteinformation"), jsonObject2.getString("vendorname"), jsonObject2.getString("contactinformation"), jsonObject2.getString("adddate")));
//                                        MainActivity.readableDatabase.execSQL("insert  into `jinhuojilu`(`usernumber`,`barcode`,`commodityname`,`quantityofgoods`,`purchaseprice`,`commoditytype`,`noteinformation`,`vendorname`,`contactinformation`,`adddate`,isupdate) values (?,?,?,?,?,?,?,?,?,?,?)", new Object[]{jsonObject2.getString("usernumber"), jsonObject2.getString("barcode"), jsonObject2.getString("commodityname"), jsonObject2.getInt("quantityofgoods"), jsonObject2.getDouble("purchaseprice"), jsonObject2.getString("commoditytype"), jsonObject2.getString("noteinformation"), jsonObject2.getString("vendorname"), jsonObject2.getString("contactinformation"), jsonObject2.getString("adddate"),1});
//                                    }
//                                    MainActivity.readableDatabase.setTransactionSuccessful();
//                                    MainActivity.readableDatabase.endTransaction();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    MainActivity.readableDatabase.endTransaction();
//                                }
//                            } catch (Exception e) {
//                                Log.i("cdl", "handleMessage: 没有jinHuoJiLus");
//                            }
//
//                            try {
////                            商品库存
//                                List<ShangPingKuCun> shangPingKuCuns = new ArrayList<>();
////insert  into `kucunbiao`(`usernumber`,`barcode`,`commodityname`,`quantityofgoods`,`commoditytype`,`saleprice`,`dateofmanufacture`,`expirationdate`,`noteinformation`,`whetherithasexpired`,`update`) values ('user','22','uu',7,'家居用品类',44.50,'2018-06-24','2018-06-27','',NULL,'2018-06-25 18:37:36'),('user','66','yy',40,'五金工具类',55.00,'2018-06-25','2018-06-28','',NULL,'2018-06-25 18:37:36')
//                                JSONArray jsonArray2 = new JSONArray(jsonObject1.getString("shangPingKuCuns"));
//                                try {
//                                    MainActivity.readableDatabase.beginTransaction();
//                                    for (int i = 0; i < jsonArray2.length(); i++) {
//                                        JSONObject jsonObject2 = new JSONObject(jsonArray2.getString(i));
//                                        Toast.makeText(getActivity(), "jsonObject2.getDouble(\"saleprice\")=" + jsonObject2.getDouble("saleprice"), Toast.LENGTH_SHORT).show();
//                                        shangPingKuCuns.add(new ShangPingKuCun(jsonObject2.getString("usernumber"), jsonObject2.getString("barcode"), jsonObject2.getString("commodityname"), jsonObject2.getInt("quantityofgoods"), jsonObject2.getString("commoditytype"), jsonObject2.getDouble("saleprice"), jsonObject2.getString("dateofmanufacture"), jsonObject2.getString("expirationdate"), jsonObject2.getString("noteinformation"), jsonObject2.getBoolean("whetherithasexpired")));
//                                        MainActivity.readableDatabase.execSQL("insert  into `kucunbiao`(`usernumber`,`barcode`,`commodityname`,`quantityofgoods`,`commoditytype`,`saleprice`,`dateofmanufacture`,`expirationdate`,`noteinformation`,`whetherithasexpired`) values (?,?,?,?,?,?,?,?,?,?)", new Object[]{jsonObject2.getString("usernumber"), jsonObject2.getString("barcode"), jsonObject2.getString("commodityname"), jsonObject2.getInt("quantityofgoods"), jsonObject2.getString("commoditytype"), jsonObject2.getDouble("saleprice"), jsonObject2.getString("dateofmanufacture"), jsonObject2.getString("expirationdate"), jsonObject2.getString("noteinformation"), jsonObject2.getBoolean("whetherithasexpired")});
//                                    }
//                                    MainActivity.readableDatabase.setTransactionSuccessful();
//                                    MainActivity.readableDatabase.endTransaction();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    MainActivity.readableDatabase.endTransaction();
//                                }
//                            } catch (Exception e) {
//                                Log.i("cdl", "handleMessage: 没有shangPingKuCuns");
//                            }
//
//                            try {
////                            商品类型
//                                List<ShangPingLeiXing> shangPingLeiXings = new ArrayList<>();
//                                JSONArray jsonArray3 = new JSONArray(jsonObject1.getString("shangPingLeiXings"));
//                                try {
//                                    MainActivity.readableDatabase.beginTransaction();
//                                    for (int i = 0; i < jsonArray3.length(); i++) {
//                                        JSONObject jsonObject2 = new JSONObject(jsonArray3.getString(i));
//                                        shangPingLeiXings.add(new ShangPingLeiXing(jsonObject2.getString("usernumber"), jsonObject2.getString("commoditytype"), jsonObject2.getString("dateandtime")));
//                                        MainActivity.readableDatabase.execSQL("insert  into `shangpingleixing`(`usernumber`,`commoditytype`,`dateandtime`) values (?,?,?)", new Object[]{jsonObject2.getString("usernumber"), jsonObject2.getString("commoditytype"), jsonObject2.getString("dateandtime")});
//                                    }
//                                    MainActivity.readableDatabase.setTransactionSuccessful();
//                                    MainActivity.readableDatabase.endTransaction();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    MainActivity.readableDatabase.endTransaction();
//                                }
//                            } catch (Exception e) {
//                                Log.i("cdl", "handleMessage: 没有shangPingLeiXings");
//                            }
//
//                            try {
////                          销售记录
//                                List<XiaoShouJiLu> xiaoShouJiLus = new ArrayList<>();
//
//                                JSONArray jsonArray4 = new JSONArray(jsonObject1.getString("xiaoShouJiLus"));
//                                try {
//                                    MainActivity.readableDatabase.beginTransaction();
//                                    for (int i = 0; i < jsonArray4.length(); i++) {
//                                        JSONObject jsonObject2 = new JSONObject(jsonArray4.getString(i));
//                                        xiaoShouJiLus.add(new XiaoShouJiLu(jsonObject2.getString("usernumber"), jsonObject2.getString("Saleno"), jsonObject2.getString("barcode"), jsonObject2.getString("commodityname"), jsonObject2.getInt("quantityofgoods"), jsonObject2.getString("commoditytype"), jsonObject2.getDouble("saleprice"), jsonObject2.getString("dateofsale")));
//                                        MainActivity.readableDatabase.execSQL("insert  into `xiaoshoujilu`(`usernumber`,`saleno`,`barcode`,`commodityname`,`quantityofgoods`,`commoditytype`,`saleprice`,`dateofsale`,isupdate) values (?,?,?,?,?,?,?,?,?)", new Object[]{jsonObject2.getString("usernumber"), jsonObject2.getString("Saleno"), jsonObject2.getString("barcode"), jsonObject2.getString("commodityname"), jsonObject2.getInt("quantityofgoods"), jsonObject2.getString("commoditytype"), jsonObject2.getDouble("saleprice"), jsonObject2.getString("dateofsale"),1});
//                                    }
//                                    Toast.makeText(getActivity(), "-------------------备份下载成功", Toast.LENGTH_SHORT).show();
//                                    MainActivity.readableDatabase.setTransactionSuccessful();
//                                    MainActivity.readableDatabase.endTransaction();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    MainActivity.readableDatabase.endTransaction();
//                                }
//                            } catch (Exception e) {
//                                Log.i("cdl", "handleMessage: 没有xiaoShouJiLus");
//                            }
//
//                            try {
////                            修改记录
//                                List<XiuGaiJiLu> xiuGaiJiLus = new ArrayList<>();
//
//                                JSONArray jsonArray5 = new JSONArray(jsonObject1.getString("xiuGaiJiLus"));
//                                try {
//                                    MainActivity.readableDatabase.beginTransaction();
//                                    for (int i = 0; i < jsonArray5.length(); i++) {
//                                        JSONObject jsonObject2 = new JSONObject(jsonArray5.getString(i));
//                                        xiuGaiJiLus.add(new XiuGaiJiLu(jsonObject2.getString("usernumber"), jsonObject2.getString("barcode"), jsonObject2.getString("commodityname"), jsonObject2.getString("preinformation"), jsonObject2.getString("alteredinformation"), jsonObject2.getString("dateandtime")));
//                                        MainActivity.readableDatabase.execSQL("insert  into `xiugaijilu`(`usernumber`,`barcode`,`commodityname`,`preinformation`,`alteredinformation`,`dateandtime`,isupdate) values (?,?,?,?,?,?,?)", new Object[]{jsonObject2.getString("usernumber"), jsonObject2.getString("barcode"), jsonObject2.getString("commodityname"), jsonObject2.getString("preinformation"), jsonObject2.getString("alteredinformation"), jsonObject2.getString("dateandtime"),1});
//                                    }
//                                    MainActivity.readableDatabase.setTransactionSuccessful();
//                                    MainActivity.readableDatabase.endTransaction();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    MainActivity.readableDatabase.endTransaction();
//                                }
//                            } catch (Exception e) {
//                                Log.i("cdl", "handleMessage: 没有xiuGaiJiLus");
//                            }
//
//                            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.progress_callback_success_layout, null);
//                            new AlertDialog.Builder(getActivity()).setView(inflate).setPositiveButton("确定", null).show();
//                        } else {
//                            Toast.makeText(getActivity(), "服务器错误！", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    break;
            }
        }
    };
    String return_datas = "";
    AlertDialog alertDialog;
    List<String> strings = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MainActivity.yu_zhi = MainActivity.sharedPreferences.getInt("threshold", -1);
        view = LayoutInflater.from(getActivity()).inflate(R.layout.four_layout, null);
        tv_user_title = view.findViewById(R.id.tv_user_title);
        iv_user_setting = view.findViewById(R.id.iv_user_setting);
        btn_query_add_shang_ping_ji_lu = view.findViewById(R.id.btn_query_add_shang_ping_ji_lu);
        btn_set_yu_zhi = view.findViewById(R.id.btn_set_yu_zhi);
        btn_set_type = view.findViewById(R.id.btn_set_type);
        btn_type_query = view.findViewById(R.id.btn_type_query);
        btn_query_modify_ji_lu = view.findViewById(R.id.btn_query_modify_ji_lu);
        btn_out = view.findViewById(R.id.btn_out);
//        btn_data_RollBACK = view.findViewById(R.id.btn_data_RollBACK);
////        数据回滚
//        btn_data_RollBACK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                strings.removeAll(strings);
//                View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.progress_callback_layout, null);
//                alertDialog = new AlertDialog.Builder(getActivity()).setView(inflate).show();
////                ReturnDatas returnDatas=new ReturnDatas(MainActivity.UserName,null,null);
//                JSONObject object = new JSONObject();
//                isNull();
//                OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).build();
//                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(new DataCallBack(MainActivity.UserName, strings)));
//                Request request = new Request.Builder().url("http://" + MainActivity.sharedPreferences.getString("ip", "10.0.2.2:80") + "/recovery_of_commodity_information").post(requestBody).build();
//                okHttpClient.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Message message = new Message();
//                        message.what = 0x112;
//                        handler.sendMessage(message);
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        return_datas = response.body().string();
//                        Message message = new Message();
//                        message.what = 0x113;
//                        handler.sendMessage(message);
//                    }
//                });
//            }
//
//            private void isNull() {
//                Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from jinhuojilu where usernumber=?", new String[]{MainActivity.UserName});
//                if (cursor.getCount() == 0) {
//                    strings.add("JinHuoJiLu");
//                }
//                Cursor cursor1 = MainActivity.readableDatabase.rawQuery("select * from kucunbiao where usernumber=?", new String[]{MainActivity.UserName});
//                if (cursor1.getCount() == 0) {
//                    strings.add("ShangPingKuCun");
//                }
//                Cursor cursor2 = MainActivity.readableDatabase.rawQuery("select * from xiugaijilu where usernumber=?", new String[]{MainActivity.UserName});
//                if (cursor2.getCount() == 0) {
//                    strings.add("XiuGaiJiLu");
//                }
//                Cursor cursor3 = MainActivity.readableDatabase.rawQuery("select * from shangpingleixing where usernumber=?", new String[]{MainActivity.UserName});
//                if (cursor3.getCount() == 0) {
//                    strings.add("ShangPingLeiXing");
//                }
//                Cursor cursor4 = MainActivity.readableDatabase.rawQuery("select * from xiaoshoujilu where usernumber=?", new String[]{MainActivity.UserName});
//                if (cursor4.getCount() == 0) {
//                    strings.add("XiaoShouJiLu");
//                }
//                Cursor cursor5 = MainActivity.readableDatabase.rawQuery("select * from danhaojilu where usernumber=?", new String[]{MainActivity.UserName});
//                if (cursor5.getCount() == 0) {
//                    strings.add("DanHaoJiLu");
//                }
//            }
//        });

        String title = MainActivity.sharedPreferences.getString("nickname", MainActivity.sharedPreferences.getString("userName", "Error"));
        tv_user_title.setText(title);

//        点击修改记录按钮
        btn_query_modify_ji_lu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().replace(R.id.ll_content, new Modify_JiLu_Fragment()).commit();
            }
        });

//        修改个人信息
        iv_user_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.user_setting_layout, null);
                TextView tv_user_id = inflate.findViewById(R.id.tv_user_id);
                et_ni_cheng = inflate.findViewById(R.id.et_ni_cheng);
                final RadioGroup rg_group = inflate.findViewById(R.id.rg_group);
                RadioButton cb_nan = inflate.findViewById(R.id.cb_nan);
                cb_nv = inflate.findViewById(R.id.cb_nv);
                if (MainActivity.sharedPreferences.getBoolean("sex", false)) {
                    cb_nv.setChecked(true);
                } else {
                    cb_nan.setChecked(true);
                }

                et_ni_cheng.setText(MainActivity.sharedPreferences.getString("nickname", MainActivity.sharedPreferences.getString("userName", "error")));
                tv_user_id.setText(MainActivity.sharedPreferences.getString("userName", "error"));
                new AlertDialog.Builder(getActivity()).setCancelable(false).setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//数据事件
                        if (cb_nv.isChecked()) {
                            is_sex = true;
                        }
                        User user = new User(MainActivity.sharedPreferences.getString("userName", "error"), null, et_ni_cheng.getText().toString(), is_sex, null, null);
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).build();
                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), json);
                        Request request = new Request.Builder().url("http://" + MainActivity.sharedPreferences.getString("ip", "10.0.2.2:80") + "/personal_information_modification").post(requestBody).build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Message message = new Message();
                                message.what = 0x112;
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                return_datas = response.body().string();
                                Message message = new Message();
                                message.what = 0x111;
                                handler.sendMessage(message);
                            }
                        });


                    }
                }).setNegativeButton("取消", null).setTitle("修改个人信息").setView(inflate).show();
            }
        });
//      进货记录
        btn_query_add_shang_ping_ji_lu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().replace(R.id.ll_content, new JinHuoJiLu_Fragment()).commit();
            }
        });
//最低阈值
        btn_set_yu_zhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.user_setting_yu_zhi, null);
                final EditText et_yu_zhi = inflate.findViewById(R.id.et_yu_zhi);
                et_yu_zhi.setHint(MainActivity.sharedPreferences.getInt("threshold", 0) + "");
                new AlertDialog.Builder(getActivity()).setCancelable(false).setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//数据事件
                        try {
                            MainActivity.editor.putInt("threshold", Integer.parseInt(et_yu_zhi.getText().toString()));
                            MainActivity.editor.commit();
                            Toast.makeText(getActivity(), "阈值设置成功！下次启动软件时提示库存中不足商品！", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "设置阈值失败！", Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNegativeButton("取消", null).setNeutralButton("删除阈值", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            MainActivity.editor.remove("threshold").commit();
                            Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "您没有设置阈值！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setTitle("设置商品数量过少提醒值").setView(inflate).show();
            }
        });

        btn_set_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.user_setting_shang_ping_lei_xing, null);
                new AlertDialog.Builder(getActivity()).setCancelable(false).setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_add_lei_xing = inflate.findViewById(R.id.et_add_lei_xing);
                        if (!et_add_lei_xing.getText().toString().trim().equals("")) {
                            Date date = new Date();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            MainActivity.readableDatabase.execSQL("insert  into `shangpingleixing`(`usernumber`,`commoditytype`,dateandtime) values (?,?,?)", new Object[]{MainActivity.UserName, et_add_lei_xing.getText().toString().trim(), simpleDateFormat.format(date)});
                            Toast.makeText(getActivity(), "添加成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "添加失败！类型不能为空！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("取消", null).setTitle("增加一个商品类型").setView(inflate).show();
            }
        });

        btn_type_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().replace(R.id.ll_content, new ShangPingLeiXing_Fragment()).commit();
            }
        });

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                登出事件

                new AlertDialog.Builder(getActivity()).setTitle("您确定退出登录？").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.editor.remove("nickname");
                        MainActivity.editor.remove("sex");
                        MainActivity.editor.remove("userName");
                        MainActivity.editor.remove("password");
                        MainActivity.editor.commit();
                        Intent intent=new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    }
                }).setNegativeButton("否", null).show();


            }
        });

        return view;
    }
}
