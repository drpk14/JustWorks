package entities;
// Generated 15-nov-2023 18:41:52 by Hibernate Tools 4.3.1



/**
 * Businessman generated by hbm2java
 */
public class Businessman  implements java.io.Serializable {


     private Integer id;
     private User user;

    public Businessman() {
    }

    public Businessman(User user) {
       this.user = user;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }




}


