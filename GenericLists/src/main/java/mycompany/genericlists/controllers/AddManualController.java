/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.controllers;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mycompany.genericlists.events.StudentInsertEvent;
import mycompany.genericlists.exceptions.InvalidListOperationException;
import mycompany.genericlists.models.Student;
import mycompany.genericlists.models.StudentService;
import mycompany.genericlists.utils.ExceptionMessages;
import mycompany.genericlists.utils.GenericComparator;

/**
 *
 * @author vitor
 */
public class AddManualController implements Initializable{
    @FXML private Button btManOK;
    @FXML private CheckBox cbManOrdenada;
    @FXML private RadioButton rbMan1;
    @FXML private RadioButton rbMan2;
    @FXML private TextField tfManMatricula;
    @FXML private TextField tfManNome;
    @FXML private ToggleGroup tgManOrd;
    
    // Consumer
    private Consumer<StudentInsertEvent> onStudentAdded;
    
    // Comparators
    private GenericComparator<Student, Long> comparatorById;
    private GenericComparator<Student, String> comparatorByName;
    
    // DTOs
    private StudentInsertEvent transfer;
    
    
    
    
    /* ------- Constructors ------- */
    
    
    
    
    
    /* ------- Initialize ------- */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comparatorById = new GenericComparator<>(s -> s.getId());
        comparatorByName = new GenericComparator<>(s -> s.getName());
        
        configureButtons(btManOK, tfManNome, tfManMatricula);
        initOrderControls();
    }
    
    
    
    /* ------- GUI EVENT ------- */
    
    @FXML
    private void onBtManOKClick(ActionEvent event) throws InvalidListOperationException {
        
        if (!validateFields()) {
            throw new 
                InvalidListOperationException("gui.error.invalid.fields");
        }
        
        Comparator<Student> comparator = getManComparator();                    // Determine the comparator based on UI state (ordered or not)
        Student student = buildStudent();                                       // Build Student object from manual input fields

        transfer = new StudentInsertEvent(student, 
                    comparator, 
                    cbManOrdenada.isSelected());

        if (onStudentAdded != null) {
            onStudentAdded.accept(transfer);
        }

        clearFields();
        setDisableOrderControls();
    }
    
    
    
    /* ------- Public API ------- */
    
    public void setOnStudentAdded(Consumer<StudentInsertEvent> callback) {
        onStudentAdded = callback;
    }
    
    
    
    
    /* ------- Private helper methods ------- */
    
    /**
     * Configures a button to be automatically enabled or disabled
     * based on the content of one or more text input fields.
     * The button will be disabled if any of the provided fields are blank.
     */
    private void configureButtons(Button button, TextInputControl... fields) {
        button.disableProperty().bind(
            Bindings.createBooleanBinding(
                () -> Arrays.stream(fields).anyMatch(f -> f.getText().isBlank()),
                Arrays.stream(fields)
                      .map(TextInputControl::textProperty)
                      .toArray(Observable[]::new)
            )
        );
    }
    
    private void initOrderControls() {
        // Default selection
        rbMan1.setSelected(true);

        // Initial state: radios disabled until corresponding checkbox is selected
        rbMan1.setDisable(true);
        rbMan2.setDisable(true);

        // Manual mode: enable/disable radios based on checkbox
        cbManOrdenada.selectedProperty().addListener((obs, oldVal, isSelected) -> {
            rbMan1.setDisable(!isSelected);
            rbMan2.setDisable(!isSelected);
        });
    }
    
    private void clearFields() {
        tfManMatricula.clear();
        tfManNome.clear();
    }
    
    private boolean validateManFields() {
        return (!tfManMatricula.getText().isBlank() && 
                !tfManNome.getText().isBlank());
    }
    
    private void setDisableOrderControls() {
        cbManOrdenada.setDisable(true);
        
        tgManOrd.getToggles().forEach(t -> ((Node) t).setDisable(true));

    }
    
    private Student buildStudent() {
        long id = Long.parseLong(tfManMatricula.getText());
        String name = tfManNome.getText();

        return new Student(name, id);
    }
    
    private Comparator<Student> getManComparator() {
        return rbMan2.isSelected() ? comparatorByName : comparatorById;
    }
    
    
    private boolean validateFields() {
        return (!tfManMatricula.getText().isBlank() && 
                !tfManNome.getText().isBlank());
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
