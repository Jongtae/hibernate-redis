package net.daum.clix.hibernate.redis;

import java.io.Serializable;
import java.util.List;

import org.hibernate.EntityMode;
import org.hibernate.criterion.Order;
import org.hibernate.type.Type;

public class RedisQueryKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1832038460666376370L;

	private String name;
	
	private Type[] parameterTypes;
	private Object[] parameters;
	private EntityMode entityMode;
	private List<Order> orders;

	public RedisQueryKey(String name, Type[] parameterTypes,
			Object[] parameters, EntityMode entityMode) {
		super();
		this.name = name;
		this.parameterTypes = parameterTypes;
		this.parameters = parameters;
		this.entityMode = entityMode;
	}
	
	public static RedisQueryKey generateKey(String name, Type[] parameterTypes,
			Object[] parameters, EntityMode entityMode){
		return new RedisQueryKey(name, parameterTypes, parameters, entityMode);
	}

	@Override
	public int hashCode() {
		int result = 13;
		for ( int i=0; i< parameters.length; i++ ) {
			result = 37 * result + ( parameters[i]==null ? 0 : parameterTypes[i].getHashCode( parameters[i] , entityMode) );
		}
		result = 37 * result + name.hashCode();
		return result;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
}
