/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.david.justworks.serverCommunication;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */ 

public class CommunicationThread extends Thread{
     
    private SharedCollection sharedCollection;
    private PrintWriter out;
    private BufferedReader in;
    private String ip;
    private int port;

    public CommunicationThread(String ip,int port,SharedCollection sharedCollection) {
        this.sharedCollection = sharedCollection;
        this.ip = ip;
        this.port = port;
    }
    
     

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }



    @Override
    public void run() {
        Socket echoSocket = null;

        try {
            echoSocket = new Socket(ip, port);

            this.setOut(new PrintWriter(echoSocket.getOutputStream(), true));
            this.setIn(new BufferedReader(new InputStreamReader(echoSocket.getInputStream())));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + ip);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + ip);
            System.exit(1);
        }

        while(true){
            synchronized (sharedCollection) {
                try {  
                    sharedCollection.wait();  
                    this.getOut().println(sharedCollection.recieveMessage()); 
                    sharedCollection.addResponse(this.getIn().readLine());
                    
                } catch (InterruptedException | IOException ex) {
                    Logger.getLogger(CommunicationThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

