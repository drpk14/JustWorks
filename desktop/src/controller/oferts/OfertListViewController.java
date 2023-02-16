/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.oferts;
  
import Entities.Ofert;
import java.lang.reflect.Array;
import java.net.URL; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle; 
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;  
import javafx.scene.text.Text;  

/**
 * FXML Controller class
 *
 * @author david
 */
public class OfertListViewController implements Initializable {
    int id = 0;
    @FXML
    private Text nameText;
    @FXML
    private Text businessmanText;
    @FXML
    private Text salaryText;
    @FXML
    private Text ubicationText;
    @FXML
    private Text labelsText;
 

    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
    }   
    
    public void configurateOfert(Ofert ofert){
        this.id = ofert.getId();
        nameText.setText(ofert.getName());
        businessmanText.setText(ofert.getUser());
        salaryText.setText(String.valueOf(ofert.getSalary()));
        ubicationText.setText(ofert.getUbication());
        String labelsString = "";
        
        
        List labelsList = ofert.getLabelsList(); 
        
        for(int i = 0;i<labelsList.size();i++){
            labelsString += labelsList.get(i);
            if(i != labelsList.size()-1){
                labelsString += " / ";
            }
            
        }
        
        labelsText.setText(labelsString);
    }
} 
