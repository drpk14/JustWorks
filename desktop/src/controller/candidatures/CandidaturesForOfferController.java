/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.candidatures;
  
import Entities.Candidature;
import cells.CandidatureCell;  
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView; 
import java.net.URL;
import java.util.ResourceBundle; 
import javafx.event.ActionEvent; 
import javafx.fxml.FXML;  
import javafx.fxml.Initializable;  
import java.util.ArrayList; 
import java.util.List; 
import util.Messages;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class CandidaturesForOfferController implements Initializable {

    private int offerId;
    @FXML
    private MFXLegacyListView<Candidature> listView; 
    
    //private MainBusinessmanController bController; 
    
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
            String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
            
            offerId = Integer.parseInt(processedInput[1]);
            
            
            initializeData();
            listView.setCellFactory(param -> new CandidatureCell());  
            
    } 
    
    private void initializeData(){ 
         
        this.manageList("Accepted");
         
    }
     

    @FXML
    private void changeToAccepted(ActionEvent event) {
        
        this.manageList("Accepted");
    }

    @FXML
    private void changeToPending(ActionEvent event) {
        
        this.manageList("Pending");
    }

    @FXML
    private void changeToDenied(ActionEvent event) {
        
        this.manageList("Denied");
    }
    
    private void manageList(String state) {
        listView.getItems().clear();
        
        JustWorkApp.sendMessage(Messages.CL_CANDIDATURES_FOR_ONE_OFFER+":"+offerId+":"+state); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        for(int i= 2;i<processedInput.length;i=i+7){
            Candidature candidature = new Candidature(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),Integer.parseInt(processedInput[i+2]),processedInput[i+3],processedInput[i+4], processedInput[i+5]);
            String[] labels = processedInput[i+6].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);
                }
                candidature.setLabels(labelsList);
            }
            listView.getItems().add(candidature); 
        }
    }   
}
