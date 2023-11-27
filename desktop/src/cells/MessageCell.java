package cells;
   
import Entities.Message;
import controller.messages.MessageListViewController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class MessageCell extends ListCell<Message>{ 

    public MessageCell(){
        super(); 
    }

    @Override
    public void updateItem(Message item,boolean empty){
        super.updateItem(item, empty); 
        
        if(item != null && !empty){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/messages/MessageListView.fxml"));

                AnchorPane pane = loader.load();

                MessageListViewController messageListViewController = loader.getController();
                
                messageListViewController.configurateMessage(item);
                this.setGraphic(pane); 
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
