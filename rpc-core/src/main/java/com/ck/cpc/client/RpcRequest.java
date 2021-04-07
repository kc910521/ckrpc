package com.ck.cpc.client;

/**
 * @Author caikun
 * @Description
 * 标识客户端的请求
 *
 * @Date 下午4:46 21-4-6
 **/
public class RpcRequest {
    private final String interfaceName;
    private final String methodName;
    private final byte [] serializedArguments;

    public RpcRequest(String interfaceName, String methodName, byte[] serializedArguments) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.serializedArguments = serializedArguments;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public byte[] getSerializedArguments() {
        return serializedArguments;
    }
}
