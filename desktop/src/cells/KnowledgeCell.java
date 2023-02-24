package cells;
  
import Entities.Knowledge; 
import controller.knowledges.KnowledgeListViewController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane; 

public class KnowledgeCell extends ListCell<Knowledge>{
        
        public KnowledgeCell(){
            super(); 
        }
        
        @Override
        public void updateItem(Knowledge item,boolean empty){
            super.updateItem(item, empty);
            
            this.setText(null);
            this.setGraphic(null);
            if(item != null && !empty){
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../view/knowledges/KnowledgeListView.fxml"));
                    
                    AnchorPane pane = loader.load();
                     
                    KnowledgeListViewController knowledgeController = loader.getController();
                    knowledgeController.configurateKnowledge(item);
                    this.setGraphic(pane);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
