/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.BusinessmanDao.checkIfBusinessman;
import entities.Knowledge;
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
public class KnowledgeDao {
    public static List<Knowledge> getKnowledgeForThisLabel(Label label,User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Knowledge> knowledges = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null; 
            query = session.createQuery("FROM LabelKnowledge LK JOIN LK.label L JOIN LK.knowledge K JOIN K.worker W JOIN W.user U WHERE U.dni = :dni AND L.name = :name"); 
            query.setString("dni", user.getDni());
            query.setString("name",label.getName());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualKnowledge: queryList){
                knowledges.add((Knowledge) actualKnowledge[2]);
            } 
            
            
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return knowledges;
    }

    public static Knowledge getKnowledgeById(int id) {
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        Knowledge knowledge = null;
        try{
            tx = session.beginTransaction();
            Query query = null; 
            query = session.createQuery("FROM Knowledge K JOIN K.worker W JOIN W.user U WHERE K.id = :id"); 
            query.setString("id",String.valueOf(id)); 
             
            List<Object[]> queryList = query.list();
            for(Object[] actualKnowledge: queryList){
                knowledge = (Knowledge) actualKnowledge[0];
            } 
            
            
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return knowledge;
    }
     
    public static List<Knowledge> getMyWorkExperience(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Knowledge> knowledges = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null; 
            query = session.createQuery("FROM Knowledge K JOIN K.worker W JOIN W.user U WHERE U.dni = :dni AND K.type = :type"); 
            query.setString("dni", user.getDni());
            query.setString("type","WorkExperience");
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                knowledges.add((Knowledge) actualUser[0]);
            } 
            
            
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return knowledges;
    }
    
    public static boolean checkIfKnowledgeExist(Knowledge knowledge, User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Knowledge K JOIN K.worker W JOIN W.user U WHERE K.name = :name AND U.dni = :dni"); 
            query.setString("name",knowledge.getName());
            query.setString("dni",user.getDni()); 
             
            List<Object[]> consult =  query.list(); 
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
    
    public static void addKnowledge(Knowledge knowledge) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(knowledge);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            
         }finally {
            session.close();
        }   
    }
    
    public static void updateKnowledge(Knowledge knowledge) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.update(knowledge);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }    
    }
    
    public static void deleteKnowledge(Knowledge knowledge) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.delete(knowledge);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }    
    }
}
