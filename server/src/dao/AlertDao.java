
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

    
    public static List<Alert> getMyAlerts(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Alert> alerts = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Alert A JOIN A.label L JOIN A.worker W JOIN W.user U WHERE U.dni = :dni"); 
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
    
    public static List<Alert> getAlertsByLabel(Label label){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Alert> alerts = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Alert A JOIN A.label L JOIN A.worker W JOIN W.user U WHERE L.name = :name"); 
            query.setString("name", label.getName());
             
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
    
    public static Alert getAlert(User user,Label label){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        Alert alert = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Alert A JOIN A.label L JOIN A.worker W JOIN W.user U WHERE U.dni = :dni AND L.name = :name"); 
            query.setString("dni", user.getDni());
            query.setString("name", label.getName());
             
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
    
    public static void deleteOffer(Alert alert) {
     
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
