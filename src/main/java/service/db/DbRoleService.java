package service.db;

import dao.Role;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

public class DbRoleService {

    public Role getRole(String roleType) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Role where role_type=:roleType");
        query.setParameter("roleType", roleType);
        Role role= (Role) query.uniqueResult();
        transaction.commit();
        session.close();
        return role;
    }
}
