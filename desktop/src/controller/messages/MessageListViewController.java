/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.messages;
       
import Entities.Message;
import java.net.URL;   
import java.util.ResourceBundle;  
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;  
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;   

/**
 * FXML Controller class
 *
 * @author david
 */
public class MessageListViewController implements Initializable {  
    @FXML
    private AnchorPane messagePane;
    @FXML
    private Text messageText;
    @FXML
    private Text textTime;
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void configurateMessage(Message message){
        if(message.isMine()) {
            messagePane.setStyle("-fx-background-color: lightgreen;");
        } else {
            messagePane.setStyle("-fx-background-color: lightblue;"); 
        }
        messageText.setText(message.getContent());
        textTime.setText(message.getSendedHour()+":"+message.getSendedMinute()); 
    }
}
