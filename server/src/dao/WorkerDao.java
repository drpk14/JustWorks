/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.User;
import entities.Worker;
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
public class WorkerDao {
    public static void addWorker(Worker worker){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null; 

        try{
            tx = session.beginTransaction();
            
            session.save(worker);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }  
    }
    
    public static Worker checkIfWorker(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Worker W JOIN W.user U WHERE U.dni = :dni");
            query.setString("dni", user.getDni());
            
            List<Object[]> users = query.list();
            if(users.size() > 0){
                Object[] object = users.get(0);
                return (Worker) object[0];
            
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
}
