package com.chaoshiguanli.bean;

/**
 * Created by Administrator on 2018/6/15.
 */

public class ShangPingLeiXing {

    private String yong_hu_id;
    private String lei_xing_ming_cheng;
    private String tian_jia_ri_qi;

    @Override
    public String toString() {
        return "ShangPingLeiXing{" +
                "yong_hu_id='" + yong_hu_id + '\'' +
                ", lei_xing_ming_cheng='" + lei_xing_ming_cheng + '\'' +
                ", tian_jia_ri_qi='" + tian_jia_ri_qi + '\'' +
                '}';
    }

    public ShangPingLeiXing() {
    }

    public ShangPingLeiXing(String yong_hu_id, String lei_xing_ming_cheng, String tian_jia_ri_qi) {
        this.yong_hu_id = yong_hu_id;
        this.lei_xing_ming_cheng = lei_xing_ming_cheng;
        this.tian_jia_ri_qi = tian_jia_ri_qi;
    }

    public String getYong_hu_id() {
        return yong_hu_id;
    }

    public void setYong_hu_id(String yong_hu_id) {
        this.yong_hu_id = yong_hu_id;
    }

    public String getLei_xing_ming_cheng() {
        return lei_xing_ming_cheng;
    }

    public void setLei_xing_ming_cheng(String lei_xing_ming_cheng) {
        this.lei_xing_ming_cheng = lei_xing_ming_cheng;
    }

    public String getTian_jia_ri_qi() {
        return tian_jia_ri_qi;
    }

    public void setTian_jia_ri_qi(String tian_jia_ri_qi) {
        this.tian_jia_ri_qi = tian_jia_ri_qi;
    }
}
