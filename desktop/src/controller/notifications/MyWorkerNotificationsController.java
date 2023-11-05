/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.notifications;
 
import Entities.BusinessmanNotification;
import Entities.WorkerNotification;
import cells.BusinessmanNotificationCell;
import cells.WorkerNotificationCell;
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
public class MyWorkerNotificationsController implements Initializable {

     
    @FXML
    private MFXLegacyListView<WorkerNotification> notificationsListView;
    
     
    
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
         
             notificationsListView.setCellFactory(param -> new WorkerNotificationCell());  
            initializeData(); 
            
    } 
    
    private void initializeData(){  
        JustWorkApp.sendMessage(Messages.CL_MY_NOTIFICATIONS); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        for(int i= 1;i<processedInput.length;i=i+4){ 
            notificationsListView.getItems().add(new WorkerNotification(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),Integer.parseInt(processedInput[i+2]),processedInput[i+3])); 
        }
    }
}