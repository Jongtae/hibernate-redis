package net.daum.clix.hibernate.redis.region;

import java.util.Properties;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.strategy.RedisAccessStrategyFactory;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.EntityRegionAccessStrategy;
import org.hibernate.cfg.Settings;

public class RedisEntityRegion extends RedisTransactionalRegion implements EntityRegion {

	public RedisEntityRegion(RedisAccessStrategyFactory accessStrategyFactory, RedisCache cache, Properties properties, CacheDataDescription metadata, Settings settings) {
		super(accessStrategyFactory, cache, properties, metadata, settings);
	}

	@Override
	public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		return accessStrategyFactory.createEntityRegionAccessStrategy(this, accessType);
	}

}
