/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.david.justworks.entities;

import java.time.LocalDate; 
import java.util.List;

public class Knowledge {
    private int id; 
    private String workerName;
    private String name;
    private String place;
    private String title;
    private String type;
    private LocalDate initDate;
    private LocalDate finishDate;
    private List<String> labels;

    public Knowledge(int id, String workerName, String name, String place, String title, String type, LocalDate initDate, LocalDate finishDate) {
        this.id = id;
        this.workerName = workerName;
        this.name = name;
        this.place = place;
        this.title = title;
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "Knowledge{" + "knowledgeName=" + name + ", place=" + place + '}';
    }
    
    /*@Override
    public boolean equals(Object object){
        Knowledge other = (Knowledge) object;
        if(!this.getName().equals(other.getName()))
            return false;
        
        if(!this.getTitle().equals(other.getTitle()))
            return false;
        
        if(!this.getPlace().equals(other.getPlace()))
            return false;
        
        if(!this.getInitDate().equals(other.getFinishDate()))
            return false;
        
        if(!this.getFinishDate().equals(other.getFinishDate()))
            return false;
        
        if(!this.getType().equals(other.getType()))
            return false;
        
        if(!this.getLabels().equals(other.getLabels()))
            return false;
        
        return true;
    }*/
}
