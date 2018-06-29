package com.example.administrator.chaoshiguanlis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/20.
 */

public class ChongZhiMiMaActivity extends Activity {

    ImageView ivreturn_activity;
    EditText et_passwords,et_er_ci_mi_ma;
    Button btn_submit;
    String userid,retrun_data="";
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x111) {
                try {
                    JSONObject jsonObject=new JSONObject(retrun_data);
                    String result = jsonObject.getString("result");
                    if (result.equals("success")) {
                    View inflate = LayoutInflater.from(ChongZhiMiMaActivity.this).inflate(R.layout.password_progress_success_layout, null);
                    new AlertDialog.Builder(ChongZhiMiMaActivity.this).setPositiveButton("去登陆", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ChongZhiMiMaActivity.this.finish();
                        }
                    }).setView(inflate).setCancelable(false).show();
                }else{
                        new AlertDialog.Builder(ChongZhiMiMaActivity.this).setCancelable(false).setPositiveButton("确定",null).setTitle("修改失败了！").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                new AlertDialog.Builder(ChongZhiMiMaActivity.this).setCancelable(false).setPositiveButton("确定",null).setTitle("连接网络失败！").show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        userid = bundle.getString("userid");
        if(userid==null){
            this.finish();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chong_zhi_mi_ma_layout);
        ivreturn_activity=findViewById(R.id.ivreturn_activity);
        et_passwords=findViewById(R.id.et_passwords);
        et_er_ci_mi_ma=findViewById(R.id.et_er_ci_mi_ma);
        btn_submit=findViewById(R.id.btn_submit);

        ivreturn_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChongZhiMiMaActivity.this.finish();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((et_passwords.getText().toString().matches("^\\d{1,10}[A-Za-z]{1,10}+$")||et_passwords.getText().toString().matches("^[A-Za-z]{1,10}\\d{1,10}+$")||et_passwords.getText().toString().matches("^[A-Za-z]{1,10}\\d{1,10}[A-Za-z]{1,10}+$")||et_passwords.getText().toString().matches("^\\d{1,10}[A-Za-z]{1,10}\\d{1,10}+$"))&&et_passwords.getText().toString().length()>=8) {
                    if (et_passwords.getText().toString().equals(et_er_ci_mi_ma.getText().toString())) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("onePassword", et_passwords.getText().toString());
                            jsonObject.put("user", userid);

                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString());
                            final Request request = new Request.Builder().url("http://" + LoginActivity.sharedPreferences.getString("ip", "10.0.2.2:80") + "/resetPassword").post(requestBody).build();
                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Message message = new Message();
                                    message.what = 0x112;
                                    handler.sendMessage(message);
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    retrun_data = response.body().string();
                                    Message message = new Message();
                                    message.what = 0x111;
                                    handler.sendMessage(message);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        new AlertDialog.Builder(ChongZhiMiMaActivity.this).setCancelable(false).setPositiveButton("确定", null).setTitle("两次密码不一致！").show();
                    }
                    Toast.makeText(ChongZhiMiMaActivity.this, "提交", Toast.LENGTH_SHORT).show();
                }else{
                    new AlertDialog.Builder(ChongZhiMiMaActivity.this).setCancelable(false).setPositiveButton("确定", null).setTitle("密码长度最少8位，只能由数字和字母组成！").show();
                }
            }
        });

    }
}
