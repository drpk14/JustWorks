package controller.oferts;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

   
import Entities.Ofert;  
import Entities.Profile;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView; 
import java.net.URL; 
import java.util.ResourceBundle;  
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;  
import cells.OfertCell;
import java.util.ArrayList; 
import java.util.List; 
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import util.Messages;
import static util.Messages.CL_MY_PROFILES;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author david
 */
public class AllOfertsController implements Initializable {

    @FXML
    private MFXLegacyListView<Ofert> listView;
    @FXML
    private MFXLegacyListView<Profile> profileListView;
    @FXML
    private Text selectedProfileText;
    @FXML
    private Text selectedProfileLabelsText;
    @FXML
    private MFXTextField offerFilterTextField;
    @FXML
    private AnchorPane filterPane;
 
    /**
     * Initializes the controller class.
     */ 
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
            initializeData();
            listView.setCellFactory(param -> new OfertCell());  
            
            profileListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Profile>() {
                @Override
                public void onChanged(javafx.collections.ListChangeListener.Change<? extends Profile> c) {  
                    
                    selectedProfileText.setText(profileListView.getSelectionModel().getSelectedItem().toString()); 
                    
                    String labelsString = ""; 
                     
                    for(int i = 0;i<profileListView.getSelectionModel().getSelectedItem().getLabelList().size();i++){ 
                        labelsString += profileListView.getSelectionModel().getSelectedItem().getLabelList().get(i);
                        if(i != profileListView.getSelectionModel().getSelectedItem().getLabelList().size()-1){
                            labelsString += ",";
                        }
                    } 
                    selectedProfileLabelsText.setText(labelsString);
                }
            });
    }
    
    private void initializeData(){
        JustWorkApp.sendMessage(Messages.CL_ALL_OFFERS+":"+0+":"+"notNameFilter"); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        for(int i= 1;i<processedInput.length;i=i+8){
            Ofert newOfert = new Ofert(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],Integer.parseInt(processedInput[i+5]),processedInput[i+6]);
            String[] labels = processedInput[i+7].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);
                }
                newOfert.setLabelsList(labelsList);
            }
            listView.getItems().add(newOfert); 
        }
        
        JustWorkApp.sendMessage(CL_MY_PROFILES);  
        String[] processedInput2 = JustWorkApp.recieveMessage().split(":");  
        for(int i= 1;i<processedInput2.length;i=i+3){ 
            Profile profile = new Profile(Integer.parseInt(processedInput2[i]),processedInput2[i+1]);
            String[] labels = processedInput2[i+2].split(",");
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
    private void changeToFilterPane(ActionEvent event) {
        filterPane.setVisible(true);
    }

    @FXML
    private void cancel(ActionEvent event) {
        filterPane.setVisible(false);
    }

    @FXML
    private void clean(ActionEvent event) {
        listView.getItems().clear();
        JustWorkApp.sendMessage(Messages.CL_ALL_OFFERS+":"+0+":"+"notNameFilter"); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        for(int i= 1;i<processedInput.length;i=i+8){
            Ofert newOfert = new Ofert(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],Integer.parseInt(processedInput[i+5]),processedInput[i+6]);
            String[] labels = processedInput[i+7].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);
                }
                newOfert.setLabelsList(labelsList);
            }
            listView.getItems().add(newOfert); 
        }
        profileListView.getSelectionModel().clearSelection();
        offerFilterTextField.setText("");
        selectedProfileText.setText("");
        selectedProfileLabelsText.setText("");
        filterPane.setVisible(false);
    }

    @FXML
    private void filter(ActionEvent event) {
        listView.getItems().clear();
        String request = Messages.CL_ALL_OFFERS+":";
        if(profileListView.getSelectionModel().getSelectedItem() != null){
            request+=profileListView.getSelectionModel().getSelectedItem().getId();
            request+=":";
        }else{
            request+="0:";
        }
        
        if(!offerFilterTextField.getText().trim().equals("")){
            request+=offerFilterTextField.getText();
            request+=":";
        }else{
            request+="notNameFilter:";
        }
        
        JustWorkApp.sendMessage(request); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        for(int i= 1;i<processedInput.length;i=i+8){
            Ofert newOfert = new Ofert(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],Integer.parseInt(processedInput[i+5]),processedInput[i+6]);
            String[] labels = processedInput[i+7].split(",");
            List labelsList = new ArrayList(0);
            if(labels.length >= 2){
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);
                }
                newOfert.setLabelsList(labelsList);
            }
            listView.getItems().add(newOfert); 
        }
        filterPane.setVisible(false);
    }
} 
