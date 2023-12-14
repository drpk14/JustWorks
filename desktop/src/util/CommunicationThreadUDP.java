/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
  
import controller.MainBusinessmanController;
import controller.MainWorkerController;
import controller.candidatures.CandidatureMessagesController;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket; 
import java.net.SocketException; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.JustWorkApp;


/**
 *
 * @author David
 */ 

public class CommunicationThreadUDP extends Thread{
       
    private String ip;
    private int port;
    private DatagramSocket socket;
    private boolean follow = true;

    public void setFollow(boolean follow) {
        this.follow = follow;
    } 

    public CommunicationThreadUDP(String ip, int port){
        this.ip = ip;
        this.port = port;
        try {
            socket = new DatagramSocket(port);
        }catch (java.net.BindException ex) {
            JOptionPane.showMessageDialog(null, "Puerto ocupado, apagando la aplicacion");
            JustWorkApp.closeApp();
        } catch (SocketException ex) {
            Logger.getLogger(CommunicationThreadUDP.class.getName()).log(Level.SEVERE, null, ex);
        } 
    } 
    
    @Override
    public void run() {
         
        byte[] receiveData = new byte[1024];
        while(follow){
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
                System.out.println(port);
                socket.receive(receivePacket);  
                String response = new String(receivePacket.getData());
                System.out.println(response);
                if(response.contains("NewOffer")){
                    if(MainBusinessmanController.getInstance() != null){ 
                        MainBusinessmanController.getInstance().changeRedDotVisibility(true);
                    }else if(MainWorkerController.getInstance() != null){  
                        MainWorkerController.getInstance().changeRedDotVisibility(true); 
                    }
                }else if(response.contains("NewCandidature")){
                    if(MainBusinessmanController.getInstance() != null){ 
                        MainBusinessmanController.getInstance().changeRedDotVisibility(true);
                    }else if(MainWorkerController.getInstance() != null){  
                        MainWorkerController.getInstance().changeRedDotVisibility(true); 
                    }
                
                }else if(response.contains("CandidatureStateChanged")){
                    if(MainBusinessmanController.getInstance() != null){ 
                        MainBusinessmanController.getInstance().changeRedDotVisibility(true);
                    }else if(MainWorkerController.getInstance() != null){  
                        MainWorkerController.getInstance().changeRedDotVisibility(true); 
                    }
                
                }else if(response.contains("NewMessage")){
                    if(MainBusinessmanController.getInstance() != null){
                        if(MainBusinessmanController.getInstance().getControler() instanceof CandidatureMessagesController){
                            CandidatureMessagesController candidatureMessagesController = (CandidatureMessagesController) MainBusinessmanController.getInstance().getControler();
                            candidatureMessagesController.refreshMessages();
                        }else{ 
                            MainBusinessmanController.getInstance().changeRedDotVisibility(true);
                        }
                            
                    }else if(MainWorkerController.getInstance() != null){  
                        if(MainWorkerController.getInstance().getControler() instanceof CandidatureMessagesController){
                            CandidatureMessagesController candidatureMessagesController = (CandidatureMessagesController) MainWorkerController.getInstance().getControler();
                            candidatureMessagesController.refreshMessages();
                        }else{
                            MainWorkerController.getInstance().changeRedDotVisibility(true);
                        }
                    }
                }else if(response.contains("ServerShoutDown")){
                    JOptionPane.showConfirmDialog(null, "The server had shoutdown, shouting down your aplicattion also");
                    JustWorkApp.closeApp();
                }
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        System.out.println("Fin del hilo");
    }
}

