/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.oferts;

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
import java.util.ArrayList; 
import java.util.List; 
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class MyOfertsController implements Initializable {

    
    @FXML
    private MFXLegacyListView<Ofert> listView;
    @FXML
    private MFXButton addButton;
    @FXML
    private MFXButton modifyButton;
    @FXML
    private MFXButton deleteButton;  
    @FXML
    private MFXButton seeButton;
    
    //private MainBusinessmanController bController; 
    
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
            initializeData();
            listView.setCellFactory(param -> new OfertCell()); 
          
            
    } 
    
    private void initializeData(){ 
         
        JustWorkApp.sendMessage("MyO:"); 
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
    
    @FXML
    private void manageButtonsActions(ActionEvent event){
        if(event.getSource() == addButton){
            JustWorkApp.sendMessage("AddO:");   
            MainBusinessmanController.getInstance().setMainPane("../view/oferts/OfertModifier.fxml","My Oferts > Add Ofert");
        }else{
            if(listView.getSelectionModel().getSelectedItem() != null){
                if(event.getSource() == seeButton){   
                    JustWorkApp.sendMessage("ODet:"+listView.getSelectionModel().getSelectedItem().getId());  
                    
                    MainBusinessmanController.getInstance().setMainPane("../view/oferts/OfertViewer.fxml","My Oferts > Ofert Viewer");
                    
                }else if(event.getSource() == modifyButton){
                    JustWorkApp.sendMessage("ModO:"+listView.getSelectionModel().getSelectedItem().getId());  
                    MainBusinessmanController.getInstance().setMainPane("../view/oferts/OfertModifier.fxml","My Oferts > Modify Ofert");
                }else if(event.getSource() == deleteButton){ 
                    JustWorkApp.sendMessage("DelO:"+listView.getSelectionModel().getSelectedItem().getId());   
                     
                    String[] processedInput = JustWorkApp.recieveMessage().split(":");
                    if(processedInput[1].equals("C")){
                        MainBusinessmanController.getInstance().setMainPane("../view/oferts/MyOferts.fxml","My Oferts");
                    } 
                }
            }else{
                JOptionPane.showMessageDialog(null, "Necesitas seleccionar algún elemento de la lista");
            }
        }
    }
        
}
