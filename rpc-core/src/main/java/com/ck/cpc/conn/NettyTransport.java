package com.ck.cpc.conn;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import java.util.concurrent.CompletableFuture;

/**
 * @Author caikun
 * @Description netty实现
 * @Date 下午2:44 21-4-2
 **/
public class NettyTransport implements Transport {


    private InFlightRequests inFlightRequests;

    private Channel channel;

    public NettyTransport(InFlightRequests inFlightRequests, Channel channel) {
        this.inFlightRequests = inFlightRequests;
        this.channel = channel;
    }

    @Override
    public CompletableFuture<Command> send(Command request) {
        CompletableFuture<Command> completableFuture = new CompletableFuture<>();
        try {
            // 将在途请求放到inFlightRequests*中
            // 根本不处理request
            inFlightRequests.put(new ResponseFuture(request.getHeader().getRequestId(), completableFuture));
            // 绑定ResponseFuture（requestId） 和 completableFuture
            // 这里处理request，发送命令
            channel.writeAndFlush(request).addListener((ChannelFutureListener) listener -> {
                if (!listener.isSuccess()) {
                    // failed
                    completableFuture.completeExceptionally(listener.cause());
                    channel.close();
                }
            });

        } catch (Throwable e) {
            // 清理ResponseFuture
            inFlightRequests.remove(request.getHeader().getRequestId());
            completableFuture.completeExceptionally(e);
        }

        return completableFuture;
    }
}
