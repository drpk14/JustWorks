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

/**
 * FXML Controller class
 *
 * @author david
 */
public class MainBusinessmanController implements Initializable {

    @FXML
    private MFXButton ofertsButton; 
    @FXML
    private BorderPane mainPane;
    @FXML
    private MFXButton myOfertsButton; 
    
    @FXML
    private Text informationText;
    
    private static MainBusinessmanController instance;
    
    /**
     * Initializes the controller class.
     */    
    
    

    public static MainBusinessmanController getInstance() {
        return instance;
    }
    @FXML
    private MFXButton profileButton;

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
        if(event.getSource() == ofertsButton){  
            this.setMainPane("../view/oferts/AllOferts.fxml","All Oferts");
        }else if(event.getSource() == myOfertsButton){
            this.setMainPane("../view/oferts/MyOferts.fxml","My Oferts");
        }else if(event.getSource() == profileButton){
            this.setMainPane("../view/users/UserViewer.fxml", "My Profile");
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }
} 
