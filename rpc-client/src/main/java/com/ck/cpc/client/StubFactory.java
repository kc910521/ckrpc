package com.ck.cpc.client;


import com.ck.cpc.conn.Transport;

/**
 * @Author caikun
 * @Description 桩工厂
 * @Date 下午5:28 21-4-2
 **/
public interface StubFactory {

    /**
     *
     * @param transport 给服务端发请求时使用的
     * @param serviceClass 创建的桩的具体类型
     * @param <T>
     * @return
     * 创建的桩
     *
     */
    <T> T createStub(Transport transport, Class<T> serviceClass);

}
