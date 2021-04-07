package com.ck.cpc.conn;

import java.util.concurrent.CompletableFuture;

/**
 * @Author caikun
 * @Description 客户端发送
 * @Date 下午2:29 21-4-2
 **/
public interface Transport {

    /**
     * 发送命令请求
     * @param request
     * @return
     */
    CompletableFuture<Command> send(Command request);

}
