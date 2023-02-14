/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties; 
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */ 

public class CommunicationThread extends Thread{
    private static CommunicationThread instance; 
    
    private PrintWriter out;
    private BufferedReader in;
    
    public static synchronized CommunicationThread getInstance() {
        if (instance == null) {
            instance = new CommunicationThread();
        }
        return instance;
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
    
    
    
    private CommunicationThread() { 
        initializeConnection();
    }
    
    public void initializeConnection(){
        InputStream is;
        Properties prop = new Properties();
        try {
            is = new FileInputStream("configuracion.ini");
            prop.load(is);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String hostServerName = prop.getProperty("host");
        int servicePort = Integer.valueOf(prop.getProperty("puerto"));

        Socket echoSocket = null; 

        try {
            echoSocket = new Socket(hostServerName, servicePort);

            this.setOut(new PrintWriter(echoSocket.getOutputStream(), true));
            this.setIn(new BufferedReader(new InputStreamReader(echoSocket.getInputStream())));
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostServerName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostServerName);
            System.exit(1);
        } 
    
    }

     
    

    @Override
    public void run() {
        
        while(true){
            synchronized (SharedCollection.getInstance()) {
                try {  
                    SharedCollection.getInstance().wait();  
                    this.getOut().println(SharedCollection.getInstance().recieveMessage()); 
                    SharedCollection.getInstance().addResponse(this.getIn().readLine()); 
                    SharedCollection.getInstance().notify(); 
                    
                } catch (InterruptedException | IOException ex) {
                    Logger.getLogger(CommunicationThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

