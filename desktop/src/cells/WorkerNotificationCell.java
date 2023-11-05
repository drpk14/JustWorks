package cells;
 
import Entities.WorkerNotification;  
import controller.notifications.WorkerNotificationListViewController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane; 

public class WorkerNotificationCell extends ListCell<WorkerNotification>{
        
        public WorkerNotificationCell(){
            super(); 
        }
        
        @Override
        public void updateItem(WorkerNotification item,boolean empty){
            super.updateItem(item, empty);
            
            this.setText(null);
            this.setGraphic(null);
            if(item != null && !empty){
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/view/notifications/WorkerNotificationListView.fxml"));
                    
                    AnchorPane pane = loader.load();
                     
                    WorkerNotificationListViewController notificationController = loader.getController();
                    notificationController.configurateNotification(item);
                    this.setGraphic(pane);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
