package net.daum.clix.hibernate.redis.aop;

import org.hibernate.EntityMode;
import org.hibernate.cache.QueryKey;
import org.hibernate.type.Type;

public privileged aspect QueryKeyIntroduction {

    Type[] around(QueryKeyIF obj)
    : execution(public Type[] getPositionalParameterTypes()) && target(obj) {
    	try {
    		// use privileged access to return the private member
    		return ((QueryKey) obj).positionalParameterTypes;
    	}
    	// safeguard against unexpected 3rd party library versions, if using load-time weaving
    	catch (NoSuchMethodError e) {
    		//log something
    	}
    	return null;
    }

    // necessary for compilation to pass
    public Type[] QueryKeyIF.getPositionalParameterTypes() {
    	return null;
    }
    
    
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
    
    EntityMode around(QueryKeyIF obj)
    : execution(public EntityMode getEntityMode()) && target(obj) {
    	try {
    		// use privileged access to return the private member
    		return ((QueryKey) obj).entityMode;
    	}
    	// safeguard against unexpected 3rd party library versions, if using load-time weaving
    	catch (NoSuchMethodError e) {
    		//log something
    	}
    	return null;
    }
    
    // necessary for compilation to pass
    public EntityMode QueryKeyIF.getEntityMode() {
    	return null;
    }

    declare parents :QueryKey implements QueryKeyIF;

}