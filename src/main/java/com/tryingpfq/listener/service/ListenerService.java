package com.tryingpfq.listener.service;

import com.tryingpfq.listener.anno.Listener;
import com.tryingpfq.listener.support.ListenerSupport;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @author tryingpfq
 * @date 2020/11/13
 **/
public class ListenerService extends InstantiationAwareBeanPostProcessorAdapter  implements PriorityOrdered {
    private Map<Class<?>, ListenerSupport<?>> listenerSupportMap = new ConcurrentHashMap<>();

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
      //
        Stream.of(ClassUtils.getAllInterfaces(bean.getClass())).forEach(aClass -> {
            if (aClass.isAnnotationPresent(Listener.class)) {
                ListenerSupport<Object> listenerSupport = (ListenerSupport<Object>) this.listenerSupportMap.computeIfAbsent(aClass,
                        ListenerSupport::create);
                listenerSupport.addListener(bean);

            }
        });
        return super.postProcessAfterInstantiation(bean, beanName);
    }

    public <T> T fire(Class<T> clazz){
        return (T) this.listenerSupportMap.get(clazz).fire();
    }

    @Override
    public int getOrder() {
        return 34234234;
    }
}
