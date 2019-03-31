package entity;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class CriticReviewDao {
    public CriticReview findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(CriticReview.class, id);
    }

    public void save(CriticReview criticReview) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(criticReview);
        tx1.commit();
        session.close();
    }

    public void update(CriticReview criticReview) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(criticReview);
        tx1.commit();
        session.close();
    }

    public void delete(CriticReview criticReview) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(criticReview);
        tx1.commit();
        session.close();
    }
}
