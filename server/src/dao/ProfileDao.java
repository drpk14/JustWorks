
package dao;
 
import entities.*; 
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import util.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProfileDao {
 
    public static Profile getProfileById(int id){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        Profile profile = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Profile P WHERE P.id = :id"); 
            query.setString("id", String.valueOf(id)); 
             
            profile = (Profile) query.list().get(0);
            
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return profile;
    }
      

    public static List<Profile> getMyProfiles(User user) {
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        List<Profile> profiles = new ArrayList();
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM Profile P JOIN P.worker W JOIN W.user U WHERE U.dni = :dni");
            query.setString("dni", user.getDni());
            List<Object[]> queryList = query.list();
            for(Object[] object: queryList){
                Profile profile = (Profile) object[0];
                profiles.add(profile);
            } 
            tx.commit();  
        }catch (HibernateException e) { 
            e.printStackTrace();
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        } 
        return profiles;
    }
    
    public static Profile getMyProfileByName(String name, User myUser) {
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        Profile profile = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM Profile P JOIN P.worker W JOIN W.user U WHERE U.dni = :dni AND P.name = :name"); 
            query.setString("dni", myUser.getDni()); 
            query.setString("name", name); 
              
            List<Object[]> queryList = query.list();
            for(Object[] actualAlert: queryList){
                profile = (Profile) actualAlert[0];
            } 
            tx.commit(); 
            
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return profile;
    }
    
    public static Profile addProfile(Profile profile) {
        Profile objectCreated = null;
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(profile);
            objectCreated = (Profile) session.get(Profile.class, profile.getId());
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            
         }finally {
            session.close();
        }
        return objectCreated;
    } 

    public static void deleteProfile(Profile profile) {
     
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.delete(profile);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        } 
    }

    public static void updateProfile(Profile profile) {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.update(profile);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }  
    }
}


