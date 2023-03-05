/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.knowledges;

import Entities.Knowledge; 
import cells.KnowledgeCell; 
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView; 
import java.net.URL;
import java.util.ResourceBundle; 
import javafx.event.ActionEvent; 
import javafx.fxml.FXML;  
import javafx.fxml.Initializable; 
import javax.swing.JOptionPane;  
import controller.MainWorkerController;
import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.List;
import view.JustWorkApp;
import static util.Messages.*;

/**
 * FXML Controller class
 *
 * @author David
 */
public class MyKnowledgesController implements Initializable {

    
    @FXML
    private MFXLegacyListView<Knowledge> listView;
    @FXML
    private MFXButton addButton;
    @FXML
    private MFXButton modifyButton;
    @FXML
    private MFXButton deleteButton; 
    
    private String type;
    
    //private MainBusinessmanController bController; 
    
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
            initializeData();
            listView.setCellFactory(param -> new KnowledgeCell()); 
          
            
    } 
    
    private void initializeData(){ 
         
        
        String[] processedInput = JustWorkApp.recieveMessage().split(":");  
        if(processedInput[0].equals(S_MY_WORK_EXPERIENCE)){
            type = "WorkExperience";
        }else if(processedInput[0].equals(S_MY_QUALIFICATION)){
            type = "Qualification";
        }
        
        for(int i= 1;i<processedInput.length;i=i+9){
            Knowledge knowledge = new Knowledge(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],processedInput[i+5],LocalDate.parse(processedInput[i+6]),LocalDate.parse(processedInput[i+7]));
            String[] labels = processedInput[i+8].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);
                }
                knowledge.setLabels(labelsList);
            }
            listView.getItems().add(knowledge);
        }
         
    }
    
    @FXML
    private void manageButtonsActions(ActionEvent event){
        if(event.getSource() == addButton){
            if(type.equals("WorkExperience")){
                JustWorkApp.sendMessage(CL_ADD_KNOWLEDGE+":WE"); 

            }else if(type.equals("Qualification")){
                JustWorkApp.sendMessage(CL_ADD_KNOWLEDGE+":Q");  
            } 
            
            MainWorkerController.getInstance().setMainPane("/view/knowledges/KnowledgeModifier.fxml","My Knowledge > Add Knowledge");
        }else{
            if(listView.getSelectionModel().getSelectedItem() != null){
                if(event.getSource() == modifyButton){
                    JustWorkApp.sendMessage(CL_MODIFY_KNOWLEDGE+":"+listView.getSelectionModel().getSelectedItem().getId());  
                    MainWorkerController.getInstance().setMainPane("/view/knowledges/KnowledgeModifier.fxml","My Knowledge > Add Knowledge");
                }else if(event.getSource() == deleteButton){ 
                    JustWorkApp.sendMessage(CL_DELETE_KNOWLEDGE+":"+listView.getSelectionModel().getSelectedItem().getId());   
                     
                    String[] processedInput = JustWorkApp.recieveMessage().split(":");
                    if(processedInput[1].equals("C")){
                        
                        if(type.equals("WorkExperience")){
                            JustWorkApp.sendMessage(CL_MY_WORK_EXPERIENCE); 
                            MainWorkerController.getInstance().setMainPane("/view/knowledges/MyKnowledges.fxml","My Work Experience");
                        
                        }else if(type.equals("Qualification")){
                            JustWorkApp.sendMessage(CL_MY_QUALIFICATION); 
                            MainWorkerController.getInstance().setMainPane("/view/knowledges/MyKnowledges.fxml","My Qualification");
                    
                        } 
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "You need to select at least one element in the list");
            }
        }
    }
        
}
