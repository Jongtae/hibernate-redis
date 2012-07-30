package net.daum.clix.hibernate.redis;

import org.hibernate.cache.CacheException;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created with IntelliJ IDEA.
 * User: jtlee
 * Date: 7/27/12
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Cache implements RedisCache{

    private JedisPool pool;

    private Jedis jedis;

    private String regionName;

    public Cache(JedisPool pool, String regionName) {
        this.pool = pool;
        this.jedis = pool.getResource();
        this.regionName = regionName;
    }

    @Override
    public Object read(Object key) throws CacheException {
        return get(key);
    }

    @Override
    public Object get(Object key) throws CacheException {
        byte[] k = serializeObject(key.toString());
        byte[] v = jedis.get(k);
        if (v != null && v.length > 0){
            Object o = deserializeObject(v);
            return o;
        }

        return null;
    }

    @Override
    public void put(Object key, Object value) throws CacheException {
        byte[] k = serializeObject(key.toString());
        byte[] v = serializeObject(value);
        jedis.set(k, v);
    }

    @Override
    public void update(Object key, Object value) throws CacheException {
        put(key,value);
    }

    @Override
    public void remove(Object key) throws CacheException {
        jedis.del(key.toString());
    }

    @Override
    public void destroy() throws CacheException {
        pool.returnResource(jedis);
    }

//  TODO 나중에 concurrent 문제가 없는 지 확인!
//    @Override
//    public void lock(Object key) throws CacheException {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public void unlock(Object key) throws CacheException {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }

    @Override
    public long nextTimestamp() {
        return System.currentTimeMillis() / 100;
    }

    @Override
    public int getTimeout() {
        return 0;
    }

    @Override
    public String getRegionName() {
        return this.regionName;
    }

    @Override
    public long getSizeInMemory() {
        return -1;
    }

    @Override
    public long getElementCountInMemory() {
        return -1;
    }

    @Override
    public long getElementCountOnDisk() {
        return -1;
    }

    private byte[] serializeObject(Object obj){
        SerializingConverter sc = new SerializingConverter();
        return sc.convert(obj);
    }

    private Object deserializeObject(byte[] b){
        DeserializingConverter dc = new DeserializingConverter();
        return dc.convert(b);
    }
}
