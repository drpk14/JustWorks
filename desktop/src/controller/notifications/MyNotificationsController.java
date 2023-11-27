/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.notifications;
  
import Entities.*;
import cells.NotificationCell;
import controller.MainBusinessmanController;
import controller.MainWorkerController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView; 
import java.net.URL; 
import java.util.ResourceBundle; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;  
import javafx.fxml.Initializable;   
import javafx.scene.layout.HBox; 
import static util.Messages.*;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class MyNotificationsController implements Initializable {

     
    @FXML
    private MFXLegacyListView<Notification> notificationsListView;
    @FXML
    private MFXButton candidatureStateChangedButton;
    @FXML
    private MFXButton newCandidature;
    @FXML
    private MFXButton newOfferButton;
    @FXML
    private MFXButton newMessageButton;
    @FXML
    private HBox hbox;
     
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        notificationsListView.setCellFactory(param -> new NotificationCell());  
        if(MainBusinessmanController.getInstance() != null){
            hbox.getChildren().remove(0);
            hbox.getChildren().remove(1);
            MainBusinessmanController.getInstance().changeRedDotVisibility(false);
        }else if(MainWorkerController.getInstance() != null){  
            MainWorkerController.getInstance().changeRedDotVisibility(false);
            hbox.getChildren().remove(1);
        }
    }

    @FXML
    private void changeNotifications(ActionEvent event) {
        notificationsListView.getItems().clear();
        if(event.getSource() == candidatureStateChangedButton){  
            
            JustWorkApp.sendMessage(CL_MY_CANDIDATURE_STATE_CHANGED_NOTIFICATIONS); 
            String[] processedInput = JustWorkApp.recieveMessage().split(":");  
            for(int i= 1;i<processedInput.length;i=i+5){ 
                notificationsListView.getItems().add(new CandidatureStateChangedNotification(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),Integer.parseInt(processedInput[i+2]),processedInput[i+3],processedInput[i+4]));  
            }
            
        }else if(event.getSource() == newCandidature){
            
            JustWorkApp.sendMessage(CL_MY_NEW_CANDIDATURE_NOTIFICATIONS); 
            String[] processedInput = JustWorkApp.recieveMessage().split(":");  
            for(int i= 1;i<processedInput.length;i=i+4){ 
                notificationsListView.getItems().add(new NewCandidatureNotification(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),Integer.parseInt(processedInput[i+2]),processedInput[i+3]));  
            }
            
        }else if(event.getSource() == newOfferButton){
            JustWorkApp.sendMessage(CL_MY_NEW_OFFER_NOTIFICATIONS); 
            String[] processedInput = JustWorkApp.recieveMessage().split(":");  
            for(int i= 1;i<processedInput.length;i=i+5){ 
                notificationsListView.getItems().add(new NewOfferNotification(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),Integer.parseInt(processedInput[i+2]),Integer.parseInt(processedInput[i+3]),processedInput[i+4])); 
            }
            
        }else if(event.getSource() == newMessageButton){  
            
            JustWorkApp.sendMessage(CL_MY_NEW_OFFER_NOTIFICATIONS); 
            String[] processedInput = JustWorkApp.recieveMessage().split(":");  
            for(int i= 1;i<processedInput.length;i=i+4){ 
                notificationsListView.getItems().add(new NewMessageNotification(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),Integer.parseInt(processedInput[i+2]),processedInput[i+3])); 
            }
            
        }
    }
}