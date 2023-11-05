package cells;
 
import Entities.BusinessmanNotification; 
import controller.notifications.BusinessmanNotificationListViewController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane; 

public class BusinessmanNotificationCell extends ListCell<BusinessmanNotification>{
        
        public BusinessmanNotificationCell(){
            super(); 
        }
        
        @Override
        public void updateItem(BusinessmanNotification item,boolean empty){
            super.updateItem(item, empty);
            
            this.setText(null);
            this.setGraphic(null);
            if(item != null && !empty){
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/notifications/BusinessmanNotificationListView.fxml"));
                    
                    AnchorPane pane = loader.load();
                     
                    BusinessmanNotificationListViewController notificationController = loader.getController();
                    notificationController.configurateNotification(item);
                    this.setGraphic(pane);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
