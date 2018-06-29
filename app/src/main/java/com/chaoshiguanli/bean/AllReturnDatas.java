package com.chaoshiguanli.bean;

import java.util.List;

public class AllReturnDatas {
    private List<DanHaoJiLu> danHaoJiLus;
    private List<JinHuoJiLu> jinHuoJiLus;
    private List<ShangPingKuCun> shangPingKuCuns;
    private List<ShangPingLeiXing> shangPingLeiXings;
    private List<ShangPingXingXi> shangPingXingXis;
    private List<XiaoShouJiLu> xiaoShouJiLus;
    private List<XiuGaiJiLu> xiuGaiJiLus;

    @Override
    public String toString() {
        return "AllReturnDatas{" +
                "danHaoJiLus=" + danHaoJiLus +
                ", jinHuoJiLus=" + jinHuoJiLus +
                ", shangPingKuCuns=" + shangPingKuCuns +
                ", shangPingLeiXings=" + shangPingLeiXings +
                ", shangPingXingXis=" + shangPingXingXis +
                ", xiaoShouJiLus=" + xiaoShouJiLus +
                ", xiuGaiJiLus=" + xiuGaiJiLus +
                '}';
    }

    public AllReturnDatas() {
    }

    public AllReturnDatas(List<DanHaoJiLu> danHaoJiLus, List<JinHuoJiLu> jinHuoJiLus, List<ShangPingKuCun> shangPingKuCuns, List<ShangPingLeiXing> shangPingLeiXings, List<ShangPingXingXi> shangPingXingXis, List<XiaoShouJiLu> xiaoShouJiLus, List<XiuGaiJiLu> xiuGaiJiLus) {
        this.danHaoJiLus = danHaoJiLus;
        this.jinHuoJiLus = jinHuoJiLus;
        this.shangPingKuCuns = shangPingKuCuns;
        this.shangPingLeiXings = shangPingLeiXings;
        this.shangPingXingXis = shangPingXingXis;
        this.xiaoShouJiLus = xiaoShouJiLus;
        this.xiuGaiJiLus = xiuGaiJiLus;
    }

    public List<DanHaoJiLu> getDanHaoJiLus() {
        return danHaoJiLus;
    }

    public void setDanHaoJiLus(List<DanHaoJiLu> danHaoJiLus) {
        this.danHaoJiLus = danHaoJiLus;
    }

    public List<JinHuoJiLu> getJinHuoJiLus() {
        return jinHuoJiLus;
    }

    public void setJinHuoJiLus(List<JinHuoJiLu> jinHuoJiLus) {
        this.jinHuoJiLus = jinHuoJiLus;
    }

    public List<ShangPingKuCun> getShangPingKuCuns() {
        return shangPingKuCuns;
    }

    public void setShangPingKuCuns(List<ShangPingKuCun> shangPingKuCuns) {
        this.shangPingKuCuns = shangPingKuCuns;
    }

    public List<ShangPingLeiXing> getShangPingLeiXings() {
        return shangPingLeiXings;
    }

    public void setShangPingLeiXings(List<ShangPingLeiXing> shangPingLeiXings) {
        this.shangPingLeiXings = shangPingLeiXings;
    }

    public List<ShangPingXingXi> getShangPingXingXis() {
        return shangPingXingXis;
    }

    public void setShangPingXingXis(List<ShangPingXingXi> shangPingXingXis) {
        this.shangPingXingXis = shangPingXingXis;
    }

    public List<XiaoShouJiLu> getXiaoShouJiLus() {
        return xiaoShouJiLus;
    }

    public void setXiaoShouJiLus(List<XiaoShouJiLu> xiaoShouJiLus) {
        this.xiaoShouJiLus = xiaoShouJiLus;
    }

    public List<XiuGaiJiLu> getXiuGaiJiLus() {
        return xiuGaiJiLus;
    }

    public void setXiuGaiJiLus(List<XiuGaiJiLu> xiuGaiJiLus) {
        this.xiuGaiJiLus = xiuGaiJiLus;
    }
}
