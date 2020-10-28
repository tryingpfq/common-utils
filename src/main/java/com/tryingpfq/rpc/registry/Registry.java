package com.tryingpfq.rpc.registry;

import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;

/**
 * @author tryingpfq
 * @date 2020/10/28
 **/
public interface Registry<T> {

    void registerService(ServiceInstance<T> service) throws Exception;

    void unRegisterService(ServiceInstance<T> service) throws Exception;

    List<ServiceInstance<T>> queryForInstances(String name) throws Exception;

}
