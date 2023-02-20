package server;
 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;  
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties; 
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    
    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream("config.ini");
        Properties prop = new Properties();
        prop.load(is); 
        int portService = Integer.valueOf(prop.getProperty("port"));
        ServerSocket serverSocket = null;
        
        try {
            System.out.println("Server listening");
            serverSocket = new ServerSocket(portService);
        } catch (IOException e) {
            System.err.println("Not able to listen in this port: " + portService);
            System.exit(1);
        }
        
        Socket clientSocket = null;   
        SharedColection sharedColection = new SharedColection();
        
        try {
            while (true) {
                clientSocket = serverSocket.accept();
                new ServerThread(clientSocket,sharedColection).start();
                
                System.out.println("Client connected.");
            }
        } catch (IOException e) {
            System.err.println("Fail acepting connection."); 
        } 
    }
    
    public static class ServerThread extends Thread {
        
        
        private Socket clientSocket = null; 
        private Protocol protocol;  
        SharedColection sharedColection;
        public boolean anotherTime; 
        PrintWriter out = null;
        BufferedReader in = null;

        public ServerThread(Socket nCliente,SharedColection sharedColection) {
            try {
                clientSocket = nCliente;
                this.protocol = new Protocol(this,sharedColection);
                anotherTime = true;
                this.sharedColection = sharedColection;
                
                out = new PrintWriter(clientSocket.getOutputStream(), true); 
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
        
        public boolean getAnotherTime() {
            return anotherTime;
        }

        public void setAnotherTime(boolean anotherTime) {
            this.anotherTime = anotherTime;
        } 
 
        public void run() { 
                
            String input = "";
            String output = ""; 

            while (anotherTime) { 

                input = this.read();
                System.out.println("El servidor ha recibido: " + input);


                output = protocol.processInput(input);
                this.write(output);
                System.out.println("El servidor ha devuelto: "+output);
            }
              
        }
        
        public void write(String text){
        
            out.println(text); 
        }
        
        public String read(){
        
            try {
                return in.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return null;
        }
    } 
}
