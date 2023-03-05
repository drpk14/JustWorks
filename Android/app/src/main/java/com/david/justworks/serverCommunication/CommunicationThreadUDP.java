/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.david.justworks.serverCommunication;
  
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.david.justworks.R;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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

    private Context context;
    public CommunicationThreadUDP(String ip, int port,Context context) {
        this.ip = ip;
        this.port = port;
        this.context = context;
    } 
 

     
    

    @Override
    public void run() {
        Log.d("ComunicationThreadUDP","Principio del run");

        byte[] buffer = new byte[1024];
        try {
            socket = new DatagramSocket();

            String mensaje = "Starting the UDP Connection";

            buffer = mensaje.getBytes();

            DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length,InetAddress.getByName(ip), port);
            Log.d("ComunicationThreadUDP","IP:    "+ip+"   Puerto:     "+port);
            Log.d("ComunicationThreadUDP","Antes de enviar el mensaje");
            socket.send(pregunta);
            Log.d("ComunicationThreadUDP","Mnesjae enviado");


        } catch (SocketException ex) {
            Logger.getLogger(CommunicationThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(CommunicationThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] receiveData = new byte[1024];
        while(true){
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                Log.d("ComunicationThreadUDP","antes del mensaje");
                socket.receive(receivePacket);
                Log.d("ComunicationThreadUDP","despues del mensaje");
                String response = new String(receivePacket.getData());
                System.out.println(response);
                if(response.contains("Notification")){
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                    // Crear la notificación y enviarla
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "canal_id")
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("Título de la notificación")
                            .setContentText("Contenido de la notificación")
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

                    int notificationId = 1; // ID de la notificación
                    notificationManager.notify(notificationId, builder.build());

                }else if(response.contains("ServerShoutDown")){
                }
                
            } catch (IOException ex) {
                Logger.getLogger(CommunicationThreadUDP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

