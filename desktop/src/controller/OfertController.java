/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;
  
import java.net.URL; 
import java.util.ResourceBundle; 
import javafx.fxml.FXML; 
import javafx.fxml.Initializable; 
import javafx.scene.control.ListView;
import javafx.scene.text.Text;  

/**
 * FXML Controller class
 *
 * @author david
 */
public class OfertController implements Initializable {
    int id = 0;
    @FXML
    private Text nameText;
    @FXML
    private Text businessmanText;
    @FXML
    private Text salaryText;
    @FXML
    private Text ubicationText;
 

    /**
     * Initializes the controller class.
     */ 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        
    }   
    
    public void configurateDate(int id,String name,String businessman, String Salary, String ubication){
        this.id = id;
        nameText.setText(name);
        businessmanText.setText(businessman);
        salaryText.setText(Salary);
        ubicationText.setText(ubication);
    }
} 
