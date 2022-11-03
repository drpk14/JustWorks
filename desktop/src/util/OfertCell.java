package util;

import controller.OfertController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane; 

public class OfertCell extends ListCell<String[]>{
        
        public OfertCell(){
            super();
             
        }
        
        @Override
        public void updateItem(String[] item,boolean empty){
            super.updateItem(item, empty);
            
            this.setText(null);
            this.setGraphic(null);
            if(item != null && !empty){
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../view/Ofert.fxml"));
                    
                    AnchorPane pane = loader.load();
                     
                    OfertController ofertController = loader.getController();
                    ofertController.configurateDate(Integer.parseInt(item[0]),item[1],item[2],item[3],item[4]);
                    this.setGraphic(pane);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
