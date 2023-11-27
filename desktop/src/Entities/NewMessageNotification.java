/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author David
 */
public class NewMessageNotification extends Notification {
     
    private int newMessageNotificationId;
    private int candidatureId; 
    private String offerName;

    public NewMessageNotification(int notificationId,int newMessageNotificationId, int candidatureId, String offerName) {
        super(notificationId);
        this.newMessageNotificationId = newMessageNotificationId;
        this.candidatureId = candidatureId;
        this.offerName = offerName;
    }

    public int getNewMessageNotificationId() {
        return newMessageNotificationId;
    }

    public void setNewMessageNotificationId(int newMessageNotificationId) {
        this.newMessageNotificationId = newMessageNotificationId;
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
