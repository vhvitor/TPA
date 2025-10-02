/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mycompany.genericlists.enums.OperationsLists;
import mycompany.genericlists.models.Service;
import mycompany.genericlists.models.Student;
import mycompany.genericlists.utils.StudentFileHandler;



/**
 *
 * @author vitor
 */
public class AddFileController implements Initializable{
    
    @FXML private AnchorPane operationsPane;
    @FXML private Button btArqGerar;
    @FXML private Button btArqOK;
    @FXML private Button btArqProcurar;
    @FXML private CheckBox cbArqArrTop;
    @FXML private CheckBox cbArqLinkTop;
    @FXML private CheckBox cbArqArrBot;
    @FXML private CheckBox cbArqLinkBot;
    @FXML private ComboBox<OperationsLists> cbbArqOp;
    @FXML private TextField tfArqCaminho;
    @FXML private TextField tfArqNome;
    @FXML private TextField tfarqMatricula;
    
    
    // File
    private File selectedFile; 
    
    
    // State properties
    private final BooleanProperty fileCreated = new SimpleBooleanProperty(false);
    private final BooleanProperty arrayListCreated = new SimpleBooleanProperty(false);
    private final BooleanProperty linkedListCreated = new SimpleBooleanProperty(false);
    
    BooleanProperty isArrayTopSelected;
    BooleanProperty isLinkedTopSelected;
    BooleanProperty isArrayBotSelected;
    BooleanProperty isLinkedBotSelected;
    BooleanBinding noneOperationSelected;
    
    // Runnable callbacks
    private Runnable onGenerateLists;
    private Runnable onActionSelected;

    
   
     
    /* ------- Initialize ------- */
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isArrayTopSelected = cbArqArrTop.selectedProperty();
        isLinkedTopSelected = cbArqLinkTop.selectedProperty();
        isArrayBotSelected = cbArqArrBot.selectedProperty();
        isLinkedBotSelected = cbArqLinkBot.selectedProperty();
        noneOperationSelected = cbbArqOp.valueProperty().isNull();
        
