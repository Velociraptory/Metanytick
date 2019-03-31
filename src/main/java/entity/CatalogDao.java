package entity;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class CatalogDao {
    public Catalog findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Catalog.class, id);
    }

    public void save(Catalog catalog) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(catalog);
        tx1.commit();
        session.close();
    }

    public void update(Catalog catalog) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(catalog);
        tx1.commit();
        session.close();
    }

    public void delete(Catalog catalog) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(catalog);
        tx1.commit();
        session.close();
    }
}
