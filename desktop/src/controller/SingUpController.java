/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;
 
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import java.io.IOException;  
import java.net.URL; 
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent; 
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent; 
import javafx.scene.text.Text; 
import view.JustWorkApp; 
import util.ValidadorDNI;

/**
 * FXML Controller class
 *
 * @author david
 */
public class SingUpController implements Initializable {

    
    @FXML
    private ToggleGroup userType;
    @FXML
    private TextField textFieldDni;
    @FXML
    private TextField textFieldUser;
    @FXML
    private TextField textFieldPassword; 
    @FXML
    private TextField textFieldEMail;
    @FXML
    private TextField textFieldSurname;
    @FXML
    private TextField textFieldName;
    @FXML
    private MFXRadioButton radioButtonWorker;
    @FXML
    private MFXRadioButton radioButtonBusinessMan;
    @FXML
    private Text textError;
    

    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    }    
    
    private boolean checkFields(){
        if(userType.getSelectedToggle() == null)
            return false;
        
        if(new ValidadorDNI(textFieldDni.getText()).validar()) 
            return false;
        
        if(textFieldPassword.getText().length() <= 0)
            return false;
    
        if(textFieldUser.getText().length() <= 0)
            return false;
        
        if(textFieldEMail.getText().length() <= 0)
            return false;
        
        if(textFieldSurname.getText().length() <= 0)
            return false; 
        
        if(textFieldName.getText().length() <= 0)
            return false;
        
        return true;
        
    } 
    
    @FXML
    private void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void backLogIn(MouseEvent event) throws IOException { 
        
        JustWorkApp.out.println("L:");
        String[] entradaDividida = JustWorkApp.in.readLine().split(":");
        if(entradaDividida[0].equals("L")){
            Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml")); 
            JustWorkApp.changeScene(root);
        } 
    }

    @FXML
    private void singUp(ActionEvent event) throws IOException {
        //if(this.checkFields()){
            String output = "R:";
            output+=textFieldDni.getText()+":";
            output+=textFieldName.getText()+":";
            output+=textFieldSurname.getText()+":";
            output+=textFieldEMail.getText()+":";
            output+=textFieldUser.getText()+":";
            output+=textFieldPassword.getText()+":";
            if(radioButtonBusinessMan.isSelected())
                output+="BusinessMan";
            if(radioButtonWorker.isSelected())
                output+="Worker";
                        
            JustWorkApp.out.println(output);
            String[] entradaDividida = JustWorkApp.in.readLine().split(":");
            if(entradaDividida[1].equals("C")){
                Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml")); 
                JustWorkApp.changeScene(root);
            }else{
                textError.setVisible(true);
            }
        /*}else{
            
        }*/
    }
} 
