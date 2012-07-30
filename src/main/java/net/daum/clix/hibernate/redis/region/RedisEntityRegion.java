package net.daum.clix.hibernate.redis.region;

import net.daum.clix.hibernate.redis.RedisCache;
import org.hibernate.cache.CacheDataDescription;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.EntityRegion;
import org.hibernate.cache.access.AccessType;
import org.hibernate.cache.access.EntityRegionAccessStrategy;

/**
 * Created with IntelliJ IDEA.
 * User: jtlee
 * Date: 7/27/12
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class RedisEntityRegion extends AbstractRedisRegion implements EntityRegion {

    protected RedisEntityRegion(RedisCache cache) {
        super(cache);
    }

    @Override
    public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isTransactionAware() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CacheDataDescription getCacheDataDescription() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
