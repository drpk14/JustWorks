/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.labels;

import controller.oferts.*;
import Entities.Ofert;
import controller.MainBusinessmanController; 
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
import javafx.scene.control.TextArea; 
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class LabelsSelectionController implements Initializable {
 
      
    @FXML
    private MFXLegacyListView<String> selectionListView;
    @FXML
    private Text selectedLabelsText;  
    
    private String lastWindow = "";
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        
        selectionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        selectionListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
                if(selectionListView.getSelectionModel().getSelectedItems().size() <= 3){
                    String labelsString = "";
                    for(int i = 0;i<selectionListView.getSelectionModel().getSelectedItems().size();i++){

                        labelsString += selectionListView.getSelectionModel().getSelectedItems().get(i);
                        if(i != selectionListView.getSelectionModel().getSelectedItems().size()-1){
                            labelsString += " , ";
                        }
                    }
                    selectedLabelsText.setText(labelsString);
                }else{
                    selectionListView.getSelectionModel().getSelectedItems().remove(c);
                    JOptionPane.showMessageDialog(null, "You can't select more than 3 labels");
                }
            } 
        });
         
    }
    
    private void initializeData(){ 
            
        JustWorkApp.sendMessage("L:");     
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        if(processedInput[0].equals("ModO")){
            lastWindow = "Modify Offer";
        }else{ 
        }  
        
        JustWorkApp.sendMessage("L:");
         
        String[] processedInput2 = JustWorkApp.recieveMessage().split(":");
        for (int i = 1; i < processedInput2.length; i++) {
            if(!selectionListView.getItems().contains(processedInput2[i])){
                selectionListView.getItems().add(processedInput2[i]); 
            } 
        } 
    } 
 
 
  

    @FXML
    private void saveLabels(ActionEvent event) {
         JustWorkApp.sendMessage("L:"+this.getLabelString());
         String[] processedInput = JustWorkApp.recieveMessage().split(":");
         if(processedInput[1].equals("C")){
             
         }
    }

    @FXML
    private void exitLabelPane(ActionEvent event) {  
        if(lastWindow.equals("Modify Offer")){
             
         } 
    }
    
    private String getLabelString(){
        String labelsString = "";
        System.out.println(selectionListView.getSelectionModel().getSelectedItems().size());
        for(int i = 0;i<selectionListView.getSelectionModel().getSelectedItems().size();i++){
            
            labelsString += selectionListView.getSelectionModel().getSelectedItems().get(i);
            if(i != selectionListView.getSelectionModel().getSelectedItems().size()-1){
                labelsString += ",";
            }
        }
    
        return labelsString;
    }
}
