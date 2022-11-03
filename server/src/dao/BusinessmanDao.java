/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Businessman;
import entities.User; 
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
public class BusinessmanDao {
    public static void addBusinessman(Businessman businessman){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null; 

        try{
            tx = session.beginTransaction();
            
            session.save(businessman);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }  
    }
    
    public static boolean checkIfBusinessman(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        User returnedUser = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Businessman B JOIN B.user U WHERE U.dni = :dni");
            query.setString("dni", user.getDni());
            
            List<Object[]> users = query.list();
            if(users.size() > 0)
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
