/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.notifications;
 
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView; 
import java.net.URL;
import java.util.ResourceBundle;
import Entities.Notification;
import cells.NotificationCell; 
import javafx.fxml.FXML;  
import javafx.fxml.Initializable;  
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class MyNotificationsController implements Initializable {

     
    @FXML
    private MFXLegacyListView<Notification> notificationsListView;
    
     
    
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
         
            notificationsListView.setCellFactory(param -> new NotificationCell());  
            initializeData(); 
            
    } 
    
    private void initializeData(){  
        JustWorkApp.sendMessage("MyN:"); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        for(int i= 1;i<processedInput.length;i=i+4){ 
            notificationsListView.getItems().add(new Notification(Integer.parseInt(processedInput[i]),processedInput[i+1],Integer.parseInt(processedInput[i+2]),processedInput[i+3])); 
        } 
    } 
}
