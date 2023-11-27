package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

  
import io.github.palexdev.materialfx.controls.MFXButton; 
import java.io.IOException;
import java.net.URL; 
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;  
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane; 
import javafx.scene.shape.Circle;
import javafx.scene.text.Text; 
import util.Messages;
import view.JustWorkApp;

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
    
    public static void resetInstance() {
        instance = null;
    }
    
    @FXML
    private MFXButton profileButton;
    @FXML
    private MFXButton notificationButton;
    @FXML
    private Circle redDot; 
    
    private Object controler;

    public Object getControler() {
        return controler;
    }
    
    public void changeRedDotVisibility(boolean visibility){
        redDot.setVisible(visibility);
    }

    public void setMainPane(String paneName, String information) {
        try {
            Pane view = null;
            FXMLLoader loader = new FXMLLoader();
            URL fileUrl = getClass().getResource(paneName);

            if (fileUrl == null)
                throw new java.io.FileNotFoundException("FXML not found");

            loader.setLocation(fileUrl);
            view = loader.load();
            mainPane.setCenter(view);
 
            controler = loader.getController(); 
             
            this.changeInformation(information);
        } catch (IOException e) {
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
            this.setMainPane("/view/oferts/AllOferts.fxml","All Oferts");
        }else if(event.getSource() == myOfertsButton){
            this.setMainPane("/view/oferts/MyOferts.fxml","My Oferts");
        }else if(event.getSource() == profileButton){
            this.setMainPane("/view/users/UserViewer.fxml", "My Profile");
        }else if(event.getSource() == notificationButton){  
            this.setMainPane("/view/notifications/MyNotifications.fxml", "My Notifications");
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        JustWorkApp.sendMessage(Messages.CL_LOGOUT); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        if(processedInput[1].equals("C")){ 
            try {
                Parent root = FXMLLoader.load(this.getClass().getResource("/view/Login.fxml"));
                JustWorkApp.changeScene(root);
            } catch (IOException ex) {
                Logger.getLogger(MainBusinessmanController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
} 
