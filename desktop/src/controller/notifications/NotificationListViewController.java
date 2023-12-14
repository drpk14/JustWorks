/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.notifications;
     
import Entities.CandidatureStateChangedNotification;
import Entities.NewCandidatureNotification;
import Entities.NewMessageNotification;
import Entities.NewOfferNotification;
import Entities.Notification;
import controller.MainBusinessmanController;
import controller.MainWorkerController;
import java.net.URL;   
import java.util.ResourceBundle; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;  
import javafx.scene.text.Text;  
import javax.swing.JOptionPane; 
import util.Messages;
import static util.Messages.*;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author david
 */
public class NotificationListViewController implements Initializable { 
    @FXML
    private Text infoText;
    private Notification notification;
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
    }
    
    public void configurateNotification(Notification notification){
        this.notification = notification;
        String message = "";
        if(notification instanceof CandidatureStateChangedNotification){
            CandidatureStateChangedNotification candidatureStateChangedNotification = (CandidatureStateChangedNotification) notification;
            message = "The state of the candidature for the offer: "+candidatureStateChangedNotification.getOfferName()+" has change";
        }else if(notification instanceof NewCandidatureNotification){
            NewCandidatureNotification newCandidatureNotification = (NewCandidatureNotification) notification;
            message = "You have a new candidature for your offer: "+newCandidatureNotification.getOfferName();
        }else if(notification instanceof NewOfferNotification){
            NewOfferNotification newOfferNotification = (NewOfferNotification) notification;
            message = "You have a new offer for your alert: "+newOfferNotification.getProfileName();
        }else if(notification instanceof NewMessageNotification){
            NewMessageNotification newMessageNotification = (NewMessageNotification) notification;
             message = "You have a new message for the offer: "+newMessageNotification.getOfferName();
        }
        infoText.setText(message);
    }

    @FXML
    private void delete(ActionEvent event) {
        JustWorkApp.sendMessage(CL_DELETE_NOTIFICATION+":"+notification.getNotificationId()); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        if(processedInput[1].equals("C")){
            if(MainBusinessmanController.getInstance() != null){
                MainBusinessmanController.getInstance().setMainPane("../view/notifications/MyNotifications.fxml", "My Notifications");
            }else if(MainWorkerController.getInstance() != null){ 
                MainWorkerController.getInstance().setMainPane("../view/notifications/MyNotifications.fxml", "My Notifications");
            }
            
        }else if(processedInput[1].equals("I")){
            JOptionPane.showMessageDialog(null, processedInput[2]);
        }
    }

    @FXML
    private void see(ActionEvent event) {
        if(notification instanceof CandidatureStateChangedNotification){
            CandidatureStateChangedNotification candidatureStateChangedNotification = (CandidatureStateChangedNotification) notification;
            JustWorkApp.sendMessage(Messages.CL_CANDIDATURE_DETAILS+":"+candidatureStateChangedNotification.getCandidatureId());   
            MainWorkerController.getInstance().setMainPane("/view/candidatures/CandidatureViewer.fxml", "Candidature Viewer");
            
        
        }else if(notification instanceof NewCandidatureNotification){
            NewCandidatureNotification newCandidatureNotification = (NewCandidatureNotification) notification;
            JustWorkApp.sendMessage(Messages.CL_CANDIDATURE_DETAILS+":"+newCandidatureNotification.getCandidatureId());   
            MainBusinessmanController.getInstance().setMainPane("/view/candidatures/CandidatureViewer.fxml", "Candidature Viewer");
            
        }else if(notification instanceof NewOfferNotification){
            NewOfferNotification newOfferNotification = (NewOfferNotification) notification;
            JustWorkApp.sendMessage(CL_OFFER_DETAILS+":"+newOfferNotification.getOfferId());   
        
            MainWorkerController.getInstance().setMainPane("../view/oferts/OfertViewer.fxml", "Ofert Viewer");
         
        
        }else if(notification instanceof NewMessageNotification){
            NewMessageNotification newMessageNotification = (NewMessageNotification) notification;
            JustWorkApp.sendMessage(Messages.CL_CANDIDATURE_DETAILS+":"+newMessageNotification.getCandidatureId());   
        
            if(MainBusinessmanController.getInstance() != null){
                MainBusinessmanController.getInstance().setMainPane("/view/candidatures/CandidatureViewer.fxml", "Candidature Viewer");
            }else if(MainWorkerController.getInstance() != null){ 
                MainWorkerController.getInstance().setMainPane("/view/candidatures/CandidatureViewer.fxml", "Candidature Viewer");
            }
        }
    }
} 
