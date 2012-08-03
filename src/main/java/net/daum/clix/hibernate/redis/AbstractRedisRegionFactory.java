package net.daum.clix.hibernate.redis;

import java.util.Properties;

import net.daum.clix.hibernate.redis.region.RedisCollectionRegion;
import net.daum.clix.hibernate.redis.region.RedisEntityRegion;
import net.daum.clix.hibernate.redis.region.RedisQueryResultRegion;
import net.daum.clix.hibernate.redis.region.RedisTimestampsRegion;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CollectionRegion;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.QueryResultsRegion;
import org.hibernate.cache.RegionFactory;
import org.hibernate.cache.TimestampsRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;

abstract class AbstractRedisRegionFactory implements RegionFactory {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	protected JedisPool pool;

	protected Properties properties;
	protected Settings settings;

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
		return new RedisEntityRegion(getCache(regionName));
	}

	@Override
	public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
		return new RedisCollectionRegion(getCache(regionName));
	}

	@Override
	public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
		return new RedisQueryResultRegion(getCache(regionName));
	}

	@Override
	public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
		return new RedisTimestampsRegion(getCache(regionName));
	}

	private RedisCache getCache(String regionName) {
		return new Cache(pool, regionName);
	}

}
