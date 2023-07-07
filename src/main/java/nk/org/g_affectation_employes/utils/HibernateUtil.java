package nk.org.g_affectation_employes.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static Session getCurrentSession(){return sessionFactory.getCurrentSession();}
}
