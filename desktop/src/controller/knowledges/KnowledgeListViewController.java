/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.knowledges;
   
import Entities.Knowledge; 
import controller.MainBusinessmanController;
import controller.MainWorkerController; 
import java.net.URL;  
import java.util.List;
import java.util.ResourceBundle; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;  
import javafx.scene.text.Text;  
import util.Messages;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author david
 */
public class KnowledgeListViewController implements Initializable {
    int id = 0;
    @FXML
    private Text nameText; 
    @FXML
    private Text labelsText;
    @FXML
    private Text workerText;
    @FXML
    private Text placeText;
    @FXML
    private Text tittleText;
 

    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
    }   
    
    public void configurateKnowledge(Knowledge knowledge){
        this.id = knowledge.getId();
        nameText.setText(knowledge.getName());
        workerText.setText(knowledge.getWorkerName());
        placeText.setText(knowledge.getPlace());
        tittleText.setText(knowledge.getTitle());
        String labelsString = ""; 
        
        List labelsList = knowledge.getLabels();
        
        for(int i = 0;i<labelsList.size();i++){
            labelsString += labelsList.get(i);
            if(i != labelsList.size()-1){
                labelsString += " / ";
            }
            
        }
        
        labelsText.setText(labelsString);
    }

    @FXML
    private void watchOffer(ActionEvent event) {
        JustWorkApp.sendMessage(Messages.CL_KNOWLEDGE_DETAILS+":"+id);  
        if(MainBusinessmanController.getInstance() != null)
            MainBusinessmanController.getInstance().setMainPane("/view/knowledges/KnowledgeViewer.fxml","Knowledge Viewer");
        else if(MainWorkerController.getInstance() != null)
            MainWorkerController.getInstance().setMainPane("/view/knowledges/KnowledgeViewer.fxml","Knowledge Viewer");
    }
} 
