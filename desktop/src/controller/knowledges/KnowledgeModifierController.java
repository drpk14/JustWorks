/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.knowledges;

import Entities.Knowledge; 
import controller.MainWorkerController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField; 
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode; 
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import view.JustWorkApp;
import static util.Messages.*;

/**
 * FXML Controller class
 *
 * @author David
 */
public class KnowledgeModifierController implements Initializable {

    @FXML
    private MFXTextField nameTextField; 
    @FXML
    private MFXButton confirmActionButton; 
    
    private Knowledge modifyKnowledge = null; 
    
    @FXML
    private MFXTextField titleTextField;
    @FXML
    private MFXTextField placeTextField;
    @FXML
    private DatePicker initDatePicker;
    @FXML
    private DatePicker finishDatePicker;
     
    @FXML
    private MFXButton changeLabelButton;
    @FXML
    private AnchorPane labelsPane; 
    @FXML
    private Text selectedLabelsText;
    @FXML
    private Text labelsInfo;
    @FXML
    private MFXLegacyListView<String> labelListView;
    @FXML
    private AnchorPane newLabelPane;
    @FXML
    private MFXTextField newLabelNameTextField;
    
    private String type;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
         
        labelListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        labelListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
                if(labelListView.getSelectionModel().getSelectedItems().size() <= 3){
                    String labelsString = "";
                    for(int i = 0;i<labelListView.getSelectionModel().getSelectedItems().size();i++){

                        labelsString += labelListView.getSelectionModel().getSelectedItems().get(i);
                        if(i != labelListView.getSelectionModel().getSelectedItems().size()-1){
                            labelsString += " , ";
                        }
                    }
                    selectedLabelsText.setText(labelsString);
                    labelsInfo.setText(labelsString);
                }else{
                    labelListView.getSelectionModel().getSelectedItems().remove(c);
                    JOptionPane.showMessageDialog(null, "You can't select more than 3 labels");
                }
            } 
        });
    }
    
    private void initializeData(){ 
            
             
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        
        if(processedInput[1].equals("WE")){
            type = "WorkExperience";
        }else if(processedInput[2].equals("Q")){
            type = "Qualification";
        }
        if(processedInput[0].equals("ModK")){
            changeLabelButton.setDisable(true);
            confirmActionButton.setText("Modify");
            this.addInfo(processedInput);
            
        }else{
            confirmActionButton.setText("Add"); 
            
        } 
        
        
    } 
    
    private void addInfo(String[] processedInput){
        for(int i= 2;i<processedInput.length;i=i+9){
            modifyKnowledge = new Knowledge(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],processedInput[i+5],LocalDate.parse(processedInput[i+6]),LocalDate.parse(processedInput[i+7]));
            String[] labels = processedInput[i+8].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);
                }
                modifyKnowledge.setLabels(labelsList);
            } 
            
        }
        
        nameTextField.setText(modifyKnowledge.getName());
        placeTextField.setText(modifyKnowledge.getPlace());
        titleTextField.setText(modifyKnowledge.getTitle());
        initDatePicker.setValue(modifyKnowledge.getInitDate());
        finishDatePicker.setValue(modifyKnowledge.getFinishDate());
         
        
     }

    @FXML
    private void exitWindow(ActionEvent event) { 
        this.backToMyKnowledges();
    }

    @FXML
    private void confirmAction(ActionEvent event) {
        if(confirmActionButton.getText().equals("Modify")){
            if(checkUserInput() == true){
                if(!nameTextField.getText().equals(modifyKnowledge.getName()) ||
                !placeTextField.getText().equals(modifyKnowledge.getPlace()) ||
                !titleTextField.getText().equals(modifyKnowledge.getTitle()) ||
                !initDatePicker.getValue().equals(modifyKnowledge.getInitDate()) ||
                !finishDatePicker.getValue().equals(modifyKnowledge.getFinishDate())){ 
                
                     
                    JustWorkApp.sendMessage(CL_MODIFY_KNOWLEDGE+":"+
                            modifyKnowledge.getId()+":"
                            +nameTextField.getText()+":"
                            +placeTextField.getText()+":"
                            +titleTextField.getText()+":"
                            +initDatePicker.getValue().toString()+":"
                            +finishDatePicker.getValue().toString()+":"
                            +labelsInfo.getText()); 
                    
                    String[] processedInput = JustWorkApp.recieveMessage().split(":");
                    if(processedInput[1].equals("C")){
                        this.backToMyKnowledges();
                    } else if(processedInput[1].equals("I")){
                        JOptionPane.showMessageDialog(null, processedInput[2]);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "You must modify at least one textfield");
                }
            }
        }else if(confirmActionButton.getText().equals("Add")){
            if(checkUserInput() == true){  
                JustWorkApp.sendMessage(CL_ADD_KNOWLEDGE+":"
                                        +nameTextField.getText()+":" 
                                        +placeTextField.getText()+":"
                                        +titleTextField.getText()+":"
                                        +type+":"   
                                        +initDatePicker.getValue().toString()+":"
                                        +finishDatePicker.getValue().toString()+":"
                                        +this.getLabelString()); 

                String[] processedInput = JustWorkApp.recieveMessage().split(":");
                if(processedInput[1].equals("C")){
                    this.backToMyKnowledges();
                } else if(processedInput[1].equals("I")){
                    JOptionPane.showMessageDialog(null, processedInput[2]);
                }
            }   
        }
    }
 
    
    private boolean checkUserInput(){
       
        if(nameTextField.getText().contains(":") || nameTextField.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or be empty");
            return false;
        }else if(placeTextField.getText().contains(":")|| placeTextField.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or be empty");
            return false;
        }else if(titleTextField.getText().contains(":")|| titleTextField.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or be empty");
            return false;
        }else if(initDatePicker.getValue() == null){
            JOptionPane.showMessageDialog(null, "You must select the init date");
            return false;
        }else if(finishDatePicker.getValue() == null){
            JOptionPane.showMessageDialog(null, "You must select the finish date");
            return false;
        }else if(initDatePicker.getValue().isAfter(finishDatePicker.getValue())){
            JOptionPane.showMessageDialog(null, "The init date must be before the finish date");
            return false;
        } 
        
        if(labelListView.getSelectionModel().getSelectedItems().size() <= 0 && confirmActionButton.getText().equals("Add")){
            JOptionPane.showMessageDialog(null, "You need to select at least one label");
            return false;
        } 
            
        return true;
    }

    
    private void backToMyKnowledges(){
        if(type.equals("WorkExperience")){
            JustWorkApp.sendMessage(CL_MY_WORK_EXPERIENCE); 
            MainWorkerController.getInstance().setMainPane("../view/knowledges/MyKnowledges.fxml","My Knowledges");
                        
        }else if(type.equals("Qualification")){
            JustWorkApp.sendMessage("MyQ:"); 
            MainWorkerController.getInstance().setMainPane("../view/knowledges/MyKnowledges.fxml","My Knowledges");

        }
    
    }
    
    
    
    @FXML
    private void changeToLabelPane(ActionEvent event) { 
        JustWorkApp.sendMessage(CL_ALL_LABELS);
         
        String[] processedInput2 = JustWorkApp.recieveMessage().split(":");
        for (int i = 1; i < processedInput2.length; i++) {
            if(!labelListView.getItems().contains(processedInput2[i])){
                labelListView.getItems().add(processedInput2[i]); 
            } 
        }
        this.labelsPane.setVisible(true); 
    }
 
    

    private void saveLabels(ActionEvent event) {
        labelsInfo.setText(this.getLabelString());
        this.labelsPane.setVisible(false);
    }

    private void exitLabelPane(ActionEvent event) {
        this.labelsPane.setVisible(false);
    }
    
    private String getLabelString(){
        String labelsString = "";
        System.out.println(labelListView.getSelectionModel().getSelectedItems().size());
        for(int i = 0;i<labelListView.getSelectionModel().getSelectedItems().size();i++){
            
            labelsString += labelListView.getSelectionModel().getSelectedItems().get(i);
            if(i != labelListView.getSelectionModel().getSelectedItems().size()-1){
                labelsString += ",";
            }
        }
    
        return labelsString;
    }
 
    @FXML
    private void changeToAddLabelPane(ActionEvent event) {
        this.labelsPane.setVisible(false);
        this.newLabelPane.setVisible(true); 
    }

    @FXML
    private void exitLabelsPane(ActionEvent event) {
        
        this.labelsPane.setVisible(false);
        this.newLabelPane.setVisible(false); 
    }

    @FXML
    private void saveLabel(ActionEvent event) {
        if(!newLabelNameTextField.getText().isEmpty()){
            JustWorkApp.sendMessage(CL_ADD_LABEL+":"+newLabelNameTextField.getText()); 

            
            String[] processedInput = JustWorkApp.recieveMessage().split(":");
            if(processedInput[1].equals("C")){
                labelListView.getItems().clear();
                JustWorkApp.sendMessage(CL_ALL_LABELS); 
         
         
                String[] processedInput2 = JustWorkApp.recieveMessage().split(":");
                for (int i = 1; i < processedInput2.length; i++) {
                    if(!labelListView.getItems().contains(processedInput2[i])){
                        labelListView.getItems().add(processedInput2[i]); 
                    } 
                }
                this.labelsPane.setVisible(true);
                this.newLabelPane.setVisible(false); 
            }else{
                JOptionPane.showMessageDialog(null, "This label already exits");
            }
        }
    }

    @FXML
    private void exitAddLabelPane(ActionEvent event) {
        this.labelsPane.setVisible(true);
        this.newLabelPane.setVisible(false); 
    }
}
