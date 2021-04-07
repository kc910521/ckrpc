package com.ck.cpc.conn;

/**
 * @Author caikun
 * @Description
 * 请求头+
 *
 * @Date 下午2:30 21-4-2
 **/
public class Command {

    protected Header header;

    /**
     * 要传输的数据
     */
    private byte[] body;

    public Command(Header header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }


    @Override
    public int hashCode() {
        return header.hashCode();
    }

    @Override
    public String toString() {
        return header.toString();
    }
}
