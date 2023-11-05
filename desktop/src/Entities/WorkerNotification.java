/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author David
 */
public class WorkerNotification {
    
    private int notificationId;
    private int workerNotificationId;
    private int offerId; 
    private String profileName;   

    public WorkerNotification(int notificationId, int workerNotificationId, int offerId, String profileName) {
        this.notificationId = notificationId;
        this.workerNotificationId = workerNotificationId;
        this.offerId = offerId;
        this.profileName = profileName;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
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
