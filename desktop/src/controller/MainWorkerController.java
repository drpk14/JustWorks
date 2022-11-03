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

/**
 * FXML Controller class
 *
 * @author david
 */
public class MainWorkerController implements Initializable {

    @FXML
    private MFXButton ofertsButton;
    @FXML
    private MFXButton myProfileButton;
    @FXML
    private BorderPane mainPane;

    /**
     * Initializes the controller class.
     */ 
    
    private void setMainPane(String paneName){
        try {
            Pane view = null;
            URL fileUrl = getClass().getResource(paneName);
            
            if(fileUrl == null)
                throw new java.io.FileNotFoundException("FXML not found");

            view =FXMLLoader.load(fileUrl);
            mainPane.setCenter(view);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    }    
 
    
    @FXML
    private void changeMainPane(ActionEvent event) {
        if(event.getSource() == ofertsButton){  
            this.setMainPane("../view/AllOferts.fxml");
        }else if(event.getSource() == myProfileButton){
                
        }
    }
} 
