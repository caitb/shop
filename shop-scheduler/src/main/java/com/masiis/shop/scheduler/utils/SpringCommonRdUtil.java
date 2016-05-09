package com.masiis.shop.scheduler.utils;

import com.masiis.shop.common.util.JdbcPropertiesUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;

/**
 * Created by lzh on 2016/2/22.
 */
public class SpringCommonRdUtil {

    private static RedisTemplate<Serializable, Serializable> redisTemplate;
    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(JdbcPropertiesUtils.getIntValue("redis.maxIdle"));
        poolConfig.setMaxTotal(JdbcPropertiesUtils.getIntValue("redis.maxTotal"));
        poolConfig.setMaxWaitMillis(JdbcPropertiesUtils.getIntValue("redis.maxWaitMillis"));
        poolConfig.setTestOnBorrow(Boolean.valueOf(JdbcPropertiesUtils.getStringValue("redis.testOnBorrow")));

        JedisConnectionFactory fac = new JedisConnectionFactory();
        fac.setPoolConfig(poolConfig);
        fac.setHostName(JdbcPropertiesUtils.getStringValue("redis.hostName"));
        fac.setDatabase(JdbcPropertiesUtils.getIntValue("redis.common.database"));
        fac.setPort(JdbcPropertiesUtils.getIntValue("redis.port"));
        fac.setPassword(JdbcPropertiesUtils.getStringValue("redis.password"));
        fac.setUsePool(true);
        fac.afterPropertiesSet();

        redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(fac);
        redisTemplate.afterPropertiesSet();
    }

    public static void save(final String key, Object value) {

        final byte[] vbytes = SerializeUtil.serialize(value);
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.set(redisTemplate.getStringSerializer().serialize(key), vbytes);
                return null;
            }
        });
    }

    /**
     * 带有有效时间的保存
     *
     * @param key
     * @param value
     * @param expires 单位秒
     */
    public static void saveEx(final String key, Object value, final long expires) {

        final byte[] vbytes = SerializeUtil.serialize(value);
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.setEx(redisTemplate.getStringSerializer().serialize(key), expires, vbytes);
                return null;
            }
        });
    }

    public static <T> T get(final String key, Class<T> elementType) {
        return redisTemplate.execute(new RedisCallback<T>() {
            public T doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keybytes = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(keybytes)) {
                    byte[] valuebytes = connection.get(keybytes);
                    Object res = SerializeUtil.unserialize(valuebytes);
                    T value = (T) res;
                    return value;
                }
                return null;
            }
        });
    }
}