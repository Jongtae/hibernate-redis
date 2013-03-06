package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.region.RedisCollectionRegion;
import net.daum.clix.hibernate.redis.region.RedisEntityRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of {@link RedisAccessStrategyFactory}
 *
 * @author 84june
 */
public class RedisAccessStrategyFactoryImpl implements RedisAccessStrategyFactory {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Override
	public EntityRegionAccessStrategy createEntityRegionAccessStrategy(RedisEntityRegion entityRegion, AccessType accessType) {
		LOG.debug("called createEntityRegionAccessStrategy by accessType:{}", accessType);

		if (AccessType.READ_ONLY.equals(accessType)) {
			return new ReadOnlyRedisEntityRegionAccessStrategy(entityRegion, entityRegion.getSettings());
		} else if (AccessType.NONSTRICT_READ_WRITE.equals(accessType)) {
			return new NonStrictReadWriteRedisEntityRegionAccessStrategy(entityRegion, entityRegion.getSettings());
		} else if (AccessType.READ_WRITE.equals(accessType)){
            return new ReadWriteRedisEntityRegionAcessStrategy(entityRegion, entityRegion.getSettings());
        }

		throw new UnsupportedOperationException("Hibernate-redis supports READ_ONLY and NONSTRICT_READ_WRITE as concurrency strategies only.");
	}

	@Override
	public CollectionRegionAccessStrategy createCollectionRegionAccessStrategy(RedisCollectionRegion collectionRegion, AccessType accessType) {
		LOG.debug("called createCollectionRegionAccessStrategy by accessType:{}", accessType);
		if (AccessType.READ_ONLY.equals(accessType)) {
			return new ReadOnlyRedisCollectionRegionAccessStrategy(collectionRegion, collectionRegion.getSettings());
		} else if (AccessType.NONSTRICT_READ_WRITE.equals(accessType)) {
			return new NonStrictReadWriteRedisCollectionRegionAccessStrategy(collectionRegion, collectionRegion.getSettings());
		} else if (AccessType.READ_WRITE.equals(accessType)) {
            return new ReadWriteRedisCollectionRegionAcessStrategy(collectionRegion, collectionRegion.getSettings());
        }

		throw new UnsupportedOperationException("Hibernate-redis supports READ_ONLY and NONSTRICT_READ_WRITE as concurrency strategies only.");
	}
}

