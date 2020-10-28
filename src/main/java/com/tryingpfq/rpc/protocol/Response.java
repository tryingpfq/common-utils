package com.tryingpfq.rpc.protocol;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
@Getter
@Setter
public class Response implements Serializable {

    /**
     * 响应的错误码
     */
    private int code;

    /**
     * 异常信息
     */
    private String errMsg;

    /**
     * 响应结果
     */
    private Object result;


}
