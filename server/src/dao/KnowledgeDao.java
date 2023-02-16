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
import org.hibernate.exception.ConstraintViolationException;
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
            query = session.createQuery("FROM LabelKnowledge LK JOIN LK.label L JOIN LW.knowledge K JOIN K.worker W JOIN W.user U WHERE U.dni = :dni AND L.name = :name"); 
            query.setString("dni", user.getDni());
            query.setString("name",label.getName());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                knowledges.add((Knowledge) actualUser[2]);
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
     
}
