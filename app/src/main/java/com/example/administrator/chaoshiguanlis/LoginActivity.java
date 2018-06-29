package com.example.administrator.chaoshiguanlis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoshiguanli.bean.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/19.
 */

public class LoginActivity extends Activity implements View.OnClickListener{

    String user,password;
    private EditText et_user,et_password;
    private TextView tv_wang_ji_mi_ma;
    private Button btn_login,btn_register,btn_ip_setting;
    private ImageView iv_title;
    private int height=0;
    String return_data="";
    public static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private AlertDialog builder;
    private Timer timer=new Timer();

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(LoginActivity.this, "datas=" + return_data, Toast.LENGTH_SHORT).show();
            if(msg.what==0x111) {
                JSONObject jsonObject = null;
                String results = null;
                try {
                    jsonObject = new JSONObject(return_data);
                    results = String.valueOf(jsonObject.get("result"));


                builder.dismiss();

                if (results.equals("success")) {
                    editor.putString("userName", user);
                    editor.putString("password", password);
                    editor.putString("nickname", (String) jsonObject.get("nickname"));
                    editor.putBoolean("sex", (Boolean) jsonObject.get("sex"));
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                    Toast.makeText(LoginActivity.this, "datas=" + return_data, Toast.LENGTH_SHORT).show();
                }if(results.equals("fail")){
                    new AlertDialog.Builder(LoginActivity.this).setTitle("账号或密码错误！").setPositiveButton("确定",null).show();
                }else if(results.equals("error")){
                    new AlertDialog.Builder(LoginActivity.this).setTitle("该账户不存在！").setCancelable(false).setPositiveButton("确定",null).show();
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    builder.dismiss();
                }catch (Exception e){}
                new AlertDialog.Builder(LoginActivity.this).setTitle("网络连接失败！").setCancelable(false).setPositiveButton("确定",null).show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_layout);

        sharedPreferences=getSharedPreferences("chaoshiguanli",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        if(sharedPreferences.getString("ip","error").equals("error")){
            editor.putString("ip","10.0.2.2:80").commit();
        }


        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height=displayMetrics.heightPixels;


        IdFind();
        TranstionListener();
        ZiDongDengLu();



        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.topMargin=height/7;
        params.width=120*3;
        params.height=120*3;
        iv_title.setLayoutParams(params);



    }

    private void ZiDongDengLu() {
        if(sharedPreferences.getString("userName","error").equals("error")&&sharedPreferences.getString("password","error").equals("error")){
            et_password.setText("");
            et_user.setText("");
        }else{
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    private void TranstionListener() {
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_wang_ji_mi_ma.setOnClickListener(this);
        btn_ip_setting.setOnClickListener(this);
    }

    private void IdFind() {
        et_user=findViewById(R.id.et_user);
        et_password=findViewById(R.id.et_password);
        tv_wang_ji_mi_ma=findViewById(R.id.tv_wang_ji_mi_ma);
        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        iv_title=findViewById(R.id.iv_title);
        btn_ip_setting=findViewById(R.id.btn_ip_setting);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                user=et_user.getText().toString();
                password=et_password.getText().toString();
                if(user.length()>0&&password.length()>0) {
                    builder=new AlertDialog.Builder(LoginActivity.this).setTitle("正在登录中…").setCancelable(false).show();
                    User user1=new User(user,password,null,false,null,null);
                    JSONObject object= null;
                    Toast.makeText(this, ""+user1, Toast.LENGTH_SHORT).show();
                    try {

                        Gson gson=new Gson();
                        String json = gson.toJson(user1);

                        object = new JSONObject(json);

                    OkHttpClient.Builder client=new OkHttpClient.Builder();
                        client.connectTimeout(3, TimeUnit.SECONDS)
                                .readTimeout(20, TimeUnit.SECONDS)
                                .writeTimeout(20,TimeUnit.SECONDS)
                                .build();
                        OkHttpClient clienta=client.build();
                    RequestBody requestBody=RequestBody.create(MediaType.parse("application/json ; charset=utf-8"),object.toString());
                        Request request= new Request.Builder().url("http://"+sharedPreferences.getString("ip","10.0.2.2:80")+"/index").post(requestBody).build();
                        clienta.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Message message=new Message();
                                message.what=0x112;
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                return_data=response.body().string();
                                Message message=new Message();
                                message.what=0x111;
                                handler.sendMessage(message);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    new AlertDialog.Builder(LoginActivity.this).setPositiveButton("确定",null).setTitle("账户或密码不能为空！").show();
                }
                break;

            case R.id.btn_register:
                Intent intent1=new Intent(LoginActivity.this,UserNewActivity.class);
                startActivity(intent1);
                break;

            case R.id.tv_wang_ji_mi_ma:
                Intent intent=new Intent(LoginActivity.this,WangJiMiMaActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_ip_setting:
                View inflate = LayoutInflater.from(LoginActivity.this).inflate(R.layout.ip_setting_layout, null);
                final EditText editText=inflate.findViewById(R.id.et_setting_ip);
                String string = sharedPreferences.getString("ip", "10.0.2.2:80");
                editText.setText(string);
                new AlertDialog.Builder(LoginActivity.this).setCancelable(false).setView(inflate).setTitle("设置ip").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString("ip",editText.getText().toString()).commit();
                    }
                }).setNegativeButton("取消", null).show();

        }
    }
}
