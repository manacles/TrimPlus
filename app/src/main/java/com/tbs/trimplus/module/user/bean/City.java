package com.tbs.trimplus.module.user.bean;

public class City {

    /**
     * cityid : 324
     * simpname : 中卫
     * hot_flag : 0
     * isopen : 1
     * pinyin_sort : z
     */

    private String cityid;
    private String simpname;
    private String hot_flag;
    private String isopen;
    private String pinyin_sort;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getSimpname() {
        return simpname;
    }

    public void setSimpname(String simpname) {
        this.simpname = simpname;
    }

    public String getHot_flag() {
        return hot_flag;
    }

    public void setHot_flag(String hot_flag) {
        this.hot_flag = hot_flag;
    }

    public String getIsopen() {
        return isopen;
    }

    public void setIsopen(String isopen) {
        this.isopen = isopen;
    }

    public String getPinyin_sort() {
        return pinyin_sort;
    }

    public void setPinyin_sort(String pinyin_sort) {
        this.pinyin_sort = pinyin_sort;
    }

    @Override
    public String toString() {
        return "{" +
                "cityid='" + cityid + '\'' +
                ", simpname='" + simpname + '\'' +
                ", hot_flag='" + hot_flag + '\'' +
                ", isopen='" + isopen + '\'' +
                ", pinyin_sort='" + pinyin_sort + '\'' +
                '}';
    }
}
