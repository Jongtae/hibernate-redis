package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.region.RedisEntityRegion;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadOnlyRedisEntityRegionAccessStrategy extends AbstractRedisAccessStrategy<RedisEntityRegion>
		implements EntityRegionAccessStrategy {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public ReadOnlyRedisEntityRegionAccessStrategy(RedisEntityRegion region, Settings settings) {
		super(region, settings);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EntityRegion getRegion() {
		return region;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object get(Object key, long txTimestamp) throws CacheException {
		LOG.debug("called get by K:{}", key);
		return cache.get(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {
		LOG.debug("called put by K:{}, V:{}", key, value);
		if (minimalPutOverride && region.contains(key)) {
			return false;
		} else {
			cache.put(key, value);
			return true;
		}
	}

	/**
	 * Throws UnsupportedOperationException since this cache is read-only
	 *
	 * @throws UnsupportedOperationException always
	 */
	@Override
	public SoftLock lockItem(Object key, Object version) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can't write to a readonly object");
	}

	/**
	 * A no-op since this cache is read-only
	 */
	public void unlockItem(Object key, SoftLock lock) throws CacheException {
		//throw new UnsupportedOperationException("Can't write to a readonly object");
	}

	/**
	 * This cache is asynchronous hence a no-op
	 */
	public boolean insert(Object key, Object value, Object version) throws CacheException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
		cache.put(key, value);
		return true;
	}

	/**
	 * Throws UnsupportedOperationException since this cache is read-only
	 *
	 * @throws UnsupportedOperationException always
	 */
	public boolean update(Object key, Object value, Object currentVersion, Object previousVersion) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can't write to a readonly object");
	}

	/**
	 * Throws UnsupportedOperationException since this cache is read-only
	 *
	 * @throws UnsupportedOperationException always
	 */
	public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Can't write to a readonly object");
	}
}

