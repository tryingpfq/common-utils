package com.tryingpfq.serializer.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * <p>注意 反序列化的时候，对象一定要有无参构造器<p/>
 * @Author tryingpfq
 * @Date 2020/5/8
 */
public class JsonUtils {
    private static final ParserConfig parserConfig = new CustomParserConfig(true);

    private static final SerializeConfig serializeConfig = new CustomSerializeConfig(true);

    static{
        ParserConfig.global = parserConfig;
    }

    public static String objectString(Object object) {
        return JSON.toJSONString(object,serializeConfig);
    }

    public static <T> T stringObject(String json, Class<T> clz) {
        return JSON.parseObject(json, clz, parserConfig);
    }

    public static <T> T stringObject(String json, TypeReference<T> tTypeReference) {
        return JSON.parseObject(json, tTypeReference.getType(), parserConfig);
    }
}
