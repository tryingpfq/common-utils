package com.tryingpfq.rpc.serialization;

import java.io.IOException;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
public interface Serialization {

    <T> byte[] serialize(T obj) throws IOException;

    <T> T deserialize(byte[] data,Class<T> clazz) throws IOException;

}
