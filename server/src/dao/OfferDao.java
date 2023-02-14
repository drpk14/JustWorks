/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.BusinessmanDao.checkIfBusinessman;
import entities.Offer;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import util.NewHibernateUtil;

/**
 *
 * @author david
 */
public class OfferDao {
    public static List<Offer> getAllOffers(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Offer> offers = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            if(checkIfBusinessman(user) != null){
                query = session.createQuery("FROM Offer O JOIN O.businessman B JOIN B.user U WHERE U.dni != :dni"); 
                query.setString("dni", user.getDni());
            }else{
                query = session.createQuery("FROM Offer O JOIN O.businessman B JOIN B.user U"); 
            }
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                offers.add((Offer) actualUser[0]);
                
            } 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return offers;
    }
    
    public static List<Offer> getMyOffers(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Offer> offers = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Offer O JOIN O.businessman B JOIN B.user U WHERE U.dni = :dni"); 
            query.setString("dni", user.getDni());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                offers.add((Offer) actualUser[0]);
            } 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return offers;
    }
    
    public static Offer getOfferDetails(Integer Id){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        Offer offer = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Offer O JOIN O.businessman B JOIN B.user U WHERE O.id = :id"); 
            query.setString("id", String.valueOf(Id));
             
            Object[] consult = (Object[]) query.list().get(0);
            offer = (Offer) consult[0];
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return offer;
    }
    
    public static boolean checkIfExistOffer(Offer offer, User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Offer O JOIN O.businessman B JOIN B.user U WHERE O.name = :name AND U.dni = :dni"); 
            query.setString("name",offer.getName());
            query.setString("dni",user.getDni());
             
            List<Object> consult =  query.list(); 
            if(consult.size() > 0){
                return true;
            }
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return false;
    }

    public static void addOffer(Offer offer) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(offer);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            
         }finally {
            session.close();
        }   
    }
    
    public static void updateOffer(Offer offer) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.update(offer);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }    
    }
    
    public static void deleteOffer(Offer offer) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.delete(offer);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }    
    }
}
