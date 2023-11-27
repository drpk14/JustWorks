/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author David
 */
public class NewOfferNotification extends Notification{ 
    
    private int newOfferNotificationId;
    private int workerNotificationId;
    private int offerId; 
    private String profileName;   

    public NewOfferNotification(int notificationId, int newOfferNotificationId, int workerNotificationId, int offerId, String profileName) {
        super(notificationId);
        this.newOfferNotificationId = newOfferNotificationId;
        this.workerNotificationId = workerNotificationId;
        this.offerId = offerId;
        this.profileName = profileName;
    }

    public int getNewOfferNotificationId() {
        return newOfferNotificationId;
    }

    public void setNewOfferNotificationId(int newOfferNotificationId) {
        this.newOfferNotificationId = newOfferNotificationId;
    }

    public int getWorkerNotificationId() {
        return workerNotificationId;
    }

    public void setWorkerNotificationId(int workerNotificationId) {
        this.workerNotificationId = workerNotificationId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    } 
}
