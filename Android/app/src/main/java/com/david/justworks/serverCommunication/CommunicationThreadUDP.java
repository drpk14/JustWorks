/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.david.justworks.serverCommunication;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.david.justworks.ui.candidatures.CandidatureMessagesFragment;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket; 
import java.net.SocketException; 
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author David
 */ 

public class CommunicationThreadUDP extends Thread{
       
    private String ip;
    private int port;
    private DatagramSocket socket;
    private boolean follow = true;
    private Context context;

    public void setFollow(boolean follow) {
        this.follow = follow;
    } 

    public CommunicationThreadUDP(String ip, int port, Context context){
        this.ip = ip;
        this.port = port;
        this.context = context;
        try {
            socket = new DatagramSocket(port);
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
                socket.receive(receivePacket);
                String response = new String(receivePacket.getData());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(response.contains("NewOffer")){
                            Toast.makeText(context, "You have a new offer", Toast.LENGTH_SHORT).show();
                        }else if(response.contains("NewCandidature")){
                            Toast.makeText(context, "You have a new candidature", Toast.LENGTH_SHORT).show();
                        }else if(response.contains("CandidatureStateChanged")){
                            Toast.makeText(context, "The state of one candidature has change", Toast.LENGTH_SHORT).show();
                        }else if(response.contains("NewMessage")){
                            if(CandidatureMessagesFragment.getCandidatureMessagesFragment() != null){
                                CandidatureMessagesFragment.getCandidatureMessagesFragment().refreshMessages();
                            }else{
                                Toast.makeText(context, "You have a new message", Toast.LENGTH_SHORT).show();
                            }
                        }else if(response.contains("ServerShoutDown")){

                        }
                    }
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

