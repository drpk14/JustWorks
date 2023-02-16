/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.alerts;

import controller.oferts.*;
import Entities.Ofert; 
import controller.MainBusinessmanController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView; 
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent; 
import javafx.fxml.FXML;  
import javafx.fxml.Initializable; 
import javax.swing.JOptionPane;
import cells.OfertCell;
import controller.MainWorkerController;
import java.util.ArrayList; 
import java.util.List; 
import javafx.collections.ListChangeListener;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class MyAlertsController implements Initializable {

    
    @FXML
    private AnchorPane labelsPane; 
    @FXML
    private Text selectedLabelsText;
    @FXML
    private MFXLegacyListView<String> alertListView;
    @FXML
    private MFXLegacyListView<String> labelListView;
    
    //private MainBusinessmanController bController; 
    
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
            initializeData(); 
            
            labelListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) { 
                selectedLabelsText.setText(labelListView.getSelectionModel().getSelectedItem());
                 
            } 
        });
    } 
    
    private void initializeData(){ 
         
        JustWorkApp.sendMessage("MyA:"); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        for(int i= 1;i<processedInput.length;i++){ 
            alertListView.getItems().add(processedInput[i]); 
        } 
    }
    

    @FXML
    private void changeToAddPane(ActionEvent event) {
        JustWorkApp.sendMessage("L:"); 
         
         
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        for (int i = 1; i < processedInput.length; i++) {
            if(!labelListView.getItems().contains(processedInput[i])){
                labelListView.getItems().add(processedInput[i]); 
            } 
        }
        this.labelsPane.setVisible(true);
    }

    @FXML
    private void deleteAlert(ActionEvent event) {
        if(!alertListView.getItems().isEmpty()){
            JustWorkApp.sendMessage("DelA:"+alertListView.getItems().get(0));
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

    @FXML
    private void saveAlert(ActionEvent event) {
        if(!labelListView.getItems().isEmpty()){
            JustWorkApp.sendMessage("AddA:"+labelListView.getSelectionModel().getSelectedItem());
            String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
            if(processedInput[1].equals("C")){
                MainWorkerController.getInstance().setMainPane("../view/alerts/MyAlerts.fxml", "My Alerts"); 
            }else{
                JOptionPane.showMessageDialog(null, processedInput[2]);
            }
        }else{
            JOptionPane.showMessageDialog(null, "You need to select one label");
        }
        
    
    }

    @FXML
    private void exitLabelPane(ActionEvent event) {
        
        this.labelsPane.setVisible(false);
    
    } 
}
