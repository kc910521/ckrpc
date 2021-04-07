package com.ck.cpc.center;

import com.ck.cpc.client.Metadata;
import com.ck.cpc.core.NameService;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author caikun
 * @Description
 * 注册
 *
 * 注册服务 registerService 方法时，把服务提供者保存到本地文件中；
 * 实现查找服务 lookupService 时，就是去本地文件中读出所有的服务提供者，找到对应的服务提供者，然后返回。
 *
 * @Date 下午6:55 21-4-6
 **/
public class LocalFileNameService implements NameService {

    private static final Logger logger = LoggerFactory.getLogger(LocalFileNameService.class);
    /**
     * 本注册中心支持的方式
     */
    private static final Collection<String> schemes = Collections.singleton("file");
    /**
     * 配置文件,暂时被metadata代替
     */
    private File configFile;
    /**
     * 一个serviceName可能对应多个URI负载均衡
     */
    private static final Metadata metadata = new Metadata();


    @Override
    public Collection<String> supportedSchemes() {
        return schemes;
    }

    @Override
    public void connect(URI nameServiceUri) {
        if (schemes.contains(nameServiceUri.getScheme())) {
            configFile = new File(nameServiceUri);
        } else {
            throw new RuntimeException("schemes unsupported");
        }
    }

    @Override
    public void registerService(String serviceName, URI uri) {
        synchronized (metadata) {
            if (metadata.containsKey(serviceName)) {
                List<URI> uris = metadata.get(serviceName);
                if (!uris.contains(uri)) {
                    uris.add(uri);
                }
            } else {
                List<URI> uris = new ArrayList<>();
                uris.add(uri);
                metadata.put(serviceName, uris);
            }
        }
    }

    @Override
    public URI lookupService(String serviceName) {
        List<URI> uris = null;
        uris = metadata.get(serviceName);
        if (uris == null || uris.isEmpty()) {
            logger.debug("service {} not found", serviceName);
            return null;
        }
        return uris.get(ThreadLocalRandom.current().nextInt(uris.size()));
    }

}
