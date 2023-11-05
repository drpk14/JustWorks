
package dao;
 
import entities.*; 
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import util.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AlertDao {

    public static Alert getAlertById(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        Alert alert = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Alert A WHERE A.id = :id"); 
            query.setString("id", String.valueOf(id)); 
             
            alert = (Alert) query.list().get(0); 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return alert;
    }
    
    public static Alert getAlertForThisProfile(Profile profile){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        Alert alert = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Alert A JOIN A.profile P WHERE P.id = :id"); 
            query.setString("id",String.valueOf(profile.getId()));
             
            List<Object[]> queryList = query.list();
            for(Object[] actualAlert: queryList){
                alert = (Alert) actualAlert[0];
            } 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return alert;
    }
    
    public static List<Alert> getMyAlerts(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Alert> alerts = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Alert A JOIN A.profile P JOIN P.worker W JOIN W.user U WHERE U.dni = :dni"); 
            query.setString("dni", user.getDni());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualAlert: queryList){
                alerts.add((Alert) actualAlert[0]);
            } 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return alerts;
    }  
    
    public static void addAlert(Alert alert){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(alert);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }   
    }
    
     

    public static void updateAlert(Alert alert){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.update(alert);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }   
    }
    
    public static void deleteAlert(Alert alert) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.delete(alert);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }    
    }
    
}
