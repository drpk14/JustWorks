/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.notifications;
   
import Entities.Notification;
import Entities.Ofert; 
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
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author david
 */
public class NotificationListViewController implements Initializable { 
    @FXML
    private Text infoText;
    private int notificationId;

    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
    }   
    
    public void configurateNotification(Notification notification){
        notificationId = notification.getId();
        
        infoText.setText("You have a new offer \" "+notification.getOfferName()+"\" for your label\" "+notification.getLabel()+"\"");
    }

    @FXML
    private void delete(ActionEvent event) {
        JustWorkApp.sendMessage("DelN:"+notificationId); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        if(processedInput[1].equals("C")){
            MainWorkerController.getInstance().setMainPane("../view/notifications/MyNotifications.fxml", "My Notifications");
        }else if(processedInput[1].equals("I")){
            JOptionPane.showMessageDialog(null, processedInput[2]);
        }  
    }

    @FXML
    private void see(ActionEvent event) {
        JustWorkApp.sendMessage("ODet:"+notificationId);   
        
        MainWorkerController.getInstance().setMainPane("../view/oferts/OfertViewer.fxml", "Ofert Viewer");
         
        
    }
} 
