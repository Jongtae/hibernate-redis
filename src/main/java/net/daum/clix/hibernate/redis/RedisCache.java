package net.daum.clix.hibernate.redis;

import org.hibernate.cache.CacheException;

/**
 * An interface for Redis.
 *
 * @author jtlee
 * @author onlyabout
 */
public interface RedisCache {

	public String getRegionName();

	public boolean exists(String key);

	public Object get(Object key) throws CacheException;

	public void put(Object key, Object value) throws CacheException;

	public void remove(Object key) throws CacheException;

	public int getTimeout();

	public long getSizeInMemory();

	public long getElementCountInMemory();

	public long getElementCountOnDisk();

	void destroy() throws CacheException;
}
