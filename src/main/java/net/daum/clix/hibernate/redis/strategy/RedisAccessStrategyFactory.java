package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.region.RedisCollectionRegion;
import net.daum.clix.hibernate.redis.region.RedisEntityRegion;

import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.access.EntityRegionAccessStrategy;

/**
 * Factory to create {@link EntityRegionAccessStrategy}
 *
 * @author 84june
 */
public interface RedisAccessStrategyFactory {

	/**
	 * Create {@link EntityRegionAccessStrategy} for the input {@link RedisEntityRegion} and {@link AccessType}
	 *
	 * @param entityRegion
	 * @param accessType
	 * @return the created {@link EntityRegionAccessStrategy}
	 */
	public EntityRegionAccessStrategy createEntityRegionAccessStrategy(RedisEntityRegion entityRegion, AccessType accessType);

	/**
	 * Create {@link CollectionRegionAccessStrategy} for the input {@link RedisCollectionRegion} and {@link AccessType}
	 *
	 * @param collectionRegion
	 * @param accessType
	 * @return the created {@link CollectionRegionAccessStrategy}
	 */
	public CollectionRegionAccessStrategy createCollectionRegionAccessStrategy(RedisCollectionRegion collectionRegion,
			AccessType accessType);

}
