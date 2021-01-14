package com.tryingpfq.redis.locK;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

public class RedisLock {

    private String lockValue;

    /**
     * 获取锁
     *
     * @param key        key
     * @param tryOutTime 超时时间
     * @param expire     key失效时间
     * @return
     */
    public String getLock(String key, int tryOutTime, int expire) {
        try {
            Jedis jedis = RedisManager.getJedisPool();
            String value = UUID.randomUUID().toString();
            long endTime = System.currentTimeMillis() + tryOutTime;
            while (System.currentTimeMillis() < endTime) {
                if (jedis.setnx(key, value) == 1) {
                    jedis.expire(key, expire);
                    this.lockValue = value;
                    return value;
                }
                //防止在设置超时时间前，连接挂了
                if (jedis.ttl(key) < 0) {
                    jedis.expire(key, expire);
                }
                Thread.sleep(1000L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 释放锁
     *
     * @param key
     * @param lockValue
     * @return
     */
    public boolean releaseLock(String key, String lockValue) {
        try {
            Jedis jedis = RedisManager.getJedisPool();
            while (true) {
                jedis.watch(key);
                if (lockValue.equals(jedis.get(key))) {
                    Transaction transaction = jedis.multi();
                    transaction.del(key);
                    List<Object> list = transaction.exec();
                    if (list == null) {
                        continue;
                    }
                    return true;
                }
                jedis.unwatch();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        RedisLock redisLock = new RedisLock();
        String key = "lock:one";
        String lock = redisLock.getLock(key, 2000, 10);
        if (lock != null) {
            System.err.println("lock success :" + lock);
        }
        String lock2 = redisLock.getLock(key, 1000, 10);
        if (lock2 == null) {
            System.err.println("second get fail");
        }
        Thread.sleep(3);
        // redisLock.releaseLock(key, lock);
    }

}
