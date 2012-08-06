package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.region.RedisCollectionRegion;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.access.SoftLock;

public class NonStrictReadWriteRedisCollectionRegionAccessStrategy implements CollectionRegionAccessStrategy {

	private CollectionRegion region;
	private RedisCache cache;

	public NonStrictReadWriteRedisCollectionRegionAccessStrategy(RedisCollectionRegion region) {
		this.region = region;
		this.cache = region.getRedisCache();
	}

	@Override
	public CollectionRegion getRegion() {
		return region;
	}

	@Override
	public Object get(Object key, long txTimestamp) throws CacheException {
		return cache.get(key);
	}

	@Override
	public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version) throws CacheException {
		cache.put(key, value);
		return true;
	}

	@Override
	public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {
		cache.put(key, value);
		return true;
	}

	@Override
	public SoftLock lockItem(Object key, Object version) throws CacheException {
		return null;
	}

	@Override
	public SoftLock lockRegion() throws CacheException {
		return null;
	}

	@Override
	public void unlockItem(Object key, SoftLock lock) throws CacheException {
	}

	@Override
	public void unlockRegion(SoftLock lock) throws CacheException {
	}

	@Override
	public void remove(Object key) throws CacheException {
		cache.remove(key);
	}

	@Override
	public void removeAll() throws CacheException {
		throw new IllegalAccessError("NonStrictReadWriteRedisEntityRegionAccessStrategy#removeAll has not implemented yet!!");
	}

	@Override
	public void evict(Object key) throws CacheException {
		cache.remove(key);
	}

	@Override
	public void evictAll() throws CacheException {
		throw new IllegalAccessError("NonStrictReadWriteRedisEntityRegionAccessStrategy#evictAll has not implemented yet!!");
	}
}

