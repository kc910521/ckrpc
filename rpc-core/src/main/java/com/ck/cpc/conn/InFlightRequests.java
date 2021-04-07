package com.ck.cpc.conn;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author caikun
 * @Description
 * 在途请求表，未被处理的请求表
 *
 * @Date 下午3:21 21-4-2
 **/
public class InFlightRequests implements Closeable {

    private final static long TIMEOUT_SEC = 10L;

    /**
     * 限流
     * 背压机制(back pressure)
     *
     */
    private final Semaphore semaphore = new Semaphore(10);

    /**
     * 请求池
     */
    private static Map<Integer, ResponseFuture> futureMap = new ConcurrentHashMap<>(128);

    /**
     * 定时处理池内超时请求
     */
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//    private final ScheduledFuture scheduledFuture;

    public InFlightRequests() {
//        scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(this::removeTimeoutFutures, TIMEOUT_SEC, TIMEOUT_SEC, TimeUnit.SECONDS);
    }


    public void put(ResponseFuture responseFuture) {
        try {
            semaphore.acquire();
            ResponseFuture put = futureMap.put(responseFuture.getRequestId(), responseFuture);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public ResponseFuture remove(int requestId) {
        ResponseFuture future = futureMap.remove(requestId);
        if(null != future) {
            semaphore.release();
        }
        return future;
    }

    @Override
    public void close() throws IOException {

    }

//    private void removeTimeoutFutures() {
//        futureMap.entrySet().removeIf(entry -> {
//            if( System.nanoTime() - entry.getValue().getTimestamp() > TIMEOUT_SEC * 1000000000L) {
//                semaphore.release();
//                return true;
//            } else {
//                return false;
//            }
//        });
//    }

}
