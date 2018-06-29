package com.com.chaoshiguanli.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaoshiguanli.adapter.ShangPingLieBiaoAdapter;
import com.chaoshiguanli.bean.ShangPingXingXi;
import com.daimajia.swipe.SwipeLayout;
import com.example.administrator.chaoshiguanlis.MainActivity;
import com.example.administrator.chaoshiguanlis.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.hugo.android.scanner.CaptureActivity;

/**
 * Created by Administrator on 2018/6/7.
 */

public class One_fragment extends Fragment {
    private ListView lv_show;
    //    private List<String> datas;
    public static ShangPingLieBiaoAdapter OneFragmentAdapter;
    private SwipeLayout sample1;
    private Button btn_jie_zhang;
    public static TextView tv_sum_title, tv_show_sum_yuan;

    private ImageView xiao_shou_sao_miao, iv_sang_ping_add;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        datas=MainActivity.datas;
//        Collections.reverse(datas);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.one_layout, null);
        xiao_shou_sao_miao = view.findViewById(R.id.xiao_shou_sao_miao);
        tv_show_sum_yuan = view.findViewById(R.id.tv_show_sum_yuan);
        tv_sum_title = view.findViewById(R.id.tv_sum_cont);
        xiao_shou_sao_miao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Id", "MainActivity");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

//        显示数量和总价值
        sumAndZongJiaZhi();

        iv_sang_ping_add = view.findViewById(R.id.iv_sang_ping_add);
        iv_sang_ping_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().replace(R.id.ll_content, new Add_ChuShou_Fragment()).commit();
            }
        });

        btn_jie_zhang = view.findViewById(R.id.btn_jie_zhang);

