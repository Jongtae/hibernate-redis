package net.daum.clix.hibernate.redis.aop;

import org.hibernate.EntityMode;
import org.hibernate.type.Type;


/**
 * Created with IntelliJ IDEA.
 * User: jtlee
 * Date: 8/13/12
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface QueryKeyIF {
    public Object[] getPositionalParameterValues();
    public EntityMode getEntityMode();
    public Type[] getPositionalParameterTypes();
}
