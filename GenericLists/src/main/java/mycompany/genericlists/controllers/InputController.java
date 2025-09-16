/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mycompany.genericlists.controllers;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import mycompany.genericlists.exception.InvalidListOperationException;
import mycompany.genericlists.models.Service;
import mycompany.genericlists.models.Student;
import mycompany.genericlists.models.StudentService;
import mycompany.genericlists.utils.ExceptionMessages;
import mycompany.genericlists.utils.GenericComparator;
import mycompany.genericlists.utils.StudentFileHandler;

/**
 * FXML Controller class
 *
 * @author vitor
 */
public class InputController implements Initializable {
    
    @FXML
    private Button btArqOK;

    @FXML
    private Button btArqProcurar;

    @FXML
    private Button btBusOK;

    @FXML
    private Button btManOK;

    @FXML
    private Button btRemOK;
    
    @FXML
    private CheckBox cbArqArr;

    @FXML
    private CheckBox cbArqLink;

    @FXML
    private CheckBox cbArqOrdenada;

    @FXML
    private CheckBox cbManOrdenada;

    @FXML
    private ListView<Student> lsItems;
    
    @FXML
    private ListView<Student> lsItems1;

    @FXML
    private RadioButton rbArq1;

    @FXML
    private RadioButton rbArq2;

    @FXML
    private RadioButton rbMan1;

    @FXML
    private RadioButton rbMan2;

    @FXML
    private TextField tfArqCaminho;

    @FXML
    private TextField tfBusMatricula;

    @FXML
    private TextField tfBusNome;

    @FXML
    private TextField tfManMatricula;

    @FXML
    private TextField tfManNome;

    @FXML
    private TextField tfRemMatricula;

    @FXML
    private TextField tfRemNome;

    @FXML
    private ToggleGroup tgManOrd;
    
    @FXML
    private ToggleGroup tgArqOrd;

    @FXML
    private Font x1;

    @FXML
    private Color x2;

    @FXML
    private Font x3;

    @FXML
    private Color x4;
    
    
    // Comparators
    private GenericComparator<Student, Long> comparatorById;
    private GenericComparator<Student, String> comparatorByName;
    
    // Services
    private StudentService studentService;
    
    // File
    private File selectedFile;
    
    
    
    /* ------- Initialize ------- */
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comparatorByName = new GenericComparator<>(s -> s.getName());
        comparatorById = new GenericComparator<>(s -> s.getId());
        
