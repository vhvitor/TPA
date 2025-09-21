/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javax.imageio.IIOException;
import mycompany.genericlists.models.StudentService;
import mycompany.genericlists.utils.GenericComparator;
import mycompany.genericlists.controllers.ListViewController;
import mycompany.genericlists.events.StudentInsertEvent;
import mycompany.genericlists.exceptions.InvalidListOperationException;
import mycompany.genericlists.exceptions.ListException;
import mycompany.genericlists.facades.StudentFacade;
import mycompany.genericlists.models.Student;
import mycompany.genericlists.utils.ExceptionMessages;

/**
 *
 * @author vitor
 */
public class MainController implements Initializable {
    // Include root nodes
    @FXML private AnchorPane addManualPane;
    @FXML private AnchorPane addFilePane;
    @FXML private AnchorPane removerPane;
    @FXML private AnchorPane buscarPane;
    @FXML private AnchorPane lvLinkedPane;
    @FXML private AnchorPane lvArrayPane;
    
    // Includes controllers
    @FXML private AddManualController addManualPaneController;
    @FXML private AddFileController addFilePaneController;
    @FXML private RemoveController removerPaneController;
    @FXML private BuscarController buscarPaneController;
    @FXML private ListViewController lvLinkedPaneController; 
    
    // Facades
    private final StudentFacade facade = new StudentFacade();
    
    
    
    public void initialize(URL url, ResourceBundle rb) {
        
        addManualPaneController.setOnStudentAdded(this::insertAddManUI);
        
/*
        // Conect AddFile
        addFilePaneController.setOnStudentsLoaded(this::handleStudentsLoaded);

        // Conect Remover
        removerPaneController.setOnStudentRemoved(this::handleStudentRemoved);

        // Conect Buscar
        buscarPaneController.setOnStudentFound(this::handleStudentFound);

        // Initialize the empty lists
        refreshListViews();*/
        
    }
    
    
    
    /* ------- Handlers ------- */
    
   /* private void handleStudentsLoaded(List<Student> students) {
        students.forEach(facade.getService()::add);
        refreshListViews();
    }

    private void handleStudentRemoved(Student student) {
        studentService.remove(student);
        refreshListViews();
    }

    private void handleStudentFound(Student student) {
        // Exemplo: mostrar só o aluno encontrado na lista Linked
        lvLinkedPaneController.updateListView(List.of(student));
    }*/

    /* ------- Views update ------- */

    private void refreshListViews() {
        lvLinkedPaneController.updateLinkedView(facade.getService());
        //lvArrayPaneController.updateListView(facade.getAllStudents());
    }
    
    
    
    /* ------- Helper methods ------- */
    
    private void insertAddManUI(StudentInsertEvent event) {
        try {
            facade.handleStudentAdded(event);
            refreshListViews();
            showSuccess("student.success.added");
        } 
        
        catch (InvalidListOperationException e) {
            showError(e.getMessage());
        } 
        
        catch (NumberFormatException e) {
            showError("gui.error.invalid.id");
        }
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
