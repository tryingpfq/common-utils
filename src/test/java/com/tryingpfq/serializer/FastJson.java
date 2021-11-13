package com.tryingpfq.serializer;

import com.tryingpfq.serializer.json.JsonUtils;

/**
 * @author tryingpfq
 * @date 2020/5/9
 **/
public class FastJson {

    private int id;

    private String name;

    public FastJson(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public FastJson(){

    }

    @Override
    public String toString() {
        return "FastJson{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        FastJson fastJson = new FastJson(1, "aaa");
        String str = JsonUtils.objectString(fastJson);

        System.err.println(str);

        FastJson fastJson1 = JsonUtils.stringObject(str, FastJson.class);

        System.err.println(fastJson1);
    }
}
