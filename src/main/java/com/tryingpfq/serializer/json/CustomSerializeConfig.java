package com.tryingpfq.serializer.json;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * @Author tryingpfq
 * @Date 2020/5/8
 */
public class CustomSerializeConfig extends SerializeConfig {

    CustomSerializeConfig(){

    }

    public CustomSerializeConfig(boolean fieldBase) {
        super(fieldBase);
    }

    @Override
    public ObjectSerializer getObjectWriter(Class<?> clazz) {
        //可以在这里自定义Serializer返回
        return super.getObjectWriter(clazz);
    }
}
