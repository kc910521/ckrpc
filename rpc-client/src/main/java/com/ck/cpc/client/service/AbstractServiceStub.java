package com.ck.cpc.client.service;

import com.ck.cpc.client.RpcRequest;
import com.ck.cpc.conn.Command;
import com.ck.cpc.conn.Header;
import com.ck.cpc.conn.ResponseHeader;
import com.ck.cpc.conn.Transport;
import com.ck.cpc.utils.SerializeSupport;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author caikun
 * @Description
 * 抽象桩，目的是为了封装远程调用
 *
 * @Date 下午4:41 21-4-6
 **/
public abstract class AbstractServiceStub implements ServiceStub{

    protected Transport transport;

    /**
     * 根据客户端发送的rpcrequest请求对应服务并返回结果
     *
     * @param rpcRequest
     * @return
     */
    protected byte[] remoteInvoke(RpcRequest rpcRequest) {
        // 构造command 请求
        Header rqHeader = new Header(UUID.randomUUID().toString().hashCode(), 1,0);
        byte [] payload = SerializeSupport.serialize(rpcRequest);
        Command command = new Command(rqHeader, payload);

        // 发送请求
        try {
            Command result = transport.send(command).get(20000L, TimeUnit.MILLISECONDS);
            ResponseHeader rpHeader = (ResponseHeader) result.getHeader();
            if (rpHeader.getCode() == 0) {
                return result.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        throw new RuntimeException("exception");
    }


    @Override
    public void setTransport(Transport transport) {
        this.transport = transport;
    }
}
