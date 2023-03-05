/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField; 
import java.io.IOException;  
import java.net.URL; 
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent; 
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent; 
import javafx.scene.text.Text;  
import javax.swing.JOptionPane;
import util.Messages;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author david
 */
public class LoginController implements Initializable {

         
    @FXML
    private TextField loginTextField;
    @FXML
    private MFXPasswordField loginPasswordField;
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
    private void login(ActionEvent event) throws IOException { 
          
        if(this.loginTextField.getText().length()>0 && this.loginPasswordField.getText().length()>0){  
            JustWorkApp.sendMessage(Messages.CL_LOGIN+":"+this.loginTextField.getText()+":"+this.loginPasswordField.getText());
            String[] processedInput = JustWorkApp.recieveMessage().split(":");
            if(processedInput[1].equals("C")){
                Parent root = null;
                if(processedInput[2].equals("B")){
                    root = FXMLLoader.load(this.getClass().getResource("/view/MainBusinessman.fxml"));
                }else if(processedInput[2].equals("W")){
                    root = FXMLLoader.load(this.getClass().getResource("/view/MainWorker.fxml"));
                }else if(processedInput[2].equals("A")){
                    root = FXMLLoader.load(this.getClass().getResource("/view/MainAdmin.fxml"));
                }
                
                if(processedInput[4].equals("True")){
                    JOptionPane.showMessageDialog(null, "You have new notifications");
                }
                    
                JustWorkApp.changeScene(root);
            }else{
                textError.setVisible(true);
            }
        }
    }

    @FXML
    private void changeToSingUp(ActionEvent event) throws IOException {
        JustWorkApp.sendMessage(Messages.CL_REGISTER+":");
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        
        if(processedInput[0].equals(Messages.S_REGISTER)){
            Parent root = FXMLLoader.load(getClass().getResource("/view/SingUp.fxml")); 
            JustWorkApp.changeScene(root);
        }
    }
} 
