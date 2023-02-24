/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.candidatures;
   
import Entities.Candidature;
import Entities.Knowledge;   
import Entities.Ofert; 
import cells.KnowledgeCell;
import controller.MainWorkerController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;  
import java.net.URL; 
import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.List;
import java.util.ResourceBundle; 
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent; 
import javafx.fxml.FXML;  
import javafx.fxml.Initializable;   
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;  
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class CandidatureViewerController implements Initializable {

     
    @FXML
    private MFXLegacyListView<Knowledge> workListView;
    @FXML
    private MFXLegacyListView<Knowledge> qualificationListView;
    @FXML
    private MFXLegacyListView<String> labelsListView;
    @FXML
    private Text stateText;
    @FXML
    private ToggleGroup StateToogleGroup;
    @FXML
    private MFXButton contactButton;
    @FXML
    private RadioButton acceptedStateButton;
    @FXML
    private RadioButton deniedStateButton;
    @FXML
    private MFXButton changeStateButton;
    
    private Candidature candidature;
    
    //private MainBusinessmanController bController; 
    
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
        initializeData(); 
        workListView.setCellFactory(param -> new KnowledgeCell()); 
        qualificationListView.setCellFactory(param -> new KnowledgeCell()); 

        if(MainWorkerController.getInstance() != null){
            acceptedStateButton.setVisible(false);

            deniedStateButton.setVisible(false);
            
            changeStateButton.setVisible(false);
        }
        
        labelsListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
                workListView.getItems().clear();
                qualificationListView.getItems().clear();
                String label = labelsListView.getSelectionModel().getSelectedItems().get(0);
                JustWorkApp.sendMessage("KBL:"+candidature.getWorkerId()+":"+label+":"+"WorkExperience"); 
                String[] processedInput = JustWorkApp.recieveMessage().split(":");  
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
                    
                    workListView.getItems().add(knowledge);
                }
                
                JustWorkApp.sendMessage("KBL:"+candidature.getWorkerId()+":"+label+":"+"Qualification"); 
                processedInput = JustWorkApp.recieveMessage().split(":");  
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
                    qualificationListView.getItems().add(knowledge);
                }
            } 
        });
            
    } 
    
    private void initializeData(){ 
         String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
         for(int i= 1;i<processedInput.length;i=i+7){
            candidature = new Candidature(Integer.parseInt(processedInput[i]),Integer.parseInt(processedInput[i+1]),Integer.parseInt(processedInput[i+2]),processedInput[i+3],processedInput[i+4], processedInput[i+5]);
            String[] labels = processedInput[i+6].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);
                }
                candidature.setLabels(labelsList);
                labelsListView.getItems().addAll(labelsList); 
                this.changeStateText(candidature.getState());
            } 
        }
    } 

    @FXML
    private void contact(ActionEvent event) {
        
    }

    @FXML
    private void changeState(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) StateToogleGroup.getSelectedToggle();
        String toogleGroupValue = selectedRadioButton.getText();
        JustWorkApp.sendMessage("CCS:"+candidature.getId()+":"+toogleGroupValue);
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        if(processedInput[1].equals("C")){
            this.changeStateText(toogleGroupValue);
        }
    }
    
    private void changeStateText(String state){
        switch (state) {
            case "Accepted" -> {
                contactButton.setDisable(false);
                stateText.setText("Accepted");
                stateText.setFill(Color.GREEN);
            }
            case "Pending" -> {
                contactButton.setDisable(true);
                stateText.setText("Pending");
                stateText.setFill(Color.ORANGE);
            }
            case "Denied" -> {
                contactButton.setDisable(true);
                stateText.setText("Denied");
                stateText.setFill(Color.RED);
            }
            default -> {
            }
        }
    }
    
}
