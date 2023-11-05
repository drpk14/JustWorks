/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.alerts;
  
import Entities.Profile; 
import controller.MainWorkerController; 
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.net.URL; 
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;   
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;   
import javafx.scene.text.Text;
import javax.swing.JOptionPane;  
import static util.Messages.*;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class AlertAdderController implements Initializable {
 
    @FXML
    private Text selectedProfileText;
    @FXML
    private MFXLegacyListView<Profile> profileListView;
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();   
        profileListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Profile>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Profile> c) {  
                selectedProfileText.setText(profileListView.getSelectionModel().getSelectedItem().toString()); 
            }
        });
    }
    
    private void initializeData(){
        
        JustWorkApp.sendMessage(CL_MY_PROFILES_WITHOUT_ALERTS);
         
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
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
            profileListView.getItems().add(profile); 
        }
    }

    @FXML
    private void exitWindow(ActionEvent event) {
        MainWorkerController.getInstance().setMainPane("/view/alerts/MyAlerts.fxml","My Alerts"); 
    }

    @FXML
    private void addAlert(ActionEvent event) {
        if(!profileListView.getSelectionModel().getSelectedItems().isEmpty()){
        
            JustWorkApp.sendMessage(CL_ADD_ALERT+":"
                                    +profileListView.getSelectionModel().getSelectedItem().getId());

           String[] processedInput = JustWorkApp.recieveMessage().split(":");
           if(processedInput[1].equals("C")){
               MainWorkerController.getInstance().setMainPane("/view/alerts/MyAlerts.fxml","My Alerts"); 
           } else if(processedInput[1].equals("I")){
               JOptionPane.showMessageDialog(null, processedInput[2]);
           }
        }else{
            JOptionPane.showMessageDialog(null, "You need to select at least one profile");  
        }
    }


}
