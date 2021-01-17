package com.tryingpfq.objsize;

import org.openjdk.jol.info.ClassLayout;

/**
 * @Author tryingpfq
 * @Date 2021/1/17
 */
public class Demo {

    public static void main(String[] args) {
        Object obj = new Object();

        System.err.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}
