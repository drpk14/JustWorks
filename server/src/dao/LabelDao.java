
package dao;
 
import entities.*; 
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import util.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LabelDao {

    public static List<Label> getAllLabels(){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Label> labels = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Label"); 
            labels = query.list(); 
            tx.commit();  
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        } 
        return labels;
    } 
    
    public static Label getLabelByName(String name){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        Label label = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Label WHERE name = :name");
            query.setString("name", name);
            label = (Label) query.list().get(0); 
            tx.commit();  
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        } 
        return label;
    } 
}
