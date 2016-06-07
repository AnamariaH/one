package service.db;


import utils.HibernateUtil;
import dao.EnrolStudentsToCourse;
import dao.Student;
import dao.Teacher;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public class DbUserService {

    /**
     * verify from DB if user is student or professor.
     */
    public String getRoleById(int id) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM EnrolToCourse where teacherID.id=:id");
        query.setParameter("id", id);
        List<EnrolStudentsToCourse> role = query.list();
        String r = role.get(0).getUserRole();
        transaction.commit();
        session.close();
        return r;
    }

    public void insertStudentsFromFile(List<Student> list) throws IOException, JAXBException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        list.forEach(session::save);
        transaction.commit();
        session.close();
    }

    public void insertStudent(Student student) throws IOException, JAXBException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(student);
        transaction.commit();
        session.close();
    }

    public void insertTeacher(Teacher teacher) throws IOException, JAXBException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(teacher);
        transaction.commit();
        session.close();
    }

    public void insertTeachersFromFile(List<Teacher> list) throws IOException, JAXBException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
//        for (Student student : list) {
//            session.save(student);
//        }
        list.forEach(session::save);
        transaction.commit();
        session.close();
    }
}
