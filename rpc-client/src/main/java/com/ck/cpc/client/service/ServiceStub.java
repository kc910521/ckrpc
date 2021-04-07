package com.ck.cpc.client.service;


import com.ck.cpc.conn.Transport;

/**
 * @Author caikun
 * @Description
 * 服务桩的抽象
 *
 *
 * @Date 下午4:32 21-4-6
 **/
public interface ServiceStub {
    /**
     * 桩主要干的事就是配置和发送消息
     *
     * @param transport
     */
    void setTransport(Transport transport);
}
