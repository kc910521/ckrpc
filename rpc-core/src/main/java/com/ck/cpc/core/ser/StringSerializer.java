package com.ck.cpc.core.ser;

import com.ck.cpc.core.Serializer;

import java.nio.charset.StandardCharsets;

/**
 * @Author caikun
 * @Description
 * 字符串序列化
 *
 * @Date 下午2:06 21-4-2
 **/
public class StringSerializer implements Serializer<String> {


    @Override
    public int size(String entry) {
        return entry.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public void serialize(String entry, byte[] bytes, int offset, int length) {
        byte[] strBytes = entry.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(strBytes, 0, bytes, offset, length);
    }

    @Override
    public String parse(byte[] bytes, int offset, int length) {
        return new String(bytes, offset, length, StandardCharsets.UTF_8);
    }

    @Override
    public byte type() {
        return 0;// Types.TYPE_STRING
    }

    @Override
    public Class<String> getSerializeClass() {
        return String.class;
    }
}
