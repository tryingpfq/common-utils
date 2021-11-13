package com.tryingpfq.objmonitor;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>
 *     对象监视器，用来监视某个对象，创建的数量
 * </p>
 * @author tryingpfq
 * @date 2020/5/13
 **/
public class ObjMonitor {

    private static HashSet<WeakReference<Object>> objSets = new HashSet<>();

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    public static void addMonitorObj(Object object) {
        lock.writeLock().lock();
        try {
            WeakReference<Object> reference = new WeakReference<>(object);
            objSets.add(reference);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 检查的时候需要把已经回收了的引用移除
     * @return 结果
     */
    public static Map<String,Integer> check(){
        Map<String, Integer> result = new HashMap<>();
        objSets.removeIf(objectWeakReference -> {
            Object o = objectWeakReference.get();
            if (o == null) {
                return true;
            }
            String simpleName = o.getClass().getSimpleName();
            result.merge(simpleName, result.getOrDefault(simpleName, 1), Integer::sum);
            return false;
        });
        return result;
    }


    public static void main(String[] args) {
        Integer integer = new Integer(1000);
        Integer a = 1;
        ObjMonitor.addMonitorObj(integer);
        ObjMonitor.addMonitorObj(a);

        Map<String, Integer> check = check();
        System.err.println(check);
    }
}
