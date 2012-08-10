package net.daum.clix.hibernate.redis.aop.ltw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspect for generating cache key from Criteria
 *
 * @author 84june
 */
public aspect CriteriaLoaderAspect {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	pointcut test()
			: execution(* org.hibernate.loader.criteria.CriteriaLoader.CriteriaLoader*(..));

	after() returning: test() {
		LOG.debug("CriteriaLoaderAspect.advice() called");
	}
}
