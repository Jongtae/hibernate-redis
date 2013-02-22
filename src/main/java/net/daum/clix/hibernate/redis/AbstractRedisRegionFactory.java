package net.daum.clix.hibernate.redis;

import net.daum.clix.hibernate.redis.jedis.JedisCacheImpl;
import net.daum.clix.hibernate.redis.region.RedisCollectionRegion;
import net.daum.clix.hibernate.redis.region.RedisEntityRegion;
import net.daum.clix.hibernate.redis.region.RedisQueryResultRegion;
import net.daum.clix.hibernate.redis.region.RedisTimestampsRegion;
import net.daum.clix.hibernate.redis.strategy.RedisAccessStrategyFactory;
import net.daum.clix.hibernate.redis.strategy.RedisAccessStrategyFactoryImpl;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.*;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;

import java.util.Properties;

/**
 * @author jtlee
 * @author 84june
 */
abstract class AbstractRedisRegionFactory implements RegionFactory {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	protected JedisPool pool;

	protected Properties properties;

	protected Settings settings;

	/**
	 * {@link RedisAccessStrategyFactory} for creating various access strategies
	 */
	private final RedisAccessStrategyFactory accessStrategyFactory = new RedisAccessStrategyFactoryImpl();

	@Override
	public boolean isMinimalPutsEnabledByDefault() {
		return true;
	}

	@Override
	public AccessType getDefaultAccessType() {
		return AccessType.READ_WRITE;
	}

	@Override
	public long nextTimestamp() {
		return System.currentTimeMillis() / 100;
	}

	@Override
	public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
		return new RedisEntityRegion(accessStrategyFactory, getRedisCache(regionName), properties, metadata, settings);
	}

	@Override
	public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata)
			throws CacheException {
		return new RedisCollectionRegion(accessStrategyFactory, getRedisCache(regionName), properties, metadata, settings);
	}

	@Override
	public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
		return new RedisQueryResultRegion(accessStrategyFactory, getRedisCache(regionName), properties);
	}

	@Override
	public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
		return new RedisTimestampsRegion(accessStrategyFactory, getRedisCache(regionName), properties);
	}

	private RedisCache getRedisCache(String regionName) {
		return new JedisCacheImpl(pool, regionName);
	}

}
