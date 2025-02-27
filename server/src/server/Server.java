package server;
 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;   
import java.net.SocketException;
import java.util.Properties;  
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static boolean follow  =true;

    public static boolean isFollow() {
        return follow;
    }

    public static void setFollow(boolean follow) {
        Server.follow = follow;
    }
    
    
    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream("config.ini");
        Properties prop = new Properties();
        prop.load(is); 
        int portService = Integer.valueOf(prop.getProperty("port"));
        int udpPortService = Integer.valueOf(prop.getProperty("UDPPort"));
        ServerSocket serverSocket = null;
        
        
        
        
        try {
            System.out.println("Server listening");
            serverSocket = new ServerSocket(portService);
        } catch (IOException e) {
            System.err.println("Not able to listen in this port: " + portService);
            System.exit(1);
        } 
        
        try {
            ProcessBuilder pb = new ProcessBuilder("C:\\xampp\\xampp_start.exe");
            Process process = pb.start();
            process.waitFor(); 

            int exitCode = process.exitValue();
            if (exitCode == 0) {
                System.out.println("Xampp started correctly");
            } else {
                System.err.println("Error starting xampp");
            }
        } catch (IOException e) {
            System.err.println("Error starting xampp: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("Error waiting for xampp to start: " + e.getMessage());
        }

        
        Socket clientSocket = null; 
        SharedColection sharedColection = new SharedColection();
        
        try {
            while (Server.isFollow()) {
                clientSocket = serverSocket.accept();
                new ServerThread(clientSocket,sharedColection,clientSocket.getInetAddress(),udpPortService).start();
                
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
        private boolean anotherTime; 
        private InetAddress ipAddress;
        private int port;
        private DatagramSocket serverSocket;
        PrintWriter out = null;
        BufferedReader in = null;
 

        public ServerThread(Socket nCliente,SharedColection sharedColection,InetAddress ipAddress,int port) {
            try {
                clientSocket = nCliente;
                this.protocol = new Protocol(this,sharedColection);
                anotherTime = true;
                this.sharedColection = sharedColection;
                this.ipAddress = ipAddress;
                this.port = port;
                
                out = new PrintWriter(clientSocket.getOutputStream(), true); 
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //startUDPConnection();
                serverSocket = new DatagramSocket();
                
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        private void startUDPConnection(){
            try {
                byte[] receiveData = new byte[1024]; 
                serverSocket = new DatagramSocket(port);
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
                System.out.println("Antes de recibir el mensaje UDP");
                serverSocket.receive(receivePacket); 
                System.out.println("Despues de recibir el mensaje UDP");
                ipAddress = receivePacket.getAddress(); 
                port = receivePacket.getPort();
                
            } catch (SocketException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void sendUDPMessage(int messageID){
            byte[] sendData = new byte[1024];
            try {
                String message = "";
                switch(messageID){
                    case 1:
                        message = "NewOffer";
                        break;
                    case 2:
                        message = "NewCandidature";
                        break;
                    case 3:
                        message = "CandidatureStateChanged";
                        break;
                    case 4:
                        message = "NewMessage";
                        break;  
                    case 5:
                        message = "ServerShoutDown";
                        break;     
                }
                
                sendData = message.getBytes();
                
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, port); 
                serverSocket.send(sendPacket);
                System.out.println("Mensaje enviado");
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
        
        public void write(String text){
        
            out.println(text); 
        }
        
        public String read(){
        
            try {
                return in.readLine();
            }  catch (java.net.SocketException ex) {
                if(protocol.myUser != null){
                    sharedColection.remove(protocol.myUser.getUser()); 
                    this.setAnotherTime(false);
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return null;
        }
 
        public void run() { 
                
            String input = "";
            String output = ""; 

            try{
                while (anotherTime) { 
                    
                    
                    input = this.read();
                    
                    if(input != null){
                        
                        System.out.println("El servidor ha recibido: " + input); 

                        output = protocol.processInput(input);
                        this.write(output);
                        System.out.println("El servidor ha devuelto: "+output);
                    } 
                }
            }catch(java.lang.NullPointerException ex){ 
                ex.printStackTrace();
            }catch(Exception ex){ 
                ex.printStackTrace();
            }
            
            System.out.println("Client disconected");
        }
         
    } 
}
