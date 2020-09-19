package com.tbs.trimplus.module.main.bean;

import java.util.List;

public class Bible {

    /**
     * 宝典的列表数据 实体类
     *
     * aid : 4396
     * title : 白色家具保养小妙招，赶快来看看吧
     * view_count : 46
     * collect_count : 0
     * tup_count : 0
     * image_url : https://back.tobosu.com/mt_banner/2017-06-21/594a0c9f47fcb.jpg
     * img_url : ["https://back.tobosu.com/mt_content/2017-06-21/p_594a0c43c5fed.jpg","https://back.tobosu.com/mt_content/2017-06-21/p_594a0c5692762.jpg","https://back.tobosu.com/mt_content/2017-06-21/p_594a0c64ace2c.jpg","https://back.tobosu.com/mt_content/2017-06-21/p_594a0c8472d44.jpg","https://back.tobosu.com/mt_content/2017-06-21/p_594a0c97760a3.jpg"]
     * style : 1
     * author_name : 夏木天泽
     * author_id : 330182
     * time : 2017-06-21 14:08:35
     */

    private String aid;
    private String title;
    private String view_count;
    private String collect_count;
    private String tup_count;
    private String image_url;
    private String style;
    private String author_name;
    private String author_id;
    private String time;
    private List<String> img_url;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(String collect_count) {
        this.collect_count = collect_count;
    }

    public String getTup_count() {
        return tup_count;
    }

    public void setTup_count(String tup_count) {
        this.tup_count = tup_count;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getImg_url() {
        return img_url;
    }

    public void setImg_url(List<String> img_url) {
        this.img_url = img_url;
    }
}
