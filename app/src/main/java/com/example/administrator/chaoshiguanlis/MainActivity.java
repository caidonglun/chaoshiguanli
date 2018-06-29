package com.example.administrator.chaoshiguanlis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaoshiguanli.bean.DanHaoJiLu;
import com.chaoshiguanli.bean.DataCallBack;
import com.chaoshiguanli.bean.JinHuoJiLu;
import com.chaoshiguanli.bean.ReturnDatas;
import com.chaoshiguanli.bean.ShangPingKuCun;
import com.chaoshiguanli.bean.ShangPingLeiXing;
import com.chaoshiguanli.bean.ShangPingXingXi;
import com.chaoshiguanli.bean.XiaoShouJiLu;
import com.chaoshiguanli.bean.XiuGaiJiLu;
import com.chaoshiguanli.datasource.MyDataSource;
import com.com.chaoshiguanli.fragments.Four_Fragment;
import com.com.chaoshiguanli.fragments.One_fragment;
import com.com.chaoshiguanli.fragments.Three_Fragment;
import com.com.chaoshiguanli.fragments.Two_ChuShouJiLu_Fragment;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends Activity {
    public static List<ShangPingXingXi> datas;
    String retrun_data="";
    AlertDialog success_alertDialog;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x111:
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(retrun_data);
                        if(jsonObject.getString("result").equals("success")){
                            readableDatabase.execSQL("update jinhuojilu set isupdate=1 where usernumber=?",new Object[]{UserName});
                            isSuccess();
                        }else{
                            isError();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 0x112:
                    isInternetError();
                    break;

                case 0x113:
                    JSONObject jsonObject1 = null;
                    try {
                        jsonObject1 = new JSONObject(retrun_data);
                        if(jsonObject1.getString("result").equals("success")){
                            isSuccess();
                        }else{
                            isError();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                break;

                case 0x114:
                    isInternetError();
                    break;

                case 0x115:
                    JSONObject jsonObject2 = null;
                    try {
                        jsonObject2 = new JSONObject(retrun_data);
                        if(jsonObject2.getString("result").equals("success")){
                            readableDatabase.execSQL("update xiugaijilu set isupdate=1 where usernumber=?",new Object[]{UserName});
                        isSuccess();
                        }else{
                            isError();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 0x116:
                    isInternetError();
                    break;

                case 0x117:
                    JSONObject jsonObject3 = null;
                    try {
                        jsonObject3 = new JSONObject(retrun_data);
                        if(jsonObject3.getString("result").equals("success")){
                            readableDatabase.execSQL("update danhaojilu set isupdate=1 where usernumber=?",new Object[]{UserName});
                            isSuccess();
                        }else{
                            isError();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 0x118:
                    isInternetError();
                    break;

                case 0x119:
                    JSONObject jsonObject4 = null;
                    try {
                        jsonObject4 = new JSONObject(retrun_data);
                        if(jsonObject4.getString("result").equals("success")){
                            readableDatabase.execSQL("update xiaoshoujilu set isupdate=1 where usernumber=?",new Object[]{UserName});
                            isSuccess();
                        }else{
                            isError();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 0x120:
                    isInternetError();
                    break;

                case 0x121:
                    JSONObject jsonObject6 = null;
                    try {
                        jsonObject6 = new JSONObject(retrun_data);
                        if(jsonObject6.getString("result").equals("success")){
                            isSuccess();
                        }else{
                            isError();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 0x122:
                    isInternetError();
                    break;

                case 0x123:
                    alertDialog.dismiss();
                    try {
                        JSONObject jsonObjectSum = new JSONObject(return_datas);
                        if (jsonObjectSum.getString("result").equals("success")) {
                            String data = jsonObjectSum.getString("data");
//                            所有数据集合
                            JSONObject jsonObject1a = new JSONObject(data);

//                            单号记录数据获取
                            List<DanHaoJiLu> danHaoJiLus = new ArrayList<>();

                            try {
                                JSONArray jsonArray = new JSONArray(jsonObject1a.getString("danHaoJiLus"));
//                            Toast.makeText(getActivity(), "danHaoJiLus"+danHaoJiLu, Toast.LENGTH_SHORT).show();
                                try {
                                    MainActivity.readableDatabase.beginTransaction();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Toast.makeText(MainActivity.this, "次数："+jsonArray.length(), Toast.LENGTH_SHORT).show();
                                        JSONObject jsonObject2a = new JSONObject(jsonArray.getString(i));
                                        danHaoJiLus.add(new DanHaoJiLu(jsonObject2a.getString("usernumber"), jsonObject2a.getDouble("totalvalue"), jsonObject2a.getString("Saleno"), jsonObject2a.getString("dateofsale")));
                                        MainActivity.readableDatabase.execSQL("insert  into `danhaojilu`(`usernumber`,`totalvalue`,`Saleno`,`dateofsale`,isupdate) values (?,?,?,?,?)", new Object[]{jsonObject2a.getString("usernumber"), jsonObject2a.getDouble("totalvalue"), jsonObject2a.getString("Saleno"), jsonObject2a.getString("dateofsale"),1});
                                    }
                                    MainActivity.readableDatabase.setTransactionSuccessful();
                                    MainActivity.readableDatabase.endTransaction();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    MainActivity.readableDatabase.endTransaction();
                                }
                                Toast.makeText(MainActivity.this, "danHaoJiLus===" + danHaoJiLus, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.i("cdl", "handleMessage: 没有danHaoJiLus");
                            }

                            try {
//                            进货记录
                                List<JinHuoJiLu> jinHuoJiLus = new ArrayList<>();

                                JSONArray jsonArray1 = new JSONArray(jsonObject1a.getString("jinHuoJiLus"));
                                try {
                                    MainActivity.readableDatabase.beginTransaction();
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        Toast.makeText(MainActivity.this, "次数："+jsonArray1.length(), Toast.LENGTH_SHORT).show();
                                        JSONObject jsonObject2a = new JSONObject(jsonArray1.getString(i));
                                        jinHuoJiLus.add(new JinHuoJiLu(jsonObject2a.getString("usernumber"), jsonObject2a.getString("barcode"), jsonObject2a.getString("commodityname"), jsonObject2a.getInt("quantityofgoods"), jsonObject2a.getDouble("purchaseprice"), jsonObject2a.getString("commoditytype"), jsonObject2a.getString("noteinformation"), jsonObject2a.getString("vendorname"), jsonObject2a.getString("contactinformation"), jsonObject2a.getString("adddate")));
                                        MainActivity.readableDatabase.execSQL("insert  into `jinhuojilu`(`usernumber`,`barcode`,`commodityname`,`quantityofgoods`,`purchaseprice`,`commoditytype`,`noteinformation`,`vendorname`,`contactinformation`,`adddate`,isupdate) values (?,?,?,?,?,?,?,?,?,?,?)", new Object[]{jsonObject2a.getString("usernumber"), jsonObject2a.getString("barcode"), jsonObject2a.getString("commodityname"), jsonObject2a.getInt("quantityofgoods"), jsonObject2a.getDouble("purchaseprice"), jsonObject2a.getString("commoditytype"), jsonObject2a.getString("noteinformation"), jsonObject2a.getString("vendorname"), jsonObject2a.getString("contactinformation"), jsonObject2a.getString("adddate"),1});
                                    }
                                    MainActivity.readableDatabase.setTransactionSuccessful();
                                    MainActivity.readableDatabase.endTransaction();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    MainActivity.readableDatabase.endTransaction();
                                }
                            } catch (Exception e) {
                                Log.i("cdl", "handleMessage: 没有jinHuoJiLus");
                            }

                            try {
//                            商品库存
                                List<ShangPingKuCun> shangPingKuCuns = new ArrayList<>();
                                JSONArray jsonArray2 = new JSONArray(jsonObject1a.getString("shangPingKuCuns"));
                                try {
                                    MainActivity.readableDatabase.beginTransaction();
                                    for (int i = 0; i < jsonArray2.length(); i++) {
                                        JSONObject jsonObject2a = new JSONObject(jsonArray2.getString(i));
                                        Toast.makeText(MainActivity.this, "jsonObject2.getDouble(\"saleprice\")=" + jsonObject2a.getDouble("saleprice"), Toast.LENGTH_SHORT).show();
                                        shangPingKuCuns.add(new ShangPingKuCun(jsonObject2a.getString("usernumber"), jsonObject2a.getString("barcode"), jsonObject2a.getString("commodityname"), jsonObject2a.getInt("quantityofgoods"), jsonObject2a.getString("commoditytype"), jsonObject2a.getDouble("saleprice"), jsonObject2a.getString("dateofmanufacture"), jsonObject2a.getString("expirationdate"), jsonObject2a.getString("noteinformation"), jsonObject2a.getBoolean("whetherithasexpired")));
                                        MainActivity.readableDatabase.execSQL("insert  into `kucunbiao`(`usernumber`,`barcode`,`commodityname`,`quantityofgoods`,`commoditytype`,`saleprice`,`dateofmanufacture`,`expirationdate`,`noteinformation`,`whetherithasexpired`) values (?,?,?,?,?,?,?,?,?,?)", new Object[]{jsonObject2a.getString("usernumber"), jsonObject2a.getString("barcode"), jsonObject2a.getString("commodityname"), jsonObject2a.getInt("quantityofgoods"), jsonObject2a.getString("commoditytype"), jsonObject2a.getDouble("saleprice"), jsonObject2a.getString("dateofmanufacture"), jsonObject2a.getString("expirationdate"), jsonObject2a.getString("noteinformation"), jsonObject2a.getBoolean("whetherithasexpired")});
                                    }
                                    MainActivity.readableDatabase.setTransactionSuccessful();
                                    MainActivity.readableDatabase.endTransaction();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    MainActivity.readableDatabase.endTransaction();
                                }
                            } catch (Exception e) {
                                Log.i("cdl", "handleMessage: 没有shangPingKuCuns");
                            }

                            try {
//                            商品类型
                                List<ShangPingLeiXing> shangPingLeiXings = new ArrayList<>();
                                JSONArray jsonArray3 = new JSONArray(jsonObject1a.getString("shangPingLeiXings"));
                                try {
                                    MainActivity.readableDatabase.beginTransaction();
                                    for (int i = 0; i < jsonArray3.length(); i++) {
                                        JSONObject jsonObject2a = new JSONObject(jsonArray3.getString(i));
                                        shangPingLeiXings.add(new ShangPingLeiXing(jsonObject2a.getString("usernumber"), jsonObject2a.getString("commoditytype"), jsonObject2a.getString("dateandtime")));
                                        MainActivity.readableDatabase.execSQL("insert  into `shangpingleixing`(`usernumber`,`commoditytype`,`dateandtime`) values (?,?,?)", new Object[]{jsonObject2a.getString("usernumber"), jsonObject2a.getString("commoditytype"), jsonObject2a.getString("dateandtime")});
                                    }
                                    MainActivity.readableDatabase.setTransactionSuccessful();
                                    MainActivity.readableDatabase.endTransaction();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    MainActivity.readableDatabase.endTransaction();
                                }
                            } catch (Exception e) {
                                Log.i("cdl", "handleMessage: 没有shangPingLeiXings");
                            }

                            try {
//                          销售记录
                                List<XiaoShouJiLu> xiaoShouJiLus = new ArrayList<>();

                                JSONArray jsonArray4 = new JSONArray(jsonObject1a.getString("xiaoShouJiLus"));
                                try {
                                    MainActivity.readableDatabase.beginTransaction();
                                    for (int i = 0; i < jsonArray4.length(); i++) {
                                        JSONObject jsonObject2a = new JSONObject(jsonArray4.getString(i));
                                        xiaoShouJiLus.add(new XiaoShouJiLu(jsonObject2a.getString("usernumber"), jsonObject2a.getString("Saleno"), jsonObject2a.getString("barcode"), jsonObject2a.getString("commodityname"), jsonObject2a.getInt("quantityofgoods"), jsonObject2a.getString("commoditytype"), jsonObject2a.getDouble("saleprice"), jsonObject2a.getString("dateofsale")));
                                        MainActivity.readableDatabase.execSQL("insert  into `xiaoshoujilu`(`usernumber`,`saleno`,`barcode`,`commodityname`,`quantityofgoods`,`commoditytype`,`saleprice`,`dateofsale`,isupdate) values (?,?,?,?,?,?,?,?,?)", new Object[]{jsonObject2a.getString("usernumber"), jsonObject2a.getString("Saleno"), jsonObject2a.getString("barcode"), jsonObject2a.getString("commodityname"), jsonObject2a.getInt("quantityofgoods"), jsonObject2a.getString("commoditytype"), jsonObject2a.getDouble("saleprice"), jsonObject2a.getString("dateofsale"),1});
                                    }
                                    Toast.makeText(MainActivity.this, "-------------------备份下载成功", Toast.LENGTH_SHORT).show();
                                    MainActivity.readableDatabase.setTransactionSuccessful();
                                    MainActivity.readableDatabase.endTransaction();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    MainActivity.readableDatabase.endTransaction();
                                }
                            } catch (Exception e) {
                                Log.i("cdl", "handleMessage: 没有xiaoShouJiLus");
                            }

                            try {
//                            修改记录
                                List<XiuGaiJiLu> xiuGaiJiLus = new ArrayList<>();

                                JSONArray jsonArray5 = new JSONArray(jsonObject1a.getString("xiuGaiJiLus"));
                                try {
                                    MainActivity.readableDatabase.beginTransaction();
                                    for (int i = 0; i < jsonArray5.length(); i++) {
                                        JSONObject jsonObject2a = new JSONObject(jsonArray5.getString(i));
                                        xiuGaiJiLus.add(new XiuGaiJiLu(jsonObject2a.getString("usernumber"), jsonObject2a.getString("barcode"), jsonObject2a.getString("commodityname"), jsonObject2a.getString("preinformation"), jsonObject2a.getString("alteredinformation"), jsonObject2a.getString("dateandtime")));
                                        MainActivity.readableDatabase.execSQL("insert  into `xiugaijilu`(`usernumber`,`barcode`,`commodityname`,`preinformation`,`alteredinformation`,`dateandtime`,isupdate) values (?,?,?,?,?,?,?)", new Object[]{jsonObject2a.getString("usernumber"), jsonObject2a.getString("barcode"), jsonObject2a.getString("commodityname"), jsonObject2a.getString("preinformation"), jsonObject2a.getString("alteredinformation"), jsonObject2a.getString("dateandtime"),1});
                                    }
                                    MainActivity.readableDatabase.setTransactionSuccessful();
                                    MainActivity.readableDatabase.endTransaction();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    MainActivity.readableDatabase.endTransaction();
                                }
                            } catch (Exception e) {
                                Log.i("cdl", "handleMessage: 没有xiuGaiJiLus");
                            }

                            View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.progress_callback_success_layout, null);
                            success_alertDialog = new AlertDialog.Builder(MainActivity.this).setView(inflate).show();
                            TimerTask timerTask=new TimerTask() {
                                @Override
                                public void run() {
                                    Message message=new Message();
                                    message.what=0x125;
                                    handler.sendMessage(message);
                                }
                            };
                            timer.schedule(timerTask,1000);
                        } else {
                            Toast.makeText(MainActivity.this, "服务器错误！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 0x124:
                    alertDialog.dismiss();
                    Toast.makeText(MainActivity.this, "获取数据失败！", Toast.LENGTH_SHORT).show();
                    break;

                case 0x125:
                    success_alertDialog.dismiss();
                    break;

            }

        }

        private void isInternetError() {
            Toast.makeText(MainActivity.this, "自动备份数据失败！", Toast.LENGTH_SHORT).show();
        }

        private void isError() {
            Toast.makeText(MainActivity.this, "服务器备份失败！", Toast.LENGTH_SHORT).show();
        }

        private void isSuccess() {
            Toast.makeText(MainActivity.this, "自动备份数据成功！", Toast.LENGTH_SHORT).show();
        }
    };

    ImageView iv_shao,iv_ji_lu,iv_ku_cun_guan_li,iv_ge_ren_zhong_xin;
    boolean is_one=true;
    long one_date=0;
    Fragment one_fragmen;
    List<String> strings = new ArrayList<>();
    String return_datas = "";
    AlertDialog alertDialog;

/* <!--库存表-->
        <!--用户编号     商品条码       商品名称          商品数量        商品类型       出售价格       生产日期             过期日期            备注信息              是否已过期           -->
  ---->    usernumber   barcode   commodityname  quantityofgoods  commoditytype   saleprice   dateofmanufacture    expirationdate    noteinformation   whetherithasexpired

            商品修改记录表
            用户编号     商品条码      商品名称          改前信息            改后信息           修改日期时间    是否上传
  ---->   usernumber    barcode   commodityname   preinformation    alteredinformation   dateandtime    isupdate

        <!--进货记录表-->
        <!--用户编号     商品条码    商品名称            商品数量              进货价格        商品类型             备注信息        厂商名称      联系方式               添加日期       是否上传-->
 ---->  usernumber    barcode   commodityname    quantityofgoods    purchaseprice    commoditytype      noteinformation   vendorname   contactinformation     adddate      isupdate

        销售记录表
        用户编号         销售单号      商品条码        商品名称         商品数量             商品类型       出售价格      出售日期时间
 ----  usernumber      Saleno      barcode    commodityname   quantityofgoods     commoditytype    saleprice     dateofsale

        单号记录表
        用户编号       本单总价值       销售单号       出售日期时间     是否上传
 ---->  usernumber    totalvalue     Saleno        dateofsale     isupdate

        商品信息表
        用户编号        商品条码        商品名称         商品数量              商品类型           出售价格       添加日期
        usernumber     barcode    commodityname    quantityofgoods     commoditytype       saleprice      adddate

        商品类型表
        用户编号        商品类型       日期时间
        usernumber    commoditytype   dateandtime
        用户表
        用户账号   用户密码    用户姓名    性别
        userid    password  username    sex
        */


    MyDataSource myDataSource;
    public static SQLiteDatabase readableDatabase;
    public static   SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String UserName;
    public static int yu_zhi=-1;
    public NotificationManager notificationManager;
    private Timer timer=new Timer();
    private boolean CloseIs=false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        datas=new ArrayList<>();
//        for (int i=0;i<20;i++){
//            datas.add(new ShangPingXingXi("00"+i,null,null,0,null,0.0,null));
//        }

        //数据库
        myDataSource=new MyDataSource(MainActivity.this,null,null,1);
        readableDatabase = myDataSource.getReadableDatabase();

        notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        sharedPreferences=getSharedPreferences("chaoshiguanli",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        UserName=sharedPreferences.getString("userName","error");
        if(UserName.equals("error")){
            UserName="";
        }

        yu_zhi=sharedPreferences.getInt("threshold",-1);
        Toast.makeText(this, ""+yu_zhi, Toast.LENGTH_SHORT).show();

        if(yu_zhi!=-1){
            UserSendNotification();
        }

//        数据备份
        dataUp();

//      数据回滚处理
       dataCallBack();

        super.onCreate(savedInstanceState);
        one_fragmen=new One_fragment();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        findId();
//最下面的按钮点击事件和图片切换
        iv_setListerner();

//        notification用失败的启动方式
//        try {
//            Intent supper_intent = getIntent();
//            Bundle supper_bundle = supper_intent.getExtras();
//            if (supper_bundle.getBoolean("Notification")) {
//                getFragmentManager().beginTransaction().replace(R.id.ll_content, new Three_Fragment()).commit();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        测试记录表是否有数据
//        Cursor cursor1 = readableDatabase.rawQuery("select * from danhaojilu ", null);
//        Toast.makeText(this, "测试记录表是否有数据:"+cursor1.getCount(), Toast.LENGTH_SHORT).show();
//
//        Cursor cursor2 = readableDatabase.rawQuery("select * from xiaoshoujilu ", null);
//        Toast.makeText(this, "测试记录:"+cursor2.getCount(), Toast.LENGTH_SHORT).show();


        //        强行关闭软件导致商品信息的数据未处理项
        List<ShangPingXingXi> before_datas=new ArrayList<>();
        MainActivity.readableDatabase.beginTransaction();
        Cursor cursor = readableDatabase.rawQuery("select usernumber,barcode,quantityofgoods from shangpingxingxi ", null);
        Toast.makeText(this, "cursor.getCount()="+cursor.getCount(), Toast.LENGTH_SHORT).show();
        for(;cursor.moveToNext();){

            try {
            before_datas.add(new ShangPingXingXi(cursor.getString(0),cursor.getString(1),null,cursor.getInt(2),null,0.0,null));
                MainActivity.readableDatabase.execSQL("delete from shangpingxingxi where usernumber=? and barcode=?",new Object[]{cursor.getString(0),cursor.getString(1)});
                MainActivity.readableDatabase.execSQL("update kucunbiao set quantityofgoods=quantityofgoods+? where usernumber=? and barcode=?", new Object[]{cursor.getInt(2),cursor.getString(0), cursor.getString(1)});

            } catch (Exception e) {
                MainActivity.readableDatabase.endTransaction();
//                    e.printStackTrace();
            }


        }
        MainActivity.readableDatabase.setTransactionSuccessful();
        MainActivity.readableDatabase.endTransaction();
        Toast.makeText(this, "datas="+before_datas.size(), Toast.LENGTH_SHORT).show();


//        myDataSource.getReadableDatabase().execSQL("insert  into `jinhuojilu`(`usernumber`,`barcode`,`commodityname`,`quantityofgoods`,`purchaseprice`,`commoditytype`,`noteinformation`,`vendorname`,`contactinformation`,`adddate`) values ('1','2','3',4,5.00,'6','7','8','9','10'),('2','2','3',4,5.00,'6','7','8','9','10')");
//        myDataSource.close();




//        启动时默认资源
        iv_shao.setImageResource(R.mipmap.button_16);
        getFragmentManager().beginTransaction().replace(R.id.ll_content,one_fragmen ).commit();

//测试查询数据库
//        Cursor cursora = myDataSource.getReadableDatabase().rawQuery("select * from kucunbiao ", null);
//        Toast.makeText(this, ""+cursora.getCount(), Toast.LENGTH_SHORT).show();
//        for (;cursora.moveToNext();){
//            Toast.makeText(this, ""+cursora.getString(0)+" "+cursora.getString(1)+" "+cursora.getString(2)+" "+cursora.getString(3)+" "+cursora.getString(4)+" "+cursora.getString(5)+" "+cursora.getString(6)+" "+cursora.getString(7)+" "+cursora.getString(8)+" "+cursora.getString(9), Toast.LENGTH_SHORT).show();
//        }
//
//        Cursor cursor = myDataSource.getReadableDatabase().rawQuery("select * from jinhuojilu ", null);
//        Toast.makeText(this, ""+cursor.getCount(), Toast.LENGTH_SHORT).show();
//        for (;cursor.moveToNext();){
//            Toast.makeText(this, ""+cursor.getString(0)+cursor.getString(1)+cursor.getString(2)+cursor.getString(3)+cursor.getString(4)+cursor.getString(5)+cursor.getString(6)+cursor.getString(7)+cursor.getString(8)+cursor.getString(9), Toast.LENGTH_SHORT).show();
//        }

    }

    private void dataCallBack() {
        strings.removeAll(strings);
        View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.progress_callback_layout, null);
        alertDialog = new AlertDialog.Builder(MainActivity.this).setView(inflate).show();
//                ReturnDatas returnDatas=new ReturnDatas(MainActivity.UserName,null,null);
        JSONObject object = new JSONObject();
//        查看数据是否为空
        isNull();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(new DataCallBack(MainActivity.UserName, strings)));
        Request request = new Request.Builder().url("http://" + MainActivity.sharedPreferences.getString("ip", "10.0.2.2:80") + "/recovery_of_commodity_information").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 0x124;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                return_datas = response.body().string();
                Message message = new Message();
                message.what = 0x123;
                handler.sendMessage(message);
            }
        });
    }

    private void dataUp() {
        //    进货记录备份接口
        List<JinHuoJiLu> jinHuoJiLus=new ArrayList<>();
        Cursor cursor1 = null;
        cursor1 = readableDatabase.rawQuery("select * from jinhuojilu where usernumber=? and isupdate=?", new String[]{UserName,"0"});

        if(cursor1.getCount()>0) {
            JinHuoJiLuUp(cursor1,jinHuoJiLus);
        }else{
            Toast.makeText(this, "进货没有未记录！", Toast.LENGTH_SHORT).show();
        }

//    库存表备份接口
        List<ShangPingKuCun> shangPingKuCuns=new ArrayList<>();
        Cursor cursor2 = null;
        cursor2 = readableDatabase.rawQuery("select * from kucunbiao where usernumber=?", new String[]{UserName});
        if(cursor2.getCount()>0){
            ShangPingKuCunUp(cursor2,shangPingKuCuns);
        }else{
            Toast.makeText(this, "没有库存商品", Toast.LENGTH_SHORT).show();
        }

//        商品修改备份接口
        List<XiuGaiJiLu> xiuGaiJiLus=new ArrayList<>();
        Cursor cursor3 = null;
        cursor3 = readableDatabase.rawQuery("select * from xiugaijilu where usernumber=? and isupdate=?", new String[]{UserName,"0"});
        if(cursor3.getCount()>0){
            XiuGaiJiLuUp(cursor3,xiuGaiJiLus);
        }else{
            Toast.makeText(this, "没有修改记录", Toast.LENGTH_SHORT).show();
        }

//        商品单号记录备份接口
        List<DanHaoJiLu> danHaoJiLus=new ArrayList<>();
        Cursor cursor4 = null;
        cursor4 = readableDatabase.rawQuery("select * from danhaojilu where usernumber=? and isupdate=?", new String[]{UserName,"0"});
        if(cursor4.getCount()>0){
            danHaoJiLuUp(cursor4,danHaoJiLus);
        }else{
            Toast.makeText(this, "商品单号记录", Toast.LENGTH_SHORT).show();
        }

//        商品销售记录备份接口
        List<XiaoShouJiLu> xiaoShouJiLus=new ArrayList<>();
        Cursor cursor5 = null;
        cursor5 = readableDatabase.rawQuery("select * from xiaoshoujilu where usernumber=? and isupdate=?", new String[]{UserName,"0"});
        if(cursor5.getCount()>0){
            XiaoShouJiLuUp(cursor5,xiaoShouJiLus);
        }else{
            Toast.makeText(this, "没有销售记录", Toast.LENGTH_SHORT).show();
        }

        //        商品类型备份接口
        List<ShangPingLeiXing> leiXings=new ArrayList<>();
        Cursor cursor6 = null;
        cursor6 = readableDatabase.rawQuery("select * from shangpingleixing where usernumber=? ", new String[]{UserName});
        if(cursor6.getCount()>0){
            ShangPingLeiXingUp(cursor6,leiXings);
        }else{
            Toast.makeText(this, "没有商品内型", Toast.LENGTH_SHORT).show();
        }

    }

    private void isNull() {
        Cursor cursor = MainActivity.readableDatabase.rawQuery("select * from jinhuojilu where usernumber=?", new String[]{MainActivity.UserName});
        if (cursor.getCount() == 0) {
            strings.add("JinHuoJiLu");
        }
        Cursor cursor1 = MainActivity.readableDatabase.rawQuery("select * from kucunbiao where usernumber=?", new String[]{MainActivity.UserName});
        if (cursor1.getCount() == 0) {
            strings.add("ShangPingKuCun");
        }
        Cursor cursor2 = MainActivity.readableDatabase.rawQuery("select * from xiugaijilu where usernumber=?", new String[]{MainActivity.UserName});
        if (cursor2.getCount() == 0) {
            strings.add("XiuGaiJiLu");
        }
        Cursor cursor3 = MainActivity.readableDatabase.rawQuery("select * from shangpingleixing where usernumber=?", new String[]{MainActivity.UserName});
        if (cursor3.getCount() == 0) {
            strings.add("ShangPingLeiXing");
        }
        Cursor cursor4 = MainActivity.readableDatabase.rawQuery("select * from xiaoshoujilu where usernumber=?", new String[]{MainActivity.UserName});
        if (cursor4.getCount() == 0) {
            strings.add("XiaoShouJiLu");
        }
        Cursor cursor5 = MainActivity.readableDatabase.rawQuery("select * from danhaojilu where usernumber=?", new String[]{MainActivity.UserName});
        if (cursor5.getCount() == 0) {
            strings.add("DanHaoJiLu");
        }
    }

    private void ShangPingLeiXingUp(Cursor cursor6, List<ShangPingLeiXing> leiXings) {
        for (; cursor6.moveToNext(); ) {
            leiXings.add(new ShangPingLeiXing(cursor6.getString(0),cursor6.getString(1),cursor6.getString(2)));
        }

        ReturnDatas returnDatas = new ReturnDatas(UserName, leiXings, null);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().writeTimeout(20, TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).connectTimeout(3,TimeUnit.SECONDS).build();
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),new Gson().toJson(returnDatas));
        Request request=new Request.Builder().url("http://"+MainActivity.sharedPreferences.getString("ip","10.0.2.2:80")+"/commodity_type_ackup").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message=new Message();
                message.what=0x122;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                retrun_data=response.body().string();
                Message message=new Message();
                message.what=0x121;
                handler.sendMessage(message);
            }
        });
    }

    private void XiaoShouJiLuUp(Cursor cursor5, List<XiaoShouJiLu> xiaoShouJiLus) {
        for (; cursor5.moveToNext(); ) {
            xiaoShouJiLus.add(new XiaoShouJiLu(cursor5.getString(0),cursor5.getString(1),cursor5.getString(2),cursor5.getString(3),cursor5.getInt(4),cursor5.getString(5),cursor5.getDouble(6),cursor5.getString(7)));
        }

        ReturnDatas returnDatas = new ReturnDatas(UserName, xiaoShouJiLus, null);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().writeTimeout(20, TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).connectTimeout(3,TimeUnit.SECONDS).build();
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),new Gson().toJson(returnDatas));
        Request request=new Request.Builder().url("http://"+MainActivity.sharedPreferences.getString("ip","10.0.2.2:80")+"/sales_record_backup").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                readableDatabase.execSQL("update xiaoshoujilu set isupdate=0 where usernumber=?",new Object[]{UserName});
                Message message=new Message();
                message.what=0x120;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                retrun_data=response.body().string();
                Message message=new Message();
                message.what=0x119;
                handler.sendMessage(message);
            }
        });

    }

    private void danHaoJiLuUp(Cursor cursor4, List<DanHaoJiLu> danHaoJiLus) {
        for (; cursor4.moveToNext(); ) {
            danHaoJiLus.add(new DanHaoJiLu(cursor4.getString(0),cursor4.getDouble(1),cursor4.getString(2),cursor4.getString(3)));
        }

        ReturnDatas returnDatas = new ReturnDatas(UserName, danHaoJiLus, null);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().writeTimeout(20, TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).connectTimeout(3,TimeUnit.SECONDS).build();
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),new Gson().toJson(returnDatas));
        Request request=new Request.Builder().url("http://"+MainActivity.sharedPreferences.getString("ip","10.0.2.2:80")+"/single_number_record_backup").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                readableDatabase.execSQL("update danhaojilu set isupdate=0 where usernumber=?",new Object[]{UserName});
                Message message=new Message();
                message.what=0x118;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                retrun_data=response.body().string();
                Message message=new Message();
                message.what=0x117;
                handler.sendMessage(message);
            }
        });
    }

    private void XiuGaiJiLuUp(Cursor cursor3, List<XiuGaiJiLu> xiuGaiJiLus) {
        for (; cursor3.moveToNext(); ) {
            xiuGaiJiLus.add(new XiuGaiJiLu(cursor3.getString(0),cursor3.getString(1),cursor3.getString(2),cursor3.getString(3),cursor3.getString(4),cursor3.getString(5)));
        }

        ReturnDatas returnDatas = new ReturnDatas(UserName, xiuGaiJiLus, null);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().writeTimeout(20, TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).connectTimeout(3,TimeUnit.SECONDS).build();
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),new Gson().toJson(returnDatas));
        Request request=new Request.Builder().url("http://"+MainActivity.sharedPreferences.getString("ip","10.0.2.2:80")+"/Modify_record_backup").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                readableDatabase.execSQL("update xiugaijilu set isupdate=0 where usernumber=?",new Object[]{UserName});
                Message message=new Message();
                message.what=0x116;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                retrun_data=response.body().string();
                Message message=new Message();
                message.what=0x115;
                handler.sendMessage(message);
            }
        });
    }

    private void ShangPingKuCunUp(Cursor cursor2, List<ShangPingKuCun> shangPingKuCuns) {
        for (; cursor2.moveToNext(); ) {
            shangPingKuCuns.add(new ShangPingKuCun(cursor2.getString(0),cursor2.getString(1),cursor2.getString(2),cursor2.getInt(3),cursor2.getString(4),cursor2.getDouble(5),cursor2.getString(6),cursor2.getString(7),cursor2.getString(8),false));
        }

        ReturnDatas returnDatas = new ReturnDatas(UserName, shangPingKuCuns, null);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().writeTimeout(20, TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).connectTimeout(3,TimeUnit.SECONDS).build();
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),new Gson().toJson(returnDatas));
        Request request=new Request.Builder().url("http://"+MainActivity.sharedPreferences.getString("ip","10.0.2.2:80")+"/inventory_backup").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message=new Message();
                message.what=0x114;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                retrun_data=response.body().string();
                Message message=new Message();
                message.what=0x113;
                handler.sendMessage(message);
            }
        });
    }

    private void JinHuoJiLuUp(Cursor cursor1, List<JinHuoJiLu> jinHuoJiLus) {
        for (; cursor1.moveToNext(); ) {
            Toast.makeText(this, "值"+cursor1.getString(10), Toast.LENGTH_SHORT).show();
            jinHuoJiLus.add(new JinHuoJiLu(cursor1.getString(0), cursor1.getString(1), cursor1.getString(2), cursor1.getInt(3), cursor1.getDouble(4), cursor1.getString(5), cursor1.getString(6), cursor1.getString(7), cursor1.getString(8), cursor1.getString(9)));
        }
        ReturnDatas returnDatas = new ReturnDatas(UserName, jinHuoJiLus, null);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().writeTimeout(20, TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).connectTimeout(3,TimeUnit.SECONDS).build();
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),new Gson().toJson(returnDatas));
        Request request=new Request.Builder().url("http://"+MainActivity.sharedPreferences.getString("ip","10.0.2.2:80")+"/stock_record_backup").post(requestBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message=new Message();
                message.what=0x112;
                handler.sendMessage(message);
                readableDatabase.execSQL("update jinhuojilu set isupdate=0 where usernumber=?",new Object[]{UserName});
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                retrun_data=response.body().string();
                Message message=new Message();
                message.what=0x111;
                handler.sendMessage(message);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void UserSendNotification() {
        try {
            Cursor cursor = readableDatabase.rawQuery("select commodityname,quantityofgoods from kucunbiao where usernumber=?", new String[]{UserName});
            for (; cursor.moveToNext(); ) {
                if (cursor.getInt(1) <= yu_zhi) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("Notification", true);
                    intent.putExtras(bundle);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                    Notification.Builder notification = new Notification.Builder(MainActivity.this);
                    notification.setTicker("有商品快要买完了~");
                    notification.setContentTitle("商品余量不足：");
                    notification.setContentText("商品名：" + cursor.getString(0) + "剩余：" + cursor.getString(1));
                    notification.setSubText("请尽快进货哦~");
                    notification.setWhen(System.currentTimeMillis());
                    notification.setSmallIcon(R.mipmap.alert);
                    notification.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                    notification.setAutoCancel(true);
                    notification.setContentIntent(pendingIntent);
                    Notification notification_supper = notification.build();
                    notificationManager.notify(1, notification_supper);
                }
            }
        }catch (Exception e){}
    }

    private void iv_setListerner() {
        iv_shao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.ll_content,one_fragmen ).commit();
                iv_Mo_Ren_SetImage();
                iv_shao.setImageResource(R.mipmap.button_16);
            }
        });
        iv_ji_lu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.ll_content,new Two_ChuShouJiLu_Fragment()).commit();
                iv_Mo_Ren_SetImage();
                iv_ji_lu.setImageResource(R.mipmap.button_16);
            }
        });
        iv_ku_cun_guan_li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.ll_content,new Three_Fragment()).commit();
                iv_Mo_Ren_SetImage();
                iv_ku_cun_guan_li.setImageResource(R.mipmap.button_16);
            }
        });
        iv_ge_ren_zhong_xin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.ll_content,new Four_Fragment()).commit();
                iv_Mo_Ren_SetImage();
                iv_ge_ren_zhong_xin.setImageResource(R.mipmap.button_16);
            }
        });
    }

    private void iv_Mo_Ren_SetImage() {
        iv_shao.setImageResource(R.mipmap.button_21);
        iv_ji_lu.setImageResource(R.mipmap.button_12);
        iv_ku_cun_guan_li.setImageResource(R.mipmap.button_10);
        iv_ge_ren_zhong_xin.setImageResource(R.mipmap.button_04);
    }

    private void findId() {
        iv_shao=findViewById(R.id.iv_shao);
        iv_ji_lu=findViewById(R.id.iv_ji_lu);
        iv_ge_ren_zhong_xin=findViewById(R.id.iv_ge_ren_zhong_xin);
        iv_ku_cun_guan_li=findViewById(R.id.iv_ku_cun_guan_li);

    }

    @Override
    protected void onDestroy() {

        if (datas.size() != 0) {
            for (int i = 0; i < datas.size(); i++) {
                try {
                    MainActivity.readableDatabase.beginTransaction();
                    Log.i("cdl-mainactivity", "onDestroy: "+MainActivity.datas.get(MainActivity.datas.size() - 1 - i).getYong_hu_bian_hao());
                    MainActivity.readableDatabase.execSQL("delete from shangpingxingxi where usernumber=? and barcode=?",new Object[]{MainActivity.datas.get(MainActivity.datas.size() - 1 - i).getYong_hu_bian_hao(),MainActivity.datas.get(MainActivity.datas.size() - 1 - i).getShang_ping_tiao_ma()});
                    MainActivity.readableDatabase.execSQL("update kucunbiao set quantityofgoods=quantityofgoods+? where usernumber=? and barcode=?", new Object[]{datas.get(i).getShang_ping_shu_liang(),UserName, "" + MainActivity.datas.get(i).getShang_ping_tiao_ma()});
                    MainActivity.readableDatabase.setTransactionSuccessful();
                    MainActivity.readableDatabase.endTransaction();
                } catch (Exception e) {
                    MainActivity.readableDatabase.endTransaction();
//                    e.printStackTrace();
                }
            }
        }

        super.onDestroy();
    }

    public static String getUUID() {

        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {

            if (is_one) {
                is_one = false;
                one_date = System.currentTimeMillis();
                Toast.makeText(this, "请再按一次退出！", Toast.LENGTH_SHORT).show();
            } else {
                if ((System.currentTimeMillis() - one_date) < 2000) {
                    this.finish();
                } else {
                    one_date = System.currentTimeMillis();
                    Toast.makeText(this, "超过两秒请再按一次！", Toast.LENGTH_SHORT).show();
                }
            }
        }

        return false;
    }
}
