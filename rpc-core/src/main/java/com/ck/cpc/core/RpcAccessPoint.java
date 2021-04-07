package com.ck.cpc.core;

import java.io.Closeable;
import java.net.URI;

/**
 * @Author caikun
 * @Description RPC对外提供服务接口,接入点
 *
 * 客户端调用这个服务、
 * 服务端注册服务
 *
 * 维护URI和class
 *
 *
 * @Date 下午6:43 21-3-31
 **/
public interface RpcAccessPoint extends Closeable {

    /**
     * 供客户端使用，获取服务的实现（引用）
     * 类似dubbo的@reference
     *
     * @param uri
     * @param serviceClass 服务的接口类
     * @param <T> 服务接口类型
     * @return
     */
    <T> T getRemoteService(URI uri, Class<T> serviceClass);

    /**
     * 服务端使用，注册服务
     * 类似Dubbo @service
     * @param service 实现实例
     * @param serviceClass 服务的接口类
     * @param <T> 服务接口的类型
     * @return 服务地址
     */
    <T> URI register(T service, Class<T> serviceClass);


    /**
     * 服务端启动
     * @return
     * @throws Exception
     */
    Closeable startServer() throws Exception;


    /**
     * 获取注册中心rpc-center
     * @param nameServiceUri
     * @return
     */
    NameService getNameService(URI nameServiceUri);

}
