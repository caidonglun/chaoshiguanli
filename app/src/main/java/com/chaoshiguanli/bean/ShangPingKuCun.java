package com.chaoshiguanli.bean;

/**
 * Created by Administrator on 2018/6/8.
 */

public class ShangPingKuCun {
    private String usernumber;
    private String shang_ping_tiao_ma;
    private String shang_ping_ming_cheng;
    private Integer shang_ping_shu_liang;
    private String shang_ping_lei_xing;
    private double chu_shou_jia_ge;
    private String sheng_can_ri_qi;
    private String guo_qi_ri_qi;
    private String bei_zhu_xing_xi;
    private boolean shi_fou_yi_guo_qi;

    @Override
    public String toString() {
        return "ShangPingKuCun{" +
                "usernumber='" + usernumber + '\'' +
                ", shang_ping_tiao_ma='" + shang_ping_tiao_ma + '\'' +
                ", shang_ping_ming_cheng='" + shang_ping_ming_cheng + '\'' +
                ", shang_ping_shu_liang=" + shang_ping_shu_liang +
                ", shang_ping_lei_xing='" + shang_ping_lei_xing + '\'' +
                ", chu_shou_jia_ge=" + chu_shou_jia_ge +
                ", sheng_can_ri_qi='" + sheng_can_ri_qi + '\'' +
                ", guo_qi_ri_qi='" + guo_qi_ri_qi + '\'' +
                ", bei_zhu_xing_xi='" + bei_zhu_xing_xi + '\'' +
                ", shi_fou_yi_guo_qi=" + shi_fou_yi_guo_qi +
                '}';
    }

    public ShangPingKuCun(String usernumber, String shang_ping_tiao_ma, String shang_ping_ming_cheng, Integer shang_ping_shu_liang, String shang_ping_lei_xing, double chu_shou_jia_ge, String sheng_can_ri_qi, String guo_qi_ri_qi, String bei_zhu_xing_xi, boolean shi_fou_yi_guo_qi) {
        this.usernumber = usernumber;
        this.shang_ping_tiao_ma = shang_ping_tiao_ma;
        this.shang_ping_ming_cheng = shang_ping_ming_cheng;
        this.shang_ping_shu_liang = shang_ping_shu_liang;
        this.shang_ping_lei_xing = shang_ping_lei_xing;
        this.chu_shou_jia_ge = chu_shou_jia_ge;
        this.sheng_can_ri_qi = sheng_can_ri_qi;
        this.guo_qi_ri_qi = guo_qi_ri_qi;
        this.bei_zhu_xing_xi = bei_zhu_xing_xi;
        this.shi_fou_yi_guo_qi = shi_fou_yi_guo_qi;
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

    public Integer getShang_ping_shu_liang() {
        return shang_ping_shu_liang;
    }

    public void setShang_ping_shu_liang(Integer shang_ping_shu_liang) {
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

    public String getSheng_can_ri_qi() {
        return sheng_can_ri_qi;
    }

    public void setSheng_can_ri_qi(String sheng_can_ri_qi) {
        this.sheng_can_ri_qi = sheng_can_ri_qi;
    }

    public String getGuo_qi_ri_qi() {
        return guo_qi_ri_qi;
    }

    public void setGuo_qi_ri_qi(String guo_qi_ri_qi) {
        this.guo_qi_ri_qi = guo_qi_ri_qi;
    }

    public String getBei_zhu_xing_xi() {
        return bei_zhu_xing_xi;
    }

    public void setBei_zhu_xing_xi(String bei_zhu_xing_xi) {
        this.bei_zhu_xing_xi = bei_zhu_xing_xi;
    }

    public boolean getShi_fou_yi_guo_qi() {
        return shi_fou_yi_guo_qi;
    }

    public void setShi_fou_yi_guo_qi(boolean shi_fou_yi_guo_qi) {
        this.shi_fou_yi_guo_qi = shi_fou_yi_guo_qi;
    }
}
