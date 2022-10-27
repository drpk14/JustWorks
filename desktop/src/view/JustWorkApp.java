/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

    public static PrintWriter out;
    public static BufferedReader in ;
    
    public static void initializeConnection(){
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

            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostServerName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostServerName);
            System.exit(1);
        } 
    
    }
    
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
        initializeConnection();
        JustWorkApp.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));   
        stage.initStyle(StageStyle.UNDECORATED);
        changeScene(root); 
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
