package com.ck.cpc.conn;

import java.util.concurrent.CompletableFuture;

/**
 * @Author caikun
 * @Description
 * 封装请求
 *
 * @Date 下午4:30 21-4-2
 **/
public class ResponseFuture {

    private final CompletableFuture<Command> future;

    private final Integer requestId;

    public ResponseFuture(int requestId, CompletableFuture<Command> future) {
        this.future = future;
        this.requestId = requestId;
    }

    public CompletableFuture<Command> getFuture() {
        return future;
    }

    public int getRequestId() {
        return requestId;
    }
}
