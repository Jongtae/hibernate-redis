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

	private JedisPool pool;

	private String regionName;

	public JedisCacheImpl(JedisPool pool, String regionName) {
		this.pool = pool;
		this.regionName = regionName;
	}

	@Override
	public Object get(Object key) throws CacheException {
		Object o = null;

		Jedis jedis = pool.getResource();
		try {
			byte[] k = serializeObject(key.toString());
			byte[] v = jedis.get(k);
			if (v != null && v.length > 0) {
				o = deserializeObject(v);
			}
		} finally {
			pool.returnResource(jedis);
		}

		return o;
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
		byte[] k = serializeObject(key.toString());
		byte[] v = serializeObject(value);

		Jedis jedis = pool.getResource();
		try {
			jedis.set(k, v);
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public void remove(Object key) throws CacheException {
		Jedis jedis = pool.getResource();
		try {
			jedis.del(key.toString());
		} finally {
			pool.returnResource(jedis);
		}
	}

	@Override
	public void destroy() throws CacheException {
		pool.destroy();
	}

	@Override
	public boolean exists(String key) {
		Jedis jedis = pool.getResource();
		boolean b = false;
		try {
			b = jedis.exists(key);
		} finally {
			pool.returnResource(jedis);
		}
		return b;
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

	private byte[] serializeObject(Object obj) {
		SerializingConverter sc = new SerializingConverter();
		return sc.convert(obj);
	}

	private Object deserializeObject(byte[] b) {
		DeserializingConverter dc = new DeserializingConverter();
		return dc.convert(b);
	}
}
