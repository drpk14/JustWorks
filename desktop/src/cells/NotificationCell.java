package cells;
 
import Entities.Notification; 
import controller.notifications.NotificationListViewController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane; 

public class NotificationCell extends ListCell<Notification>{
        
        public NotificationCell(){
            super(); 
        }
        
        @Override
        public void updateItem(Notification item,boolean empty){
            super.updateItem(item, empty);
            
            this.setText(null);
            this.setGraphic(null);
            if(item != null && !empty){
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/notifications/NotificationListView.fxml"));
                    
                    AnchorPane pane = loader.load();
                     
                    NotificationListViewController notificationController = loader.getController();
                    notificationController.configurateNotification(item);
                    this.setGraphic(pane);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
