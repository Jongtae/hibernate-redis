package net.daum.clix.hibernate.redis.aop;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.impl.CriteriaImpl;


/**
 * Created with IntelliJ IDEA.
 * User: jtlee
 * Date: 8/14/12
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
public aspect QueryKeyAspect {

//	pointcut generatorInterceptor() : execution(static * *..hibernate.cache.QueryKey.generateQueryKey(..));
//	pointcut generatorInterceptor() : execution(* *..hibernate.loader.criteria.CriteriaLoader.list(..));
	pointcut generatorInterceptor() : execution(*..hibernate.loader.criteria.CriteriaQueryTranslator.new(..));
    
	private ThreadLocal local = new ThreadLocal<Map>();
	
    before() : generatorInterceptor() {
    	Object[] args = thisJoinPoint.getArgs();
    	CriteriaImpl criteria = (CriteriaImpl) args[1];
    	System.out.println("=============" + criteria.getCacheRegion());
    	
//    	StringBuffer orderBy = new StringBuffer( 30 );
//		Iterator criterionIterator = criteria.iterateOrderings();
//		while ( criterionIterator.hasNext() ) {
//			CriteriaImpl.OrderEntry oe = ( CriteriaImpl.OrderEntry ) criterionIterator.next();
//			orderBy.append( oe.getOrder().toSqlString( oe.getCriteria(), (CriteriaQuery) this ) );
//			if ( criterionIterator.hasNext() ) {
//				orderBy.append( ", " );
//			}
//		}
//    	Map map = new HashMap<String, String>();
//    	map.put(criteria.getCacheRegion(), criteria.getO)
		
    }

}
