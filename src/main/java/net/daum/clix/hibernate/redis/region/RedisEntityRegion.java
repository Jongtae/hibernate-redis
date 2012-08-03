package net.daum.clix.hibernate.redis.region;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.strategy.ReadOnlyRedisEntityRegionAccessStrategy;
import net.daum.clix.hibernate.redis.strategy.ReadWriteRedisEntityRegionAccessStrategy;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.EntityRegionAccessStrategy;

public class RedisEntityRegion extends AbstractRedisRegion implements EntityRegion {

	public RedisEntityRegion(RedisCache cache) {
		super(cache);
	}

	@Override
	public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		if (AccessType.READ_WRITE.equals(accessType)) {
			return new ReadWriteRedisEntityRegionAccessStrategy(this);
		} else if (AccessType.READ_ONLY.equals(accessType)) {
			return new ReadOnlyRedisEntityRegionAccessStrategy(this);
		}

		throw new IllegalAccessError("RedisEntityRegion#buildAccessStrategy has not implemented yet!!");
	}

	@Override
	public boolean isTransactionAware() {
		throw new IllegalAccessError("RedisEntityRegion#isTransactionAware has not implemented yet!!");
	}

	@Override
	public CacheDataDescription getCacheDataDescription() {
		throw new IllegalAccessError("RedisEntityRegion#getCacheDataDescription has not implemented yet!!");
	}
}
