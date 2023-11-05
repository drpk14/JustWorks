/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
 
import entities.BusinessmanNotification;
import entities.Notification; 
import entities.User;
import entities.WorkerNotification;
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
    public static List<BusinessmanNotification> getMyBusinessmanNotifications(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<BusinessmanNotification> notifications = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM BusinessmanNotification BN JOIN BN.notification N JOIN BN.candidature C JOIN C.offer O JOIN O.businessman B JOIN B.user U WHERE U.dni = :dni"); 
            query.setString("dni", user.getDni());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                BusinessmanNotification notification = (BusinessmanNotification) actualUser[0];
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
    
    public static List<WorkerNotification> getMyWorkerNotifications(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<WorkerNotification> notifications = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM WorkerNotification WN JOIN WN.notification N JOIN WN.alert A JOIN A.profile P JOIN P.worker W JOIN W.user U WHERE U.dni = :dni");
            query.setString("dni", user.getDni());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                WorkerNotification notification = (WorkerNotification) actualUser[0];
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
    
    public static WorkerNotification getWorkerNotificationByNotification(Notification notification){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        WorkerNotification workerNotification = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM WorkerNotification WN JOIN Notification N WHERE N.id = :id"); 
            query.setString("id",String.valueOf(notification.getId()));
             
            List<Object[]> queryList = query.list();
            for(Object[] actualAlert: queryList){
                workerNotification = (WorkerNotification) actualAlert[0];
            } 
             
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return workerNotification;
    }
    
    public static BusinessmanNotification getBusinessmanNotificationByNotification(Notification notification){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        BusinessmanNotification businessmanNotification = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM BusinessmanNotification BN JOIN Notification N WHERE N.id = :id"); 
            query.setString("id",String.valueOf(notification.getId()));
             
            List<Object[]> queryList = query.list();
            for(Object[] actualAlert: queryList){
                businessmanNotification = (BusinessmanNotification) actualAlert[0];
            } 
             
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return businessmanNotification;
    }  

    public static WorkerNotification getWorkerNotificationById(Integer Id){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        WorkerNotification workerNotification = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM WorkerNotification WN  WHERE WN.id = :id"); 
            query.setString("id",String.valueOf(Id));
             
            List<Object[]> queryList = query.list();
            for(Object[] actualAlert: queryList){
                workerNotification = (WorkerNotification) actualAlert[0];
            } 
             
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return workerNotification;
    }
    
    public static BusinessmanNotification getBusinessmanNotificationById(Integer Id){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        BusinessmanNotification businessmanNotification = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM BusinessmanNotification BN WHERE BN.id = :id"); 
            query.setString("id",String.valueOf(Id));
             
            List<Object[]> queryList = query.list();
            for(Object[] actualAlert: queryList){
                businessmanNotification = (BusinessmanNotification) actualAlert[0];
            } 
             
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return businessmanNotification;
    }  
    
    public static Notification addNotification(Notification notification) {
        Notification objectCreated = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(notification);
            objectCreated = (Notification) session.get(Notification.class, notification.getId());
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            
         }finally {
            session.close();
        }   
        return objectCreated;
    } 
    
    public static void addNotification(WorkerNotification notification) {
     
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
    
    public static void addNotification(BusinessmanNotification notification) {
     
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
            
            session.save(notification);
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
    
    public static void deleteNotification(WorkerNotification notification) {
     
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
    
    public static void deleteNotification(BusinessmanNotification notification) {
     
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
