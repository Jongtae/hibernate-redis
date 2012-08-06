package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.region.RedisCollectionRegion;
import net.daum.clix.hibernate.redis.region.RedisEntityRegion;

import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.access.EntityRegionAccessStrategy;

/**
 * An implementation of {@link RedisAccessStrategyFactory}
 *
 * @author 84june
 */
public class RedisAccessStrategyFactoryImpl implements RedisAccessStrategyFactory {

	@Override
	public EntityRegionAccessStrategy createEntityRegionAccessStrategy(RedisEntityRegion entityRegion, AccessType accessType) {
		if (AccessType.READ_ONLY.equals(accessType)) {
			return new ReadOnlyRedisEntityRegionAccessStrategy(entityRegion);
		} else if (AccessType.NONSTRICT_READ_WRITE.equals(accessType)) {
			return new NonStrictReadWriteRedisEntityRegionAccessStrategy(entityRegion);
		}

		throw new UnsupportedOperationException("Hibernate-redis supports READ_ONLY and NONSTRICT_READ_WRITE as concurrency strategies only.");
	}

	@Override
	public CollectionRegionAccessStrategy createCollectionRegionAccessStrategy(RedisCollectionRegion collectionRegion, AccessType accessType) {
		if (AccessType.READ_ONLY.equals(accessType)) {
			return new ReadOnlyRedisCollectionRegionAccessStrategy(collectionRegion);
		} else if (AccessType.NONSTRICT_READ_WRITE.equals(accessType)) {
			return new NonStrictReadWriteRedisCollectionRegionAccessStrategy(collectionRegion);
		}

		throw new UnsupportedOperationException("Hibernate-redis supports READ_ONLY and NONSTRICT_READ_WRITE as concurrency strategies only.");
	}
}

