package com.example.administrator.chaoshiguanlis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoshiguanli.bean.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

public class UserNewActivity extends Activity implements View.OnClickListener{

    ImageView ivreturn_activity;
    EditText et_user_id,et_passwords,et_er_ci_mi_ma,et_aswer;
    Spinner s_wen_ti;
    Button btn_register_user;
    List<String> datas=null;
    String question="-请选择-";
    ImageView iv_help;
    String retrun_datas="";
    Timer timer=new Timer();
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x111){
//                判断是否成功
                try {
                    Object result;
                    JSONObject jsonObject=new JSONObject(retrun_datas);
                    result = jsonObject.get("result");
                    alertDialog.dismiss();
                    if(result.equals("success")) {

                        View inflate = LayoutInflater.from(UserNewActivity.this).inflate(R.layout.progress_success_layout, null);
                        new AlertDialog.Builder(UserNewActivity.this).setPositiveButton("去登陆", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserNewActivity.this.finish();
                            }
                        }).setView(inflate).setCancelable(false).show();
                    }else if(result.equals("fail")){
                        new AlertDialog.Builder(UserNewActivity.this).setCancelable(false).setPositiveButton("确定",null).setTitle("该账户已存在！请更改注册账户~").show();
                    }else{
                        new AlertDialog.Builder(UserNewActivity.this).setCancelable(false).setPositiveButton("确定",null).setTitle("抱歉！服务器错误~").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(msg.what==0x112){
                alertDialog.dismiss();
                new AlertDialog.Builder(UserNewActivity.this).setCancelable(false).setTitle("连接网络失败！").setPositiveButton("确定",null).show();
            }


        }
    };
    AlertDialog alertDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas=new ArrayList<>();
        String[] stringArray = getResources().getStringArray(R.array.question);
        datas= Arrays.asList(stringArray);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_new_layout);
        ivreturn_activity=findViewById(R.id.ivreturn_activity);
        et_user_id=findViewById(R.id.et_user_id);
        s_wen_ti=findViewById(R.id.s_wen_ti);
        et_er_ci_mi_ma=findViewById(R.id.et_er_ci_mi_ma);
        et_passwords=findViewById(R.id.et_passwords);
        et_aswer=findViewById(R.id.et_aswer);
        btn_register_user=findViewById(R.id.btn_register_user);
        iv_help=findViewById(R.id.iv_help);
        iv_help.setOnClickListener(this);
        btn_register_user.setOnClickListener(this);
        ivreturn_activity.setOnClickListener(this);

        BaseAdapter baseAdapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return datas.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView==null) {
                    convertView = LayoutInflater.from(UserNewActivity.this).inflate(R.layout.spinner_question_layout, null);
                }
                TextView tv_lei_xing=convertView.findViewById(R.id.tv_lei_xing);
                tv_lei_xing.setText(datas.get(position));

                return convertView;
            }
        };
        s_wen_ti.setAdapter(baseAdapter);
        s_wen_ti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                question=datas.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivreturn_activity:
                UserNewActivity.this.finish();
                break;

            case R.id.btn_register_user:
                if((et_user_id.getText().toString().matches("^\\d{1,10}[A-Za-z]{1,10}+$")||et_user_id.getText().toString().matches("^[A-Za-z]{1,10}\\d{1,10}+$"))&&et_user_id.getText().toString().length()>=8){
                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                    if((et_passwords.getText().toString().matches("^\\d{1,10}[A-Za-z]{1,10}+$")||et_passwords.getText().toString().matches("^[A-Za-z]{1,10}\\d{1,10}+$")||et_passwords.getText().toString().matches("^[A-Za-z]{1,10}\\d{1,10}[A-Za-z]{1,10}+$")||et_passwords.getText().toString().matches("^\\d{1,10}[A-Za-z]{1,10}\\d{1,10}+$"))&&et_passwords.getText().toString().length()>=8){
                        if(!question.equals("-请选择-")) {
                            if(!et_aswer.getText().toString().equals("")) {
                                if (et_passwords.getText().toString().equals(et_er_ci_mi_ma.getText().toString())) {

                                    String user_id = et_user_id.getText().toString();
                                    String password = et_passwords.getText().toString();
                                    String password_two = et_er_ci_mi_ma.getText().toString();
//                            question;
                                    String aswer = et_aswer.getText().toString();
                                    Toast.makeText(this, "" + user_id + " " + password + " " + password_two + " " + question + " " + aswer, Toast.LENGTH_SHORT).show();
//                            信息通过访问服务器
                                    View inflate = LayoutInflater.from(UserNewActivity.this).inflate(R.layout.progress_layout, null);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(UserNewActivity.this).setView(inflate).setCancelable(false);
                                    alertDialog = builder.show();

                                    User user=new User(et_user_id.getText().toString(),et_passwords.getText().toString(),et_user_id.getText().toString(),false,question,et_aswer.getText().toString());
                                    Gson gson=new Gson();
                                    String json = gson.toJson(user);

                                    OkHttpClient okHttpClient=new OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS).writeTimeout(20,TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).build();
                                    RequestBody requestBody=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json);
                                    Request request=new Request.Builder().url("http://"+LoginActivity.sharedPreferences.getString("ip","10.0.2.2:80")+"/register").post(requestBody).build();
                                    okHttpClient.newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            Message message = new Message();
                                            message.what = 0x112;
                                            handler.sendMessage(message);
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
//                            这是发送注册成功后的消息
                                            retrun_datas=response.body().string();
                                            Message message = new Message();
                                            message.what = 0x111;
                                            handler.sendMessage(message);
                                        }
                                    });
                                } else {
                                    new AlertDialog.Builder(UserNewActivity.this).setPositiveButton("确定", null).setTitle("两次输入密码不一致").show();
                                }
                            }else{
                                new AlertDialog.Builder(UserNewActivity.this).setPositiveButton("确定", null).setTitle("找回密码答案不能为空！~").show();
                            }
                        }else{
                            new AlertDialog.Builder(UserNewActivity.this).setPositiveButton("确定", null).setTitle("请选择找回密码的问题~").show();
                        }
                    }else{
                        new AlertDialog.Builder(UserNewActivity.this).setPositiveButton("确定",null).setTitle("密码长度最少8位，只能由数字和字母组成").show();
                    }
                }else{
                    new AlertDialog.Builder(UserNewActivity.this).setPositiveButton("确定",null).setTitle("账号长度最少8位，只能由字母加数字组成。").show();
                }
                break;

            case R.id.iv_help:
                View inflate = LayoutInflater.from(UserNewActivity.this).inflate(R.layout.help_layout, null);
                new AlertDialog.Builder(UserNewActivity.this).setCancelable(false).setPositiveButton("确定",null).setView(inflate).setTitle("帮助").show();
                break;
        }
    }
}
