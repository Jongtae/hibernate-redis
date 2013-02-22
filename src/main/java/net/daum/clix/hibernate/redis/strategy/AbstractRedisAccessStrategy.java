package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.region.RedisTransactionalRegion;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;


/**
 * Superclass for all Redis specific Hibernate AccessStrategy implementations.
 *
 * @param <T> type of the enclosed region
 * @author 84june
 */
abstract class AbstractRedisAccessStrategy<T extends RedisTransactionalRegion> {

	/**
	 * The wrapped Hibernate cache region.
	 */
	protected final T region;
	/**
	 * The settings for this persistence unit.
	 */
	protected final Settings settings;
	/**
	 * RedisCache client instance
	 */
	protected final RedisCache cache;

	/**
	 * Create an access strategy wrapping the given region.
	 */
	AbstractRedisAccessStrategy(T region, Settings settings) {
		this.region = region;
		this.settings = settings;
		this.cache = region.getRedisCache();
	}

	/**
	 * This method is a placeholder for method signatures supplied by interfaces pulled in further down the class
	 * hierarchy.
	 *
	 */
	public final boolean putFromLoad(Object key, Object value, long txTimestamp, Object version) throws CacheException {
		return putFromLoad(key, value, txTimestamp, version, settings.isMinimalPutsEnabled());
	}

	/**
	 * This method is a placeholder for method signatures supplied by interfaces pulled in further down the class
	 * hierarchy.
	 *
	 */
	public abstract boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride)
			throws CacheException;

	/**
	 * Region locks are not supported.
	 *
	 * @return <code>null</code>
	 */
	public final SoftLock lockRegion() {
		return null;
	}

	public final void unlockRegion(SoftLock lock) throws CacheException {
	}

	/**
	 * A no-op since this is an asynchronous cache access strategy.
	 *
	 */
	public void remove(Object key) throws CacheException {
	}

	/**
	 * Not supported yet!! Called to evict data from the entire region
	 *
	 */
	public final void removeAll() throws CacheException {
//		region.clear();
	}

	/**
	 * Remove the given mapping without regard to transactional safety
	 *
	 */
	public final void evict(Object key) throws CacheException {
		region.remove(key);
	}

	/**
	 * Not Supported yet!! Remove all mappings without regard to transactional safety
	 *
	 */
	public final void evictAll() throws CacheException {
//		region.clear();
	}
}

