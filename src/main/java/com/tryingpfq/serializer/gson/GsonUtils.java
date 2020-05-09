package com.tryingpfq.serializer.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @Author tryingpfq
 * @Date 2020/5/8
 */
public class GsonUtils {

    private static ExclusionStrategy strategy = new CustomExclusionStrategy();

    private static JsonDeserializer list_Deserializer = new CustomDeseializerAdapter();

    private static Gson gson = new GsonBuilder().setExclusionStrategies(strategy)
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
            //.registerTypeHierarchyAdapter(List.class,list_Deserializer)
            .serializeNulls()
            .create();


    public static String object2String(Object object) {
        return gson.toJson(object);
    }

    public static <T> T string2Object(String json, Class<T> clz) {
        return gson.fromJson(json, clz);
    }

    public static <T> T string2Object(String json, TypeToken<T> tTypeToken) {
        return gson.fromJson(json, tTypeToken.getType());
    }

}
