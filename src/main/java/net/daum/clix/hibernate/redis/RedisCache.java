package net.daum.clix.hibernate.redis;

import org.hibernate.cache.CacheException;

/**
 * An interface for Redis.
 *
 * @author jtlee
 * @author 84june
 */
public interface RedisCache {

	String getRegionName();

	boolean exists(String key);

	Object get(Object key) throws CacheException;

	void put(Object key, Object value) throws CacheException;

	void remove(Object key) throws CacheException;

	void destory();

    boolean lock(Object key, Integer expireMsecs) throws InterruptedException;

    void unlock(Object key);
}
