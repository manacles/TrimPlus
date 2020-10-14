package com.tbs.trimplus.module.user.bean;

import java.util.List;

public class Message {

    /**
     * error_code : 0
     * msg : 操作成功！
     * data : {"count":"4","page":1,"pages":1,"data":[{"aid":"4404","title":"衣帽间门效果图，一扇好门两处欢喜","type_id":"3","time_create":"1498028875","uid":"330186","description":"衣帽间到底适合什么样的门呢？看看下面的衣帽间门效果图，你就有答案了。\u200b","time_rec":{"time":1,"time_unit":"月前"},"nick":"走走停停","header_pic_url":"https://back.tobosu.com/mt_company_logo/2017-05-15/591932940dc45.jpg"},{"aid":"4406","title":"美式装修设计说明，装修师傅看完都说好！","type_id":"1","time_create":"1498030448","uid":"330186","description":"美式装修设计总是透着一股随意、大气，给人一种自由自在的感觉，也因此成为了许多小伙伴的最佳选择。今天带来一个美式装修案例，为大家做一个美式装修设计说明。\u200b","time_rec":{"time":1,"time_unit":"月前"},"nick":"走走停停","header_pic_url":"https://back.tobosu.com/mt_company_logo/2017-05-15/591932940dc45.jpg"}]}
     */

    private String error_code;
    private String msg;
    private DataBeanX data;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * count : 4
         * page : 1
         * pages : 1
         * data : [{"aid":"4404","title":"衣帽间门效果图，一扇好门两处欢喜","type_id":"3","time_create":"1498028875","uid":"330186","description":"衣帽间到底适合什么样的门呢？看看下面的衣帽间门效果图，你就有答案了。\u200b","time_rec":{"time":1,"time_unit":"月前"},"nick":"走走停停","header_pic_url":"https://back.tobosu.com/mt_company_logo/2017-05-15/591932940dc45.jpg"},{"aid":"4406","title":"美式装修设计说明，装修师傅看完都说好！","type_id":"1","time_create":"1498030448","uid":"330186","description":"美式装修设计总是透着一股随意、大气，给人一种自由自在的感觉，也因此成为了许多小伙伴的最佳选择。今天带来一个美式装修案例，为大家做一个美式装修设计说明。\u200b","time_rec":{"time":1,"time_unit":"月前"},"nick":"走走停停","header_pic_url":"https://back.tobosu.com/mt_company_logo/2017-05-15/591932940dc45.jpg"}]
         */

        private String count;
        private String page;
        private String pages;
        private List<DataBean> data;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getPages() {
            return pages;
        }

        public void setPages(String pages) {
            this.pages = pages;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * aid : 4404
             * title : 衣帽间门效果图，一扇好门两处欢喜
             * type_id : 3
             * time_create : 1498028875
             * uid : 330186
             * description : 衣帽间到底适合什么样的门呢？看看下面的衣帽间门效果图，你就有答案了。​
             * time_rec : {"time":1,"time_unit":"月前"}
             * nick : 走走停停
             * header_pic_url : https://back.tobosu.com/mt_company_logo/2017-05-15/591932940dc45.jpg
             */

            private String aid;
            private String title;
            private String type_id;
            private String time_create;
            private String uid;
            private String description;
            private TimeRecBean time_rec;
            private String nick;
            private String header_pic_url;

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

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getTime_create() {
                return time_create;
            }

            public void setTime_create(String time_create) {
                this.time_create = time_create;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public TimeRecBean getTime_rec() {
                return time_rec;
            }

            public void setTime_rec(TimeRecBean time_rec) {
                this.time_rec = time_rec;
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

            public static class TimeRecBean {
                /**
                 * time : 1
                 * time_unit : 月前
                 */

                private String time;
                private String time_unit;

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getTime_unit() {
                    return time_unit;
                }

                public void setTime_unit(String time_unit) {
                    this.time_unit = time_unit;
                }
            }
        }
    }
}
