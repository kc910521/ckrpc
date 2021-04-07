package com.ck.cpc.conn;

/**
 * @Author caikun
 * @Description
 * 响应头
 *
 * @Date 下午2:31 21-4-2
 **/
public class ResponseHeader extends Header {

    private int code;

    private String msg;

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
}
