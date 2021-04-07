package com.ck.cpc.core;

/**
 * @Author caikun
 * @Description
 * 序列化接口
 *
 * @Date 下午7:04 21-4-1
 **/
public interface Serializer<T> {

    /**
     * 序列化后的长度，用于事先来申请存放序列化数据的字节数组
     * @param entry
     * @return
     */
    int size(T entry);

    /**
     * 序列化entry 为 bytes，
     *
     * @param entry
     * @param bytes
     * @param offset 数据偏移量
     * @param length 序列化后的长度
     */
    void serialize(T entry, byte[] bytes, int offset, int length);

    /**
     * 将bytes字节流转换为T类型
     * @param bytes
     * @param offset
     * @param length
     * @return
     */
    T parse(byte[] bytes, int offset, int length);

    /**
     * 用一个字节标识对象类型，每种类型的数据应该具有不同的类型值
     * 定义每种序列化类型
     * @return
     */
    byte type();

    /**
     * 获取序列化对象类型的class对象，
     * 在执行序列化的时候，通过被序列化的对象类型找到对应序列化实现类
     *
     * @return
     */
    Class<T> getSerializeClass();

}
