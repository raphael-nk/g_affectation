package nk.org.g_affectation_employes.services;

import nk.org.g_affectation_employes.models.Employe;
import nk.org.g_affectation_employes.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.util.List;

public class EmployeService {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private static Session session;
    public static List<Employe> getAll() {
        session = sessionFactory.openSession();
        Query<Employe> query = session.createQuery("FROM Employe", Employe.class);
        List<Employe>employees = query.list();
        session.close();

        return employees;
    }


    public static Integer totalEmploye(){
        BigInteger total = BigInteger.valueOf(0);
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            total = (BigInteger) session.createNativeQuery("SELECT COUNT(p.codeemp) FROM Employe p").uniqueResult();
            System.out.println(total);
            return total.intValue();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return total.intValue();
    }

    public static boolean delete(Employe employee) {
        session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            Employe deletedRow = session.get(Employe.class, employee.getCodeemp());
            session.delete(deletedRow);
            session.getTransaction().commit();
            return true;

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public static boolean store(Employe newEmploye) {
        try{
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(newEmploye);
            session.getTransaction().commit();
            return true;

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return false;
    }

    public static boolean update(Employe selectedItem) {
        session = sessionFactory.getCurrentSession();
        try{
            session.beginTransaction();
            session.update(selectedItem);
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
