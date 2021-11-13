package com.tryingpfq.rpc.serialization;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
public class HessianSerialization implements Serialization{
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(bos);
        out.writeObject(obj);
        out.flush();
        return bos.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws IOException {
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(data));
        return (T) input.readObject(clazz);
    }
}
