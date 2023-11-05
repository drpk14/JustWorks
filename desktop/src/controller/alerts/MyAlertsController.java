/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.alerts;
 
import Entities.Alert;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView; 
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent; 
import javafx.fxml.FXML;  
import javafx.fxml.Initializable; 
import javax.swing.JOptionPane; 
import controller.MainWorkerController;  
import util.Messages;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class MyAlertsController implements Initializable {

      
    @FXML
    private MFXLegacyListView<Alert> alertListView;  
    
    //private MainBusinessmanController bController; 
    
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
            initializeData();   
    }
    
    private void initializeData(){ 
         
        JustWorkApp.sendMessage(Messages.CL_MY_ALERTS); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        for(int i= 1;i<processedInput.length;i=i+3){ 
            alertListView.getItems().add(new Alert(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),processedInput[i+2])); 
        }
    } 

    @FXML
    private void changeToAddPane(ActionEvent event) {
       MainWorkerController.getInstance().setMainPane("../view/alerts/AlertAdder.fxml", "My Alerts > Add Alert");
    }

    @FXML
    private void deleteAlert(ActionEvent event) {
        if(!alertListView.getItems().isEmpty()){
            JustWorkApp.sendMessage(Messages.CL_DELETE_ALERT+":"+alertListView.getItems().get(0).getId());
            String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
            if(processedInput[1].equals("C")){
                MainWorkerController.getInstance().setMainPane("../view/alerts/MyAlerts.fxml", "My Alerts");
            }else{
                JOptionPane.showMessageDialog(null, processedInput[2]);
            }
        }else{
            JOptionPane.showMessageDialog(null, "You need to select one alert");
        }
    }
}
