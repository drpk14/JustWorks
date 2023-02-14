/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
 
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author David
 */ 

public class SharedCollection{
    private static SharedCollection instance;
     
    private Queue<String> messages = new LinkedList<>();
    
    private Queue<String> responses = new LinkedList<>();
    
    private SharedCollection() { 
        
    }
    
    public static synchronized SharedCollection getInstance() {
        if (instance == null) {
            instance = new SharedCollection();
            
        }
        return instance;
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

