/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import mycompany.genericlists.models.StudentService;
import mycompany.genericlists.dispatchers.Dispatcher;
import mycompany.genericlists.dtos.StudentDTO;
import mycompany.genericlists.enums.OperationActionCustomList;
import mycompany.genericlists.enums.OperationActionLists;
import mycompany.genericlists.enums.OperationsCustomList;
import mycompany.genericlists.enums.OperationsLists;
import mycompany.genericlists.exceptions.ElementNotFoundException;
import mycompany.genericlists.exceptions.EmptyListException;
import mycompany.genericlists.exceptions.InvalidListOperationException;
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
    @FXML private Tab tbAddArquivo;
    @FXML private TabPane tbpAll;
    
    // Includes controllers
    @FXML private AddManualController addManualPaneController;
    @FXML private AddFileController addFilePaneController;
    @FXML private RemoveController removerPaneController;
    @FXML private BuscarController buscarPaneController;
    @FXML private ListViewController listViewPaneController;
    
    // Facades
    private final StudentFacade facade = new StudentFacade();
    
    // Dispatchers
    Dispatcher<OperationsCustomList> manualDispatcher = new Dispatcher<>();
    Dispatcher<OperationsLists> fileDispatcher = new Dispatcher<>();
    Dispatcher dispatcher = new Dispatcher();

    
    
    
    public void initialize(URL url, ResourceBundle rb) {
        configureTabs();
        
        
        
        
        insertAddArqGUI();
        setupManualDispatcher();
        setupFileDispatcher();
        insertAddArqGUI();
        
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
    
    
    
    /* ------- Views update ------- */

    private void refreshManualListViews() {
        StudentService studentService = facade.getService();
        
        // Update linked list view
        if (studentService != null) {
            listViewPaneController.updateListView(studentService);
        }
    }
    
    private void refreshFileListViews() {
        List<Student> arrayList = facade.getArrayList();
        List<Student> linkedList = facade.getLinkedList();
        
        if (arrayList != null && !arrayList.isEmpty()) {
            listViewPaneController.updateListView(arrayList);
        }
        
        if (linkedList != null && !linkedList.isEmpty()) {
            listViewPaneController.updateListView(linkedList);
        }
    }
    
    
    
    /* ------- Helper methods ------- */
    
    private void configureTabs() {
        // Reage sempre que a aba for trocada
        tbpAll.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            handleTabSelection(newTab);
        });

        // Aplica o estado inicial
        handleTabSelection(tbpAll.getSelectionModel().getSelectedItem());
    }

    private void handleTabSelection(Tab selectedTab) {
        if (selectedTab == tbAddArquivo) {
            // Aba "Arquivo" → habilita todas as listas e dá refresh
            listViewPaneController.enableArray();
            listViewPaneController.enableLinked();
            refreshFileListViews();
        } else {
            // Qualquer outra aba → desabilita só array e dá refresh
            listViewPaneController.disableArray();
            refreshManualListViews();
        }
    }
    
    private void insertAddManGUI(StudentDTO transfer) {
        try {
            facade.makeStudentService(transfer);
            refreshManualListViews();
            showSuccess("student.success.added");
        } 
        
        catch (InvalidListOperationException e) {
            showError(e.getMessage());
        } 
        
        catch (NumberFormatException e) {
            showError("gui.error.invalid.id");
        }
    }
    
    private void insertAddArqGUI() { 
        addFilePaneController.setOnGenerateLists(() -> { 
            try { 
                File selectedFile = addFilePaneController.getSelectedFile(); 
                boolean isArraySelected = addFilePaneController.isArrayTopSelected(); 
                boolean isLinkedSelected = addFilePaneController.isLinkedTopSelected(); 
                
                facade.makeLists(selectedFile, 
                        isArraySelected, 
                        isLinkedSelected); 
                
                refreshFileListViews(); 
                
                if (isArraySelected && !isLinkedSelected) {
                    showSuccess("list.array.created");
                } 
                
                else if (isLinkedSelected && !isArraySelected) {
                    showSuccess("list.linked.created");
                }
                
                else {
                    showSuccess("list.both.created");
                }
            } 
            
            catch (Exception e) { 
                showError(e.getMessage()); 
            } 
        }); 
    }
    
    private void showError(String messageKey, Object... params) {
        String message = String.format(
                ExceptionMessages.get(messageKey), params);
        
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
    
    
    
    /* ------- Dispatchs settings ------- */
    
    private void setupFileDispatcher() {
        for (OperationsLists op : OperationsLists.values()) {
            fileDispatcher.register(op, op.getAction());
        }

        addFilePaneController.setOnActionSelected(() -> {
            OperationsLists selectedOp = addFilePaneController.getSelectedOperation();
            boolean arraySelected = addFilePaneController.isArrayBotSelected();
            boolean linkedSelected = addFilePaneController.isLinkedBotSelected();
            Student student = null;
            Long durationArray = null;
            Long durationLinked = null;
            Student studentFound = null;
            
            if (selectedOp.getIndex() < 3) {
                student = addFilePaneController.buildStudent(); // CORRIGIR ESSE BUG
            }

            OperationActionLists action = fileDispatcher.get(selectedOp);
            if (action != null) {
                try {
                    if (arraySelected) {
                        long start = System.nanoTime();
                        studentFound = action.execute(facade, student, true, false);
                        durationArray = (System.nanoTime() - start) / 1_000_000;
                    }
                    
                    if (linkedSelected) {
                        long start = System.nanoTime();
                        studentFound = action.execute(facade, student, false, true);
                        durationLinked = (System.nanoTime() - start) / 1_000_000;
                    }
                    
                    if (studentFound != null) {
                        logOperationResult(selectedOp, studentFound, arraySelected, linkedSelected, durationArray, durationLinked);
                    } else {
                        logOperationResult(selectedOp, student, arraySelected, linkedSelected, durationArray, durationLinked);
                    }
                    
                    refreshFileListViews();
                } catch (Exception e) {
                    showError(e.getMessage());
                }
            }
        });
    }

    
    private void setupManualDispatcher() {
        for (OperationsCustomList op : OperationsCustomList.values()) {
            manualDispatcher.register(op, op.getAction());
        }
        
        addCustomList();
        searchCustomList();
        removeCustomList();
    }
    
    private void addCustomList() {
        addManualPaneController.setOnStudentAdded((var transfer) -> {
            OperationsCustomList add = OperationsCustomList.ADD_CUSTOM;
            OperationActionCustomList action = manualDispatcher.get(add);
            if (action != null) {
                try {
                    action.execute(facade, transfer, null);
                    showSuccess(add.getMessage());
                    refreshManualListViews();
                } catch (InvalidListOperationException e) {
                    showError("gui.error.invalid.id");
                } catch (EmptyListException ex) {
                    showError("list.empty");
                } catch (ElementNotFoundException ex) {
                    showError("element.not.found");
                }
            }
        });
    }
    
    private void searchCustomList() {
        buscarPaneController.setOnStudentFound((var student) -> {
            OperationsCustomList search = OperationsCustomList.SEARCH_CUSTOM;
            OperationActionCustomList action = manualDispatcher.get(search);
            
            if (action != null) {
                try {
                    action.execute(facade, student, studentFound -> {
                        showSuccess("student.success.found", studentFound.toString());
                    });
                } catch (InvalidListOperationException e) {
                    showError("gui.error.invalid.id");
                } catch (EmptyListException ex) {
                    showError("list.empty");
                } catch (ElementNotFoundException ex) {
                    showError("student.error.not.found", student.toString());
                }
            }
        });
    }
    
    private void removeCustomList() {
    removerPaneController.setOnStudentRemove((var student) -> {
        OperationsCustomList remove = OperationsCustomList.REMOVE_CUSTOM;
        OperationActionCustomList action = manualDispatcher.get(remove);

        if (action != null) {
            try {
                action.execute(facade, student, null);
                showSuccess("student.success.removed", student.toString());
                refreshManualListViews();
            } catch (InvalidListOperationException e) {
                showError("gui.error.invalid.id");
            } catch (EmptyListException ex) {
                showError("list.empty");
            } catch (ElementNotFoundException ex) {
                showError("student.error.not.found", student.toString());
            }
        }
    });
}
    
    
    
    /* ------- Helpers ------- */
    
    private void logOperationResult(
            OperationsLists selectedOp,
            Student student,
            boolean arraySelected,
            boolean linkedSelected,
            Long durationArrayMs, 
            Long durationLinkedMs) {
        StringBuilder msg = new StringBuilder();
        msg.append("Operação: ").append(selectedOp.getDescription()).append("\n\n");

        if (student != null) {
            msg.append("Aluno: ").append(student).append("\n\n");
        }

        if (arraySelected) {
            msg.append("Lista usada: ArrayList\n");
            msg.append("Tamanho atual: ").append(facade.getArrayList().size()).append("\n");
            msg.append("Tempo de execução: ").append(durationArrayMs).append(" ms\n\n");
        }

        if (linkedSelected) {
            msg.append("Lista usada: LinkedList\n");
            msg.append("Tamanho atual: ").append(facade.getLinkedList().size()).append("\n");
            msg.append("Tempo de execução: ").append(durationLinkedMs).append(" ms\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado da Operação");
        alert.setHeaderText("Sucesso");
        alert.setContentText(msg.toString());
        alert.showAndWait();
    }
}
