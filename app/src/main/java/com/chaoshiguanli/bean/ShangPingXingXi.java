package com.chaoshiguanli.bean;

/**
 * Created by Administrator on 2018/6/16.
 */

public class ShangPingXingXi {

    private String yong_hu_bian_hao;
    private String shang_ping_tiao_ma;
    private String shang_ping_ming_cheng;
    private int shang_ping_shu_liang;
    private String shang_ping_lei_xing;
    private double shang_ping_jia_ge;
    private String tian_jia_ri_qi;

    @Override
    public String toString() {
        return "ShangPingXingXi{" +
                "yong_hu_bian_hao='" + yong_hu_bian_hao + '\'' +
                ", shang_ping_tiao_ma='" + shang_ping_tiao_ma + '\'' +
                ", shang_ping_ming_cheng='" + shang_ping_ming_cheng + '\'' +
                ", shang_ping_shu_liang=" + shang_ping_shu_liang +
                ", shang_ping_lei_xing='" + shang_ping_lei_xing + '\'' +
                ", shang_ping_jia_ge=" + shang_ping_jia_ge +
                ", tian_jia_ri_qi='" + tian_jia_ri_qi + '\'' +
                '}';
    }

    public ShangPingXingXi() {
    }

    public ShangPingXingXi(String yong_hu_bian_hao, String shang_ping_tiao_ma, String shang_ping_ming_cheng, int shang_ping_shu_liang, String shang_ping_lei_xing, double shang_ping_jia_ge, String tian_jia_ri_qi) {
        this.yong_hu_bian_hao = yong_hu_bian_hao;
        this.shang_ping_tiao_ma = shang_ping_tiao_ma;
        this.shang_ping_ming_cheng = shang_ping_ming_cheng;
        this.shang_ping_shu_liang = shang_ping_shu_liang;
        this.shang_ping_lei_xing = shang_ping_lei_xing;
        this.shang_ping_jia_ge = shang_ping_jia_ge;
        this.tian_jia_ri_qi = tian_jia_ri_qi;
    }

    public String getYong_hu_bian_hao() {
        return yong_hu_bian_hao;
    }

    public void setYong_hu_bian_hao(String yong_hu_bian_hao) {
        this.yong_hu_bian_hao = yong_hu_bian_hao;
    }

    public String getShang_ping_tiao_ma() {
        return shang_ping_tiao_ma;
    }

    public void setShang_ping_tiao_ma(String shang_ping_tiao_ma) {
        this.shang_ping_tiao_ma = shang_ping_tiao_ma;
    }

    public String getShang_ping_ming_cheng() {
        return shang_ping_ming_cheng;
    }

    public void setShang_ping_ming_cheng(String shang_ping_ming_cheng) {
        this.shang_ping_ming_cheng = shang_ping_ming_cheng;
    }

    public int getShang_ping_shu_liang() {
        return shang_ping_shu_liang;
    }

    public void setShang_ping_shu_liang(int shang_ping_shu_liang) {
        this.shang_ping_shu_liang = shang_ping_shu_liang;
    }

    public String getShang_ping_lei_xing() {
        return shang_ping_lei_xing;
    }

    public void setShang_ping_lei_xing(String shang_ping_lei_xing) {
        this.shang_ping_lei_xing = shang_ping_lei_xing;
    }

    public double getShang_ping_jia_ge() {
        return shang_ping_jia_ge;
    }

    public void setShang_ping_jia_ge(double shang_ping_jia_ge) {
        this.shang_ping_jia_ge = shang_ping_jia_ge;
    }

    public String getTian_jia_ri_qi() {
        return tian_jia_ri_qi;
    }

    public void setTian_jia_ri_qi(String tian_jia_ri_qi) {
        this.tian_jia_ri_qi = tian_jia_ri_qi;
    }
}
