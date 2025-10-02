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
import mycompany.genericlists.dtos.StudentDTO;
import mycompany.genericlists.exceptions.InvalidListOperationException;
import mycompany.genericlists.models.Student;

/**
 *
 * @author vitor
 */
public class BuscarController implements Initializable{
    @FXML private Button btBusOK;
    @FXML private TextField tfBusMatricula;
    @FXML private TextField tfBusNome;
    
    // Consumer callback
    private Consumer<Student> onStudentFound;
    
    
    
    
    /* ------- Initialize ------- */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureButtons(btBusOK, tfBusNome, tfBusMatricula);
    }
    
    
    
    /* ------- GUI EVENT ------- */
    
    @FXML // SE MUDAR, TEM QUE MUDAR O ENUM!!!!!!!!!
    private void onBtBusOKClick(ActionEvent event) throws InvalidListOperationException {
        
        if (!validateFields()) {
            throw new 
                InvalidListOperationException("gui.error.invalid.fields");
        }
        
        Student student = buildStudent();

        if (onStudentFound != null) {
           onStudentFound.accept(student);
        }
        
        clearFields();
    }
    
    
    
    /* ------- Public API ------- */
    
    public void setOnStudentFound(Consumer<Student> callback) {
        onStudentFound = callback;
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

        // Garante que todos os campos estejam sempre ativos
        Arrays.stream(fields).forEach(f -> f.setDisable(false));
    }
    
    private void clearFields() {
        tfBusMatricula.clear();
        tfBusNome.clear();
    }
    
    private Student buildStudent() {
        long id = Long.parseLong(tfBusMatricula.getText());
        String name = tfBusNome.getText();

        return new Student(name, id);
    }
    
    private boolean validateFields() {
        return (!tfBusMatricula.getText().isBlank() && 
                !tfBusNome.getText().isBlank());
    }
}