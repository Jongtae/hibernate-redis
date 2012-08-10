package net.daum.clix.test;

import net.daum.clix.Campaign;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: jtlee
 * Date: 8/10/12
 * Time: 4:29 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class CampaignTest {

	@Test
	public void testSave() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

		Session session = sessionFactory.getCurrentSession();

		Transaction tx = session.beginTransaction();

		Campaign campaign = new Campaign();
		campaign.setName("campaign");
		campaign.setBudget(1000L);
		Long id = (Long) session.save(campaign);

		tx.commit();

	}
}