//        结账按钮
        btn_jie_zhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.datas.size() > 0) {
//                结账事件
//                获取唯一的id
                    String uuid = MainActivity.getUUID();
                    Cursor cursor = MainActivity.readableDatabase.rawQuery("select Saleno from danhaojilu where Saleno=? and usernumber=?", new String[]{uuid, MainActivity.UserName});
                    for (; cursor.getCount() > 0; ) {
                        uuid = MainActivity.getUUID();
                        cursor = MainActivity.readableDatabase.rawQuery("select Saleno from danhaojilu where Saleno=? and usernumber=?", new String[]{uuid, MainActivity.UserName});
                    }

                    MainActivity.readableDatabase.beginTransaction();
                    try {
                        Date date = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String format = simpleDateFormat.format(date);

                        //                总价值
                        double yuan = 0;
                        for (int i = 0; i < MainActivity.datas.size(); i++) {
                            yuan += MainActivity.datas.get(i).getShang_ping_jia_ge()*MainActivity.datas.get(i).getShang_ping_shu_liang();
                        }
                        MainActivity.readableDatabase.execSQL("insert into danhaojilu(usernumber,totalvalue,Saleno,dateofsale,`isupdate`) values(?,?,?,?,?)", new Object[]{MainActivity.UserName,yuan, uuid, format,0});

                        for (int i = 0; i < MainActivity.datas.size(); i++) {
                            Log.i("cdl", "onClick: "+MainActivity.datas.get(i).getYong_hu_bian_hao());
                            MainActivity.readableDatabase.execSQL("insert into xiaoshoujilu(usernumber,Saleno,barcode,commodityname,quantityofgoods,commoditytype,saleprice,dateofsale,`isupdate`) values(?,?,?,?,?,?,?,?,?)", new Object[]{MainActivity.UserName, uuid, MainActivity.datas.get(i).getShang_ping_tiao_ma(), MainActivity.datas.get(i).getShang_ping_ming_cheng(), MainActivity.datas.get(i).getShang_ping_shu_liang(), MainActivity.datas.get(i).getShang_ping_lei_xing(), (MainActivity.datas.get(i).getShang_ping_shu_liang() * MainActivity.datas.get(i).getShang_ping_jia_ge()), format,0});

                            MainActivity.readableDatabase.execSQL("delete from shangpingxingxi where usernumber=? and barcode=?", new Object[]{MainActivity.datas.get(i).getYong_hu_bian_hao(), MainActivity.datas.get(i).getShang_ping_tiao_ma()});
                        }

                        MainActivity.readableDatabase.setTransactionSuccessful();
                        MainActivity.readableDatabase.endTransaction();
                    } catch (Exception e) {
                        MainActivity.readableDatabase.endTransaction();
                    }

                    MainActivity.datas.removeAll(MainActivity.datas);
                    OneFragmentAdapter.notifyDataSetChanged();
                    sumAndZongJiaZhi();
                } else {
                    Toast.makeText(getActivity(), "没有商品可以结账~", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //        这三个一起
        lv_show = view.findViewById(R.id.lv_show);
        lv_show_setData();
        setAdapter();

        return view;
    }


    private void lv_show_setData() {
        OneFragmentAdapter = new ShangPingLieBiaoAdapter(MainActivity.datas) {
            @Override
            protected View setView(final int position, View convertView, ViewGroup parent, final List<ShangPingXingXi> listData) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.shang_ping_xing_xi_layout, null);
                    Swipe(convertView, listData, position);
                    TextView tv_list_show, tv_num, tv_show_jia_ge;
                    tv_num = convertView.findViewById(R.id.tv_num);
                    tv_show_jia_ge = convertView.findViewById(R.id.tv_show_jia_ge);
                    tv_list_show = convertView.findViewById(R.id.tv_list_show);
                    tv_list_show.setText(MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_ming_cheng() + "");
                    tv_num.setText(MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_shu_liang() + "");
                    tv_show_jia_ge.setText(MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_jia_ge() + "");
                } else {
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.shang_ping_xing_xi_layout, null);
                    Swipe(convertView, listData, position);
                    TextView tv_list_show, tv_num, tv_show_jia_ge;
                    tv_num = convertView.findViewById(R.id.tv_num);
                    tv_show_jia_ge = convertView.findViewById(R.id.tv_show_jia_ge);
                    tv_list_show = convertView.findViewById(R.id.tv_list_show);
                    tv_list_show.setText(MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_ming_cheng() + "");
                    tv_num.setText(MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_shu_liang() + "");
                    tv_show_jia_ge.setText(MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_jia_ge() + "");
                }
                return convertView;
            }
        };
    }

    private void Swipe(View convertView, final List listData, final int position) {
        sample1 = convertView.findViewById(R.id.sl_content);

        sample1.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                这里会代替点击事件
//                查询数量
                try {
                    final Cursor cursor = MainActivity.readableDatabase.rawQuery("select quantityofgoods from kucunbiao where usernumber=? and barcode=?", new String[]{MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getYong_hu_bian_hao(), MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_tiao_ma()});
                    cursor.moveToFirst();


                    View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.show_and_edit_layout, null);

                    final TextView et_id, tv_name, tv_ku_cun_sum_count, tv_shang_ping_lei_xing;
                    ImageView remove_sum, add_sum;
                    final EditText et_sums, et_sum_yuan, et_sum_yuan_xiao_shu;

                    et_sum_yuan_xiao_shu = inflate.findViewById(R.id.et_sum_yuan_xiao_shu);
                    et_id = inflate.findViewById(R.id.et_id);
                    tv_name = inflate.findViewById(R.id.tv_name);
                    remove_sum = inflate.findViewById(R.id.remove_sum);
                    et_sums = inflate.findViewById(R.id.et_sums);
                    add_sum = inflate.findViewById(R.id.add_sum);
                    tv_ku_cun_sum_count = inflate.findViewById(R.id.tv_ku_cun_sum_count);
                    et_sum_yuan = inflate.findViewById(R.id.et_sum_yuan);
                    tv_shang_ping_lei_xing = inflate.findViewById(R.id.tv_shang_ping_lei_xing);

                    et_id.setText(MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_tiao_ma());
                    tv_name.setText(MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_ming_cheng());
                    et_sums.setText(MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_shu_liang() + "");
                    String jia_ge = MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_jia_ge() + "";
                    tv_shang_ping_lei_xing.setText(MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_lei_xing() + "");
                    String[] split = jia_ge.split("\\.");

                    et_sum_yuan.setText(split[0] + "");
                    et_sum_yuan_xiao_shu.setText(split[1] + "");

                    tv_ku_cun_sum_count.setText(cursor.getInt(0) + MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_shu_liang() + "");
                    final int is_addOrremove = Integer.parseInt(et_sums.getText().toString());
//              增加数量
                    remove_sum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Integer.parseInt(et_sums.getText().toString()) > 1) {
                                et_sums.setText((Integer.parseInt(et_sums.getText().toString()) - 1) + "");
                            }
                        }
                    });
