/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.oferts;
 
import Entities.Label;
import Entities.Ofert; 
import cells.LabelOfferCell;
import controller.MainBusinessmanController;  
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField; 
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import java.net.URL;
import java.util.ArrayList; 
import java.util.List; 
import java.util.ResourceBundle;   
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML; 
import javafx.fxml.Initializable;  
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;  
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javax.swing.JOptionPane; 
import util.Messages;
import static util.Messages.*;
import view.JustWorkApp;

/**
 * FXML Controller class
 *
 * @author David
 */
public class OfertModifierController implements Initializable {

    @FXML
    private MFXTextField nameTextField;
    @FXML
    private TextArea descriptionTextArea; 
    @FXML
    private MFXTextField ubicationTextField;
    @FXML
    private MFXTextField salaryTextField;
    @FXML
    private MFXTextField contractTypeTextField;
    @FXML
    private MFXButton confirmActionButton; 
    
    private Ofert modifyOfert = null;   
    @FXML
    private MFXButton changeLabelButton;
    @FXML
    private AnchorPane labelsPane;  
    @FXML
    private Text labelsInfo;
    @FXML
    private MFXLegacyListView<String> labelListView;
    @FXML
    private AnchorPane newLabelPane;
    @FXML
    private MFXTextField newLabelNameTextField;
    @FXML
    private MFXLegacyListView<Label> selectedLabelListView; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        labelListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedLabelListView.setCellFactory(param -> new LabelOfferCell());  
        labelListView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) { 
                List<String> diferenciasEnListaStrings = new ArrayList<>(labelListView.getSelectionModel().getSelectedItems());
                diferenciasEnListaStrings.removeIf(str -> selectedLabelListView.getItems().stream().anyMatch(obj -> obj.getName().equals(str)));

                List<Label> diferenciasEnListaObjetos = new ArrayList<>(selectedLabelListView.getItems());
                diferenciasEnListaObjetos.removeIf(obj -> labelListView.getSelectionModel().getSelectedItems().contains(obj.getName()));

                // Mostrar las diferencias 
                for (String diferencia : diferenciasEnListaStrings) {
                    int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "¿Es obligatoria?",
                    "",
                    JOptionPane.OK_OPTION
                    );
                    Label label = new Label(diferencia);
                    if (respuesta == JOptionPane.OK_OPTION) {
                       label.setObligatority(true);
                    } else {
                       label.setObligatority(false); 
                    }
                    selectedLabelListView.getItems().add(label);
                    System.out.println(selectedLabelListView.getItems().size());
                } 
                
                selectedLabelListView.getItems().removeAll(diferenciasEnListaObjetos);  
            }
        });
    }
    
    private void initializeData(){ 
            
             
        String[] processedInput = JustWorkApp.recieveMessage().split(":");
        if(processedInput[0].equals(Messages.S_MODIFY_OFFER)){
            changeLabelButton.setDisable(true);
            confirmActionButton.setText("Modify");
            this.addInfo(processedInput); 
        }else{
            confirmActionButton.setText("Add");
        } 
        
        
    }

    @FXML
    private void exitWindow(ActionEvent event) { 
        MainBusinessmanController.getInstance().setMainPane("/view/oferts/MyOferts.fxml","My Oferts"); 
    }

    @FXML
    private void confirmAction(ActionEvent event) {
        if(confirmActionButton.getText().equals("Modify")){
            if(checkUserInput() == true){
                if(!nameTextField.getText().equals(modifyOfert.getName()) ||
                !descriptionTextArea.getText().equals(modifyOfert.getDescription()) ||
                !ubicationTextField.getText().equals(modifyOfert.getUbication()) ||
                !salaryTextField.getText().equals(modifyOfert.getSalary()) ||
                !contractTypeTextField.getText().equals(modifyOfert.getContractType())){ 
                
                     
                    JustWorkApp.sendMessage(CL_MODIFY_OFFER+":"+
                            modifyOfert.getId()+":"
                            +nameTextField.getText()+":"
                            +descriptionTextArea.getText()+":"
                            +ubicationTextField.getText()+":"
                            +salaryTextField.getText()+":"
                            +contractTypeTextField.getText()+":"
                            +labelsInfo.getText()); 
                    
                    String[] processedInput = JustWorkApp.recieveMessage().split(":");
                    if(processedInput[1].equals("C")){
                        MainBusinessmanController.getInstance().setMainPane("/view/oferts/MyOferts.fxml","My Oferts");
                    } else if(processedInput[1].equals("I")){
                        JOptionPane.showMessageDialog(null, processedInput[2]);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "You must modify at least one textfield");
                }
            }
        }else if(confirmActionButton.getText().equals("Add")){
            if(checkUserInput() == true){  
                JustWorkApp.sendMessage(CL_ADD_OFFER+":"
                                        +nameTextField.getText()+":"
                                        +descriptionTextArea.getText()+":"
                                        +ubicationTextField.getText()+":"
                                        +salaryTextField.getText()+":"
                                        +contractTypeTextField.getText()+":"
                                        +this.getLabelString()); 

                String[] processedInput = JustWorkApp.recieveMessage().split(":");
                if(processedInput[1].equals("C")){
                    MainBusinessmanController.getInstance().setMainPane("/view/oferts/MyOferts.fxml","My Oferts");
                } else if(processedInput[1].equals("I")){
                    JOptionPane.showMessageDialog(null, processedInput[2]);
                }
            }   
        }
    }
    
    private boolean checkUserInput(){
        
        if(nameTextField.getText().contains(":")|| nameTextField.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or be empty");
            return false;
        }else if(descriptionTextArea.getText().contains(":")|| descriptionTextArea.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or be empty");
            return false;
        }else if(ubicationTextField.getText().contains(":")|| ubicationTextField.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or be empty");
            return false;
        }else if(salaryTextField.getText().contains(":")|| salaryTextField.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or be empty");
            return false;
        }else if(contractTypeTextField.getText().contains(":")|| contractTypeTextField.getText().length() <= 0){
            JOptionPane.showMessageDialog(null, "The text fields can't  contain : or be empty");
            return false;
        }
        
        if(confirmActionButton.getText().equals("Add")){
            boolean obligatority = false;
            for(Label label : selectedLabelListView.getItems()){
                if(label.isObligatority())
                    obligatority = true;
            }
            if(!obligatority){
                JOptionPane.showMessageDialog(null, "You need to select at least one obligatory label");
                return false;
            }
        }
        
        try{
            Integer.valueOf(salaryTextField.getText());
        }catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "The salary text field can't contain letters");
            return false;
        }
        
        
        if(selectedLabelListView.getItems().isEmpty()){
            return false;
        }
        boolean oneObligatory = false;
        for(Label label:selectedLabelListView.getItems()){  
            if(label.isObligatority()){
                oneObligatory = true;
            }
        }
        
        if(!oneObligatory)
            return false;
            
        return true;
    }

    @FXML
    private void changeToLabelPane(ActionEvent event) { 
        JustWorkApp.sendMessage(CL_ALL_LABELS);
         
        String[] processedInput2 = JustWorkApp.recieveMessage().split(":");
        for (int i = 1; i < processedInput2.length; i++) {
            if(!labelListView.getItems().contains(processedInput2[i])){
                labelListView.getItems().add(processedInput2[i]); 
            } 
        }
        this.labelsPane.setVisible(true); 
    }
 
    private void addInfo(String[] processedInput){
        for(int i= 1;i<processedInput.length;i=i+8){
            modifyOfert = new Ofert(Integer.parseInt(processedInput[i]),processedInput[i+1],processedInput[i+2],processedInput[i+3],processedInput[i+4],Integer.parseInt(processedInput[i+5]),processedInput[i+6]);

            nameTextField.setText(modifyOfert.getName());
            descriptionTextArea.setText(modifyOfert.getDescription()); 
            ubicationTextField.setText(modifyOfert.getUbication());
            salaryTextField.setText(String.valueOf(modifyOfert.getSalary()));
            contractTypeTextField.setText(modifyOfert.getContractType());
            String[] labels = processedInput[8].split(",");
            String labelsString = "";
            if(labels.length >= 2){
                List labelsList = new ArrayList();
                for(int j = 1;j<labels.length;j++){
                    labelsList.add(labels[j]);

                    labelsString += labels[j]; 
                    
                    if(i != labels.length-1){
                        labelsString += " , ";
                    }
                }
                labelsInfo.setText(labelsString);
                modifyOfert.setLabelsList(labelsList); 
            }
        }
     }

    private void saveLabels(ActionEvent event) {
        labelsInfo.setText(this.getLabelString());
        this.labelsPane.setVisible(false);
    }

    private void exitLabelPane(ActionEvent event) {
        this.labelsPane.setVisible(false);
    }
    
    private String getLabelString(){
          
        String labelsString = "";
        for(Label label:selectedLabelListView.getItems()){
            labelsString+=label.getName();
            if(label.isObligatority())
                labelsString+="/1";
            else
                labelsString+="/0";
            
            labelsString+=",";
        }
        return labelsString;
    }

    @FXML
    private void changeToAddLabelPane(ActionEvent event) {
        this.labelsPane.setVisible(false);
        this.newLabelPane.setVisible(true); 
    }

    @FXML
    private void exitLabelsPane(ActionEvent event) {
        
        this.labelsPane.setVisible(false);
        this.newLabelPane.setVisible(false); 
    }

    @FXML
    private void saveLabel(ActionEvent event) {
        if(!newLabelNameTextField.getText().isEmpty()){
            JustWorkApp.sendMessage(CL_ADD_LABEL+":"+newLabelNameTextField.getText()); 

            
            String[] processedInput = JustWorkApp.recieveMessage().split(":");
            if(processedInput[1].equals("C")){
                labelListView.getItems().clear();
                JustWorkApp.sendMessage(CL_ALL_LABELS); 
         
         
                String[] processedInput2 = JustWorkApp.recieveMessage().split(":");
                for (int i = 1; i < processedInput2.length; i++) {
                    if(!labelListView.getItems().contains(processedInput2[i])){
                        labelListView.getItems().add(processedInput2[i]); 
                    } 
                }
                this.labelsPane.setVisible(true);
                this.newLabelPane.setVisible(false); 
            }else{
                JOptionPane.showMessageDialog(null, "This label already exits");
            }
        }
    }

    @FXML
    private void exitAddLabelPane(ActionEvent event) {
        this.labelsPane.setVisible(true);
        this.newLabelPane.setVisible(false); 
    } 
}
