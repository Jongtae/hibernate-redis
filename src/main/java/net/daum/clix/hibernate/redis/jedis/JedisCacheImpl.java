package net.daum.clix.hibernate.redis.jedis;

import net.daum.clix.hibernate.redis.RedisCache;
import org.hibernate.cache.CacheException;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author jtlee
 * @author 84june
 */
public class JedisCacheImpl implements RedisCache {

	private JedisPool jedisPool;

	private Jedis jedis;

	private String regionName;

	public JedisCacheImpl(JedisPool jedisPool, String regionName) {
		this.jedisPool = jedisPool;
		this.regionName = regionName;
		this.jedis = jedisPool.getResource();
	}

	@Override
	public Object get(Object key) throws CacheException {
		Object o = null;

		byte[] k = serializeObject(key.toString());
		byte[] v = jedis.get(k);
		if (v != null && v.length > 0) {
			o = deserializeObject(v);
		}

		return o;
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
		byte[] k = serializeObject(key.toString());
		byte[] v = serializeObject(value);

		jedis.set(k, v);
	}

	@Override
	public void remove(Object key) throws CacheException {
		jedis.del(serializeObject(key.toString()));
	}

	@Override
	public boolean exists(String key) {
		return jedis.exists(serializeObject(key.toString()));
	}

//  TODO : Check any concurrency issues around...
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

	@Override
	public void destory() {
		jedisPool.returnResource(jedis);
	}

	private byte[] serializeObject(Object obj) {
		SerializingConverter sc = new SerializingConverter();
		return sc.convert(obj);
	}

	private Object deserializeObject(byte[] b) {
		DeserializingConverter dc = new DeserializingConverter();
		return dc.convert(b);
	}
}
