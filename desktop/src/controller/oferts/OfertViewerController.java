/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.oferts;

import Entities.Ofert;
import controller.MainBusinessmanController; 
import controller.MainWorkerController;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;  
import javafx.event.ActionEvent;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class OfertViewerController implements Initializable {

    @FXML
    private Text nameTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Text businessmanTextField;
    @FXML
    private Text ubicationTextField;
    @FXML
    private Text salaryTextField;
    @FXML
    private Text contractTypeTextField;
    @FXML
    private MFXButton exitButton; 
    @FXML
    private Text labelTextField;
     
    private int offerId = 0;
    @FXML
    private MFXButton candidateButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
         if(MainBusinessmanController.getInstance() != null){
             candidateButton.setText("See Candidates");
        }else if(MainWorkerController.getInstance() != null){
             candidateButton.setText("Candidate");
        }
    }
    
    private void initializeData(){  
        String[] processedInput = JustWorkApp.recieveMessage().split(":");  
        for(int i= 1;i<processedInput.length;i=i+8){
            Ofert newOffer = new Ofert(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],Integer.parseInt(processedInput[i+5]),processedInput[i+6]);
                offerId = Integer.parseInt(processedInput[i]);
                nameTextField.setText(newOffer.getName());
                descriptionTextArea.setText(newOffer.getDescription()); 
                ubicationTextField.setText(newOffer.getUbication());
                salaryTextField.setText(String.valueOf(newOffer.getSalary()));
                contractTypeTextField.setText(newOffer.getContractType());
                String[] labels = processedInput[8].split(",");
                String labelsString = "";
                if(labels.length >= 2){
                    List labelsList = new ArrayList();
                    for(int j = 1;j<labels.length;j++){
                        labelsList.add(labels[j]);

                        labelsString += labels[j];
                        if(i != labels.length-1){
                            labelsString += " , ";
                        } 
                    }
                    newOffer.setLabelsList(labelsList);
                    labelTextField.setText(labelsString);
                           
                } 
        } 
    } 

    @FXML
    private void exitWindow(ActionEvent event) {
        if(MainBusinessmanController.getInstance() != null){
            MainBusinessmanController.getInstance().setMainPane("../view/oferts/MyOferts.fxml","My Oferts"); 
        }else if(MainWorkerController.getInstance() != null){ 
        
            MainWorkerController.getInstance().setMainPane("../view/oferts/AllOferts.fxml", "All Oferts");
        }
    } 

    @FXML
    private void candidate(ActionEvent event) {
        JustWorkApp.sendMessage("CheckC:"+offerId); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        if(processedInput[1].equals("C")){
            System.out.println("todo okeyyyyy");
        }else if(processedInput[1].equals("I")){
            if(processedInput[2].equals("Some")){
                System.out.println("solo unas cuantas");
            }else{
                System.out.println("aasdasdasdasd");
            }
        }
    } 
}
