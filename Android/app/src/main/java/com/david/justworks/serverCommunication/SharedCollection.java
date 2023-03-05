/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.david.justworks.serverCommunication;
 
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author David
 */ 

public class SharedCollection{ 
     
    private Queue<String> messages;
    
    private Queue<String> responses;
    
    public SharedCollection() { 
        responses = new LinkedList<>();
        messages = new LinkedList<>();
    } 
    
    public synchronized void addMessage(String message){
        
        messages.add(message); 
        this.notify();
    }
    
    public synchronized String recieveMessage(){
 
        return messages.poll();
    
    }
    
    public synchronized void addResponse(String message){
    
        responses.add(message);
        this.notify();
    }
    
    public synchronized String recieveResponse(){
        
        return responses.poll();
        
    }
     
    public synchronized boolean isMessagesEmpty(){
        if(messages.isEmpty())
            return true;
        else
            return false;
    }
    
    public synchronized boolean isResponsesEmpty(){
        if(responses.isEmpty())
            return true;
        else
            return false;
    }
     
}

