
package dao;
 
import entities.*; 
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import util.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDao {

    public static boolean checkUserPassword(String user, String password){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM User"); 
            List<User> users = query.list();
            for(User actualUser: users){
                if(actualUser.getUser().equals(user)){ 
                    if(actualUser.getPassword().equals(password)){
                        return true;
                    }
                }
            } 
            tx.commit(); 
            return false;
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        } 
        return false;
    }
    
    public static boolean checkUserIsIn(String user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM User"); 
            List<User> users = query.list();
            for(User actualUser: users){
                if(actualUser.getUser().equals(user)){
                    return true; 
                }
            } 
            tx.commit(); 
            return false;
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        } 
        return false;
    }
    
    public static User getUser(String user){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        User returnedUser = null;
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM User"); 
            List<User> users = query.list();
            for(User actualUser: users){
                if(actualUser.getUser().equals(user)){
                    returnedUser = actualUser;
                }
            } 
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return returnedUser;
    }
    
    public static void addUser(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.save(user);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }   
    }
}
