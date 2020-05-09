package com.tryingpfq.serializer.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义解析设配器 这里只对集合
 * @author tryingpfq
 * @date 2020/5/9
 **/
public class CustomDeseializerAdapter implements JsonDeserializer<List<?>> {
    @Override
    public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonArray()) {
            JsonArray jsonArray = json.getAsJsonArray();
            if (jsonArray.size() == 0) {
                return Collections.EMPTY_LIST;
            }
            List<?> resultList = new ArrayList<>();
            for (JsonElement element : jsonArray) {
                resultList.add(context.deserialize(element, typeOfT));
            }
            return resultList;
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}
