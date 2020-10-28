package com.tryingpfq.rpc.protocol;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
@Getter
@Setter
public class Request implements Serializable {
    /**
     * 请求的服务接口类名
     */
    private String serviceName;

    /**
     * 方法名称
     */
    private String methodName;


    /**
     * 方法参数类型
     */
    private Class[] argTypes;

    /**
     * 方法参数
     */
    private Object[] args;


    public Request(String serviceName, String methodName, Object[] args) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.args = args;
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
    }


    @Override
    public String toString() {
        return "Request{" +
                "serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", argTypes=" + Arrays.toString(argTypes) +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