        configureAllButtons();
        initAllOrderControls();
        initArq();
    }
    
    
    
    /* ------- GUI EVENT ------- */
    
    @FXML
    private void onBtManOKClick(ActionEvent event) 
            throws InvalidListOperationException {
        
        try {
            Comparator<Student> comparator = getManComparator();                // Determine the comparator based on UI state (ordered or not)
            Student student = buildStudentFromManFields();                      // Build Student object from manual input fields
        
            handleInsert(comparator, student);
            clearManFields();
            updateListView(studentService);
            showSuccess("student.success.added", student.getName());
            setDisableAllOrderControls();
        }
        
        catch (InvalidListOperationException e) {
            showError(e.getMessage());
        }
        
        catch (NumberFormatException e) {
            showError("gui.error.invalid.id");
        }
    }
    
    @FXML
    private void onBtArqProcurarClick(ActionEvent event) throws IOException {
        File file = selectFile((Stage) btArqProcurar.getScene().getWindow());
        
        if (file != null) {
            selectedFile = file;
            tfArqCaminho.setText(file.getPath());
        }
    }
    
    @FXML
    private void onBtArqOKClick(ActionEvent event) throws IOException {
        StudentFileHandler fileHandler = new StudentFileHandler(selectedFile);
        
        if (cbArqArr.isSelected()) {
            ArrayList<Student> arrayList = fileHandler.makeArrayList();
            updateArrayView(arrayList);
        }
        
        if (cbArqLink.isSelected()) {
            LinkedList<Student> linkedList = fileHandler.makeLinkedList();
            updateLinkedView(linkedList);
        }
    }
    
    
    
    /* ------- Private helper methods ------- */
    
    // ALL
    
    /**
    * Configures a button to be automatically enabled or disabled
    * based on the content of one or more text input fields.
    * The button will be disabled if any of the provided fields are blank.
    *
    * @param button the button to configure
    * @param fields the text input fields to observe
    */
   private void configureButton(Button button, TextInputControl... fields) {
       button.disableProperty().bind(
           Bindings.createBooleanBinding(
               // Disable if ANY field is blank (empty or only whitespace)
               () -> Arrays.stream(fields)
                           .anyMatch(f -> f.getText().isBlank()),
               // Observe all text properties for changes
               Arrays.stream(fields)
                     .map(TextInputControl::textProperty)
                     .toArray(Observable[]::new)
           )
       );
   }

   /**
    * Configures all buttons in the application with their respective
    * text input fields that control their enabled/disabled state.
    * This method centralizes the configuration to avoid repetition.
    */
   private void configureAllButtons() {
       List<Object[]> configurations = List.of(
           new Object[]{ btManOK, new TextInputControl[]{ tfManMatricula, tfManNome } },
           new Object[]{ btArqOK, new TextInputControl[]{ tfArqCaminho } },
           new Object[]{ btRemOK, new TextInputControl[]{ tfRemMatricula } },
           new Object[]{ btBusOK, new TextInputControl[]{ tfBusMatricula } }
       );

       // Apply configuration for each button
       configurations.forEach(cfg -> {
           Button button = (Button) cfg[0];
           TextInputControl[] fields = (TextInputControl[]) cfg[1];
           configureButton(button, fields);
       });
   }
    
    private void updateListView(Service<Student> service) {
        lsItems.getItems().clear();
        service.getAll().forEach(lsItems.getItems()::add);
    }
    
    /**
    * Initializes all order-related controls (checkboxes and radio buttons)
    * across all screens, setting default selections and initial enable/disable state.
    */
    private void initAllOrderControls() {
        // Default selection
        rbMan1.setSelected(true);
        rbArq1.setSelected(true);

        // Initial state: radios disabled until corresponding checkbox is selected
        rbMan1.setDisable(true);
        rbMan2.setDisable(true);
        rbArq1.setDisable(true);
        rbArq2.setDisable(true);

        // Manual mode: enable/disable radios based on checkbox
        cbManOrdenada.selectedProperty().addListener((obs, oldVal, isSelected) -> {
            rbMan1.setDisable(!isSelected);
            rbMan2.setDisable(!isSelected);
        });

        // File mode: enable/disable radios based on checkbox
        cbArqOrdenada.selectedProperty().addListener((obs, oldVal, isSelected) -> {
            rbArq1.setDisable(!isSelected);
            rbArq2.setDisable(!isSelected);
        });
    }
    
    private void setDisableAllOrderControls() {
        cbArqOrdenada.setDisable(true);
        cbManOrdenada.setDisable(true);
        
        tgManOrd.getToggles().forEach(t -> ((Node) t).setDisable(true));

    }
    
    private File selectFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();                            // Create a new FileChooser instance
        
        fileChooser.setTitle("Carregar Alu]nos");                               // Set the title of the dialog
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home")));                     // Set initial directory
        
        // Add optional filters for file types
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Text Files", "*.txt")
        );
        
        return fileChooser.showOpenDialog(stage);
    }
    
    // ADD MANUAL
    
    private void clearManFields() {
        tfManMatricula.clear();
        tfManNome.clear();
    }
    
    private void updateArrayView(ArrayList<Student> arrayList) {
        lsItems1.getItems().clear();
        lsItems1.getItems().addAll(arrayList);
    }
    
    private void updateLinkedView(LinkedList<Student> linkedList) {
        lsItems.getItems().clear();
        lsItems.getItems().addAll(linkedList);
    }
    
    private Comparator<Student> getManComparator() {
        return rbMan2.isSelected() ? comparatorByName : comparatorById;
    }
    
    private Student buildStudentFromManFields() {
        long id = Long.parseLong(tfManMatricula.getText());
        String name = tfManNome.getText();

        return new Student(name, id);
    }
    
    private boolean validateManFields() {
        return (!tfManMatricula.getText().isBlank() && 
                !tfManNome.getText().isBlank());
    }
    
    // ADD FILE
    
    private void initArq() {        
        tfArqCaminho.setEditable(false);
    }
    
    // REMOVE
    
    
    
    // SEARCH
    
    
    
    
    
    
    private void handleInsert(Comparator<Student> comparator, Student student) 
            throws InvalidListOperationException {
        
        boolean ordered = cbManOrdenada.isSelected();
        
        if (studentService == null) {
            studentService = new StudentService(comparator, ordered);
        }
        
        studentService.add(student);
    }

    private void handleRemove() {
        System.out.println("Removendo...");
        // lógica de remoção
    }
    
    private void showError(String messageKey) {
        String message = ExceptionMessages.get(messageKey);
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showSuccess(String messageKey, Object... params) {
        String message = String.format(
                ExceptionMessages.get(messageKey), params);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setContentText(message);
        alert.showAndWait();
    }
}