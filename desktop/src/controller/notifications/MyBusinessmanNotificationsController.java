/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.notifications;
 
import Entities.BusinessmanNotification; 
import cells.BusinessmanNotificationCell; 
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView; 
import java.net.URL;
import java.util.ResourceBundle;  
import javafx.fxml.FXML;  
import javafx.fxml.Initializable;  
import util.Messages;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class MyBusinessmanNotificationsController implements Initializable {

     
    @FXML
    private MFXLegacyListView<BusinessmanNotification> notificationsListView;
    
     
    
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
         
        notificationsListView.setCellFactory(param -> new BusinessmanNotificationCell());  
       
        initializeData(); 

    } 
    
    private void initializeData(){  
        JustWorkApp.sendMessage(Messages.CL_MY_NOTIFICATIONS); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        for(int i= 1;i<processedInput.length;i=i+4){ 
            notificationsListView.getItems().add(new BusinessmanNotification(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),Integer.parseInt(processedInput[i+2]),processedInput[i+3])); 
        }
    }
}