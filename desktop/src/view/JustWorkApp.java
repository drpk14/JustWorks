/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream; 
import java.util.Properties; 
import javafx.application.Application;
import static javafx.application.Application.launch; 
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import util.CommunicationThread;
import util.CommunicationThreadUDP;
import util.SharedCollection;

/**
 *
 * @author david
 *  Esta clase carga el escenario y lanza la aplicacion del login
 */
public class JustWorkApp extends Application{
 
    private static double xOffset=0.0;
    private static double yOffset=0.0; 
    private static Scene scene;
    private static Stage stage; 
    private static SharedCollection sharedCollection;
    
    public static void changeScene(Parent root) {
        scene = new Scene(root); 
        
        root.setOnMousePressed((MouseEvent t) -> {
            xOffset = t.getSceneX();
            yOffset = t.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent t) -> {
            stage.setX(t.getScreenX() - xOffset);
            stage.setY(t.getScreenY() - yOffset);
        });
        
        stage.setScene(scene);
        
    }
     
    @Override
    public void start(Stage stage) throws Exception { 
        System.out.println("nueva coleccion");
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

        String ip = prop.getProperty("host");
        int port = Integer.valueOf(prop.getProperty("puerto"));
          
        
        sharedCollection = new SharedCollection();
        
        new CommunicationThread(sharedCollection,ip,port).start();
        new CommunicationThreadUDP(ip, port).start();
        JustWorkApp.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));   
        stage.initStyle(StageStyle.UNDECORATED);
        changeScene(root); 
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    } 
    
    public static void sendMessage(String message){ 
        sharedCollection.addMessage(message); 
    }
    
    public static String recieveMessage(){ 
        
        String response = "";
        synchronized (sharedCollection) {
            try {
                if(sharedCollection.isResponsesEmpty())
                    sharedCollection.wait(); 
                
                response = sharedCollection.recieveResponse();
                 
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } 
        }  
        return response;
    }
    
    public static void closeApp(){
        System.exit(0);
    }
}
