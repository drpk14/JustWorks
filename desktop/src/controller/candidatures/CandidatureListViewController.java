/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.candidatures;
     
import Entities.Candidature;
import controller.MainBusinessmanController;
import controller.MainWorkerController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;   
import java.util.ResourceBundle; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;  
import javafx.scene.text.Text;  
import javax.swing.JOptionPane;
import util.Messages;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author david
 */
public class CandidatureListViewController implements Initializable { 
    @FXML
    private Text infoText;
    private int candidatureId;
    @FXML
    private MFXButton deleteButton;

    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        if(MainBusinessmanController.getInstance() != null){
            deleteButton.setVisible(false);
        }
    }   
    
    public void configurateCandidature(Candidature candidature){
        candidatureId = candidature.getId();
        
        infoText.setText("Offer: \" "+candidature.getOfferName()+"\" Worker\" "+candidature.getWorkerName()+"\"");
    }

    @FXML
    private void delete(ActionEvent event) {
        JustWorkApp.sendMessage("DelN:"+candidatureId); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        if(processedInput[1].equals("C")){
            MainWorkerController.getInstance().setMainPane("../view/notifications/MyNotifications.fxml", "My Notifications");
        }else if(processedInput[1].equals("I")){
            JOptionPane.showMessageDialog(null, processedInput[2]);
        }  
    }

    @FXML
    private void see(ActionEvent event) {
        
        JustWorkApp.sendMessage(Messages.CL_CANDIDATURE_DETAILS+":"+candidatureId);   
        
        if(MainBusinessmanController.getInstance() != null){
            MainBusinessmanController.getInstance().setMainPane("../view/candidatures/CandidatureViewer.fxml", "Candidature Viewer");
        }else if(MainWorkerController.getInstance() != null){ 
            MainWorkerController.getInstance().setMainPane("../view/candidatures/CandidatureViewer.fxml", "Candidature Viewer");
        }  
    }
} 
