package net.daum.clix.hibernate.redis.region;

import org.hibernate.cache.*;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cfg.Settings;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jtlee
 * Date: 7/27/12
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractRedisRegionFactory implements RegionFactory {
    @Override
    public void start(Settings settings, Properties properties) throws CacheException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void stop() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isMinimalPutsEnabledByDefault() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AccessType getDefaultAccessType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long nextTimestamp() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
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
}
