package com.tryingpfq.listener.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author tryingpfq
 * @date 2020/11/13
 **/
public class ListenerSupport<T> {

    private Class<T> interfaceClazz;

    private List<T> listeners = new CopyOnWriteArrayList<>();

    private T proxy;


    public static <T> ListenerSupport<T> create(final Class<T> interfaceClazz) {
        return new ListenerSupport<>(interfaceClazz);
    }

    public ListenerSupport(Class<T> interfaceClazz) {
        this.proxy = interfaceClazz.cast(Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{interfaceClazz}, new ListenerInvocationHandler()));
    }

    public void addListener(T bean) {
        this.listeners.add(bean);
    }

    public T fire() {
        return this.proxy;
    }

    class ListenerInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (final T listener : listeners) {
                try {
                    method.invoke(listener, args);
                } catch (Exception e) {
                    throw new RuntimeException(String.format("Listener[%s] run error !", listeners.getClass()));
                }
            }
            return null;
        }
    }
}
