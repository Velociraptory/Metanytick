package entity;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class MediaProductDao {
    public MediaProduct findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(MediaProduct.class, id);
    }

    public void save(MediaProduct mediaProduct) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(mediaProduct);
        tx1.commit();
        session.close();
    }

    public void update(MediaProduct mediaProduct) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(mediaProduct);
        tx1.commit();
        session.close();
    }

    public void delete(MediaProduct mediaProduct) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(mediaProduct);
        tx1.commit();
        session.close();
    }
}
