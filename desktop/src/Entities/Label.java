/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author David
 */
public class Label {
    private String name;
    private boolean obligatority;

    public Label(String name) {
        this.name = name;
        this.obligatority = false;
    }

    public Label(String name, boolean obligatority) {
        this.name = name;
        this.obligatority = obligatority;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isObligatority() {
        return obligatority;
    }

    public void setObligatority(boolean obligatority) {
        this.obligatority = obligatority;
    }
 
}
