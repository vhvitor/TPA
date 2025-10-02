/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import mycompany.genericlists.models.Service;
import mycompany.genericlists.models.Student;

/**
 *
 * @author vitor
 */
public class ListViewController {
    @FXML private ListView<Student> lvLinked;
    @FXML private ListView<Student> lvArray;
    
    
    
    /* ------- Public API ------- */
    
    public void updateListView(Service<Student> service) {
        lvLinked.getItems().clear();
        service.getAll().forEach(lvLinked.getItems()::add);
    }
    
    public void updateListView(List<Student> list) {
        if (list instanceof LinkedList) {
            lvLinked.getItems().clear();
            lvLinked.getItems().addAll(list);
        }
        
        if (list instanceof ArrayList) {
            lvArray.getItems().clear();
            lvArray.getItems().addAll(list);
        }
    }
    
    public void disableLinked() {
        lvLinked.setDisable(true);
    }
    
    public void disableArray() {
        lvArray.setDisable(true);
    }

    void enableArray() {
        lvArray.setDisable(false);
    }

    void enableLinked() {
        lvLinked.setDisable(false);
    }
}