        tfArqCaminho.setEditable(false);
        configureTopSection();
        configureBottomSection();
        cbbArqOp.getItems().setAll(OperationsLists.values());
        
    }
    
    
    
    /* ------- Gui event ------- */
    
    @FXML
    private void onBtArqProcurarClick(ActionEvent event) throws IOException {
        File file = selectFile((Stage) btArqProcurar.getScene().getWindow());
        
        if (file != null) {
            selectedFile = file;
            tfArqCaminho.setText(file.getPath());
            fileCreated.set(true);
        }
    }
    
    @FXML
    private void onBtArqGerarClick(ActionEvent event) throws IOException {
        try {
            handleGenerateListSelection();
            
            if (onGenerateLists != null) {
                onGenerateLists.run();
            }
        } 
        
        catch (Exception e) {
            e.printStackTrace(); // força mostrar no console
        }
    }
    
    @FXML
    private void onBtArqOKClick(ActionEvent event) throws IOException {
        if (onActionSelected != null) onActionSelected.run();
        
        
    }
    
    
    
    /* ------- Gui Settings ------- */
    
    private void configureTopSection() {
        tfArqCaminho.setEditable(false);
        
        // 1 - checkboxes e botão gerar desativados até que um arquivo seja selecionado
        configureDisable(cbArqArrTop, 
                (arrayListCreated.and(fileCreated))
                        .or(fileCreated.not())
        );
        
        configureDisable(cbArqLinkTop, 
                (linkedListCreated.and(fileCreated))
                        .or(fileCreated.not()));

        configureDisable(btArqGerar, canGenerate().not());

        // 4 - botão procurar desativado se houver pelo menos uma lista criada
        configureDisable(btArqProcurar, anyListCreated());

        // 5 - botão gerar desativado se as duas listas já foram criadas
        btArqGerar.disableProperty().bind(
                canGenerate().not().or(bothListsCreated())
        );
    }
    
    private void configureBottomSection() {
        
        
        configureDisable(cbbArqOp, anyListCreated().not());
        
        configureDisable(tfArqNome, noneOperationSelected);
        configureDisable(tfarqMatricula, noneOperationSelected);
        
        configureDisable(cbArqArrBot, noneOperationSelected
                .and(validateFields().not())
        );
        
        configureDisable(cbArqLinkBot, noneOperationSelected
                .and(validateFields().not())
        );
        
        configureTextFields();
        
        configureDisable(btArqOK, isArrayBotSelected.not().and(isLinkedBotSelected.not()));
    }
    
    private void configureTextFields() {
        BooleanBinding noneInsertSelected = Bindings.createBooleanBinding(() -> {
                OperationsLists selected = cbbArqOp.getValue(); 
                
                return selected == null || 
                       !(selected == OperationsLists.ADD_LAST || 
                         selected == OperationsLists.ADD_FIRST || 
                         selected == OperationsLists.ADD_MIDDLE);
            },
                
            cbbArqOp.valueProperty()
        );
        
        configureDisable(tfArqNome, noneInsertSelected);
        configureDisable(tfarqMatricula, noneInsertSelected);
    }
    
    
    
    /* ------- Getters and Setters ------- */
    
    public File getSelectedFile() {
        return selectedFile;
    }
    
    public OperationsLists getSelectedOperation() {
        return cbbArqOp.getValue();
    }

    
    public boolean isArrayTopSelected() {
        return cbArqArrTop.isSelected();
    }
    
    public boolean isLinkedTopSelected() {
        return cbArqLinkTop.isSelected();
    }
    
    public boolean isArrayBotSelected() {
        return cbArqArrBot.isSelected();
    }
    
    public boolean isLinkedBotSelected() {
        return cbArqLinkBot.isSelected();
    }
    
    
    
    /* ------- Public API ------- */
    
    public void setOnGenerateLists(Runnable callback) { this.onGenerateLists = callback; }
    public void setOnActionSelected(Runnable callback) { this.onActionSelected = callback; }
    
    
    
    /* ------- Private Helper Methods ------- */
    
    private void clearFields() {
        tfArqNome.setDisable(true);
        tfarqMatricula.setDisable(true);
    }
    
    Student buildStudent() {
        long id = Long.parseLong(tfarqMatricula.getText());
        String name = tfArqNome.getText();

        return new Student(name, id);
    }
    
    private File selectFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();                            // Create a new FileChooser instance
        
        fileChooser.setTitle("Carregar Alunos");                                // Set the title of the dialog
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home")));                     // Set initial directory
        
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        
        return fileChooser.showOpenDialog(stage);
    }
    
    private void configureDisable(Node node, ObservableValue<Boolean> condition) {
        node.disableProperty().bind(condition);
    }
    
    private BooleanBinding canGenerate() {
        return fileCreated.and
        (cbArqArrTop.selectedProperty().or(cbArqLinkTop.selectedProperty()));
    }
    
    private BooleanBinding anyListCreated() {
        return arrayListCreated.or(linkedListCreated);
    }
    
    private BooleanBinding bothListsCreated() {
        return arrayListCreated.and(linkedListCreated);
    }
    
    private BooleanBinding validateFields() {
        return Bindings.createBooleanBinding(
            () -> !tfArqNome.getText().isBlank() && !tfarqMatricula.getText().isBlank(),
            tfArqNome.textProperty(),
            tfarqMatricula.textProperty()
        );
    }
    
    private void handleGenerateListSelection() {
        if (cbArqArrTop.isSelected() && !cbArqLinkTop.isSelected()) {
            arrayListCreated.set(true);
        } 
        
        else if (cbArqLinkTop.isSelected() && !cbArqArrTop.isSelected()) {
            linkedListCreated.set(true);
        } 
        
        else {
            arrayListCreated.set(true);
            linkedListCreated.set(true);
        }
    }
}
