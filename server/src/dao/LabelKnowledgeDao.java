
package dao;
 
import entities.*; 
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import util.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LabelKnowledgeDao { 
    
    public static List<Knowledge> getKnowledgeByLabel(Label label,User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Knowledge> knowledges = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM LabelKnowledge LK JOIN LK.knowledge K JOIN K.worker W JOIN W.user U JOIN LK.label L WHERE U.dni = :dni AND L.name = :name"); 
            query.setString("dni", user.getDni());
            query.setString("name",label.getName()); 
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                knowledges.add((Knowledge) actualUser[1]);
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
    public static List<Knowledge> getKnowledgeByLabel(Label label,User user,String type){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Knowledge> knowledges = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM LabelKnowledge LK JOIN LK.knowledge K JOIN K.worker W JOIN W.user U JOIN LK.label L WHERE U.dni = :dni AND L.name = :name AND K.type = :type"); 
            query.setString("dni", user.getDni());
            query.setString("name",label.getName());
            query.setString("type",type);
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                knowledges.add((Knowledge) actualUser[1]);
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

    public static List<Label> getLabelsForThisKnowledge(Knowledge knowledge) {
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Label> labels = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM LabelKnowledge LK JOIN LK.knowledge K JOIN LK.label L WHERE K.id = :id"); 
            query.setString("id",String.valueOf(knowledge.getId()));
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                labels.add((Label) actualUser[2]);
            } 
            tx.commit();  
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        } 
        return labels;
    }

    public static void addLabelKnowledge(LabelKnowledge labelKnowledge) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(labelKnowledge);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            
         }finally {
            session.close();
        }   
    }
}
