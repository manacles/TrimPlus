package com.tbs.trimplus.module.bible.bean;

import java.util.List;

public class Author {

    /**
     * author : {"nick":"灌灌灌灌灌过","icon":"https://back.tobosu.com/mt_company_logo/2017-03-17/58cbac72b34f9.jpg","is_follow":0,"article_count":3}
     * article : [{"aid":"840","uid":"23109","title":"5万元装出118平时尚美家，个性书柜让人眼前一亮~","image_url":"http://back.tobosu.com/mt_banner/2017-01-09/5872f89514e34.jpg","type_id":"1","view_count":"87","type_name":"设计","time":3,"time2":"年前"},{"aid":"802","uid":"23109","title":"130平高端大气的现代简约三居室，7w硬装超有质感","image_url":"http://back.tobosu.com/mt_banner/2017-01-07/58704d400c05a.jpg","type_id":"2","view_count":"115","type_name":"家装","time":3,"time2":"年前"},{"aid":"801","uid":"23109","title":"58㎡装成这样，我家门槛快被邻居踏平了！","image_url":"http://back.tobosu.com/mt_banner/2017-01-07/58704cfe55e20.jpg","type_id":"1","view_count":"90","type_name":"设计","time":3,"time2":"年前"}]
     */

    private AuthorBean author;
    private List<ArticleBean> article;

    public AuthorBean getAuthor() {
        return author;
    }

    public void setAuthor(AuthorBean author) {
        this.author = author;
    }

    public List<ArticleBean> getArticle() {
        return article;
    }

    public void setArticle(List<ArticleBean> article) {
        this.article = article;
    }

    public static class AuthorBean {
        /**
         * nick : 灌灌灌灌灌过
         * icon : https://back.tobosu.com/mt_company_logo/2017-03-17/58cbac72b34f9.jpg
         * is_follow : 0
         * article_count : 3
         */

        private String nick;
        private String icon;
        private String is_follow;
        private String article_count;

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

        public String getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }

        public String getArticle_count() {
            return article_count;
        }

        public void setArticle_count(String article_count) {
            this.article_count = article_count;
        }
    }

    public static class ArticleBean {
        /**
         * aid : 840
         * uid : 23109
         * title : 5万元装出118平时尚美家，个性书柜让人眼前一亮~
         * image_url : http://back.tobosu.com/mt_banner/2017-01-09/5872f89514e34.jpg
         * type_id : 1
         * view_count : 87
         * type_name : 设计
         * time : 3
         * time2 : 年前
         */

        private String aid;
        private String uid;
        private String title;
        private String image_url;
        private String type_id;
        private String view_count;
        private String type_name;
        private String time;
        private String time2;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime2() {
            return time2;
        }

        public void setTime2(String time2) {
            this.time2 = time2;
        }
    }
}
