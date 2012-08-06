package net.daum.clix.hibernate.redis.region;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.strategy.RedisAccessStrategyFactory;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jtlee
 * @author 84june
 */
public abstract class RedisRegion implements Region {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static final String CACHE_LOCK_TIMEOUT_PROPERTY = "net.daum.clix.hibernate.redis.cache_lock_timeout";
	private static final int DEFAULT_CACHE_LOCK_TIMEOUT = 60000;

	/**
	 * RedisCache instance backing this Hibernate data region.
	 */
	protected final RedisCache cache;

	/**
	 * The {@link net.daum.clix.hibernate.redis.strategy.RedisAccessStrategyFactory} used for creating various access strategies
	 */
	protected final RedisAccessStrategyFactory accessStrategyFactory;

	private int cacheLockTimeout;

	/**
	 * Create a Hibernate data region backed by the given Ehcache instance.
	 */
	RedisRegion(RedisAccessStrategyFactory accessStrategyFactory, RedisCache cache, Properties properties) {
		this.accessStrategyFactory = accessStrategyFactory;
		this.cache = cache;
		String timeoutProperty = properties.getProperty(CACHE_LOCK_TIMEOUT_PROPERTY);
		this.cacheLockTimeout = timeoutProperty == null ? DEFAULT_CACHE_LOCK_TIMEOUT : Integer.parseInt(timeoutProperty);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return cache.getRegionName();
	}

	/**
	 * {@inheritDoc}
	 */
	public void destroy() throws CacheException {
		cache.destroy();
	}

	/**
	 * {@inheritDoc}
	 */
	public long getSizeInMemory() {
		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	public long getElementCountInMemory() {
		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	public long getElementCountOnDisk() {
		return -1;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map toMap() {
		return new HashMap();
	}

	/**
	 * {@inheritDoc}
	 */
	public long nextTimestamp() {
		return System.currentTimeMillis() / 100;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getTimeout() {
		return cacheLockTimeout;
	}

	/**
	 * Return the RedisCache instance backing this Hibernate data region.
	 */
	public RedisCache getRedisCache() {
		return cache;
	}

	/**
	 * Returns <code>true</code> if this region contains data for the given key.
	 * <p/>
	 * This is a Hibernate 3.5 method.
	 */
	public boolean contains(Object key) {
		return cache.exists(key.toString());
	}
}
