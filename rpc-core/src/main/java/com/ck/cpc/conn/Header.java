package com.ck.cpc.conn;

/**
 * @Author caikun
 * @Description
 * 报文头
 *
 * @Date 下午2:30 21-4-2
 **/
public class Header {

    /**
     * 1. 标识唯一请求
     * 2. 双工
     */
    private int requestId;

    /**
     * 一个请求的版本号，因为我们无法保证协议永不变
     * 所以定义了一个向下兼容的方式
     */
    private int version;

    /**
     * main
     * 让接收方识别收到的是什么命令
     * 以便路由到对应类中
     *
     */
    private int type;

    public Header() {
    }

    public Header(int requestId, int version, int type) {
        this.requestId = requestId;
        this.version = version;
        this.type = type;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return (requestId + "," + version + "," + type).hashCode();
    }

    @Override
    public String toString() {
        return requestId + "," + version + "," + type;
    }
}
