/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.labels;
    
import Entities.Label;
import java.net.URL;   
import java.util.ResourceBundle;  
import javafx.event.ActionEvent;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;  
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;    

/**
 * FXML Controller class
 *
 * @author david
 */
public class LabelListViewController implements Initializable { 
    @FXML
    private Text labelName;
    @FXML
    private CheckBox obligatorityCheckBox; 
    private Label label;
    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
    }  
    
    public void configurateLabel(Label label){
        this.label = label;
        labelName.setText(label.getName());  
        obligatorityCheckBox.setSelected(label.isObligatority());  
    }

    @FXML
    private void changeObligatority(ActionEvent event) {
        if(obligatorityCheckBox.isSelected())
            label.setObligatority(true);
        else
            label.setObligatority(false);
    } 

    public Label getLabel() {
        return label;
    } 
} 
