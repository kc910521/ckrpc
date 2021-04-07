package com.ck.cpc.core;

import java.net.URI;
import java.util.Collection;

/**
 * @Author caikun
 * @Description
 * 注册中心(rpc-center)
 * 注册服务
 * 内部用返回服务地址
 *
 * 维护名称对应URI
 *
 * 一个完整的注册中心也是分为客户端和服务端两部分的，
 * 客户端给调用方提供 API，并实现与服务端的通信；
 * 服务端提供真正的业务功能，记录每个 RPC 服务发来的注册信息，并保存到它的元数据中。
 * 当有客户端来查询服务地址的时候，它会从元数据中获取服务地址，返回给客户端。
 *
 *
 * @Date 下午6:46 21-3-31
 **/
public interface NameService {


    /**
     * 获取注册中心所有支持的协议
     *
     *
     * @return
     * eg：file 通过客户端本地文件定位访问目标服务
     *
     */
    Collection<String> supportedSchemes();

    /**
     * 连接指定注册中心
     * @param nameServiceUri 注册中心地址
     */
    void connect(URI nameServiceUri);
    /**
     * 注册指定服务名-uri映射
     * @param serviceName
     * @param uri
     */
    void registerService(String serviceName, URI uri);

    /**
     * 根据服务名获取URI
     * @param serviceName
     * @return
     */
    URI lookupService(String serviceName);


}
