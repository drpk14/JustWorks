/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author David
 */
public class BusinessmanNotification {
    
    private int notificationId;
    private int businessmanNotificationId;
    private int candidatureId; 
    private String offerName;  

    public BusinessmanNotification(int notificationId, int businessmanNotificationId, int candidatureId, String offerName) {
        this.notificationId = notificationId;
        this.businessmanNotificationId = businessmanNotificationId;
        this.candidatureId = candidatureId;
        this.offerName = offerName;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getBusinessmanNotificationId() {
        return businessmanNotificationId;
    }

    public void setBusinessmanNotificationId(int businessmanNotificationId) {
        this.businessmanNotificationId = businessmanNotificationId;
    }

    public int getCandidatureId() {
        return candidatureId;
    }

    public void setCandidatureId(int candidatureId) {
        this.candidatureId = candidatureId;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }
    
    
}
