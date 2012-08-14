package net.daum.clix.hibernate.redis.region;

import java.util.Properties;

import net.daum.clix.hibernate.redis.RedisCache;
import net.daum.clix.hibernate.redis.RedisQueryKey;
import net.daum.clix.hibernate.redis.aop.QueryKeyIF;
import net.daum.clix.hibernate.redis.strategy.RedisAccessStrategyFactory;

import org.hibernate.EntityMode;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.QueryResultsRegion;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class RedisQueryResultRegion extends RedisRegion implements QueryResultsRegion {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public RedisQueryResultRegion(RedisAccessStrategyFactory accessStrategyFactory, RedisCache cache, Properties properties) {
		super(accessStrategyFactory, cache, properties);
	}

	@Override
	public Object get(Object key) throws CacheException {
		 if(StringUtils.startsWithIgnoreCase(cache.getRegionName(),"@Sorted")){
	            Object[] parameters = ((QueryKeyIF) key).getPositionalParameterValues();
	            Type[] parameterTypes = ((QueryKeyIF) key).getPositionalParameterTypes();
	            EntityMode entityMode = ((QueryKeyIF) key).getEntityMode();
	            RedisQueryKey replaceKey = new RedisQueryKey(cache.getRegionName(), parameterTypes, parameters, entityMode);
	            return cache.get(replaceKey);
	        }else{
			    return cache.get(key);
	        }
	}

	@Override
	public void put(Object key, Object value) throws CacheException {
        if(StringUtils.startsWithIgnoreCase(cache.getRegionName(),"@Sorted")){
        	Object[] parameters = ((QueryKeyIF) key).getPositionalParameterValues();
            Type[] parameterTypes = ((QueryKeyIF) key).getPositionalParameterTypes();
            EntityMode entityMode = ((QueryKeyIF) key).getEntityMode();
            RedisQueryKey replaceKey = new RedisQueryKey(cache.getRegionName(), parameterTypes, parameters, entityMode);
            cache.put(replaceKey, value);
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
