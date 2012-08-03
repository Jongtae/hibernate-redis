package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.region.RedisEntityRegion;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.access.EntityRegionAccessStrategy;
import org.hibernate.cache.access.SoftLock;

public class ReadOnlyRedisEntityRegionAccessStrategy implements EntityRegionAccessStrategy {

	private EntityRegion region;
	private RedisCache cache;

	public ReadOnlyRedisEntityRegionAccessStrategy(RedisEntityRegion region) {
		this.region = region;
		this.cache = region.getCache();
	}

	@Override
	public EntityRegion getRegion() {
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
		if (minimalPutOverride && region.contains(key)) {
			return false;
		} else {
			cache.put(key, value);
			return true;
		}
	}

	@Override
	public SoftLock lockItem(Object key, Object version) throws CacheException {
		return null;
	}

	@Override
	public SoftLock lockRegion() throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisEntityRegionAccessStrategy#lockRegion has not implemented yet!!");
	}

	@Override
	public void unlockItem(Object key, SoftLock lock) throws CacheException {
	}

	@Override
	public void unlockRegion(SoftLock lock) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisEntityRegionAccessStrategy#unlockRegion has not implemented yet!!");
	}

	@Override
	public boolean insert(Object key, Object value, Object version) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisEntityRegionAccessStrategy#insert has not implemented yet!!");
	}

	@Override
	public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisEntityRegionAccessStrategy#afterInsert has not implemented yet!!");
	}

	@Override
	public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisEntityRegionAccessStrategy#update has not implemented yet!!");
	}

	@Override
	public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisEntityRegionAccessStrategy#afterUpdate has not implemented yet!!");
	}

	@Override
	public void remove(Object key) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisEntityRegionAccessStrategy#remove has not implemented yet!!");
	}

	@Override
	public void removeAll() throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisEntityRegionAccessStrategy#removeAll has not implemented yet!!");
	}

	@Override
	public void evict(Object key) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisEntityRegionAccessStrategy#evict has not implemented yet!!");
	}

	@Override
	public void evictAll() throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisEntityRegionAccessStrategy#evictAll has not implemented yet!!");
	}
}
