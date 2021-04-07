package com.ck.cpc.utils;

import com.ck.cpc.core.Serializer;
import com.ck.cpc.core.ser.StringSerializer;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author caikun
 * @Description
 * 序列化工具
 *
 * @Date 下午7:00 21-4-1
 **/
public class SerializeSupport {
    private static final Logger logger = LoggerFactory.getLogger(SerializeSupport.class);

    /**
     * 在序列化的时候，通过被序列化的对象类型，找到对应的序列化实现类
     * K:序列化对象类型
     * V:序列化实现
     *
     */
    private static Map<Class<?>, Serializer<?>> serializerMap = new HashMap<>();


    /**
     * 用于在反序列化的时候，从序列化的数据中读出对象类型，然后找到对应的序列化实现类
     * K:序列化实现类型
     * V:序列化对象类型
     */
    private static Map<Byte, Class<?>> typeMap = new HashMap<>();

    static {
        // 只能搞string
        Serializer serializer = new StringSerializer();
        registerType(serializer.type(), serializer.getSerializeClass(), serializer);
        logger.debug("Found serializer, class: {}, type: {}.",
                serializer.getSerializeClass().getCanonicalName(),
                serializer.type());
//        for (Serializer serializer : ServiceSupport.loadAll(Serializer.class)) {
//            registerType(serializer.type(), serializer.getSerializeClass(), serializer);
//            logger.debug("Found serializer, class: {}, type: {}.",
//                    serializer.getSerializeClass().getCanonicalName(),
//                    serializer.type());
//        }
    }
    private static byte parseEntryType(byte[] buffer) {
        return buffer[0];
    }
    private static <E> void registerType(byte type, Class<E> eClass, Serializer<E> serializer) {
        serializerMap.put(eClass, serializer);
        typeMap.put(type, eClass);
    }
    @SuppressWarnings("unchecked")
    private static  <E> E parse(byte [] buffer, int offset, int length, Class<E> eClass) {
        Object entry =  serializerMap.get(eClass).parse(buffer, offset, length);
        if (eClass.isAssignableFrom(entry.getClass())) {
            return (E) entry;
        } else {
            throw new RuntimeException("Type mismatch!");
        }
    }
    public static  <E> E parse(byte [] buffer) {
        return parse(buffer, 0, buffer.length);
    }

    private static  <E> E parse(byte[] buffer, int offset, int length) {
        byte type = parseEntryType(buffer);
        @SuppressWarnings("unchecked")
        Class<E> eClass = (Class<E> )typeMap.get(type);
        if(null == eClass) {
            throw new RuntimeException(String.format("Unknown entry type: %d!", type));
        } else {
            return parse(buffer, offset + 1, length - 1,eClass);
        }

    }

    public static <E> byte [] serialize(E  entry) {
        @SuppressWarnings("unchecked")
        Serializer<E> serializer = (Serializer<E>) serializerMap.get(entry.getClass());
        if(serializer == null) {
            throw new RuntimeException(String.format("Unknown entry class type: %s", entry.getClass().toString()));
        }
        byte [] bytes = new byte [serializer.size(entry) + 1];
        bytes[0] = serializer.type();
        serializer.serialize(entry, bytes, 1, bytes.length - 1);
        return bytes;
    }


}
