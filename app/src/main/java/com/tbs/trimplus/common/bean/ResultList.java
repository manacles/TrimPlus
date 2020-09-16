package com.tbs.trimplus.common.bean;

import java.util.ArrayList;

public class ResultList<T> {

    /**
     * error_code : 0成功
     *
     */

    private String error_code;
    private String msg;
    private ArrayList<T> data;

    public ResultList() {
    }

    public ResultList(String error_code, String msg, ArrayList<T> data) {
        this.error_code = error_code;
        this.msg = msg;
        this.data = data;
    }

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

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
