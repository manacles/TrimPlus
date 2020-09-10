package com.tbs.trimplus.module.login.bean;

public class User {

    /**
     * error_code : 0
     * msg : 用户登录成功!
     * data : {"id":"333663","mark":"1","name":"tbs_5f55b0132fc20","realname":"","cityid":"88","icon":"https://front.tobosu.com/res/common/images/icon/icon27.jpg","mobile":"18158178155","newuid":"370235","nickname":"tbs_5f55b0132fc20","cityname":"宁波市","province":"浙江省","uid":"370235","cellphone":"18158178155","type_id":"1","is_modify_channel":"","expected_cost":"0","login_time":"2020-09-08 15:37:20","token":"rUUhxJgkns0jUxRxIqxrjeLXaRC9KMU3XhucEiMoVVR2frdmwPQeHslgrQLCDIVsPlLSN6KjVN3Lf76VgXLbBaZtkaG08ujsHZKGf/r/zeDgo+XcDeu6K/R7nx0EHjfVu4EI9EbNtAwBtVuEXV5wo/VB1ZOcVJMsJ/+1nzWFK+qu/EqKmxssm6PTl3uw+MCqldgwAcmsMv2M+6yErmBIyKe3s4wAOp1hUmJf5PVxC/Z1El7BWpxdmkLHsF9JsonOtlwAjAL1L9FhQvJ0lbOcI0CbXVczbZy5ubsBOl7zgi0+Hi8znrXNIpOc7l10HawQOkkHEZe3yGfcu8PzjP6azVPMqo41xz2J6Wz8boH1/easw6PbRLUEXmfR37vrrYwkvwBnByZIa/WbLDcKIcvMGlGvwMoWBNoXkur8eQWmPQZBkwF8PTB/YVVPhHGT/UA3iMto4rqX8GmArrt23smSQMiTm7ECznA3cpCKFnSz99I5i2rkZ4gpubOQlbGc+lfDIZ8WInZhzP82QyNCgUMHl141en6TuMxp1D5YaEggnyBLfMIFYm/n7NN23fHGsZoUT0KV2o80Kf1HSy9kx3fsEncGCBgEe6h0H/jqsFzoUkXZWN2a6H0PezK22OkXAXQ5CHj05hfpkUh0E9YexyO3nv0LqAoEF9mwuru/2w72UVyN/OS2s1IM+pTl5iNCn7KYTv/UcWEj5E8z2XU3LCPVLQFLJfQ/fYx17Y3TlBpXnDVk1zivY1zZV2MmlBrYh/C5/STGVP6SpCxVzU3VPhI1qdAq7RQPmQzmH8wKRAubikk93KhNjtzCjQE4xsIe/8WoKjcVyMi6rFMvL/vG+9vB5yF0NByJVUIG7mJvqY4Onpv7rdX4vlMKcxygiOTZ+6JDzfV9bgLDuXVodVaNbHrCFTv0RJdWqbgrZ8GnmM7sy/LeY9IHhTX7yL25vEsVdFFep0RE3TJumjazxxGPnhs7zgj021R94k8DrHGv1SOw98OChJ5sF8BR1Y0NgQGXbCDMG0mFwboY5xpX0hPNqt0Mhc00OELF3BXYSumUjQWR9Q4M13+Ei3cd/FSln50rq5o6R6Zon4zFlhrLAc39HcN2la4v8cv4wNZOP7aso1PENbwPK4TCsyZFMlNYtj/GyH7wvhBuYLzJfU1i7ygY5SPT8IzGcmKABEBmkPZyzAWlL55FhSwOv0I5oA5RWke61N4Z+tsx5pdN8qYOHUp++HLqjLTMWgTDUpTWjKV3II5XwjcrKNOns2IkRjl98TFkLdbweC0COd8k0FHZyEJvgesbzBuUo6eivftBveKlPwuLQ+nfmopwARTN5bzo4zNnkypuO9MZKVQ5ACyJj6dJykdFfQ=="}
     */

