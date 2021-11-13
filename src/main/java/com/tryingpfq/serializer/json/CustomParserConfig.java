package com.tryingpfq.serializer.json;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * @Author tryingpfq
 * @Date 2020/5/8
 */
public class CustomParserConfig extends ParserConfig {
    private static ObjectDeserializer deserializer = new CustomDeserializer();

    public CustomParserConfig(){

    }

    public CustomParserConfig(boolean fieldBase) {
        super(fieldBase);
    }


    @Override
    public ObjectDeserializer getDeserializer(Class<?> clazz, Type type) {
        return super.getDeserializer(clazz, type);
    }

    @Override
    public ObjectDeserializer get(Type type) {
        return super.get(type);
    }
}
