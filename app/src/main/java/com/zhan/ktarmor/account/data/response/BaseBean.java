package com.zhan.ktarmor.account.data.response;

/**
 * Created by xingyunye on
 * 2020/5/6.16:27
 */
public class BaseBean<T> {
    private int code ;
    private String msg ;
    private T body ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
