/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author David
 */
public class Alert {
    private int id;
    private int profileId;
    private String profileName;

    public Alert(int id, int profileId, String profileName) {
        this.id = id;
        this.profileId = profileId;
        this.profileName = profileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }  

    @Override
    public String toString() {
        return profileName;
    }
    
    
}
