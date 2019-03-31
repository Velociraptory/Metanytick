package entity;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Catalog.class);
                configuration.addAnnotatedClass(RegisteredUser.class);
                configuration.addAnnotatedClass(Administrator.class);
                configuration.addAnnotatedClass(Review.class);
                configuration.addAnnotatedClass(CriticReview.class);
                configuration.addAnnotatedClass(UserReview.class);
                configuration.addAnnotatedClass(MediaProduct.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Session Factory Exception" + e);
            }
        }
        return sessionFactory;
    }
}
