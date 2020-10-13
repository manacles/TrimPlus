package com.tbs.trimplus.module.user.bean;

public class Like {
    /**
     *     点赞数据实体类
     * id : 2116
     * aid : 4406
     * author_id : 330186
     * nick : 走走停停
     * icon : https://back.tobosu.com/mt_company_logo/2017-05-15/591932940dc45.jpg
     * title : 美式装修设计说明，装修师傅看完都说好！
     * image_url : https://back.tobosu.com/mt_banner/2017-06-21/594a21564304f.jpg
     * tup_count : 12
     */

    private String id;
    private String aid;
    private String author_id;
    private String nick;
    private String icon;
    private String title;
    private String image_url;
    private String tup_count;
    private boolean isChecked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTup_count() {
        return tup_count;
    }

    public void setTup_count(String tup_count) {
        this.tup_count = tup_count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
