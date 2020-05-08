package serializer.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * @Author tryingpfq
 * @Date 2020/5/8
 */
public class JsonUtils {
    private static final ParserConfig parserConfig = new CustomParserConfig();

    private static final SerializeConfig serializeConfig = new CustomSerializeConfig();

    static{
        ParserConfig.global = parserConfig;
    }

    public static String objectString(Object object) {
        return JSON.toJSONString(object);
    }



}
