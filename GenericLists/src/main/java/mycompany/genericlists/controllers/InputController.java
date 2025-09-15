/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mycompany.genericlists.controllers;

import mycompany.genericlists.utils.GeradorArquivosOrdenados;
import mycompany.genericlists.utils.GeradorArquivosBalanceados;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
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
import mycompany.genericlists.exception.InvalidListOperationException;
import mycompany.genericlists.models.Service;
import mycompany.genericlists.models.Student;
import mycompany.genericlists.models.StudentService;
import mycompany.genericlists.utils.ExceptionMessages;
import mycompany.genericlists.utils.GenericComparator;

/**
 * FXML Controller class
 *
 * @author vitor
 */
public class InputController implements Initializable {
    
    @FXML
    private TextField tfGerarNumRegistros;
    
    @FXML
    private Button btArqOK;

    @FXML
    private Button btBusOK;

    @FXML
    private Button btManOK;

    @FXML
    private Button btRemOK;

    @FXML
    private CheckBox cbArqOrdenada;

    @FXML
    private CheckBox cbManOrdenada;
    
    @FXML
    private ListView<Student> arraylstItems;
    
    @FXML
    private ListView<Student> linkedlstItems;

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
    private Button btGerarOrdenado;

    @FXML
    private Button btGerarBalanceado;
    
    // Comparators
    private GenericComparator<Student, Integer> comparatorById;
    private GenericComparator<Student, String> comparatorByName;
    
    // Services
    private StudentService studentService;
    
    
    
    /* ------- Initialize ------- */
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureAllButtons();
        initAllOrderControls();
        initManual();
    }
    
    
    
    /* ------- Public API ------- */
    
    @FXML
    void onBtGerarOrdenadoClick(ActionEvent event) {
        try {
            int numRegistros = Integer.parseInt(tfGerarNumRegistros.getText());
            String nomeArquivo = "alunosOrdenados.txt";

            GeradorArquivosOrdenados.gerarArquivo(numRegistros);

            tfArqCaminho.setText(nomeArquivo);
            showSuccess("file.success.generated", nomeArquivo);

        } catch (NumberFormatException e) {
            // Mostra um erro se o texto não for um número válido
            showError("gui.error.invalid.id");
        }
    }

    @FXML
    void onBtGerarBalanceadoClick(ActionEvent event) {
        try {
            int numRegistros = Integer.parseInt(tfGerarNumRegistros.getText());
            String nomeArquivo = "alunosBalanceados.txt";

            GeradorArquivosBalanceados.gerarArquivo(numRegistros);

            tfArqCaminho.setText(nomeArquivo);
            showSuccess("file.success.generated", nomeArquivo);

        } catch (NumberFormatException e) {
            // Mostra um erro se o texto não for um número válido
            showError("gui.error.invalid.id");
        }
    }
        @FXML
        private void onBtManOKClick(ActionEvent event) 
                throws InvalidListOperationException {

            try {
                Comparator<Student> comparator = getManComparator();                // Determine the comparator based on UI state (ordered or not)
                Student student = buildStudentFromManFields();                      // Build Student object from manual input fields

                handleInsert(comparator, student);
                clearManFields();
                updateLinkedListView(studentService);
                updateArrayListView(studentService);
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
    void onBtArqOKClick(ActionEvent event) {

        String path = tfArqCaminho.getText();
        boolean ordered = cbArqOrdenada.isSelected();
        String comparatorName = rbArq2.isSelected() ? "Nome" : "ID"; // Para o log


        Comparator<Student> comparator = rbArq2.isSelected() ? comparatorByName : comparatorById;

        // Cria o serviço se ele ainda não existir
        if (studentService == null) {
            studentService = new StudentService(comparator, ordered);
        } else {
            System.out.println("[DEBUG] Usando StudentService existente.");
        }

        try (Scanner scanner = new Scanner(new FileInputStream(path))) {
            int studentsAdded = 0;
            System.out.println("[DEBUG] Começando a ler o arquivo...");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.split(",");
                if (parts.length == 2) {
                    long id = Long.parseLong(parts[0].trim());
                    String name = parts[1].trim();

                    studentService.add(new Student(name, id));
                    studentsAdded++;
                } else {
                    System.out.println("    [DEBUG] Linha ignorada (formato incorreto): '" + line + "'");
                }
            }
            System.out.println("[DEBUG] Fim da leitura do arquivo. Total de alunos processados: " + studentsAdded);

            System.out.println("[DEBUG] Chamando updateListView...");
            updateLinkedListView(studentService);
            System.out.println("[DEBUG] updateLinkedListView finalizado.");

            System.out.println("[DEBUG] Chamando updateArrayListView...");
            updateArrayListView(studentService);
            System.out.println("[DEBUG] updateArrayListView finalizado.");

            System.out.println("[DEBUG] Desabilitando controles de ordenação...");
            setDisableAllOrderControls();

            System.out.println("[DEBUG] Chamando showSuccess...");
            showSuccess("file.success.loaded", studentsAdded);
            System.out.println("[DEBUG] Processo finalizado com sucesso.");

        } catch (FileNotFoundException e) {
            System.err.println("[DEBUG] ERRO: Arquivo não encontrado.");
            showError("file.error.not.found");
        } catch (NumberFormatException e) {
            System.err.println("[DEBUG] ERRO: Formato de número inválido no arquivo.");
            showError("file.error.invalid.id");
        } catch (InvalidListOperationException e) {
            System.err.println("[DEBUG] ERRO: Operação inválida na lista - " + e.getMessage());
            showError("Erro na Operação", e.getMessage());
        }
    }
    
    
    
    /* ------- Private helper methods ------- */
    
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
    
    private void updateLinkedListView(Service<Student> service) {
        linkedlstItems.getItems().clear();
        service.getAll().forEach(linkedlstItems.getItems()::add);
    }
    private void updateArrayListView(Service<Student> service) {
        arraylstItems.getItems().clear();
        service.getAll().forEach(arraylstItems.getItems()::add);
    }
    
    private void clearManFields() {
        tfManMatricula.clear();
        tfManNome.clear();
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
    
    private void initManual() {        
        clearManFields();
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
    
        private void showError(String title, String rawMessage) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(rawMessage);
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