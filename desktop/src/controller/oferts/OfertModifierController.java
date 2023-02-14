/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.oferts;

import Entities.Ofert;
import controller.MainBusinessmanController; 
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField; 
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
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class OfertModifierController implements Initializable {

    @FXML
    private MFXTextField nameTextField;
    @FXML
    private TextArea descriptionTextArea; 
    @FXML
    private MFXTextField ubicationTextField;
    @FXML
    private MFXTextField salaryTextField;
    @FXML
    private MFXTextField contractTypeTextField;
    @FXML
    private MFXButton confirmActionButton; 
    
    private Ofert modifyOfert = null;  
    private Text labelsInfo;
    @FXML
    private MFXButton changeLabelButton;
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
         
         
    }
    
    private void initializeData(){ 
            
             
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        if(processedInput[0].equals("ModO")){
            changeLabelButton.setDisable(true);
            confirmActionButton.setText("Modify");
            
        }else{
            confirmActionButton.setText("Add");
        }   
    } 

    @FXML
    private void exitWindow(ActionEvent event) { 
        MainBusinessmanController.getInstance().setMainPane("../view/oferts/MyOferts.fxml","My Oferts"); 
    }

    @FXML
    private void confirmAction(ActionEvent event) {
        if(confirmActionButton.getText().equals("Modify")){
            if(checkUserInput() == true){
                if(!nameTextField.getText().equals(modifyOfert.getName()) ||
                !descriptionTextArea.getText().equals(modifyOfert.getDescription()) ||
                !ubicationTextField.getText().equals(modifyOfert.getUbication()) ||
                !salaryTextField.getText().equals(modifyOfert.getSalary()) ||
                !contractTypeTextField.getText().equals(modifyOfert.getContractType())){ 
                
                     
                    JustWorkApp.sendMessage("ModO:"+
                            modifyOfert.getId()+":"
                            +nameTextField.getText()+":"
                            +descriptionTextArea.getText()+":"
                            +ubicationTextField.getText()+":"
                            +salaryTextField.getText()+":"
                            +contractTypeTextField.getText()); 
                    
                    String[] processedInput = JustWorkApp.recieveMessage().split(":");
                    if(processedInput[1].equals("C")){
                        MainBusinessmanController.getInstance().setMainPane("../view/oferts/MyOferts.fxml","My Oferts");
                    } else if(processedInput[1].equals("I")){
                        JOptionPane.showMessageDialog(null, processedInput[2]);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "You must modify at least one textfield");
                }
            }
        }else if(confirmActionButton.getText().equals("Add")){
            if(checkUserInput() == true){  
                JustWorkApp.sendMessage("AddO:"
                                        +nameTextField.getText()+":"
                                        +descriptionTextArea.getText()+":"
                                        +ubicationTextField.getText()+":"
                                        +salaryTextField.getText()+":"
                                        +contractTypeTextField.getText()+":");

                String[] processedInput = JustWorkApp.recieveMessage().split(":");
                if(processedInput[1].equals("C")){
                    MainBusinessmanController.getInstance().setMainPane("../view/oferts/MyOferts.fxml","My Oferts");
                } else if(processedInput[1].equals("I")){
                    JOptionPane.showMessageDialog(null, processedInput[2]);
                }
            }   
        }
    }
 
    
    private boolean checkUserInput(){
    
        if(nameTextField.getText().contains(":")){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain :");
            return false;
        }else if(descriptionTextArea.getText().contains(":")){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain :");
            return false;
        }else if(ubicationTextField.getText().contains(":")){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain :");
            return false;
        }else if(salaryTextField.getText().contains(":")){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain :");
            return false;
        }else if(contractTypeTextField.getText().contains(":")){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain :");
            return false;
        } 
        try{
            Integer.valueOf(salaryTextField.getText());
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "The salary text field can't contain letters");
            return false;
        }
            
        return true;
    }

    @FXML
    private void changeToLabelPane(ActionEvent event) {
        JustWorkApp.sendMessage("L:AddO");           
        MainBusinessmanController.getInstance().setMainPane("../view/labels/labelsSelection.fxml", "Add Offer > Select Labels"); 
        
        
    }
 
     private void addInfo(String[] processedInput){
         for(int i= 1;i<processedInput.length;i=i+8){
                modifyOfert = new Ofert(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],Integer.parseInt(processedInput[i+5]),processedInput[i+6]);

                nameTextField.setText(modifyOfert.getName());
                descriptionTextArea.setText(modifyOfert.getDescription()); 
                ubicationTextField.setText(modifyOfert.getUbication());
                salaryTextField.setText(String.valueOf(modifyOfert.getSalary()));
                contractTypeTextField.setText(modifyOfert.getContractType());
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
                    labelsInfo.setText(labelsString);
                    modifyOfert.setLabelsList(labelsList);

                }
            }
     }
}
