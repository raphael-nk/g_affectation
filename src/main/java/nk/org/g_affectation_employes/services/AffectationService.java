package nk.org.g_affectation_employes.services;

import nk.org.g_affectation_employes.models.Affecter;
import nk.org.g_affectation_employes.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.util.List;

public class AffectationService {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    public static List<Affecter> getAll() {
        Session session = sessionFactory.openSession();
        Query<Affecter> query = session.createQuery("FROM Affecter p ORDER BY p.codeemp, p.date", Affecter.class);
        List<Affecter> pointages = query.list();
        session.close();
        return pointages;
    }
    public static Integer total(){
        BigInteger total = BigInteger.valueOf(0);
        Session session = sessionFactory.getCurrentSession();
        try{
            session.beginTransaction();
            total = (BigInteger) session.createNativeQuery("SELECT COUNT(p.id) FROM Affecter p").uniqueResult();
            System.out.println(total);
            return total.intValue();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return total.intValue();
    }

    public static boolean store(Affecter pointage) {
        if(alreadyExists(pointage)){
            return false;
        }
        else {
            Session session = sessionFactory.openSession();
            try{
                session = sessionFactory.openSession();
                session.beginTransaction();
                session.save(pointage);
                session.getTransaction().commit();
                return true;

            } catch (Exception e){
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return false;
    }

    private static boolean alreadyExists(Affecter data) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Affecter pointage = (Affecter) session
                    .createQuery("SELECT p FROM Affecter p WHERE p.codeemp = :codeemp AND p.date = :date")
                    .setParameter("codeemp", data.getCodeemp())
                    .setParameter("date", data.getDate())
                    .uniqueResult();
            return pointage != null;
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public static boolean delete(Affecter pointage) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.delete(pointage);
            session.getTransaction().commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean update(Affecter selectedItem) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.update(selectedItem);
            session.getTransaction().commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
