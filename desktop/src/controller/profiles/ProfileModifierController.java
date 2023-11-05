/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.profiles;
 
import Entities.Profile; 
import controller.MainWorkerController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField; 
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.net.URL; 
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;   
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;  
import javafx.scene.control.SelectionMode;  
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javax.swing.JOptionPane; 
import util.Messages;
import static util.Messages.*;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class ProfileModifierController implements Initializable {
    
    private Profile modifyProfile = null;   
    @FXML
    private Text selectedLabelsText; 
    @FXML
    private MFXLegacyListView<String> labelListView;
    @FXML
    private AnchorPane newLabelPane;
    @FXML
    private MFXTextField newLabelNameTextField;
    @FXML
    private MFXButton confirmActionButton;
    @FXML
    private MFXTextField profileNameTextField;
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        
        labelListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        labelListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) { 
                String labelsString = "";
                for(int i = 0;i<labelListView.getSelectionModel().getSelectedItems().size();i++){ 
                    labelsString += labelListView.getSelectionModel().getSelectedItems().get(i);
                    if(i != labelListView.getSelectionModel().getSelectedItems().size()-1){
                        labelsString += " , ";
                    }
                }
                selectedLabelsText.setText(labelsString); 
            } 
        });
    }
    
    private void initializeData(){
             
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        if(processedInput[0].equals(Messages.S_MODIFY_PROFILE)){
            confirmActionButton.setText("Modify");
            this.addInfo(processedInput); 
        }else{
            confirmActionButton.setText("Add");
        }
        
        JustWorkApp.sendMessage(CL_ALL_LABELS);
         
        String[] processedInput2 = JustWorkApp.recieveMessage().split(":");
        for (int i = 1; i < processedInput2.length; i++) {
            if(!labelListView.getItems().contains(processedInput2[i])){
                labelListView.getItems().add(processedInput2[i]); 
            }
        }
        if(processedInput[0].equals(Messages.S_MODIFY_PROFILE)){ 
            /*int[] labelsIndexArray = new int[modifyProfile.getLabelList().size()]; 
            
            for(int i = 0;i<modifyProfile.getLabelList().size();i++){
                labelsIndexArray[i] = labelListView.getItems().indexOf(modifyProfile.getLabelList().get(i));
            }*/
            
            /*for(String label: modifyProfile.getLabelList()){
                labelListView.getSelectionModel().getSelectedItems().add
            }*/
        }
    }

    @FXML
    private void exitWindow(ActionEvent event) { 
        MainWorkerController.getInstance().setMainPane("/view/profile/MyProfiles.fxml","My Profiles"); 
    }

    @FXML
    private void confirmAction(ActionEvent event) {
        if(confirmActionButton.getText().equals("Modify")){
            if(checkUserInput() == true){ 
                JustWorkApp.sendMessage(CL_MODIFY_PROFILE+":"+
                        modifyProfile.getId()+":"+
                        profileNameTextField.getText()+":"
                        +selectedLabelsText.getText()); 

                String[] processedInput = JustWorkApp.recieveMessage().split(":");
                if(processedInput[1].equals("C")){
                    MainWorkerController.getInstance().setMainPane("/view/profiles/MyProfiles.fxml","My Profiles");
                } else if(processedInput[1].equals("I")){
                    JOptionPane.showMessageDialog(null, processedInput[2]);
                }
            }
        }else if(confirmActionButton.getText().equals("Add")){
            if(checkUserInput() == true){  
                JustWorkApp.sendMessage(CL_ADD_PROFILE+":"+
                                         profileNameTextField.getText()+":"
                                         +selectedLabelsText.getText()); 

                String[] processedInput = JustWorkApp.recieveMessage().split(":");
                if(processedInput[1].equals("C")){
                    MainWorkerController.getInstance().setMainPane("/view/profiles/MyProfiles.fxml","My Profiles"); 
                } else if(processedInput[1].equals("I")){
                    JOptionPane.showMessageDialog(null, processedInput[2]);
                }
            }   
        }
    } 
    
    private boolean checkUserInput(){
        
        if(profileNameTextField.getText().contains(":")|| profileNameTextField.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or be empty");
            return false;
        }
        
        if(labelListView.getSelectionModel().getSelectedItems().size() <= 0){
            JOptionPane.showMessageDialog(null, "You need to select at least one label");
            return false;
        }
            
        return true;
    }
 
    private void addInfo(String[] processedInput){
        for(int i= 1;i<processedInput.length;i=i+3){ 
            Profile profile = new Profile(Integer.parseInt(processedInput[i]),processedInput[i+1]);
            String[] labels = processedInput[i+2].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);
                    
                }
                profile.setLabelList(labelsList);
            }
            
            modifyProfile = profile;
            profileNameTextField.setText(profile.getName());
            
        }
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
        this.newLabelPane.setVisible(true); 
    }

    @FXML
    private void exitAddLabelPane(ActionEvent event) { 
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
                this.newLabelPane.setVisible(false); 
            }else{
                JOptionPane.showMessageDialog(null, "This label already exits");
            }
        }
    }

}
