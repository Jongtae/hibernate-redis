package net.daum.clix.hibernate.redis.region;

import java.util.Properties;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.strategy.RedisAccessStrategyFactory;

import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.TransactionalDataRegion;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 84june
 */
public class RedisTransactionalRegion extends RedisRegion implements TransactionalDataRegion {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	/**
	 * Hibernate settings associated with the persistence unit.
	 */
	protected final Settings settings;

	/**
	 * Metadata associated with the objects stored in the region.
	 */
	protected final CacheDataDescription metadata;

	RedisTransactionalRegion(RedisAccessStrategyFactory accessStrategyFactory, RedisCache cache, Properties properties,
			CacheDataDescription metadata, Settings settings) {
		super(accessStrategyFactory, cache, properties);
		this.metadata = metadata;
		this.settings = settings;
	}

	@Override
	public boolean isTransactionAware() {
		return false;
	}

	@Override
	public CacheDataDescription getCacheDataDescription() {
		return metadata;
	}

	public Settings getSettings() {
		return settings;
	}

	public final void remove(Object key) {
		cache.remove(key);
	}
}

