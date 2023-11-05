
package dao;
 
import entities.*; 
import java.util.ArrayList;
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
    
    public static void deleteUser(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.delete(user);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }   
    }
    
    public static boolean checkIfDniExits(String DNI){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM User U WHERE U.dni = :dni");  
            query.setString("dni",DNI);
             
            List<Object> consult =  query.list(); 
            if(consult.size() > 0){
                return true;
            }
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return false;
    }
    
    public static boolean checkIfUsernameExits(String username){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();  
            Query query = session.createQuery("FROM User U WHERE U.user = :user ");  
            query.setString("user",username); 
             
            List<Object> consult =  query.list(); 
            if(consult.size() > 0){
                return true;
            }
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return false;
    }
     
    
    public static boolean checkIfeMailExits(String email){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            Query query = session.createQuery("FROM User U WHERE U.email = :email");  
            query.setString("email",email);
             
            List<Object> consult =  query.list(); 
            if(consult.size() > 0){
                return true;
            }
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return false;
    }

    public static User getUserById(Integer id){
        Session session = NewHibernateUtil.getSessionFactory().openSession(); 
        Transaction tx = null; 
        User user = null;
        try{
            tx = session.beginTransaction();
            Query query = null;
            query = session.createQuery("FROM User U WHERE U.id = :id"); 
            query.setString("id", id+"");
             
            List<User> queryList = query.list();
            user =  queryList.get(0);
            tx.commit(); 
            
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback(); 
            e.printStackTrace();
        }finally {
            session.close();
        }  
        return user;
    }
    
    public static void updateUser(User user){
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;  
        try{
            tx = session.beginTransaction();
            
            session.update(user);
            tx.commit();
        }catch (HibernateException e) { 
            if (tx!=null) tx.rollback();
                e.printStackTrace();
        }finally {
            session.close();
        }   
    }
    
    
}
