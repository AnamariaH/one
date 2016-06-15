package service.db;

import dao.SubscriptionRole;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class DbSubscriptionRoleService {

    public void insertSubscriptionRole(SubscriptionRole subscriptionRole) throws IOException, JAXBException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(subscriptionRole);
        transaction.commit();
        session.close();
    }
}
