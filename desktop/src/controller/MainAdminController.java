package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


import io.github.palexdev.materialfx.controls.MFXButton; 
import java.io.IOException;
import java.net.URL; 
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;  
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;  
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import util.Messages;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author david
 */
public class MainAdminController implements Initializable {
 
    @FXML
    private BorderPane mainPane;
    
    @FXML
    private Text informationText; 
    
    @FXML
    private MFXButton profileButton;
     
    
    private static MainAdminController instance;
    
    /**
     * Initializes the controller class.
     */   
     
    
    public static MainAdminController getInstance() {
        return instance;
    } 
    @FXML
    private MFXButton closeServerButton;
    
    
    public void setMainPane(String paneName, String information) {
        try {
            Pane view = null;
            URL fileUrl = getClass().getResource(paneName);
            
            if(fileUrl == null)
                throw new java.io.FileNotFoundException("FXML not found");

            
            view =FXMLLoader.load(fileUrl);
            mainPane.setCenter(view);
            this.changeInformation(information);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void changeInformation(String informationString){
        informationText.setText(informationString);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        instance = this;
    }    
 
    
    @FXML
    private void changeMainPane(ActionEvent event) { 
    }

    @FXML
    private void exit(ActionEvent event) {
        JustWorkApp.sendMessage(Messages.CL_EXIT); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        if(processedInput[1].equals("C")){
            System.exit(0);
        }
    }

    @FXML
    private void closeServer(ActionEvent event) {
        
        JustWorkApp.sendMessage(Messages.CL_STOP_SERVER);
        String[] processedInput = JustWorkApp.recieveMessage().split(":");  
        if(processedInput[1].equals("C")){
            JOptionPane.showMessageDialog(null, "Shouting down the server");
            System.exit(0);
        }
    }
} 
