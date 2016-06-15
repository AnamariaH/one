package service.db;

import dao.Course;
import org.hibernate.Query;
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

    public Course getCourse(String courseName) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Course where name=:courseName");
        query.setParameter("courseName", courseName);
        Course course= (Course) query.uniqueResult();
        transaction.commit();
        session.close();
        return course;
    }

//    public void insertCourseOne(OneCourse oneCourse) throws IOException, JAXBException {
//        Session session = HibernateUtil.getSession();
//        Transaction transaction = session.beginTransaction();
//        session.save(oneCourse);
//        transaction.commit();
//        session.close();
//    }
}
