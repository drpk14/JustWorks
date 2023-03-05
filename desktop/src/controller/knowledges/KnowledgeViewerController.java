/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.knowledges;
 
import Entities.Knowledge; 
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;   
import javafx.fxml.FXML; 
import javafx.fxml.Initializable; 
import javafx.scene.text.Text; 
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class KnowledgeViewerController implements Initializable {

    @FXML
    private Text nameTextField; 
    @FXML
    private Text labelTextField;
     
    private int knowledgeId = 0; 
    @FXML
    private Text workerTextField;
    @FXML
    private Text placeTextField;
    @FXML
    private Text titleTextField;
    @FXML
    private Text initDateTextField;
    @FXML
    private Text finishDateTextField;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
         
    }
    
    private void initializeData(){  
        String[] processedInput = JustWorkApp.recieveMessage().split(":");    
        for(int i= 1;i<processedInput.length;i=i+10){
            Knowledge knowledge = new Knowledge(Integer.parseInt(processedInput[1]),processedInput[2],processedInput[3],processedInput[4],processedInput[5],processedInput[6],LocalDate.parse(processedInput[7]),LocalDate.parse(processedInput[8]));
            knowledgeId = knowledge.getId();
            nameTextField.setText(knowledge.getName());
            workerTextField.setText(knowledge.getWorkerName());
            placeTextField.setText(knowledge.getPlace());
            titleTextField.setText(knowledge.getTitle());
            initDateTextField.setText(knowledge.getInitDate().toString());
            finishDateTextField.setText(knowledge.getFinishDate().toString());
            
            String[] labels = processedInput[9].split(",");
            String labelsString = "";
            if(labels.length >= 2){
                
                List labelsList = new ArrayList();
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);

                    labelsString += labels[j];
                    if(i != labels.length-1){
                        labelsString += " , ";
                    } 
                }
                knowledge.setLabels(labelsList);
                labelTextField.setText(labelsString);

            } 
        } 
    }  
}
