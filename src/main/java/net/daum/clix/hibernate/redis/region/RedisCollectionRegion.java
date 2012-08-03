package net.daum.clix.hibernate.redis.region;

import java.util.Map;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.strategy.ReadWriteRedisCollectionRegionAccessStrategy;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;

/**
 * @author 84june
 */
public class RedisCollectionRegion extends AbstractRedisRegion implements CollectionRegion {

	public RedisCollectionRegion(RedisCache cache) {
		super(cache);
	}

	@Override
	public CollectionRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		if (AccessType.READ_WRITE.equals(accessType)) {
			return new ReadWriteRedisCollectionRegionAccessStrategy(this);
		}

		throw new IllegalAccessError("RedisCollectionRegion#buildAccessStrategy has not implemented yet!!");
	}

	@Override
	public boolean isTransactionAware() {
		return false;
	}

	@Override
	public CacheDataDescription getCacheDataDescription() {
		throw new IllegalAccessError("RedisCollectionRegion#getCacheDataDescription has not implemented yet!!");
	}

	@Override
	public String getName() {
		throw new IllegalAccessError("RedisCollectionRegion#getName has not implemented yet!!");
	}

	@Override
	public void destroy() throws CacheException {
		throw new IllegalAccessError("RedisCollectionRegion#destroy has not implemented yet!!");
	}

	@Override
	public boolean contains(Object key) {
		throw new IllegalAccessError("RedisCollectionRegion#contains has not implemented yet!!");
	}

	@Override
	public long getSizeInMemory() {
		throw new IllegalAccessError("RedisCollectionRegion#getSizeInMemory has not implemented yet!!");
	}

	@Override
	public long getElementCountInMemory() {
		throw new IllegalAccessError("RedisCollectionRegion#getElementCountInMemory has not implemented yet!!");
	}

	@Override
	public long getElementCountOnDisk() {
		throw new IllegalAccessError("RedisCollectionRegion#getElementCountOnDisk has not implemented yet!!");
	}

	@Override
	public Map toMap() {
		throw new IllegalAccessError("RedisCollectionRegion#toMap has not implemented yet!!");
	}

	@Override
	public long nextTimestamp() {
		throw new IllegalAccessError("RedisCollectionRegion#nextTimestamp has not implemented yet!!");
	}

	@Override
	public int getTimeout() {
		throw new IllegalAccessError("RedisCollectionRegion#getTimeout has not implemented yet!!");
	}
}
