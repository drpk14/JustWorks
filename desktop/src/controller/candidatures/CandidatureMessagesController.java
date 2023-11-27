/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.candidatures;
   
import Entities.*;
import cells.MessageCell; 
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView; 
import java.net.URL;  
import java.util.ResourceBundle; 
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;  
import javafx.fxml.Initializable;   
import javafx.scene.control.TextField; 
import javafx.scene.text.Text;
import static util.Messages.*;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class CandidatureMessagesController implements Initializable {

    @FXML
    private MFXLegacyListView<Message> messagesListView;
    @FXML
    private Text offerName;
    @FXML
    private TextField messageTextField;
    private int candidatureId;
     
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        messagesListView.setCellFactory(param -> new MessageCell()); 
        initializeData();
    }   

    private void initializeData(){
         
        String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
        candidatureId=Integer.parseInt(processedInput[1]); 
        offerName.setText(processedInput[2]);
        for(int i= 3;i<processedInput.length;i=i+4){
            String[] sendedTime = processedInput[i+2].split("_"); 
            Message message = new Message(Integer.parseInt(processedInput[i]),processedInput[i+1],Integer.parseInt(sendedTime[0]),Integer.parseInt(sendedTime[1]),Boolean.parseBoolean(processedInput[i+3]));
            messagesListView.getItems().add(message); 
        }
        
        messagesListView.scrollTo(messagesListView.getItems().size() - 1);
    }
    
    @FXML
    private void sendMessages(ActionEvent event) {
        if(!messageTextField.getText().isEmpty()){
            JustWorkApp.sendMessage(CL_ADD_MESSAGE+":"+candidatureId+":"+messageTextField.getText());
            String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
            for(int i= 1;i<processedInput.length;i=i+4){
                String[] sendedTime = processedInput[i+2].split("_"); 
                Message message = new Message(Integer.parseInt(processedInput[i]),processedInput[i+1],Integer.parseInt(sendedTime[0]),Integer.parseInt(sendedTime[1]),Boolean.parseBoolean(processedInput[i+3]));
                messagesListView.getItems().add(message); 
                System.out.println(messagesListView.getItems().size());
                messagesListView.scrollTo(messagesListView.getItems().size() - 1);
            }
        }
    }
    
    public void refreshMessages(){
        messagesListView.getItems().clear();
        Platform.runLater(new Runnable() {
            @Override public void run() {
                
                JustWorkApp.sendMessage(CL_MESSAGES_OF_THIS_CANDIDATURE+":"+candidatureId);
                String[] processedInput = JustWorkApp.recieveMessage().split(":"); 
                candidatureId=Integer.parseInt(processedInput[1]); 
                offerName.setText(processedInput[2]);
                for(int i= 3;i<processedInput.length;i=i+4){
                    String[] sendedTime = processedInput[i+2].split("_"); 
                    Message message = new Message(Integer.parseInt(processedInput[i]),processedInput[i+1],Integer.parseInt(sendedTime[0]),Integer.parseInt(sendedTime[1]),Boolean.parseBoolean(processedInput[i+3]));
                    messagesListView.getItems().add(message); 
                    System.out.println(messagesListView.getItems().size());
                }
                messagesListView.scrollTo(messagesListView.getItems().size() - 1);
            }
        }); 
    }
}