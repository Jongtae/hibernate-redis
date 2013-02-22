package net.daum.clix.hibernate.redis.region;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.strategy.RedisAccessStrategyFactory;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cfg.Settings;

import java.util.Properties;

public class RedisEntityRegion extends RedisTransactionalRegion implements EntityRegion {

	public RedisEntityRegion(RedisAccessStrategyFactory accessStrategyFactory, RedisCache cache, Properties properties, CacheDataDescription metadata, Settings settings) {
		super(accessStrategyFactory, cache, properties, metadata, settings);
	}

	@Override
	public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		return accessStrategyFactory.createEntityRegionAccessStrategy(this, accessType);
	}

}
