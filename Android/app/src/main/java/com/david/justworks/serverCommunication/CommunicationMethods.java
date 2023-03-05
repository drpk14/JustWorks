package com.david.justworks.serverCommunication;

public class CommunicationMethods {

    private static CommunicationMethods instance;

    private SharedCollection sharedCollection;
    private CommunicationMethods() {}

    public static CommunicationMethods getInstance() {
        if (instance == null) {
            instance = new CommunicationMethods();
        }
        return instance;
    }

    public void setSharedCollection(SharedCollection sharedCollection) {
        this.sharedCollection = sharedCollection;
    }

    public void sendMessage(String message){
        sharedCollection.addMessage(message);
    }

    public String recieveMessage(){

        String response = "";
        synchronized (sharedCollection) {
            try {
                if(sharedCollection.isResponsesEmpty())
                    sharedCollection.wait();


                response = sharedCollection.recieveResponse();

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return response;
    }
}
