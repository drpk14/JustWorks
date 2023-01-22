package controller.oferts;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

   
import Entities.Ofert;
import io.github.palexdev.materialfx.controls.MFXButton; 
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.io.IOException;
import java.net.URL; 
import java.util.ResourceBundle;  
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;  
import util.OfertCell;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author david
 */
public class AllOfertsController implements Initializable {

    @FXML
    private MFXTextField textField;
    @FXML
    private MFXButton searchButton;
    @FXML
    private MFXLegacyListView<Ofert> listView;
 
    /**
     * Initializes the controller class.
     */ 
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
            initializeData();
            listView.setCellFactory(param -> new OfertCell());  
    }
    
    private void initializeData(){
        
        try {
            JustWorkApp.out.println("AllO:");
            String[] entradaDividida = JustWorkApp.in.readLine().split(":");  
            for(int i= 1;i<entradaDividida.length;i=i+7){
                Ofert newOfert = new Ofert(Integer.parseInt(entradaDividida[i]),entradaDividida[i+1],entradaDividida[i+2],entradaDividida[i+3],entradaDividida[i+4],Integer.parseInt(entradaDividida[i+5]),entradaDividida[i+6]);
                listView.getItems().add(newOfert);
                System.out.println("Se ha añadido la oferta");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    } 
} 
