package com.example.administrator.chaoshiguanlis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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

import com.chaoshiguanli.bean.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
 * Created by Administrator on 2018/6/20.
 */

public class WangJiMiMaActivity extends Activity {

    EditText et_user_id,et_aswer;
    Spinner s_wen_ti;
    Button btn_submit;
    ImageView ivreturn_activity;
    List<String> datas=new ArrayList<>();
    String question="-请选择-";
    String return_data="";
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0x111) {
                try {
                    JSONObject jsonObject=new JSONObject(return_data);
                    String result = jsonObject.getString("result");
                    if(result.equals("success")){
                        WangJiMiMaActivity.this.finish();
                        Intent intent=new Intent(WangJiMiMaActivity.this,ChongZhiMiMaActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("userid",et_user_id.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }else if(result.equals("fail")){
                        new AlertDialog.Builder(WangJiMiMaActivity.this).setCancelable(false).setPositiveButton("确定",null).setTitle("找回账户失败信息填写错误~").show();
                    }else{
                        new AlertDialog.Builder(WangJiMiMaActivity.this).setCancelable(false).setPositiveButton("确定",null).setTitle("服务器错误~").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                new AlertDialog.Builder(WangJiMiMaActivity.this).setCancelable(false).setPositiveButton("确定",null).setTitle("网络连接失败~").show();
            }
            }
        };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        datas= Arrays.asList(getResources().getStringArray(R.array.question));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wang_ji_mi_ma_layout);
        btn_submit=findViewById(R.id.btn_submit);
        s_wen_ti=findViewById(R.id.s_wen_ti);
        et_user_id=findViewById(R.id.et_user_id);
        et_aswer=findViewById(R.id.et_aswer);
        ivreturn_activity=findViewById(R.id.ivreturn_activity);
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
                if(convertView==null){
                    convertView= LayoutInflater.from(WangJiMiMaActivity.this).inflate(R.layout.spinner_question_layout,null);
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


        ivreturn_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WangJiMiMaActivity.this.finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!question.equals("-请选择-")&&!et_aswer.getText().toString().equals("")&&!et_user_id.getText().toString().equals("")) {
                    et_user_id.getText().toString();
                    et_aswer.getText().toString();
                    User user=new User(et_user_id.getText().toString(),null,null,false,question,et_aswer.getText().toString());
                    Gson gson=new Gson();
                    String json = gson.toJson(user);

                    OkHttpClient client=new OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS).readTimeout(20,TimeUnit.SECONDS).writeTimeout(20,TimeUnit.SECONDS).build();
                    RequestBody requestBody=RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json);
                    Request request=new Request.Builder().url("http://"+LoginActivity.sharedPreferences.getString("ip","10.0.2.2：80")+"/retrievethepassword").post(requestBody).build();
                    client.newCall(request).enqueue(new Callback() {
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


                }else{
                    new AlertDialog.Builder(WangJiMiMaActivity.this).setCancelable(false).setPositiveButton("确定",null).setTitle("账户、问题、答案都不能为空").show();
                }
            }
        });

    }
}
