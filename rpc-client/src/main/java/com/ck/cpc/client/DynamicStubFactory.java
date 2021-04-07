package com.ck.cpc.client;

import com.ck.cpc.client.service.ServiceStub;
import com.ck.cpc.conn.Transport;
import com.itranswarp.compiler.JavaStringCompiler;

import java.util.Map;

/**
 * @Author caikun
 * @Description //TODO $END
 * @Date 下午5:37 21-4-2
 **/

public class DynamicStubFactory implements StubFactory{

    /**
     * 把接口的类名、方法名、和序列化后的参数封装为一个RPrequest对象，
     * 调用父类AbstractStub的invokeRemote方法发给服务端
     *
     * invokeRemote方法的返回值就是序列化的调用结果，
     * 需要在模板中对结果反序列化，直接作为返回值返回给调用方
     *
     *
     */
    private final static String STUB_SOURCE_TEMPLATE =
            "package com.ck.cpc.client.stubs;\n" +
                    "import com.ck.cpc.utils.SerializeSupport;\n" +
                    "\n" +
                    "public class %s extends AbstractServiceStub implements %s {\n" +
                    "    @Override\n" +
                    "    public String %s(String arg) {\n" +
                    "        return SerializeSupport.parse(\n" +
                    "                remoteInvoke(\n" +
                    "                        new RpcRequest(\n" +
                    "                                \"%s\",\n" +
                    "                                \"%s\",\n" +
                    "                                SerializeSupport.serialize(arg)\n" +
                    "                        )\n" +
                    "                )\n" +
                    "        );\n" +
                    "    }\n" +
                    "}";

    /**
     * 创建代理类方法
     * @param transport 给服务端发请求时使用的
     * @param serviceClass 创建的桩的具体类型
     * @param <T>
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T createStub(Transport transport, Class<T> serviceClass) {
        try {
            // 填充模板
            String stubSimpleName = serviceClass.getSimpleName() + "Stub";
            String classFullName = serviceClass.getName();
            String stubFullName = "com.ck.cpc.client." + stubSimpleName;
            String methodName = serviceClass.getMethods()[0].getName();

            String source = String.format(STUB_SOURCE_TEMPLATE, stubSimpleName, classFullName, methodName, classFullName, methodName);
            // 编译源代码
            JavaStringCompiler compiler = new JavaStringCompiler();
            Map<String, byte[]> results = compiler.compile(stubSimpleName + ".java", source);
            // 加载编译好的类
            Class<?> clazz = compiler.loadClass(stubFullName, results);
            // 把Transport赋值给桩
            ServiceStub stubInstance = (ServiceStub) clazz.newInstance();
            stubInstance.setTransport(transport);
            // 返回这个桩
            return (T) stubInstance;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
