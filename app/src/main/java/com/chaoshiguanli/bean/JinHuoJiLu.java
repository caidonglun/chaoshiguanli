package com.chaoshiguanli.bean;

/**
 * Created by Administrator on 2018/6/15.
 */

public class JinHuoJiLu {
    private String usernumber;
    private String shang_ping_tiao_ma;
    private String shang_ping_ming_cheng;
    private int shang_ping_shu_liang;
    private double jin_huo_jia_ge;
    private String shang_ping_lei_xing;
    private String bei_zhu_lei_xing;
    private String chang_shang_ming_cheng;
    private String lian_xi_fang_shi;
    private String tian_jia_ri_qi;

    @Override
    public String toString() {
        return "JinHuoJiLu{" +
                "usernumber='" + usernumber + '\'' +
                ", shang_ping_tiao_ma='" + shang_ping_tiao_ma + '\'' +
                ", shang_ping_ming_cheng='" + shang_ping_ming_cheng + '\'' +
                ", shang_ping_shu_liang=" + shang_ping_shu_liang +
                ", jin_huo_jia_ge=" + jin_huo_jia_ge +
                ", shang_ping_lei_xing='" + shang_ping_lei_xing + '\'' +
                ", bei_zhu_lei_xing='" + bei_zhu_lei_xing + '\'' +
                ", chang_shang_ming_cheng='" + chang_shang_ming_cheng + '\'' +
                ", lian_xi_fang_shi='" + lian_xi_fang_shi + '\'' +
                ", tian_jia_ri_qi='" + tian_jia_ri_qi + '\'' +
                '}';
    }

    public JinHuoJiLu() {
    }

    public JinHuoJiLu(String usernumber, String shang_ping_tiao_ma, String shang_ping_ming_cheng, int shang_ping_shu_liang, double jin_huo_jia_ge, String shang_ping_lei_xing, String bei_zhu_lei_xing, String chang_shang_ming_cheng, String lian_xi_fang_shi, String tian_jia_ri_qi) {
        this.usernumber = usernumber;
        this.shang_ping_tiao_ma = shang_ping_tiao_ma;
        this.shang_ping_ming_cheng = shang_ping_ming_cheng;
        this.shang_ping_shu_liang = shang_ping_shu_liang;
        this.jin_huo_jia_ge = jin_huo_jia_ge;
        this.shang_ping_lei_xing = shang_ping_lei_xing;
        this.bei_zhu_lei_xing = bei_zhu_lei_xing;
        this.chang_shang_ming_cheng = chang_shang_ming_cheng;
        this.lian_xi_fang_shi = lian_xi_fang_shi;
        this.tian_jia_ri_qi = tian_jia_ri_qi;
    }

    public String getUsernumber() {
        return usernumber;
    }

    public void setUsernumber(String usernumber) {
        this.usernumber = usernumber;
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

    public double getJin_huo_jia_ge() {
        return jin_huo_jia_ge;
    }

    public void setJin_huo_jia_ge(double jin_huo_jia_ge) {
        this.jin_huo_jia_ge = jin_huo_jia_ge;
    }

    public String getShang_ping_lei_xing() {
        return shang_ping_lei_xing;
    }

    public void setShang_ping_lei_xing(String shang_ping_lei_xing) {
        this.shang_ping_lei_xing = shang_ping_lei_xing;
    }

    public String getBei_zhu_lei_xing() {
        return bei_zhu_lei_xing;
    }

    public void setBei_zhu_lei_xing(String bei_zhu_lei_xing) {
        this.bei_zhu_lei_xing = bei_zhu_lei_xing;
    }

    public String getChang_shang_ming_cheng() {
        return chang_shang_ming_cheng;
    }

    public void setChang_shang_ming_cheng(String chang_shang_ming_cheng) {
        this.chang_shang_ming_cheng = chang_shang_ming_cheng;
    }

    public String getLian_xi_fang_shi() {
        return lian_xi_fang_shi;
    }

    public void setLian_xi_fang_shi(String lian_xi_fang_shi) {
        this.lian_xi_fang_shi = lian_xi_fang_shi;
    }

    public String getTian_jia_ri_qi() {
        return tian_jia_ri_qi;
    }

    public void setTian_jia_ri_qi(String tian_jia_ri_qi) {
        this.tian_jia_ri_qi = tian_jia_ri_qi;
    }
}
