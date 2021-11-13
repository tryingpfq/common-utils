package com.tryingpfq.serializer.json;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * @Author tryingpfq
 * @Date 2020/5/8
 */
public class CustomDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}
