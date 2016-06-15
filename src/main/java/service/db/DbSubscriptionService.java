package service.db;

import dao.Subscription;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class DbSubscriptionService {

    public void insertSubscription(Subscription subscription) throws IOException, JAXBException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(subscription);
        transaction.commit();
        session.close();
    }

}
