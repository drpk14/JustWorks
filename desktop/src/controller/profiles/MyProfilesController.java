/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.profiles;
 
import Entities.Profile; 
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView; 
import java.net.URL;
import java.util.ResourceBundle; 
import javafx.event.ActionEvent; 
import javafx.fxml.FXML;  
import javafx.fxml.Initializable; 
import javax.swing.JOptionPane; 
import controller.MainWorkerController; 
import java.util.ArrayList; 
import java.util.List; 
import static util.Messages.*;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class MyProfilesController implements Initializable {

    
    @FXML
    private MFXLegacyListView<Profile> listView;
    @FXML
    private MFXButton addButton;
    @FXML
    private MFXButton modifyButton;
    @FXML
    private MFXButton deleteButton;   
    
    //private MainBusinessmanController bController; 
    
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
            initializeData();  
            
    } 
    
    private void initializeData(){
        JustWorkApp.sendMessage(CL_MY_PROFILES);  
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
            listView.getItems().add(profile); 
        }
    }
    
    @FXML
    private void manageButtonsActions(ActionEvent event){
        if(event.getSource() == addButton){
            JustWorkApp.sendMessage(CL_ADD_PROFILE);   
            MainWorkerController.getInstance().setMainPane("/view/profiles/ProfileModifier.fxml","My Profiles > Add Profile");
        }else{ 
            if(listView.getSelectionModel().getSelectedItem() != null){
                if(event.getSource() == modifyButton){
                    JustWorkApp.sendMessage(CL_MODIFY_PROFILE+":"+listView.getSelectionModel().getSelectedItem().getId());  
                    MainWorkerController.getInstance().setMainPane("/view/profiles/ProfileModifier.fxml","My Profiles > Modify Profile");
                }else if(event.getSource() == deleteButton){
                    JustWorkApp.sendMessage(CL_DELETE_PROFILE+":"+listView.getSelectionModel().getSelectedItem().getId());   
                     
                    String[] processedInput = JustWorkApp.recieveMessage().split(":");
                    if(processedInput[1].equals("C")){
                        MainWorkerController.getInstance().setMainPane("/view/profiles/MyProfiles.fxml","My Profiles");
                    } 
                }
            }else{
                JOptionPane.showMessageDialog(null, "You need to select at least one profile");
            }
        }
    } 
}
