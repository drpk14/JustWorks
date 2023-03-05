/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
  
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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

    public CommunicationThreadUDP(String ip, int port) {
        this.ip = ip;
        this.port = port;
        initializeConnection();
    } 
 
    
    public void initializeConnection(){ 
        byte[] buffer = new byte[1024];
        try {
            socket = new DatagramSocket();
            
            String mensaje = "Starting the UDP Connection";
             
            buffer = mensaje.getBytes();
             
            DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length,InetAddress.getByName(ip), port);
            
            socket.send(pregunta); 
            
            
            
        } catch (SocketException ex) {
            Logger.getLogger(JustWorkApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(JustWorkApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JustWorkApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     
    

    @Override
    public void run() {
         
        byte[] receiveData = new byte[1024];
        while(true){
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                System.out.println("Esperando al mensaje");
                socket.receive(receivePacket);
                 
                System.out.println("Despues de recibir el mensaje");
                String response = new String(receivePacket.getData());
                System.out.println(response);
                if(response.contains("Notification")){
                    JOptionPane.showMessageDialog(null, "Tienes nuevas notificaciones");
                }else if(response.contains("ServerShoutDown")){
                    JOptionPane.showConfirmDialog(null, "The server had shoutdown, shouting down your aplicattion also");
                    JustWorkApp.closeApp();
                }
                
            } catch (IOException ex) {
                Logger.getLogger(CommunicationThreadUDP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

