/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Businessman;
import entities.Worker;
import org.hibernate.HibernateException;
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
}
