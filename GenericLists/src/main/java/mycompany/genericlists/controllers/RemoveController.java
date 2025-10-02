/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import mycompany.genericlists.exceptions.InvalidListOperationException;
import mycompany.genericlists.models.Student;

/**
 *
 * @author vitor
 */
public class RemoveController implements Initializable{
    @FXML private Button btRemOK;
    @FXML private TextField tfRemMatricula;
    @FXML private TextField tfRemNome;
    
    // Consumer callback
    private Consumer<Student> onStudentRemoved;
    
    
    
    
    /* ------- Initialize ------- */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureButtons(btRemOK, tfRemNome, tfRemMatricula);
    }
    
    
    
    /* ------- GUI EVENT ------- */
    
    @FXML // SE MUDAR, TEM QUE MUDAR O ENUM!!!!!!!!!
    private void onBtRemOKClick(ActionEvent event) throws InvalidListOperationException {
        
        if (!validateFields()) {
            throw new 
                InvalidListOperationException("gui.error.invalid.fields");
        }
        
        Student student = buildStudent();

        if (onStudentRemoved != null) {
           onStudentRemoved.accept(student);
        }
        
        clearFields();
    }
    
    
    
    /* ------- Public API ------- */
    
    public void setOnStudentRemove(Consumer<Student> callback) {
        onStudentRemoved = callback;
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
        
        Arrays.stream(fields).forEach(f -> f.setDisable(false));
    }
    
    private void clearFields() {
        tfRemMatricula.clear();
        tfRemNome.clear();
    }
    
    private Student buildStudent() {
        long id = Long.parseLong(tfRemMatricula.getText());
        String name = tfRemNome.getText();

        return new Student(name, id);
    }
    
    private boolean validateFields() {
        return (!tfRemMatricula.getText().isBlank() && 
                !tfRemNome.getText().isBlank());
    }
}
