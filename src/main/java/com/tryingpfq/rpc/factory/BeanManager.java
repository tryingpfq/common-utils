package com.tryingpfq.rpc.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
public class BeanManager {
    private static Map<String, Object> services = new ConcurrentHashMap<>();

    public static void registerBean(String serviceName, Object bean) {
        services.put(serviceName, bean);
    }

    public static Object getBean(String serviceName) {
        return services.get(serviceName);
    }

}
