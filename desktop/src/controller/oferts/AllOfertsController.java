package controller.oferts;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

   
import Entities.Ofert;
import io.github.palexdev.materialfx.controls.MFXButton; 
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView; 
import java.net.URL; 
import java.util.ResourceBundle;  
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;  
import cells.OfertCell;
import java.util.ArrayList; 
import java.util.List; 
import util.Messages;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author david
 */
public class AllOfertsController implements Initializable {

    @FXML
    private MFXTextField textField;
    @FXML
    private MFXButton searchButton;
    @FXML
    private MFXLegacyListView<Ofert> listView;
 
    /**
     * Initializes the controller class.
     */ 
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
            initializeData();
            listView.setCellFactory(param -> new OfertCell());  
    }
    
    private void initializeData(){
        
        JustWorkApp.sendMessage(Messages.CL_ALL_OFFERS); 
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
        
    } 
} 
