package service.db;

import dao.Course;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class DbGroupService {

    public void insertCourse(Course course) throws IOException, JAXBException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(course);
        transaction.commit();
        session.close();
    }
}
