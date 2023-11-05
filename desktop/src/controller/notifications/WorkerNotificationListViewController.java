/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.notifications;
    
import Entities.Ofert; 
import Entities.WorkerNotification;
import controller.MainBusinessmanController;
import controller.MainWorkerController;
import java.net.URL;  
import java.util.List;
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
public class WorkerNotificationListViewController implements Initializable { 
    @FXML
    private Text infoText;
    private int notificationId;
    private int offerId;
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
    }
    
    public void configurateNotification(WorkerNotification notification){
        notificationId = notification.getNotificationId();
        offerId = notification.getOfferId();
        infoText.setText("You have a new offer for your alert\" "+notification.getProfileName()+" \" ");
    }

    @FXML
    private void delete(ActionEvent event) {
        JustWorkApp.sendMessage(CL_DELETE_NOTIFICATION+":"+notificationId); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        if(processedInput[1].equals("C")){
            MainWorkerController.getInstance().setMainPane("../view/notifications/MyWorkerNotifications.fxml", "My Notifications");
        }else if(processedInput[1].equals("I")){
            JOptionPane.showMessageDialog(null, processedInput[2]);
        }  
    }

    @FXML
    private void see(ActionEvent event) {
        JustWorkApp.sendMessage(CL_OFFER_DETAILS+":"+offerId);   
        
        MainWorkerController.getInstance().setMainPane("../view/oferts/OfertViewer.fxml", "Ofert Viewer");
         
        
    }
} 
