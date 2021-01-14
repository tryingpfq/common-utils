package com.tryingpfq.redis.locK;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {
    private static JedisPool jedisPool;


    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(10);
        jedisPool = new JedisPool(config, "192.168.1.1", 6379);
    }

    public static Jedis getJedisPool() throws Exception {
        if (jedisPool != null) {
            return jedisPool.getResource();
        }
        throw new Exception("JedisPool is null");
    }
}
