package com.tryingpfq.listener.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author tryingpfq
 * @date 2020/11/13
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Order {
    /**
     * 在指定的之后执行
     * @return
     */
    Class<?>[] afterClass();
}
