package net.daum.clix.hibernate.redis.jedis;

import net.daum.clix.hibernate.redis.RedisCache;
import org.hibernate.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author jtlee
 * @author 84june
 */
public class JedisCacheImpl implements RedisCache {

    private JedisPool jedisPool;

    private Jedis jedis;

    private String regionName;

    private boolean locked = false;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public JedisCacheImpl(JedisPool jedisPool, String regionName) {
        this.jedisPool = jedisPool;
        this.regionName = regionName;
        this.jedis = jedisPool.getResource();
    }

    @Override
    public Object get(Object key) throws CacheException {
        Object o = null;

        byte[] k = serializeObject(key.toString());
        try {
            byte[] v = jedis.get(k);
            if (v != null && v.length > 0) {
                o = deserializeObject(v);
            }

            return o;
        } catch (JedisConnectionException e) {
            logger.error(key.toString(), e);
        }
        return null;
    }

    @Override
    public void put(Object key, Object value) throws CacheException {
        byte[] k = serializeObject(key.toString());
        byte[] v = serializeObject(value);

        try {
            jedis.set(k, v);
        } catch (JedisConnectionException e) {
            logger.error(key.toString(), e);
        }
    }

    @Override
    public void remove(Object key) throws CacheException {
        try {
            jedis.del(serializeObject(key.toString()));
        } catch (JedisConnectionException e) {
            logger.error(key.toString(), e);
        }
    }

    @Override
    public boolean exists(String key) {
        try {
            return jedis.exists(serializeObject(key));
        } catch (JedisConnectionException e) {
            logger.error(key, e);
        }
        return false;
    }

    @Override
    public int getTimeout() {
        return 0;
    }

    @Override
    public String getRegionName() {
        return this.regionName;
    }

    @Override
    public long getSizeInMemory() {
        return -1;
    }

    @Override
    public long getElementCountInMemory() {
        return -1;
    }

    @Override
    public long getElementCountOnDisk() {
        return -1;
    }

    @Override
    public void destory() {
        jedisPool.returnResource(jedis);
    }

    private byte[] serializeObject(Object obj) {
        SerializingConverter sc = new SerializingConverter();
        return sc.convert(obj);
    }

    private Object deserializeObject(byte[] b) {
        DeserializingConverter dc = new DeserializingConverter();
        return dc.convert(b);
    }

    public boolean lock(Object key, Long expireMsecs) throws InterruptedException {

        int timeout = getTimeout();
        String lockKey = generateLockKey(key);
        long expires = System.currentTimeMillis() + expireMsecs + 1;
        String expiresStr = String.valueOf(expires);

        while (timeout >= 0) {

            try {
                if (jedis.setnx(lockKey, expiresStr) == 1) {
                    locked = true;
                    return true;
                }

                String currentValueStr = jedis.get(lockKey);
                if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                    // lock is expired

                    String oldValueStr = jedis.getSet(lockKey, expiresStr);
                    if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                        // lock acquired
                        locked = true;
                        return true;
                    }
                }
            } catch (JedisConnectionException e) {
                logger.error(key.toString(), e);
            }
            timeout -= 100;
            Thread.sleep(100);
        }
        return false;
    }

    @Override
    public void unlock(Object key) {
        if (locked) {
            jedis.del(generateLockKey(key));
            locked = false;
        }
    }

    private String generateLockKey(Object key) {

        if (null == key) {
            throw new IllegalArgumentException("key must not be null");
        }

        return key.toString() + ".lock";
    }


}
