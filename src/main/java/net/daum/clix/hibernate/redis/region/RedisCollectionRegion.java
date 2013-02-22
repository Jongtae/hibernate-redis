package net.daum.clix.hibernate.redis.region;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.strategy.RedisAccessStrategyFactory;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cfg.Settings;

import java.util.Properties;

public class RedisCollectionRegion extends RedisTransactionalRegion implements CollectionRegion {

	public RedisCollectionRegion(RedisAccessStrategyFactory accessStrategyFactory, RedisCache cache, Properties properties, CacheDataDescription metadata, Settings settings) {
		super(accessStrategyFactory, cache, properties, metadata, settings);
	}

	@Override
	public CollectionRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		return accessStrategyFactory.createCollectionRegionAccessStrategy(this, accessType);
	}
}

