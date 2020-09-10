package com.tbs.trimplus.module.main.bean;

import java.util.List;

public class Mine {

    /**
     * uid : 23109
     * name : cctv
     * type : 3
     * icon : http://cdn111.dev.tobosu.com/app/2020-08-28/5f488a6181455.jpeg
     * view_count : 140
     * collect_count : 0
     * tup_count : 8
     * attention : [{"id":"443","aid":"328409","nick":"心香印","header_pic_url":"https://back.tobosu.com/mt_company_logo/2017-04-17/58f474bdf0f24.jpg","article_count":0}]
     * follow : [{"uid":"332487","nick":"北京元洲装饰香河","icon":"https://back.tobosu.com/mt_company_logo/2017-06-21/594a1161ddd97.jpg"},{"uid":"276904","nick":"赖小丽","icon":"https://back.tobosu.com/mt_company_logo/2017-06-14/5940a87373f27.jpg"},{"uid":"264542","nick":"天津生活家装饰","icon":"https://back.tobosu.com/mt_company_logo/2017-06-12/593e018f8b974.jpg"},{"uid":"328475","nick":"橙果装饰罗钦","icon":"https://back.tobosu.com/mt_company_logo/2017-04-21/58f9682693d68.jpg"},{"uid":"332007","nick":"王念","icon":"https://back.tobosu.com/mt_company_logo/2017-06-15/5941d38fc7d20.jpg"},{"uid":"328507","nick":"王倩","icon":"https://back.tobosu.com/mt_company_logo/2017-05-16/591a79918c54a.jpg"}]
     */

    private String uid;
    private String name;
    private String type;
    private String icon;
    private String view_count;
    private String collect_count;
    private String tup_count;
    private List<AttentionBean> attention;
    private List<FollowBean> follow;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public List<AttentionBean> getAttention() {
        return attention;
    }

    public void setAttention(List<AttentionBean> attention) {
        this.attention = attention;
    }

    public List<FollowBean> getFollow() {
        return follow;
    }

    public void setFollow(List<FollowBean> follow) {
        this.follow = follow;
    }

    public static class AttentionBean {
        /**
         * id : 443
         * aid : 328409
         * nick : 心香印
         * header_pic_url : https://back.tobosu.com/mt_company_logo/2017-04-17/58f474bdf0f24.jpg
         * article_count : 0
         */

        private String id;
        private String aid;
        private String nick;
        private String header_pic_url;
        private int article_count;

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

        public String getNick() {
            return nick;
        }

        public void setNick(String nick) {
            this.nick = nick;
        }

        public String getHeader_pic_url() {
            return header_pic_url;
        }

        public void setHeader_pic_url(String header_pic_url) {
            this.header_pic_url = header_pic_url;
        }

        public int getArticle_count() {
            return article_count;
        }

        public void setArticle_count(int article_count) {
            this.article_count = article_count;
        }
    }

    public static class FollowBean {
        /**
         * uid : 332487
         * nick : 北京元洲装饰香河
         * icon : https://back.tobosu.com/mt_company_logo/2017-06-21/594a1161ddd97.jpg
         */

        private String uid;
        private String nick;
        private String icon;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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
    }
}
