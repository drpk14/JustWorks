/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.david.justworks.entities;

/**
 *
 * @author David
 */
public class CandidatureStateChangedNotification extends Notification {
     
    private int candidatureStateChangedNotificationnId;
    private int candidatureId; 
    private String offerName;  
    private String candidatureState;

    public CandidatureStateChangedNotification(int notificationId,int candidatureStateChangedNotificationnId, int candidatureId, String offerName, String candidatureState) {
        super(notificationId);
        this.candidatureStateChangedNotificationnId = candidatureStateChangedNotificationnId;
        this.candidatureId = candidatureId;
        this.offerName = offerName;
        this.candidatureState = candidatureState;
    }

    public int getCandidatureStateChangedNotificationnId() {
        return candidatureStateChangedNotificationnId;
    }

    public void setCandidatureStateChangedNotificationnId(int candidatureStateChangedNotificationnId) {
        this.candidatureStateChangedNotificationnId = candidatureStateChangedNotificationnId;
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

    public String getCandidatureState() {
        return candidatureState;
    }

    public void setCandidatureState(String candidatureState) {
        this.candidatureState = candidatureState;
    }  
}
