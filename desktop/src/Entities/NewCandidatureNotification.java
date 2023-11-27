/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author David
 */
public class NewCandidatureNotification extends Notification {
     
    private int newCandidatureNotificationId;
    private int candidatureId; 
    private String offerName;  

    public NewCandidatureNotification(int notificationId,int newCandidatureNotificationId, int candidatureId, String offerName) {
        super(notificationId);
        this.newCandidatureNotificationId = newCandidatureNotificationId;
        this.candidatureId = candidatureId;
        this.offerName = offerName;
    }

    public int getNewCandidatureNotificationId() {
        return newCandidatureNotificationId;
    }

    public void setNewCandidatureNotificationId(int newCandidatureNotificationId) {
        this.newCandidatureNotificationId = newCandidatureNotificationId;
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
