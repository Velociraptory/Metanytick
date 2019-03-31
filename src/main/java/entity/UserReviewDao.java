package entity;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserReviewDao {
    public UserReview findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(UserReview.class, id);
    }

    public void save(UserReview userReview) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(userReview);
        tx1.commit();
        session.close();
    }

    public void update(UserReview userReview) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(userReview);
        tx1.commit();
        session.close();
    }

    public void delete(UserReview userReview) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(userReview);
        tx1.commit();
        session.close();
    }
}
