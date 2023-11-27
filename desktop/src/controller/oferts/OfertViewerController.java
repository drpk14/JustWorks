/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.oferts;

import Entities.Ofert;
import controller.MainBusinessmanController; 
import controller.MainWorkerController;
import io.github.palexdev.materialfx.controls.MFXButton; 
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;  
import javafx.event.ActionEvent;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javax.swing.JOptionPane; 
import static util.Messages.*;
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
    private Text labelTextField;
     
    private int offerId = 0;
    @FXML
    private MFXButton candidateButton;
    @FXML
    private MFXButton exitButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData(); 
    }
    
    private void initializeData(){  
        String[] processedInput = JustWorkApp.recieveMessage().split(":");  
        for(int i= 2;i<processedInput.length;i=i+8){
            Ofert newOffer = new Ofert(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],Integer.parseInt(processedInput[i+5]),processedInput[i+6]);
            offerId = Integer.parseInt(processedInput[i]);
            nameTextField.setText(newOffer.getName());
            descriptionTextArea.setText(newOffer.getDescription()); 
            businessmanTextField.setText(newOffer.getUser());
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
            
            int option = Integer.parseInt(processedInput[1]);

        switch (option){
            case 1:
                candidateButton.setText("See Candidatures"); 

                break;
            case 2:
                candidateButton.setVisible(false);
                break;
            case 3:
                candidateButton.setText("Candidate"); 
                break;
            }
        } 
    } 

    @FXML
    private void exitWindow(ActionEvent event) {
        if(MainBusinessmanController.getInstance() != null){
            MainBusinessmanController.getInstance().setMainPane("/view/oferts/MyOferts.fxml","My Oferts"); 
        }else if(MainWorkerController.getInstance() != null){ 
        
            MainWorkerController.getInstance().setMainPane("/view/oferts/AllOferts.fxml", "All Oferts");
        }
    } 
        
    @FXML
    private void candidate(ActionEvent event) {
        if(MainBusinessmanController.getInstance() != null){
            JustWorkApp.sendMessage(CL_CANDIDATURE_DETAILS+":"+offerId); 
            MainBusinessmanController.getInstance().setMainPane("/view/candidatures/CandidaturesForOffer.fxml","Candidatures for Offer");
            
        }else if(MainWorkerController.getInstance() != null){ 
            JustWorkApp.sendMessage(CL_CHECK_IF_CANDIDATURE_IS_ABLE+":"+offerId); 
            String[] processedInput = JustWorkApp.recieveMessage().split(":");
            if(processedInput[1].equals("C")){ 
                JustWorkApp.sendMessage(CL_ADD_CANDIDATURE+":"+offerId); 
                String[] processedInput2 = JustWorkApp.recieveMessage().split(":");
                if(processedInput2[1].equals("C")){
                    MainWorkerController.getInstance().setMainPane("/view/candidatures/MyCandidatures.fxml","My Candidatures");
                }else{
                    JOptionPane.showMessageDialog(null, "You already have one candidature for this offer");
                }
            }else if(processedInput[1].equals("I")){
                if(processedInput[2].equals("NonOptionals")){
                    if(JOptionPane.showConfirmDialog(null, "You don't have one knowledge for each obligatory label, Do you want to make candidature?") == 0){
                        JustWorkApp.sendMessage(CL_ADD_CANDIDATURE+":"+offerId); 
                        String[] processedInput2 = JustWorkApp.recieveMessage().split(":");
                        if(processedInput2[1].equals("C")){
                            MainWorkerController.getInstance().setMainPane("/view/candidatures/MyCandidatures.fxml","My Candidatures");
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "You don't have one knowledge for any label");
                }
            }
        }
    }
}
