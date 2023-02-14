/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.users;
  
import Entities.User; 
import controller.MainBusinessmanController;
import controller.MainWorkerController;
import java.net.URL; 
import java.util.ResourceBundle;  
import javafx.event.ActionEvent;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable; 
import javafx.scene.text.Text;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class UserViewerController implements Initializable {

    @FXML
    private Text nameTextField; 
    @FXML
    private Text dniTextField;
    @FXML
    private Text emailTextField;
    @FXML
    private Text userTextField;
    @FXML
    private Text passwordTextField;
    @FXML
    private Text surnameTextField; 
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
         
    }
    
    private void initializeData(){  
        JustWorkApp.sendMessage("UDet");
        String[] processedInput = JustWorkApp.recieveMessage().split(":");  
        for(int i= 1;i<processedInput.length;i=i+6){
            User newUser= new User(processedInput[i],processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],processedInput[i+5] );

            nameTextField.setText(newUser.getName());
            dniTextField.setText(newUser.getDni());
            emailTextField.setText(newUser.getEmail());
            userTextField.setText(newUser.getUser());
            passwordTextField.setText(newUser.getPassword());
            surnameTextField.setText(newUser.getSurname());

        } 
    }  

    @FXML
    private void showEditPane(ActionEvent event) {
        if(MainBusinessmanController.getInstance() != null){
            MainBusinessmanController.getInstance().setMainPane("../view/users/UserModifier.fxml","My Profile > Edit Profile"); 
        }else if(MainWorkerController.getInstance() != null){
            MainWorkerController.getInstance().setMainPane("../view/users/UserModifier.fxml","My Profile > Edit Profile"); 
        }
    }
}
