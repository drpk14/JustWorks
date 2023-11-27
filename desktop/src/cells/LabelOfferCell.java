package cells;
  
import Entities.Label; 
import javafx.scene.control.ListCell;

public class LabelOfferCell extends ListCell<Label>{ 

    public LabelOfferCell(){
        super(); 
    }

    @Override
    public void updateItem(Label item,boolean empty){
        super.updateItem(item, empty);

        if (item != null) {
            if(item.isObligatority()) {
                setStyle("-fx-background-color: lightgreen;");
            } else {
                setStyle("-fx-background-color: lightblue;"); // Restablece el estilo predeterminado
            }
            setText(item.getName());
        } else {
            setText(null);
            setStyle(null);
        }
        
        /*if(item != null && !empty){ 
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/labels/LabelListView.fxml"));

                AnchorPane pane = loader.load();

                labelListViewController = loader.getController();
                labelListViewController.configurateLabel(item);
                this.setGraphic(pane);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }*/
    }
}
