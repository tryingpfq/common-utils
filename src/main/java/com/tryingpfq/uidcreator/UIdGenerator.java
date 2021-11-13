package com.tryingpfq.uidcreator;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 时间戳唯一id= 当前时间 + 前缀 + 当前时间自增
 *
 * @author tryingpfq
 * @date 2021/1/11
 **/
public class UIdGenerator {
    private static final long MAX_PREFIX = 100000;

    /**
     * 1s内允许使用的最大唯一id数，超过则使用下一秒的
     */
    private static final long ONE_SEC_MAX = 1000;

    private long prefix;

    private volatile int markTime;

    private final AtomicInteger SEC_INC = new AtomicInteger(0);

    public static UIdGenerator valueOf(int prefix) {
        Validate.inclusiveBetween(1L,
                MAX_PREFIX,
                (long) prefix, "prefix[%d] out of range[%d-%d]", prefix, 1, MAX_PREFIX);
        if (prefix == 0) {
            prefix = 1;
        }
        UIdGenerator uIdGenerator = new UIdGenerator();
        uIdGenerator.prefix = prefix;
        return uIdGenerator;
    }

    public synchronized long get() {
        int time = (int) (System.currentTimeMillis() / 1000);
        if (this.markTime < time) {
            SEC_INC.set(0);
            this.markTime = time;
        } else {
            if (SEC_INC.get() > ONE_SEC_MAX) {
                SEC_INC.set(0);
                markTime += 1;
            }
        }
        int index = SEC_INC.incrementAndGet();
        long id = markTime * MAX_PREFIX;
        return (id + prefix) * ONE_SEC_MAX + index;
    }
}
