/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.HashMap;
import java.util.Map;
import server.Server.ServerThread;

/**
 *
 * @author David
 */
public class SharedColection {
    Map<String,ServerThread> activeThreads;
    
    public SharedColection(){
        activeThreads = new HashMap();
    }
    
    public synchronized  void add(String key, ServerThread thread){
    
        activeThreads.put(key,thread);
    }
    
    public synchronized  void remove(String key){
        activeThreads.remove(key);
    }
    
    public synchronized  boolean search(String key){
        return activeThreads.containsKey(key);
    }
}
