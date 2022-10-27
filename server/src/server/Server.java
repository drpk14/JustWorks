package server;
 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;  
import java.util.Properties; 

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
        
        try {
            while (true) {
                clientSocket = serverSocket.accept();
                new ServerThread(clientSocket).start();
                System.out.println("Client connected.");
            }
        } catch (IOException e) {
            System.err.println("Fail acepting connection."); 
        } 
    }
    
    public static class ServerThread extends Thread {
        
        
        private Socket clientSocket = null; 
        private Protocol protocol;  
        
        public boolean anotherTime; 

        public ServerThread(Socket nCliente ) {
            clientSocket = nCliente;
            this.protocol = new Protocol(this);   
            anotherTime = true; 
             
        }  
        
        public boolean getAnotherTime() {
            return anotherTime;
        }

        public void setAnotherTime(boolean anotherTime) {
            this.anotherTime = anotherTime;
        } 
 
        public void run() {
            
            try {
                PrintWriter out = null;
                BufferedReader in = null;
                String input = "";
                String output = "";
                
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                
                while (anotherTime) {
                    
                    input = in.readLine();
                    
                    System.out.println("El servidor ha recibido: " + input);
                    
                    
                    output = protocol.processInput(input);
                    out.println(output); 
                    System.out.println("El servidor ha devuelto: "+output);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
             
        }
        
       
    } 
}
