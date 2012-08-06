package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.region.RedisCollectionRegion;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.access.SoftLock;

/**
 * @author jtlee
 */
public class ReadOnlyRedisCollectionRegionAccessStrategy implements CollectionRegionAccessStrategy {
	public ReadOnlyRedisCollectionRegionAccessStrategy(RedisCollectionRegion redisCollectionRegion) {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#ReadOnlyRedisCollectionRegionAccessStrategy has not implemented yet!!");
	}

	@Override
	public CollectionRegion getRegion() {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#getRegion has not implemented yet!!");
	}

	@Override
	public Object get(Object key, long txTimestamp) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#get has not implemented yet!!");
	}

	@Override
	public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#putFromLoad has not implemented yet!!");
	}

	@Override
	public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#putFromLoad has not implemented yet!!");
	}

	@Override
	public SoftLock lockItem(Object key, Object version) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#lockItem has not implemented yet!!");
	}

	@Override
	public SoftLock lockRegion() throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#lockRegion has not implemented yet!!");
	}

	@Override
	public void unlockItem(Object key, SoftLock lock) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#unlockItem has not implemented yet!!");
	}

	@Override
	public void unlockRegion(SoftLock lock) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#unlockRegion has not implemented yet!!");
	}

	@Override
	public void remove(Object key) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#remove has not implemented yet!!");
	}

	@Override
	public void removeAll() throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#removeAll has not implemented yet!!");
	}

	@Override
	public void evict(Object key) throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#evict has not implemented yet!!");
	}

	@Override
	public void evictAll() throws CacheException {
		throw new IllegalAccessError("ReadOnlyRedisCollectionRegionAccessStrategy#evictAll has not implemented yet!!");
	}
}
