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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mycompany.genericlists.models.Service;
import mycompany.genericlists.models.Student;
import mycompany.genericlists.utils.StudentFileHandler;



/**
 *
 * @author vitor
 */
public class AddFileController implements Initializable{
    
    @FXML private Button btArqGerar;
    @FXML private Button btArqOK;
    @FXML private Button btArqProcurar;
    @FXML private CheckBox cbArqArr;
    @FXML private CheckBox cbArqLink;
    @FXML private ComboBox<?> cbbArqOp;
    @FXML private TextField tfArqCaminho;
    @FXML private TextField tfArqNome;
    @FXML private TextField tfarqMatricula;
    
    
        
     private void initArq() {        
        tfArqCaminho.setEditable(false);
    }
     
    /* ------- Initialize ------- */
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    /* ------- Gui event ------- */
    
    @FXML
    private void onBtArqProcurarClick(ActionEvent event) throws IOException {
        File file = selectFile((Stage) btArqProcurar.getScene().getWindow());
        
        if (file != null) {
            selectedFile = file;
            tfArqCaminho.setText(file.getPath());
        }
    }
    
    @FXML
    private void onBtArqGerarClick(ActionEvent event) throws IOException {
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
