/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.oferts;

import Entities.Ofert;
import controller.MainBusinessmanController; 
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
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
import javafx.scene.input.KeyEvent; 
import javax.swing.JOptionPane;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class OfertModifierController implements Initializable {

    @FXML
    private MFXTextField nameTextField;
    @FXML
    private TextArea descriptionTextArea; 
    @FXML
    private MFXTextField ubicationTextField;
    @FXML
    private MFXTextField salaryTextField;
    @FXML
    private MFXTextField contractTypeTextField;
    @FXML
    private MFXButton confirmActionButton; 
    
    private Ofert modifyOfert = null;
    
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
            if(entradaDividida[0].equals("ModO")){
                confirmActionButton.setText("Modify");
                for(int i= 1;i<entradaDividida.length;i=i+7){
                    modifyOfert = new Ofert(Integer.parseInt(entradaDividida[i]),entradaDividida[i+1],entradaDividida[i+2],entradaDividida[i+3],entradaDividida[i+4],Integer.parseInt(entradaDividida[i+5]),entradaDividida[i+6]);

                    nameTextField.setText(modifyOfert.getName());
                    descriptionTextArea.setText(modifyOfert.getDescription()); 
                    ubicationTextField.setText(modifyOfert.getUbication());
                    salaryTextField.setText(String.valueOf(modifyOfert.getSalary()));
                    contractTypeTextField.setText(modifyOfert.getContractType());
                }
            }else{
                confirmActionButton.setText("Add");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } 

    @FXML
    private void exitWindow(ActionEvent event) { 
        MainBusinessmanController.getInstance().setMainPane("../view/oferts/MyOferts.fxml","My Oferts"); 
    }

    @FXML
    private void confirmAction(ActionEvent event) {
        if(confirmActionButton.getText().equals("Modify")){
            if(checkUserInput() == true){
                if(!nameTextField.getText().equals(modifyOfert.getName()) ||
                !descriptionTextArea.getText().equals(modifyOfert.getDescription()) ||
                !ubicationTextField.getText().equals(modifyOfert.getUbication()) ||
                !salaryTextField.getText().equals(modifyOfert.getSalary()) ||
                !contractTypeTextField.getText().equals(modifyOfert.getContractType())){
                    
                    try {
                        JustWorkApp.out.println("ModO:"+
                                modifyOfert.getId()+":"
                                +nameTextField.getText()+":"
                                +descriptionTextArea.getText()+":"
                                +ubicationTextField.getText()+":"
                                +salaryTextField.getText()+":"
                                +contractTypeTextField.getText());
                        
                        String[] entradaDividida = JustWorkApp.in.readLine().split(":");
                        if(entradaDividida[1].equals("C")){
                            MainBusinessmanController.getInstance().setMainPane("../view/oferts/MyOferts.fxml","My Oferts"); 
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(null, "You must modify at least one textfield");
                }
            }
        }else if(confirmActionButton.getText().equals("Add")){
            if(checkUserInput() == true){
                try {
                    JustWorkApp.out.println("AddO:"
                                            +nameTextField.getText()+":"
                                            +descriptionTextArea.getText()+":"
                                            +ubicationTextField.getText()+":"
                                            +salaryTextField.getText()+":"
                                            +contractTypeTextField.getText()+":");
                    
                    String[] entradaDividida = JustWorkApp.in.readLine().split(":");
                    if(entradaDividida[1].equals("C")){
                        MainBusinessmanController.getInstance().setMainPane("../view/oferts/MyOferts.fxml","My Oferts");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } 
            }   
        }
    }

    @FXML
    private void checkLength(KeyEvent event) {
        /*if(descriptionTextArea.getText().length()>30){
            descriptionTextArea.setText(descriptionTextArea.getText(0, descriptionTextArea.getText().length()-1));
        }*/
    }
    
    
    private boolean checkUserInput(){
    
        if(nameTextField.getText().contains(":")){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain :");
            return false;
        }else if(descriptionTextArea.getText().contains(":")){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain :");
            return false;
        }else if(ubicationTextField.getText().contains(":")){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain :");
            return false;
        }else if(salaryTextField.getText().contains(":")){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain :");
            return false;
        }else if(contractTypeTextField.getText().contains(":")){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain :");
            return false;
        }
        
        try{
            Integer.valueOf(salaryTextField.getText());
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "The salary text field can't contain letters");
            return false;
        }
            
        return true;
    }
}
