/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
 
import entities.Notification; 
import entities.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction; 
import util.NewHibernateUtil;

/**
 *
 * @author david
 */
public class NotificationDao{  
    public static List<Notification> getMyNotifications(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Notification> notifications = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Notification N JOIN N.alert A JOIN N.label L JOIN N.offer O JOIN A.worker W JOIN W.user U WHERE U.dni = :dni"); 
            query.setString("dni", user.getDni());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                Notification notification = (Notification) actualUser[0];
                notifications.add(notification);
            } 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return notifications;
    }  
    
    public static boolean getMyUnwatchedNotifications(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Notification N JOIN N.alert A JOIN N.label L JOIN N.offer O JOIN A.worker W JOIN W.user U WHERE U.dni = :dni AND N.notified is false"); 
            query.setString("dni", user.getDni());
             
            List<Object[]> queryList = query.list();
            if(queryList.size() > 0)
                return true;
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return false;
    }  
    public static Notification getNotificationById(Integer Id){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        Notification notification = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Notification N WHERE N.id = :id"); 
            query.setString("id",Id+"");
             
            notification = (Notification) query.list().get(0);
             
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return notification;
    }  

    public static void addNotification(Notification notification) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(notification);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            
         }finally {
            session.close();
        }   
    }
    
    public static void updateNotification(Notification notification) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.update(notification);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            
         }finally {
            session.close();
        }   
    }
     
    
    public static void deleteNotification(Notification notification) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.delete(notification);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }    
    }
}