//                减少数量
                    add_sum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (Integer.parseInt(et_sums.getText().toString()) < Integer.parseInt(tv_ku_cun_sum_count.getText().toString())) {
                                et_sums.setText((Integer.parseInt(et_sums.getText().toString()) + 1) + "");
                            }
                        }
                    });

                    new AlertDialog.Builder(getActivity()).setView(inflate).setNegativeButton("取消", null).setCancelable(false).setPositiveButton("修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                      现将已有的那一个减掉，然后修改数据库
                            if (is_addOrremove < Integer.parseInt(et_sums.getText().toString())) {
                                try {
                                    MainActivity.readableDatabase.beginTransaction();
                                    MainActivity.readableDatabase.execSQL("update kucunbiao set quantityofgoods=quantityofgoods-? where usernumber=? and barcode=?", new Object[]{(Integer.parseInt(et_sums.getText().toString()) - MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_shu_liang()), MainActivity.UserName, MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_tiao_ma()});
                                    MainActivity.readableDatabase.execSQL("update shangpingxingxi set quantityofgoods=quantityofgoods+?,saleprice=? where usernumber=? and barcode=?", new Object[]{(Integer.parseInt(et_sums.getText().toString()) - MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_shu_liang()), Double.parseDouble(et_sum_yuan.getText().toString() + "." + et_sum_yuan_xiao_shu.getText().toString()), MainActivity.UserName, MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_tiao_ma()});
                                    MainActivity.datas.get(MainActivity.datas.size() - 1 - position).setShang_ping_shu_liang(Integer.parseInt(et_sums.getText().toString()));
                                    MainActivity.datas.get(MainActivity.datas.size() - 1 - position).setShang_ping_jia_ge(Double.parseDouble(et_sum_yuan.getText().toString() + "." + et_sum_yuan_xiao_shu.getText().toString()));
                                    MainActivity.readableDatabase.setTransactionSuccessful();
                                    MainActivity.readableDatabase.endTransaction();

                                    sumAndZongJiaZhi();
                                    OneFragmentAdapter.notifyDataSetChanged();

                                } catch (Exception e) {
                                    MainActivity.readableDatabase.endTransaction();
                                }
                            } else if (is_addOrremove > Integer.parseInt(et_sums.getText().toString())) {
                                Toast.makeText(getActivity(), "变少了", Toast.LENGTH_SHORT).show();

                                try {
                                    MainActivity.readableDatabase.beginTransaction();
                                    MainActivity.readableDatabase.execSQL("update kucunbiao set quantityofgoods=quantityofgoods+? where usernumber=? and barcode=?", new Object[]{is_addOrremove - Integer.parseInt(et_sums.getText().toString()), MainActivity.UserName, MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_tiao_ma()});
                                    MainActivity.readableDatabase.execSQL("update shangpingxingxi set quantityofgoods=quantityofgoods-?,saleprice=? where usernumber=? and barcode=?", new Object[]{is_addOrremove - Integer.parseInt(et_sums.getText().toString()), Double.parseDouble(et_sum_yuan.getText().toString() + "." + et_sum_yuan_xiao_shu.getText().toString()), MainActivity.UserName, MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_tiao_ma()});
                                    MainActivity.datas.get(MainActivity.datas.size() - 1 - position).setShang_ping_shu_liang(Integer.parseInt(et_sums.getText().toString()));
                                    MainActivity.datas.get(MainActivity.datas.size() - 1 - position).setShang_ping_jia_ge(Double.parseDouble(et_sum_yuan.getText().toString() + "." + et_sum_yuan_xiao_shu.getText().toString()));
                                    MainActivity.readableDatabase.setTransactionSuccessful();
                                    MainActivity.readableDatabase.endTransaction();

                                    sumAndZongJiaZhi();
                                    OneFragmentAdapter.notifyDataSetChanged();

                                } catch (Exception e) {
                                    MainActivity.readableDatabase.endTransaction();
                                }


                            } else {

                                MainActivity.readableDatabase.execSQL("update shangpingxingxi set saleprice=? where usernumber=? and barcode=?", new Object[]{Double.parseDouble(et_sum_yuan.getText().toString() + "." + et_sum_yuan_xiao_shu.getText().toString()), MainActivity.UserName, MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_tiao_ma()});
                                MainActivity.datas.get(MainActivity.datas.size() - 1 - position).setShang_ping_jia_ge(Double.parseDouble(et_sum_yuan.getText().toString() + "." + et_sum_yuan_xiao_shu.getText().toString()));
                                sumAndZongJiaZhi();
                                OneFragmentAdapter.notifyDataSetChanged();
//                                return;
                            }
                        }
                    }).show();
                    Toast.makeText(getActivity(), "Click on surface", Toast.LENGTH_SHORT).show();
                    Log.d(MainActivity.class.getName(), "click on surface");
                } catch (Exception e) {
                }
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

