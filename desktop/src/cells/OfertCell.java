package cells;

import Entities.Ofert;
import controller.oferts.OfertListViewController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane; 

public class OfertCell extends ListCell<Ofert>{
        
        public OfertCell(){
            super(); 
        }
        
        @Override
        public void updateItem(Ofert item,boolean empty){
            super.updateItem(item, empty);
            
            this.setText(null);
            this.setGraphic(null);
            if(item != null && !empty){
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../view/oferts/OfertListView.fxml"));
                    
                    AnchorPane pane = loader.load();
                     
                    OfertListViewController ofertController = loader.getController();
                    ofertController.configurateDate(item);
                    this.setGraphic(pane);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
