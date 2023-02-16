 
package Entities;
 
import java.util.List; 

public class Ofert {
    private int Id;
    private String User;
    private String Name;
    private String description;
    private String ubication;
    private int salary;
    private String contractType; 
    private List labelsList;

    public Ofert(int Id, String User, String Name, String description, String ubication, int salary, String contractType) {
        this.Id = Id;
        this.User = User;
        this.Name = Name;
        this.description = description;
        this.ubication = ubication;
        this.salary = salary;
        this.contractType = contractType; 
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUbication() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public List getLabelsList() {
        return labelsList;
    }

    public void setLabelsList(List labelsList) {
        this.labelsList = labelsList;
    }
    
    
}
