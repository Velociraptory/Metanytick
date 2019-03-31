package entity;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class RegisteredUserDao {
    public RegisteredUser findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(RegisteredUser.class, id);
    }

    public void save(RegisteredUser registeredUser) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(registeredUser);
        tx1.commit();
        session.close();
    }

    public void update(RegisteredUser registeredUser) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(registeredUser);
        tx1.commit();
        session.close();
    }

    public void delete(RegisteredUser registeredUser) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(registeredUser);
        tx1.commit();
        session.close();
    }
}
