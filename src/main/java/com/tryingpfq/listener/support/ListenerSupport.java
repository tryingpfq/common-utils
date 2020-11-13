package com.tryingpfq.listener.support;

import com.tryingpfq.listener.anno.Order;
import lombok.Getter;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tryingpfq
 * @date 2020/11/13
 **/
public class ListenerSupport<L> {

    private Class<L> interfaceClazz;

    private List<L> listeners = new CopyOnWriteArrayList<>();

    private L proxy;

    private Map<Method, List<L>> sortListenerMap = new ConcurrentHashMap<>();


    public static <T> ListenerSupport<T> create(final Class<T> interfaceClazz) {
        return new ListenerSupport<>(interfaceClazz);
    }

    public ListenerSupport(Class<L> interfaceClazz) {
        this.proxy = interfaceClazz.cast(Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{interfaceClazz}, new ListenerInvocationHandler()));
    }

    public void addListener(L bean) {
        this.listeners.add(bean);
        this.sortListenerMap.clear();
    }

    public L fire() {
        return this.proxy;
    }

    class ListenerInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            List<L> sortLists = sortListenerMap.computeIfAbsent(method,m -> sortByMethod(m, listeners));
            for (final L listener : sortLists) {
                try {
                    method.invoke(listener, args);
                } catch (Exception e) {
                    throw new RuntimeException(String.format("Listener[%s] run error !", listeners.getClass()));
                }
            }
            return null;
        }

    }

    public static <T> List<T> sortByMethod(Method method,Collection<T> ts ) {
        return sort(ts,t -> {
            Method mt = MethodUtils.getAccessibleMethod(t.getClass(), method.getName(), method.getParameterTypes());
            Order order = mt.getAnnotation(Order.class);
            if (order == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(order.afterClass());
        });
    }

    public static <T> List<T> sort(Collection<T> ts, Function<T, Collection<Class<?>>> getter) {
        Map<Class<?>, SortData<T>> sortDataMap = new HashMap<>(ts.size());
        ts.forEach(t -> {
            Collection<Class<?>> afters = getter.apply(t);
            if (sortDataMap.put(t.getClass(), new SortData<>(t, new LinkedList<>(afters))) != null) {
                throw new RuntimeException(String.format("repeated obj in sort list by class [%s]", t.getClass()));
            }
        });
        //检查是否有先后顺序错误
        sortDataMap.forEach((aClass, tSortData) -> {
            //TODO
        });
        //排序
        List<SortData<T>> sortList = new ArrayList<>(sortDataMap.values());
        sortList.sort((o1, o2) -> {
            //TODO
            return 0;
        });
        return sortList.stream().map(v -> v.t).collect(Collectors.toList());
    }

    @Getter
    static class SortData<T>{
        private T t;

        private LinkedList<Class<?>> nextClazz;

        public SortData(T t, LinkedList<Class<?>> nextClazz) {
            this.t = t;
            this.nextClazz = nextClazz;
        }

    }
}
