package net.daum.clix.hibernate.redis.aop;

import org.hibernate.cache.QueryKey;

public privileged aspect QueryKeyIntroduction {
    
    Object[] around(QueryKeyIF obj)
    : execution(public Object[] getPositionalParameterValues()) && target(obj) {
    	try {
    		// use privileged access to return the private member
    		return ((QueryKey) obj).positionalParameterValues;
    	}
    	// safeguard against unexpected 3rd party library versions, if using load-time weaving
    	catch (NoSuchMethodError e) {
    		//log something
    	}
    	return null;
    }

    // necessary for compilation to pass
    public Object[] QueryKeyIF.getPositionalParameterValues() {
    	return null;
    }

    declare parents :QueryKey implements QueryKeyIF;

}