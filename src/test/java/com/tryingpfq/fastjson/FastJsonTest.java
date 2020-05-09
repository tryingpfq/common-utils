package com.tryingpfq.fastjson;

import com.alibaba.fastjson.JSON;

/**
 * @author tryingpfq
 * @date 2020/5/8
 **/
public class FastJsonTest {

    public static void main(String[] args) {
        Demo demo = new Demo(1, "aa");
        String str = JSON.toJSONString(demo);
        System.out.println(str);
        Demo demo1 = JSON.parseObject(str, Demo.class);
        System.err.println(demo1);
    }

    static class Demo{
        private int id;

        private String name;

        public Demo() {

        }
        public Demo(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Demo{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
