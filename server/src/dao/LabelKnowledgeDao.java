
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
}
