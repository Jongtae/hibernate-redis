package net.daum.clix.hibernate.redis.strategy;

import net.daum.clix.hibernate.redis.region.RedisCollectionRegion;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * User: jtlee
 * Date: 3/5/13
 * Time: 1:50 PM
 */
public class ReadWriteRedisCollectionRegionAcessStrategy extends AbstractReadWriteRedisAccessStrategy<RedisCollectionRegion> implements CollectionRegionAccessStrategy {

    /**
     * Create an access strategy wrapping the given region.
     */
    ReadWriteRedisCollectionRegionAcessStrategy(RedisCollectionRegion region, Settings settings) {
        super(region, settings);
    }

    @Override
    public Object get(Object key, long txTimestamp) throws CacheException {
        throw new NotImplementedException(); //TODO still not implemented
    }

    @Override
    public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride) throws CacheException {
        throw new NotImplementedException(); //TODO still not implemented
    }

    @Override
    public SoftLock lockItem(Object key, Object version) throws CacheException {
        throw new NotImplementedException(); //TODO still not implemented
    }

    @Override
    public void unlockItem(Object key, SoftLock lock) throws CacheException {
        throw new NotImplementedException(); //TODO still not implemented
    }

    @Override
    public CollectionRegion getRegion() {
        return region;
    }
}
