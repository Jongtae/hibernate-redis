package net.daum.clix.hibernate.redis;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jtlee
 * Date: 7/27/12
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RedisCache {

    public Object read(Object key) throws CacheException;

    public Object get(Object key) throws CacheException;

    public void put(Object key, Object value) throws CacheException;

    public void update(Object key, Object value) throws CacheException;

    public void remove(Object key) throws CacheException;

    public void destroy() throws CacheException;

    public long nextTimestamp();

    public int getTimeout();

    public String getRegionName();

    public long getSizeInMemory();

    public long getElementCountInMemory();

    public long getElementCountOnDisk();

}
