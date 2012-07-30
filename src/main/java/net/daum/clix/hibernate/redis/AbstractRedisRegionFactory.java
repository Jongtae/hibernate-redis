package net.daum.clix.hibernate.redis;

import org.hibernate.cache.*;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cfg.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jtlee
 * Date: 7/27/12
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractRedisRegionFactory implements RegionFactory {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private JedisPool pool;

    private Properties properties;
    private Settings settings;

    @Override
    public void start(Settings settings, Properties properties) throws CacheException {
        this.settings = settings;
        this.properties = properties;
        logger.info("Starting MemcachedClient...");
        pool = new JedisPool(new JedisPoolConfig(), "localhost");
    }

    @Override
    public void stop() {
        pool.destroy();
    }

    @Override
    public boolean isMinimalPutsEnabledByDefault() {
        return true;
    }

    @Override
    public AccessType getDefaultAccessType() {
        return AccessType.READ_WRITE;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long nextTimestamp() {
        return System.currentTimeMillis() / 100;
    }

    @Override
    public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata) throws CacheException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties) throws CacheException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties) throws CacheException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private RedisCache getCache(String regionName)
    {
        return new Cache(pool, regionName);
    }

}
