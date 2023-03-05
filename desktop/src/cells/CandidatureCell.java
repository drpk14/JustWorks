package cells;
 
import Entities.Candidature; 
import controller.candidatures.CandidatureListViewController; 
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane; 

public class CandidatureCell extends ListCell<Candidature>{
        
        public CandidatureCell(){
            super(); 
        }
        
        @Override
        public void updateItem(Candidature item,boolean empty){
            super.updateItem(item, empty);
            
            this.setText(null);
            this.setGraphic(null);
            if(item != null && !empty){
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/candidatures/CandidatureListView.fxml"));
                    
                    AnchorPane pane = loader.load();
                     
                    CandidatureListViewController candidatureController = loader.getController();
                    candidatureController.configurateCandidature(item);
                    this.setGraphic(pane);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
