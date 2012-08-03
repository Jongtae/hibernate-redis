package net.daum.clix.hibernate.redis.region;

import java.util.Map;

import net.daum.clix.hibernate.redis.RedisCache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.Region;

/**
 * Created with IntelliJ IDEA.
 * User: jtlee
 * Date: 7/27/12
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRedisRegion implements Region {

	protected RedisCache cache;

	protected AbstractRedisRegion(RedisCache cache) {
		this.cache = cache;
	}

	@Override
	public String getName() {
		return cache.getRegionName();
	}

	@Override
	public void destroy() throws CacheException {
		cache.destroy();
	}

	@Override
	public boolean contains(Object key) {
		return cache.get(key) != null;
	}

	@Override
	public long getSizeInMemory() {
		return cache.getSizeInMemory();
	}

	@Override
	public long getElementCountInMemory() {
		return cache.getElementCountInMemory();
	}

	@Override
	public long getElementCountOnDisk() {
		return cache.getElementCountOnDisk();
	}

	@Override
	public Map toMap() {
		return null;
	}

	@Override
	public long nextTimestamp() {
		return cache.nextTimestamp();
	}

	@Override
	public int getTimeout() {
		return cache.getTimeout();
	}

	public RedisCache getCache() {
		return cache;
	}
}
