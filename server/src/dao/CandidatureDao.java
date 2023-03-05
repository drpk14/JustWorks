/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
 
import entities.Candidature;
import entities.Label;
import entities.Offer;
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
public class CandidatureDao { 
    public static void addCandidature(Candidature candidature) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(candidature);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            
         }finally {
            session.close();
        }   
    }
    
    public static void updateCandidature(Candidature candidature) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.update(candidature);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }    
    }
    
    public static void deleteCandidature(Candidature candidature) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.delete(candidature);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }    
    }

    public static List<Candidature> getMyCandidatures(User user,String state) {
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Candidature> candidatures = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
        
            query = session.createQuery("FROM Candidature C JOIN C.worker W JOIN W.user U JOIN C.offer O WHERE U.dni = :dni AND C.state = :state"); 
            query.setString("dni", user.getDni());
            query.setString("state", state);
            
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                candidatures.add((Candidature) actualUser[0]);
                
            } 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return candidatures;
    } 
    
    public static List<Candidature> getCandidaturesForOneOffer(Offer offer,String state) {
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Candidature> candidatures = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
        
            query = session.createQuery("FROM Candidature C JOIN C.worker W JOIN W.user U JOIN C.offer O WHERE O.name = :name AND C.state = :state"); 
            query.setString("name", offer.getName());
            query.setString("state", state);
            
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                candidatures.add((Candidature) actualUser[0]);
                
            } 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return candidatures;
    } 
    
    public static Candidature getCandidatureDetails(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
        
            query = session.createQuery("FROM Candidature C JOIN C.worker W JOIN W.user U JOIN C.offer O WHERE C.id = :id"); 
            query.setString("id",id+"");
            
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                return ((Candidature) actualUser[0]);
                
            } 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return null;
    } 
    
    public static boolean getMyCandidaturesWithLabel(User user,Label label) {
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null;  
        
        try{
            tx = session.beginTransaction();
            Query query = null;
                
            query = session.createQuery("FROM Candidature C JOIN C.worker W JOIN W.user U JOIN C.offer O WHERE U.dni = :dni"); 
            query.setString("dni", user.getDni()); 
            
            List<Object[]> queryList = query.list();
            List<Offer> offersList = new ArrayList();
            for(Object[] actualUser: queryList){
                offersList.add((Offer) actualUser[3]);
                
            } 
            
            for(Offer offer : offersList){
               List<Label> labels = LabelOfferDao.getLabelsByOffer(offer);
               
               for(Label actualLabel: labels){
                   if(actualLabel.getName().equals(label.getName())){
                       return true;
                   }
               }
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
    
    public static boolean checkIfCandidatureExits(User user,Offer offer){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Candidature C JOIN C.worker W JOIN W.user U JOIN C.offer O WHERE U.dni = :dni AND O.id = :id"); 
            query.setString("dni", user.getDni());
            query.setString("id",String.valueOf(offer.getId()));
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
