/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.oferts;

import Entities.Ofert; 
import controller.MainBusinessmanController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent; 
import javafx.fxml.FXML; 
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable; 
import javax.swing.JOptionPane;
import util.OfertCell;
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
            
        /*try{    
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("../../view/MainBusinessman.fxml"));
            loader.load(); 
            bController = loader.getController();
            
        } catch (IOException ex) {
            Logger.getLogger(MyOfertsController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
            
    } 
    
    private void initializeData(){
        
        try {
            JustWorkApp.out.println("MyO:");
            String[] entradaDividida = JustWorkApp.in.readLine().split(":");   
            for(int i= 1;i<entradaDividida.length;i=i+7){
                Ofert newOfert = new Ofert(Integer.parseInt(entradaDividida[i]),entradaDividida[i+1],entradaDividida[i+2],entradaDividida[i+3],entradaDividida[i+4],Integer.parseInt(entradaDividida[i+5]),entradaDividida[i+6]);
                listView.getItems().add(newOfert);
                
            } 
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
    }
    
    @FXML
    private void manageButtonsActions(ActionEvent event){
        if(event.getSource() == addButton){
            JustWorkApp.out.println("AddO:");  
            MainBusinessmanController.getInstance().setMainPane("../view/oferts/OfertModifier.fxml","My Oferts > Add Ofert");
        }else{
            if(listView.getSelectionModel().getSelectedItem() != null){
                if(event.getSource() == seeButton){   
                    JustWorkApp.out.println("ODet:"+listView.getSelectionModel().getSelectedItem().getId());  
                    MainBusinessmanController.getInstance().setMainPane("../view/oferts/OfertViewer.fxml","My Oferts > Ofert Viewer");
                }else if(event.getSource() == modifyButton){
                    JustWorkApp.out.println("ModO:"+listView.getSelectionModel().getSelectedItem().getId());  
                    MainBusinessmanController.getInstance().setMainPane("../view/oferts/OfertModifier.fxml","My Oferts > Modify Ofert");
                }else if(event.getSource() == deleteButton){ 
                    JustWorkApp.out.println("DelO:"+listView.getSelectionModel().getSelectedItem().getId());   
                    
                    try {
                        String[] processedInput = JustWorkApp.in.readLine().split(":");
                        if(processedInput[1].equals("C")){
                            MainBusinessmanController.getInstance().setMainPane("../view/oferts/MyOferts.fxml","My Oferts");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(MyOfertsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Necesitas seleccionar algún elemento de la lista");
            }
        }
    }
        
}
