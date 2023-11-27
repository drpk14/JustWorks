/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Candidature;
import entities.Message;
import entities.Profile;
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
 * @author David
 */
public class MessageDao {

    public static List<Message> getMessagesForThisCandidature(Candidature candidature) { 
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Message> messages = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = null;
        
            query = session.createQuery("FROM Message M JOIN M.candidature C JOIN M.user U  WHERE C.id = :id ORDER BY M.sendedTime"); 
            query.setString("id", String.valueOf(candidature.getId()));
            
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                messages.add((Message) actualUser[0]); 
            } 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }
        return messages;
    } 

    public static Message addMessage(Message message) {
        Message objectCreated = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(message); 
            objectCreated = (Message) session.get(Message.class, message.getId());
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            
         }finally {
            session.close();
        } 
        return objectCreated;
    }  
}
