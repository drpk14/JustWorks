/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.oferts;

import Entities.Ofert;
import controller.MainBusinessmanController; 
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class OfertViewerController implements Initializable {

    @FXML
    private Text nameTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Text businessmanTextField;
    @FXML
    private Text ubicationTextField;
    @FXML
    private Text salaryTextField;
    @FXML
    private Text contractTypeTextField;
    @FXML
    private MFXButton exitButton; 
    
    //private MainBusinessmanController bController; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        
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
            String[] entradaDividida = JustWorkApp.in.readLine().split(":");  
            for(int i= 1;i<entradaDividida.length;i=i+7){
                Ofert newOfert = new Ofert(Integer.parseInt(entradaDividida[i]),entradaDividida[i+1],entradaDividida[i+2],entradaDividida[i+3],entradaDividida[i+4],Integer.parseInt(entradaDividida[i+5]),entradaDividida[i+6]);
                
                nameTextField.setText(newOfert.getName());
                descriptionTextArea.setText(newOfert.getDescription());
                businessmanTextField.setText(newOfert.getUser());
                ubicationTextField.setText(newOfert.getUbication());
                salaryTextField.setText(String.valueOf(newOfert.getSalary()));
                contractTypeTextField.setText(newOfert.getContractType());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } 

    @FXML
    private void exitWindow(ActionEvent event) { 
        MainBusinessmanController.getInstance().setMainPane("../view/oferts/MyOferts.fxml","My Oferts"); 
    }
}
