package com.tbs.trimplus.module.main.bean;

import java.util.List;

public class Home {

    private List<CarouselBean> carousel;
    private List<ArticleBean> article;
    private List<AuthorBean> author;

    public List<CarouselBean> getCarousel() {
        return carousel;
    }

    public void setCarousel(List<CarouselBean> carousel) {
        this.carousel = carousel;
    }

    public List<ArticleBean> getArticle() {
        return article;
    }

    public void setArticle(List<ArticleBean> article) {
        this.article = article;
    }

    public List<AuthorBean> getAuthor() {
        return author;
    }

    public void setAuthor(List<AuthorBean> author) {
        this.author = author;
    }

    public static class CarouselBean {
        /**
         * id : 137
         * img_url : http://cdn111.dev.tobosu.com/app_advert_other/2020-09-05/5f53090e6adf2.jpg
         * content_url : http://m.dev.tobosu.com/www_t_price_page?channel=app&subchannel=android&app_type=2
         */

        private String id;
        private String img_url;
        private String content_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getContent_url() {
            return content_url;
        }

        public void setContent_url(String content_url) {
            this.content_url = content_url;
        }
    }

    public static class ArticleBean {
        /**
         * aid : 4411
         * uid : 328409
         * title : 美式精品装修注意事项
         * image_url : https://back.tobosu.com/mt_banner/2017-06-21/594a36fe9a60e.jpg
         * view_count : 256
         * rank_num : 1
         */

        private String aid;
        private String uid;
        private String title;
        private String image_url;
        private String view_count;
        private String rank_num;

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

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }

        public String getRank_num() {
            return rank_num;
        }

        public void setRank_num(String rank_num) {
            this.rank_num = rank_num;
        }
    }

    public static class AuthorBean {
        /**
         * uid : 332487
         * view_count : 135
         * article_count : 1
         * nick : 北京元洲装饰香河
         * icon : https://back.tobosu.com/mt_company_logo/2017-06-21/594a1161ddd97.jpg
         * rank_num : 1
         */

        private String uid;
        private String view_count;
        private int article_count;
        private String nick;
        private String icon;
        private String rank_num;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }

        public int getArticle_count() {
            return article_count;
        }

        public void setArticle_count(int article_count) {
            this.article_count = article_count;
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

        public String getRank_num() {
            return rank_num;
        }

        public void setRank_num(String rank_num) {
            this.rank_num = rank_num;
        }
    }
}
