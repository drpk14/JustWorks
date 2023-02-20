/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author David
 */
public class Knowledge {
    private int id; 
    private String workerName;
    private String knowledgeName; 
    private String place;  
    private LocalDate initDate;
    private LocalDate finishDate;
    private List<String> labels;

    public Knowledge(int id,String workerName, String knowledgeName,String place, LocalDate initDate, LocalDate finishDate) {
        this.id = id;
        this.workerName = workerName;
        this.knowledgeName = knowledgeName;
        this.place = place;
        this.initDate = initDate;
        this.finishDate = finishDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "Knowledge{" + "knowledgeName=" + knowledgeName + ", place=" + place + '}';
    }
    
}
