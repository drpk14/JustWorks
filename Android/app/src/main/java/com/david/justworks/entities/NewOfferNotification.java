/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.david.justworks.entities;

/**
 *
 * @author David
 */
public class NewOfferNotification extends Notification{

    private int newOfferNotificationId;
    private int offerId;
    private String profileName;

    public NewOfferNotification(int notificationId, int newOfferNotificationId, int offerId, String profileName) {
        super(notificationId);
        this.newOfferNotificationId = newOfferNotificationId;
        this.offerId = offerId;
        this.profileName = profileName;
    }

    public int getNewOfferNotificationId() {
        return newOfferNotificationId;
    }

    public void setNewOfferNotificationId(int newOfferNotificationId) {
        this.newOfferNotificationId = newOfferNotificationId;
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
