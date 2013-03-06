package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.region.RedisEntityRegion;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jtlee
 * @author 84june
 */
public class NonStrictReadWriteRedisEntityRegionAccessStrategy extends AbstractRedisAccessStrategy<RedisEntityRegion>
		implements EntityRegionAccessStrategy {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public NonStrictReadWriteRedisEntityRegionAccessStrategy(RedisEntityRegion region, Settings settings) {
		super(region, settings);
	}

	@Override
	public EntityRegion getRegion() {
		return this.region;
	}

	@Override
	public Object get(Object key, long txTimestamp) throws CacheException {
		LOG.debug("called get K:{}", key);
		return cache.get(key);
	}

	@Override
	public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride)
			throws CacheException {
		LOG.debug("called putFromLoad by K:{}, V:{}", key, value);
		if (minimalPutOverride && region.contains(key)) {
			return false;
		} else {
			cache.put(key, value);
			return true;
		}
	}

	/**
	 * Since this is a non-strict read/write strategy item locking is not used.
	 */
	@Override
	public SoftLock lockItem(Object key, Object version) throws CacheException {
		return null;
	}

	/**
	 * Since this is a non-strict read/write strategy item locking is not used.
	 */
	@Override
	public void unlockItem(Object key, SoftLock lock) throws CacheException {
		region.remove(key);
	}

	/**
	 * Returns <code>false</code> since this is an asynchronous cache access strategy.
	 */
	@Override
	public boolean insert(Object key, Object value, Object version) throws CacheException {
		return false;
	}

	/**
	 * Returns <code>false</code> since this is a non-strict read/write cache access strategy
	 */
	@Override
	public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
		return false;
	}

	/**
	 * Removes the entry since this is a non-strict read/write cache strategy.
	 */
	@Override
	public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws CacheException {
		LOG.debug("called update by K:{}, V:{}", key, value);
		remove(key);
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock) throws CacheException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Object key) throws CacheException {
		cache.remove(key);
	}
}