//        sample1.findViewById(R.id.star2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Star", Toast.LENGTH_SHORT).show();
//            }
//        });

        sample1.findViewById(R.id.trash2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.readableDatabase.beginTransaction();
                    MainActivity.readableDatabase.execSQL("delete from shangpingxingxi where usernumber=? and barcode=?", new Object[]{MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getYong_hu_bian_hao(), MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_tiao_ma()});
                    MainActivity.readableDatabase.execSQL("update kucunbiao set quantityofgoods=quantityofgoods+? where usernumber=? and barcode=?", new Object[]{MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_shu_liang(), MainActivity.UserName, "" + MainActivity.datas.get(MainActivity.datas.size() - 1 - position).getShang_ping_tiao_ma()});
                    MainActivity.readableDatabase.setTransactionSuccessful();
                    MainActivity.readableDatabase.endTransaction();
                } catch (Exception e) {
                    MainActivity.readableDatabase.endTransaction();
//                    e.printStackTrace();
                }

                MainActivity.datas.remove(MainActivity.datas.size() - 1 - position);
                OneFragmentAdapter.notifyDataSetChanged();

                sumAndZongJiaZhi();

//                setAdapter();
                Toast.makeText(getActivity(), "Trash Bin", Toast.LENGTH_SHORT).show();
            }
        });

//        sample1.findViewById(R.id.magnifier2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Magnifier", Toast.LENGTH_SHORT).show();
//            }
//        });

        sample1.addRevealListener(R.id.starbott, new SwipeLayout.OnRevealListener() {
            @Override
            public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
                View star = child.findViewById(R.id.star);
                float d = child.getHeight() / 2 - star.getHeight() / 2;
//                        ViewHelper.setTranslationY(star, d * fraction);
//                        ViewHelper.setScaleX(star, fraction + 0.6f);
//                        ViewHelper.setScaleY(star, fraction + 0.6f);
            }
        });

    }

    public static void sumAndZongJiaZhi() {
        //                数量
        int sum = 0;
        for (int i = 0; i < MainActivity.datas.size(); i++) {
            sum += MainActivity.datas.get(i).getShang_ping_shu_liang();
        }
        One_fragment.tv_sum_title.setText("" + sum);

//                总价值
        double yuan = 0;
        for (int i = 0; i < MainActivity.datas.size(); i++) {
            yuan += MainActivity.datas.get(i).getShang_ping_shu_liang() * MainActivity.datas.get(i).getShang_ping_jia_ge();
        }
        DecimalFormat formater = new DecimalFormat();
        //保留几位小数
        formater.setMaximumFractionDigits(1);
        //模式  四舍五入
        formater.setRoundingMode(RoundingMode.UP);
        One_fragment.tv_show_sum_yuan.setText("" + formater.format(yuan));
    }

    private void setAdapter() {
        lv_show.setAdapter(OneFragmentAdapter);
    }
}
