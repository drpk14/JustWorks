package entities;
// Generated 18-feb-2023 23:32:23 by Hibernate Tools 4.3.1


import java.sql.Date;

/**
 * Knowledge generated by hbm2java
 */
public class Knowledge  implements java.io.Serializable {


     private Integer id;
     private Worker worker;
     private String name;
     private String place;
     private String title;
     private String type;
     private Date fechaInicio;
     private Date fechaFin;
     

    public Knowledge() {
    }

    public Knowledge(Worker worker, String name, String place, String title, String type, Date fechaInicio, Date fechaFin) {
        this.worker = worker;
        this.name = name;
        this.place = place;
        this.title = title;
        this.type = type;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Worker getWorker() {
        return this.worker;
    }
    
    public void setWorker(Worker worker) {
        this.worker = worker;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    public Date getFechaInicio() {
        return this.fechaInicio;
    }
    
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public Date getFechaFin() {
        return this.fechaFin;
    }
    
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    public String getPlace() {
        return this.place;
    }
    
    public void setPlace(String place) {
        this.place = place;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




}


