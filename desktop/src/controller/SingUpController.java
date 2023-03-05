/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;
 
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import java.io.IOException;  
import java.net.URL; 
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent; 
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent; 
import javafx.scene.text.Text;  
import javax.swing.JOptionPane;
import util.Messages;
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
    
    @FXML
    private void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void backLogIn(MouseEvent event) throws IOException { 
        JustWorkApp.sendMessage(Messages.CL_LOGIN); 
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        
        if(processedInput[0].equals("L")){
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml")); 
            JustWorkApp.changeScene(root);
        } 
    }

    @FXML
    private void singUp(ActionEvent event) throws IOException {
        if(this.checkUserInput()){
            String output = Messages.CL_REGISTER+":";
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
                      
            JustWorkApp.sendMessage(output); 
            String[] processedInput = JustWorkApp.recieveMessage().split(":");  
            if(processedInput[1].equals("C")){
                Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml")); 
                JustWorkApp.changeScene(root);
            }else if(processedInput[1].equals("I")){
                JOptionPane.showMessageDialog(null,processedInput[2]);
            }
        } 
    }
    
    private boolean checkUserInput(){
    
        if(textFieldName.getText().contains(":") || textFieldName.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or can't be empty");
            return false;
        }else if(textFieldDni.getText().contains(":")){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or can't be empty");
            return false;
        }else if(textFieldSurname.getText().contains(":")|| textFieldSurname.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or can't be empty");
            return false;
        }else if(textFieldEMail.getText().contains(":")){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or can't be empty");
            return false;
        }else if(textFieldUser.getText().contains(":")|| textFieldUser.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or can't be empty");
            return false;
        }else if(textFieldPassword.getText().contains(":")|| textFieldPassword.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or can't be empty");
            return false;
        }else if((!new ValidadorDNI(textFieldDni.getText()).validar())){
            JOptionPane.showMessageDialog(null, "DNI has not a correct format");
            return false;
        }else if(textFieldEMail.getText().contains(":")|| textFieldEMail.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or can't be empty");
            return false;
        }else if(userType.getSelectedToggle() != null){
            JOptionPane.showMessageDialog(null, "You must select one button");
            return false;
        } else{
            return true;
        }  
    } 
    
} 
