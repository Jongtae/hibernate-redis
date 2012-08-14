package net.daum.clix.hibernate.redis.aop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.daum.clix.hibernate.redis.RedisQueryKey;

import org.hibernate.criterion.Order;
import org.hibernate.impl.CriteriaImpl;

/**
 * Created with IntelliJ IDEA. User: jtlee Date: 8/14/12 Time: 2:48 PM To change
 * this template use File | Settings | File Templates.
 */
public aspect QueryKeyAspect {

	// pointcut generatorInterceptor() : execution(static *
	// *..hibernate.cache.QueryKey.generateQueryKey(..));
	// pointcut generatorInterceptor() : execution(*
	// *..hibernate.loader.criteria.CriteriaLoader.list(..));
	pointcut generatorInterceptor() : execution(*..hibernate.loader.criteria.CriteriaQueryTranslator.new(..));

	private ThreadLocal local = new ThreadLocal<List>();

	before() : generatorInterceptor() {
		Object[] args = thisJoinPoint.getArgs();
		CriteriaImpl criteria = (CriteriaImpl) args[1];
		System.out.println("=============" + criteria.getCacheRegion());

		List orders = new ArrayList<Order>();
		Iterator criterionIterator = criteria.iterateOrderings();
		while (criterionIterator.hasNext()) {
			CriteriaImpl.OrderEntry oe = (CriteriaImpl.OrderEntry) criterionIterator
					.next();
			orders.add(oe.getOrder());
		}

		local.set(orders);
	}
	
	after() returning(RedisQueryKey key) : execution(static * *..daum.clix.hibernate.redis.RedisQueryKey.generateKey(..)){
//		System.out.println("!!!" + local.get().toString() + thisJoinPoint.getArgs().len);
//		Object[] args = thisJoinPoint.getArgs();
		key.setOrders((List<Order>) local.get());
	}

}
