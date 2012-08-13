package net.daum.clix.hibernate.redis.region;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.aop.QueryKeyIF;
import net.daum.clix.hibernate.redis.strategy.RedisAccessStrategyFactory;

import org.hibernate.EntityMode;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.QueryKey;
import org.hibernate.cache.QueryResultsRegion;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import org.springframework.util.StringUtils;

public class RedisQueryResultRegion extends RedisRegion implements QueryResultsRegion {

	public RedisQueryResultRegion(RedisAccessStrategyFactory accessStrategyFactory, RedisCache cache, Properties properties) {
		super(accessStrategyFactory, cache, properties);
	}

	@Override
	public Object get(Object key) throws CacheException {
		return cache.get(key);
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
        if(StringUtils.startsWithIgnoreCase(cache.getRegionName(),"@Sorted")){
            Object[] parameters = ((QueryKeyIF) key).getPositionalParameterValues();
            System.out.println("====== " + parameters.toString());
        }else{
		    cache.put(key, value);
        }
	}

	@Override
	public void evict(Object key) throws CacheException {
		cache.remove(key);
	}

	@Override
	public void evictAll() throws CacheException {
		throw new UnsupportedOperationException("RedisQueryResultRegion#evictAll has not implemented yet!!");
	}
}
