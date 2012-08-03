package net.daum.clix.hibernate.redis.region;

import net.daum.clix.hibernate.redis.RedisCache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.TimestampsRegion;

public class RedisTimestampsRegion extends AbstractRedisRegion implements TimestampsRegion {

	public RedisTimestampsRegion(RedisCache cache) {
		super(cache);
	}

	@Override
	public Object get(Object key) throws CacheException {
		return cache.get(key);
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
		cache.put(key, value);
	}

	@Override
	public void evict(Object key) throws CacheException {
		cache.remove(key);
	}

	@Override
	public void evictAll() throws CacheException {
		throw new IllegalAccessError("RedisTimestampsRegion#evictAll has not implemented yet!!");
	}
}
