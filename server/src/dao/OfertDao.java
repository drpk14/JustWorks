/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.BusinessmanDao.checkIfBusinessman;
import entities.Ofert;
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
public class OfertDao {
    public static List<Ofert> getAllOferts(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Ofert> oferts = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            if(checkIfBusinessman(user) != null){
                query = session.createQuery("FROM Ofert O JOIN O.businessman B JOIN B.user U WHERE U.dni != :dni"); 
                query.setString("dni", user.getDni());
            }else{
                query = session.createQuery("FROM Ofert O JOIN O.businessman B JOIN B.user U"); 
            }
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                oferts.add((Ofert) actualUser[0]);
            } 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return oferts;
    }
    
    public static List<Ofert> getMyOferts(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Ofert> oferts = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Ofert O JOIN O.businessman B JOIN B.user U WHERE U.dni = :dni"); 
            query.setString("dni", user.getDni());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                oferts.add((Ofert) actualUser[0]);
            } 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return oferts;
    }
    
    public static Ofert getOfertDetails(Integer Id){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        Ofert ofert = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Ofert O JOIN O.businessman B JOIN B.user U WHERE O.id = :id"); 
            query.setString("id", String.valueOf(Id));
             
            Object[] consult = (Object[]) query.list().get(0);
            ofert = (Ofert) consult[0];
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return ofert;
    }

    public static void addOfert(Ofert ofert) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(ofert);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }   
     
    }
    
    public static void updateOfert(Ofert ofert) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.update(ofert);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }    
    }
    
    public static void deleteOfert(Ofert ofert) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.delete(ofert);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }    
    }
}
