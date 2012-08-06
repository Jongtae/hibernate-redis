package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.region.RedisTransactionalRegion;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.access.SoftLock;
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
	 * @see org.hibernate.cache.access.EntityRegionAccessStrategy#putFromLoad(java.lang.Object, java.lang.Object, long, java.lang.Object)
	 * @see org.hibernate.cache.access.CollectionRegionAccessStrategy#putFromLoad(java.lang.Object, java.lang.Object, long, java.lang.Object)
	 */
	public final boolean putFromLoad(Object key, Object value, long txTimestamp, Object version) throws CacheException {
		return putFromLoad(key, value, txTimestamp, version, settings.isMinimalPutsEnabled());
	}

	/**
	 * This method is a placeholder for method signatures supplied by interfaces pulled in further down the class
	 * hierarchy.
	 *
	 * @see org.hibernate.cache.access.EntityRegionAccessStrategy#putFromLoad(java.lang.Object, java.lang.Object, long, java.lang.Object, boolean)
	 * @see org.hibernate.cache.access.CollectionRegionAccessStrategy#putFromLoad(java.lang.Object, java.lang.Object, long, java.lang.Object, boolean)
	 */
	public abstract boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride)
			throws CacheException;

	/**
	 * Region locks are not supported.
	 *
	 * @return <code>null</code>
	 * @see org.hibernate.cache.access.EntityRegionAccessStrategy#lockRegion()
	 * @see org.hibernate.cache.access.CollectionRegionAccessStrategy#lockRegion()
	 */
	public final SoftLock lockRegion() {
		return null;
	}

	/**
	 * TODO:: Ehcache's implementation of this function is clear whole region.(what should do?)
	 *
	 * @see org.hibernate.cache.access.EntityRegionAccessStrategy#unlockRegion(org.hibernate.cache.access.SoftLock)
	 * @see org.hibernate.cache.access.CollectionRegionAccessStrategy#unlockRegion(org.hibernate.cache.access.SoftLock)
	 */
	public final void unlockRegion(SoftLock lock) throws CacheException {
	}

	/**
	 * A no-op since this is an asynchronous cache access strategy.
	 *
	 * @see org.hibernate.cache.access.EntityRegionAccessStrategy#remove(java.lang.Object)
	 * @see org.hibernate.cache.access.CollectionRegionAccessStrategy#remove(java.lang.Object)
	 */
	public void remove(Object key) throws CacheException {
	}

	/**
	 * Not supported yet!! Called to evict data from the entire region
	 *
	 * @throws CacheException Propogated from underlying {@link org.hibernate.cache.Region}
	 * @see org.hibernate.cache.access.EntityRegionAccessStrategy#removeAll()
	 * @see org.hibernate.cache.access.CollectionRegionAccessStrategy#removeAll()
	 */
	public final void removeAll() throws CacheException {
//		region.clear();
	}

	/**
	 * Remove the given mapping without regard to transactional safety
	 *
	 * @see org.hibernate.cache.access.EntityRegionAccessStrategy#evict(java.lang.Object)
	 * @see org.hibernate.cache.access.CollectionRegionAccessStrategy#evict(java.lang.Object)
	 */
	public final void evict(Object key) throws CacheException {
		region.remove(key);
	}

	/**
	 * Not Supported yet!! Remove all mappings without regard to transactional safety
	 *
	 * @see org.hibernate.cache.access.EntityRegionAccessStrategy#evictAll()
	 * @see org.hibernate.cache.access.CollectionRegionAccessStrategy#evictAll()
	 */
	public final void evictAll() throws CacheException {
//		region.clear();
	}
}

