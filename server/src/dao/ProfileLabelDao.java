
package dao;
 
import entities.*; 
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import util.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProfileLabelDao {
  
    public static List<Profile> getProfileForThisLabel(String name){ 
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Profile> profiles = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM ProfileLabel PL JOIN PL.label L JOIN PL.profile P WHERE L.name = :name");
            query.setString("name", name);
            List<Object[]> queryList = query.list();
            for(Object[] actualProfile: queryList){
                Profile profile = (Profile) actualProfile[2];
                profiles.add(profile);
            } 
            tx.commit();  
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }
        return profiles;
    }    
    
    public static ProfileLabel getProfileLabel(int id,String labelName){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        ProfileLabel profileLabel = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM ProfileLabel PL JOIN PL.label L JOIN PL.profile P WHERE L.name = :name AND P.id = :id"); 
            query.setString("id", String.valueOf(id)); 
            query.setString("name",labelName); 
            List<Object[]> queryList = query.list();
            for(Object[] actualProfileLabel: queryList){
                profileLabel = (ProfileLabel) actualProfileLabel[0]; 
            } 
            
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return profileLabel;
    }
    
    public static List<Label> getLabelsForThisProfile(Profile profile) {
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Label> labels = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM ProfileLabel PL JOIN PL.label L JOIN PL.profile P WHERE P.id = :id");
            query.setString("id",String.valueOf(profile.getId()));
            List<Object[]> queryList = query.list();
            for(Object[] object: queryList){
                Label label = (Label) object[1];
                labels.add(label);
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

    public static void addProfileLabel(ProfileLabel profileLabel) { 
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(profileLabel); 
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            
         }finally {
            session.close();
        } 
    }
    
    
    public static void deleteProfileLabel(ProfileLabel profileLabel) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.delete(profileLabel);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        } 
    }

}

