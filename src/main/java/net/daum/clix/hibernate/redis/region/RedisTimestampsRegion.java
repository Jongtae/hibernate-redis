package net.daum.clix.hibernate.redis.region;

import java.util.Properties;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.strategy.RedisAccessStrategyFactory;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.TimestampsRegion;

public class RedisTimestampsRegion extends RedisRegion implements TimestampsRegion {

	public RedisTimestampsRegion(RedisAccessStrategyFactory accessStrategyFactory, RedisCache cache, Properties properties) {
		super(accessStrategyFactory, cache, properties);
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
		throw new UnsupportedOperationException("RedisTimestampsRegion#evictAll has not implemented yet!!");
	}
}
