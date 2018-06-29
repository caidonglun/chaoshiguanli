package com.chaoshiguanli.bean;

/**
 * Created by Administrator on 2018/6/17.
 */

public class XiaoShouJiLu {

    private String yong_hu_bian_hao;
    private String xiao_shou_dan_hao;
    private String shang_ping_tiao_ma;
    private String shang_ping_ming_cheng;
    private int shang_ping_shu_liang;
    private String shang_ping_lei_xing;
    private double chu_shou_jia_ge;
    private String chu_shou_ri_qi;

    @Override
    public String toString() {
        return "XiaoShouJiLu{" +
                "yong_hu_bian_hao='" + yong_hu_bian_hao + '\'' +
                ", xiao_shou_dan_hao='" + xiao_shou_dan_hao + '\'' +
                ", shang_ping_tiao_ma='" + shang_ping_tiao_ma + '\'' +
                ", shang_ping_ming_cheng='" + shang_ping_ming_cheng + '\'' +
                ", shang_ping_shu_liang=" + shang_ping_shu_liang +
                ", shang_ping_lei_xing='" + shang_ping_lei_xing + '\'' +
                ", chu_shou_jia_ge=" + chu_shou_jia_ge +
                ", chu_shou_ri_qi='" + chu_shou_ri_qi + '\'' +
                '}';
    }

    public XiaoShouJiLu() {
    }

    public XiaoShouJiLu(String yong_hu_bian_hao, String xiao_shou_dan_hao, String shang_ping_tiao_ma, String shang_ping_ming_cheng, int shang_ping_shu_liang, String shang_ping_lei_xing, double chu_shou_jia_ge, String chu_shou_ri_qi) {
        this.yong_hu_bian_hao = yong_hu_bian_hao;
        this.xiao_shou_dan_hao = xiao_shou_dan_hao;
        this.shang_ping_tiao_ma = shang_ping_tiao_ma;
        this.shang_ping_ming_cheng = shang_ping_ming_cheng;
        this.shang_ping_shu_liang = shang_ping_shu_liang;
        this.shang_ping_lei_xing = shang_ping_lei_xing;
        this.chu_shou_jia_ge = chu_shou_jia_ge;
        this.chu_shou_ri_qi = chu_shou_ri_qi;
    }

    public String getYong_hu_bian_hao() {
        return yong_hu_bian_hao;
    }

    public void setYong_hu_bian_hao(String yong_hu_bian_hao) {
        this.yong_hu_bian_hao = yong_hu_bian_hao;
    }

    public String getXiao_shou_dan_hao() {
        return xiao_shou_dan_hao;
    }

    public void setXiao_shou_dan_hao(String xiao_shou_dan_hao) {
        this.xiao_shou_dan_hao = xiao_shou_dan_hao;
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

    public double getChu_shou_jia_ge() {
        return chu_shou_jia_ge;
    }

    public void setChu_shou_jia_ge(double chu_shou_jia_ge) {
        this.chu_shou_jia_ge = chu_shou_jia_ge;
    }

    public String getChu_shou_ri_qi() {
        return chu_shou_ri_qi;
    }

    public void setChu_shou_ri_qi(String chu_shou_ri_qi) {
        this.chu_shou_ri_qi = chu_shou_ri_qi;
    }
}
