package com.tryingpfq.serializer;

import com.tryingpfq.serializer.gson.GsonUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author tryingpfq
 * @date 2020/5/9
 **/
public class GsonTest {

    private int id;

    private String name;

    private List<Integer> lists;

    private Map<Integer,String> map;

    public GsonTest(int id, String name,List<Integer> lists) {
        this.id = id;
        this.name = name;
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "GsonTest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lists=" + lists +
                ", map=" + map +
                '}';
    }

    public static void main(String[] args) {
        List<Integer> lists = Arrays.asList(1, 2, 3);
        GsonTest gsonTest = new GsonTest(1, "aa",lists);
        String str = GsonUtils.object2String(gsonTest);
        System.err.println(str);

        GsonTest obj = GsonUtils.string2Object(str, GsonTest.class);

        System.err.println(obj);

    }
}
