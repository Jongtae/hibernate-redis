package net.daum.clix.test;

import net.daum.clix.Campaign;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: jtlee
 * Date: 8/10/12
 * Time: 4:29 PM
 */
@RunWith(JUnit4ClassRunner.class)
public class CampaignTest {

    private static SessionFactory sessionFactory;

    @Test
    public void testSave() {

        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        Campaign campaign = new Campaign();
        campaign.setName("campaign");
        campaign.setBudget(1000L);
        Long id = (Long) session.save(campaign);

        tx.commit();

    }

    @BeforeClass
    public static void setUp() {

        sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        Campaign campaign = new Campaign();
        campaign.setName("campaign2");
        campaign.setBudget(1000L);
        Long id = (Long) session.save(campaign);

        tx.commit();
    }

    @Test
    public void testSelect() {

        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        Campaign campaign = (Campaign) session.get(Campaign.class, 1L);

        tx.commit();

        assertNotNull(campaign);

    }

    @Test
    public void testQueryCache(){
        Session  session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        Criteria criteria = session.createCriteria(Campaign.class);
        criteria.add(Restrictions.like("name", "campaign%"));
        criteria.addOrder(Order.asc("name"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(10);
        criteria.setCacheable(true);
//        criteria.setCacheRegion("@Sorted_queryCache");
        List<Campaign> campaigns = criteria.list();

        tx.commit();

        assertFalse("It should not be empty",campaigns.isEmpty());
    }

    @Test
    public void testCachedQuery(){
        Session  session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        Criteria criteria = session.createCriteria(Campaign.class);
        criteria.add(Restrictions.like("name", "campaign%"));
        criteria.addOrder(Order.asc("name"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(10);
        criteria.setCacheable(true);
//        criteria.setCacheRegion("@Sorted_queryCache");
        List<Campaign> campaigns = criteria.list();

        tx.commit();

        assertFalse("It should not be empty",campaigns.isEmpty());

    }

    @Test
    public void testEvictCachedQuery(){

        Session  session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();

        Campaign campaign = (Campaign) session.get(Campaign.class, 1L);
        campaign.setName("kampaign100");
        session.saveOrUpdate(campaign);

        Criteria criteria = session.createCriteria(Campaign.class);
        criteria.add(Restrictions.like("name", "campaign%"));
        criteria.addOrder(Order.asc("name"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(10);
        criteria.setCacheable(true);
//        criteria.setCacheRegion("@Sorted_queryCache");
        List<Campaign> campaigns = criteria.list();

        tx.rollback();

        assertFalse("It should not be empty",campaigns.isEmpty());
    }

    @AfterClass
    public static void tearDown(){
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        Jedis jedis = pool.getResource();
        jedis.flushDB();
    }

}