    private String error_code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 333663
         * mark : 1
         * name : tbs_5f55b0132fc20
         * realname :
         * cityid : 88
         * icon : https://front.tobosu.com/res/common/images/icon/icon27.jpg
         * mobile : 18158178155
         * newuid : 370235
         * nickname : tbs_5f55b0132fc20
         * cityname : 宁波市
         * province : 浙江省
         * uid : 370235
         * cellphone : 18158178155
         * type_id : 1
         * is_modify_channel :
         * expected_cost : 0
         * login_time : 2020-09-08 15:37:20
         * token : rUUhxJgkns0jUxRxIqxrjeLXaRC9KMU3XhucEiMoVVR2frdmwPQeHslgrQLCDIVsPlLSN6KjVN3Lf76VgXLbBaZtkaG08ujsHZKGf/r/zeDgo+XcDeu6K/R7nx0EHjfVu4EI9EbNtAwBtVuEXV5wo/VB1ZOcVJMsJ/+1nzWFK+qu/EqKmxssm6PTl3uw+MCqldgwAcmsMv2M+6yErmBIyKe3s4wAOp1hUmJf5PVxC/Z1El7BWpxdmkLHsF9JsonOtlwAjAL1L9FhQvJ0lbOcI0CbXVczbZy5ubsBOl7zgi0+Hi8znrXNIpOc7l10HawQOkkHEZe3yGfcu8PzjP6azVPMqo41xz2J6Wz8boH1/easw6PbRLUEXmfR37vrrYwkvwBnByZIa/WbLDcKIcvMGlGvwMoWBNoXkur8eQWmPQZBkwF8PTB/YVVPhHGT/UA3iMto4rqX8GmArrt23smSQMiTm7ECznA3cpCKFnSz99I5i2rkZ4gpubOQlbGc+lfDIZ8WInZhzP82QyNCgUMHl141en6TuMxp1D5YaEggnyBLfMIFYm/n7NN23fHGsZoUT0KV2o80Kf1HSy9kx3fsEncGCBgEe6h0H/jqsFzoUkXZWN2a6H0PezK22OkXAXQ5CHj05hfpkUh0E9YexyO3nv0LqAoEF9mwuru/2w72UVyN/OS2s1IM+pTl5iNCn7KYTv/UcWEj5E8z2XU3LCPVLQFLJfQ/fYx17Y3TlBpXnDVk1zivY1zZV2MmlBrYh/C5/STGVP6SpCxVzU3VPhI1qdAq7RQPmQzmH8wKRAubikk93KhNjtzCjQE4xsIe/8WoKjcVyMi6rFMvL/vG+9vB5yF0NByJVUIG7mJvqY4Onpv7rdX4vlMKcxygiOTZ+6JDzfV9bgLDuXVodVaNbHrCFTv0RJdWqbgrZ8GnmM7sy/LeY9IHhTX7yL25vEsVdFFep0RE3TJumjazxxGPnhs7zgj021R94k8DrHGv1SOw98OChJ5sF8BR1Y0NgQGXbCDMG0mFwboY5xpX0hPNqt0Mhc00OELF3BXYSumUjQWR9Q4M13+Ei3cd/FSln50rq5o6R6Zon4zFlhrLAc39HcN2la4v8cv4wNZOP7aso1PENbwPK4TCsyZFMlNYtj/GyH7wvhBuYLzJfU1i7ygY5SPT8IzGcmKABEBmkPZyzAWlL55FhSwOv0I5oA5RWke61N4Z+tsx5pdN8qYOHUp++HLqjLTMWgTDUpTWjKV3II5XwjcrKNOns2IkRjl98TFkLdbweC0COd8k0FHZyEJvgesbzBuUo6eivftBveKlPwuLQ+nfmopwARTN5bzo4zNnkypuO9MZKVQ5ACyJj6dJykdFfQ==
         */

        private String id;
        private String mark;
        private String name;
        private String realname;
        private String cityid;
        private String icon;
        private String mobile;
        private String newuid;
        private String nickname;
        private String cityname;
        private String province;
        private String uid;
        private String cellphone;
        private String type_id;
        private String is_modify_channel;
        private String expected_cost;
        private String login_time;
        private String token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNewuid() {
            return newuid;
        }

        public void setNewuid(String newuid) {
            this.newuid = newuid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getIs_modify_channel() {
            return is_modify_channel;
        }

        public void setIs_modify_channel(String is_modify_channel) {
            this.is_modify_channel = is_modify_channel;
        }

        public String getExpected_cost() {
            return expected_cost;
        }

        public void setExpected_cost(String expected_cost) {
            this.expected_cost = expected_cost;
        }

        public String getLogin_time() {
            return login_time;
        }

        public void setLogin_time(String login_time) {
            this.login_time = login_time;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
