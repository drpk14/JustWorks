
package dao;
 
import entities.*; 
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import util.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LabelOfferDao { 
    public static List<LabelOffer> getLabelsByOffer(Offer offer){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<LabelOffer> labels = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM LabelOffer LO JOIN LO.offer O JOIN LO.label L WHERE O.name = :name"); 
            query.setString("name", offer.getName());
             
            List<Object[]> queryList = query.list();
            for(Object[] actualUser: queryList){
                labels.add((LabelOffer) actualUser[0]);
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
    
    public static List<Label> getOptionalLabelsByOffer(Offer offer){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Label> labels = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM LabelOffer LO JOIN LO.offer O JOIN LO.label L WHERE O.name = :name AND LO.obligatory is false"); 
            query.setString("name", offer.getName());
             
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
    
    public static List<Label> getObligatoryLabelsByOffer(Offer offer){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Label> labels = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM LabelOffer LO JOIN LO.offer O JOIN LO.label L WHERE O.name = :name AND LO.obligatory is true"); 
            query.setString("name", offer.getName());
             
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
    
    public static void addLabelOffer(LabelOffer labelOffer) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(labelOffer);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }   
     
    }
}
