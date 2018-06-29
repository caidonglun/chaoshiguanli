package com.chaoshiguanli.bean;

/**
 * Created by Administrator on 2018/6/19.
 */

public class DanHaoJiLu {
    private String yong_hu_bian_hao;
    private Double ben_dan_zong_jia_zhi;
    private String xiao_shou_dan_hao;
    private String chu_shou_ri_qi;

    @Override
    public String toString() {
        return "DanHaoJiLu{" +
                "yong_hu_bian_hao='" + yong_hu_bian_hao + '\'' +
                ", ben_dan_zong_jia_zhi=" + ben_dan_zong_jia_zhi +
                ", xiao_shou_dan_hao='" + xiao_shou_dan_hao + '\'' +
                ", chu_shou_ri_qi='" + chu_shou_ri_qi + '\'' +
                '}';
    }

    public DanHaoJiLu() {
    }

    public DanHaoJiLu(String yong_hu_bian_hao, Double ben_dan_zong_jia_zhi, String xiao_shou_dan_hao, String chu_shou_ri_qi) {
        this.yong_hu_bian_hao = yong_hu_bian_hao;
        this.ben_dan_zong_jia_zhi = ben_dan_zong_jia_zhi;
        this.xiao_shou_dan_hao = xiao_shou_dan_hao;
        this.chu_shou_ri_qi = chu_shou_ri_qi;
    }

    public String getYong_hu_bian_hao() {
        return yong_hu_bian_hao;
    }

    public void setYong_hu_bian_hao(String yong_hu_bian_hao) {
        this.yong_hu_bian_hao = yong_hu_bian_hao;
    }

    public Double getBen_dan_zong_jia_zhi() {
        return ben_dan_zong_jia_zhi;
    }

    public void setBen_dan_zong_jia_zhi(Double ben_dan_zong_jia_zhi) {
        this.ben_dan_zong_jia_zhi = ben_dan_zong_jia_zhi;
    }

    public String getXiao_shou_dan_hao() {
        return xiao_shou_dan_hao;
    }

    public void setXiao_shou_dan_hao(String xiao_shou_dan_hao) {
        this.xiao_shou_dan_hao = xiao_shou_dan_hao;
    }

    public String getChu_shou_ri_qi() {
        return chu_shou_ri_qi;
    }

    public void setChu_shou_ri_qi(String chu_shou_ri_qi) {
        this.chu_shou_ri_qi = chu_shou_ri_qi;
    }
}
