package net.daum.clix.hibernate.redis.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 84june
 */
@Aspect
@Deprecated
public class CriteriaLoaderLTWAspect {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Before("execution(* org.hibernate.loader.criteria.CriteriaLoader.CriteriaLoader*(..))")
	public void advice(JoinPoint joinPoint) {
		LOG.debug("CriteriaLoaderAspect.advice() called on {}", joinPoint);
//		System.out.printf("CriteriaLoaderAspect.advice() called on '%s'%n", joinPoint);
	}
}
