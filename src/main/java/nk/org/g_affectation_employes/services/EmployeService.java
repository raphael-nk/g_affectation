package nk.org.g_affectation_employes.services;

import nk.org.g_affectation_employes.models.Employe;
import nk.org.g_affectation_employes.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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
        Integer total = 0;
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            total = (Integer) session.createNativeQuery("SELECT COUNT(p.codeemp) FROM Employe p").uniqueResult();
            return total;

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }

        return total;
    }
}
