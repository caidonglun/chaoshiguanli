package com.chaoshiguanli.bean;

/**
 * Created by Administrator on 2018/6/15.
 */

public class XiuGaiJiLu {

    private String usernumber;
    private String shang_ping_tiao_ma;
    private String Shang_ping_ming_cheng;
    private String gai_qian_xing_xi;
    private String gai_hou_xing_xi;
    private String xiu_gai_shi_jian;

    public XiuGaiJiLu() {
    }

    public XiuGaiJiLu(String usernumber, String shang_ping_tiao_ma, String shang_ping_ming_cheng, String gai_qian_xing_xi, String gai_hou_xing_xi, String xiu_gai_shi_jian) {
        this.usernumber = usernumber;
        this.shang_ping_tiao_ma = shang_ping_tiao_ma;
        Shang_ping_ming_cheng = shang_ping_ming_cheng;
        this.gai_qian_xing_xi = gai_qian_xing_xi;
        this.gai_hou_xing_xi = gai_hou_xing_xi;
        this.xiu_gai_shi_jian = xiu_gai_shi_jian;
    }

    @Override
    public String toString() {
        return "XiuGaiJiLu{" +
                "usernumber='" + usernumber + '\'' +
                ", shang_ping_tiao_ma='" + shang_ping_tiao_ma + '\'' +
                ", Shang_ping_ming_cheng='" + Shang_ping_ming_cheng + '\'' +
                ", gai_qian_xing_xi='" + gai_qian_xing_xi + '\'' +
                ", gai_hou_xing_xi='" + gai_hou_xing_xi + '\'' +
                ", xiu_gai_shi_jian='" + xiu_gai_shi_jian + '\'' +
                '}';
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
        return Shang_ping_ming_cheng;
    }

    public void setShang_ping_ming_cheng(String shang_ping_ming_cheng) {
        Shang_ping_ming_cheng = shang_ping_ming_cheng;
    }

    public String getGai_qian_xing_xi() {
        return gai_qian_xing_xi;
    }

    public void setGai_qian_xing_xi(String gai_qian_xing_xi) {
        this.gai_qian_xing_xi = gai_qian_xing_xi;
    }

    public String getGai_hou_xing_xi() {
        return gai_hou_xing_xi;
    }

    public void setGai_hou_xing_xi(String gai_hou_xing_xi) {
        this.gai_hou_xing_xi = gai_hou_xing_xi;
    }

    public String getXiu_gai_shi_jian() {
        return xiu_gai_shi_jian;
    }

    public void setXiu_gai_shi_jian(String xiu_gai_shi_jian) {
        this.xiu_gai_shi_jian = xiu_gai_shi_jian;
    }
}
