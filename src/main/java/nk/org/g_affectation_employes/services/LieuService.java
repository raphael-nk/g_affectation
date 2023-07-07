package nk.org.g_affectation_employes.services;

import nk.org.g_affectation_employes.models.Lieu;
import nk.org.g_affectation_employes.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.util.List;

public class LieuService {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static Session session;
    public static Integer total(){
        BigInteger total = BigInteger.valueOf(0);
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            total = (BigInteger) session.createNativeQuery("SELECT COUNT(p.codelieu) FROM Lieu p").uniqueResult();
            System.out.println(total);
            return total.intValue();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return total.intValue();
    }

    public static List<Lieu> getAll() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query<Lieu> query = session.createQuery("FROM Lieu", Lieu.class);
        List<Lieu> lieux = query.list();
        session.getTransaction().commit();
        session.close();
        return lieux;
    }

    public static boolean store(Lieu lieu) {
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(lieu);
            session.getTransaction().commit();
            return true;

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
}
