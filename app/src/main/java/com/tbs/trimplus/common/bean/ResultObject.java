package com.tbs.trimplus.common.bean;

public class ResultObject {

    /**
     * error_code : 0
     * msg : 操作成功！
     */

    private int error_code;
    private String msg;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
