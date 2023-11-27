/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
  
import entities.Candidature;
import entities.CandidatureStateChangedNotification;
import entities.NewCandidatureNotification;
import entities.NewMessageNotification;
import entities.NewOfferNotification;
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
    
    public static List<CandidatureStateChangedNotification> getMyCandidatureStateChangedNotifications(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<CandidatureStateChangedNotification> notifications = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM CandidatureStateChangedNotification CSCN JOIN CSCN.candidature C JOIN C.offer O JOIN CSCN.notification N JOIN N.user U WHERE U.dni = :dni"); 
            query.setString("dni", user.getDni());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                CandidatureStateChangedNotification notification = (CandidatureStateChangedNotification) actualUser[0];
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
    
    public static List<NewCandidatureNotification> getMyNewCandidatureNotifications(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<NewCandidatureNotification> notifications = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM NewCandidatureNotification NCN JOIN NCN.candidature C JOIN C.offer O JOIN NCN.notification N JOIN N.user U WHERE U.dni = :dni"); 
            query.setString("dni", user.getDni());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                NewCandidatureNotification notification = (NewCandidatureNotification) actualUser[0];
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
    
    public static List<NewOfferNotification> getMyNewOfferNotifications(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<NewOfferNotification> notifications = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM NewOfferNotification NON JOIN NON.alert A JOIN A.profile P JOIN NON.notification N JOIN N.user U WHERE U.dni = :dni"); 
            query.setString("dni", user.getDni());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                NewOfferNotification notification = (NewOfferNotification) actualUser[0];
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
    
    public static List<NewMessageNotification> getMyNewMessageNotifications(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<NewMessageNotification> notifications = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM NewMessageNotification NMN JOIN NMN.message M JOIN NMN.notification N JOIN N.user N JOIN NMN.candidature C JOIN C.offer O WHERE U.dni = :dni "); 
            query.setString("dni", user.getDni());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                NewMessageNotification notification = (NewMessageNotification) actualUser[0];
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
    
    public static Notification addNotification(CandidatureStateChangedNotification notification) {
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
    
    public static Notification addNotification(NewOfferNotification notification) {
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
    
    public static Notification addNotification(NewCandidatureNotification notification) {
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
    
    public static Notification addNotification(NewMessageNotification notification) {
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
    
    public static boolean IHaveUnwatchedNotifications(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Notification N JOIN N.user U WHERE U.dni = :dni AND N.notified is false"); 
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

    public static boolean IHaveANotificationForThisCandidatureStageChanged(Candidature candidature) {
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM CandidatureStateChangedNotification CSCN JOIN CSCN.candidature C WHERE C.id = :id"); 
            query.setString("id", String.valueOf(candidature.getId()));
             
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
    
    //NO FUNCIONA
    public static boolean IHaveANotificationForThisCandidatureMessagesStageChanged(Candidature candidature,User myUser) {
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM NewMessageNotification NMN JOIN NMN.candidature C JOIN WHERE C.id = :id"); 
            query.setString("id", String.valueOf(candidature.getId()));
             
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
}
    











/*  public static BusinessmanNotification getBusinessmanNotificationByNotification(Notification notification){
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
    } */
