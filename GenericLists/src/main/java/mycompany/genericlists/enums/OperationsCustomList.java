/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mycompany.genericlists.enums;

import java.util.function.Consumer;
import mycompany.genericlists.dtos.StudentDTO;
import mycompany.genericlists.exceptions.ElementNotFoundException;
import mycompany.genericlists.exceptions.EmptyListException;
import mycompany.genericlists.facades.StudentFacade;
import mycompany.genericlists.exceptions.InvalidListOperationException;
import mycompany.genericlists.models.Student;

/**
 *
 * @author vitor
 */
public enum OperationsCustomList {
    ADD_CUSTOM("student.success.added", (facade, param, callback) -> {
        StudentDTO transfer = (StudentDTO) param;
        
        facade.makeStudentService(transfer);
        facade.add(transfer.getStudent());
    }),
    
    REMOVE_CUSTOM("student.success.removed", (facade, param, callback) -> {
        Student student = (Student) param;
        facade.remove(student);
    }),
    
    SEARCH_CUSTOM("student.found", (facade, param, callback) -> {
        Student student = (Student) param;
        Student found = facade.search(student);
        
        if (callback != null) callback.accept(found);
    });
    
    private final OperationActionCustomList action;
    private final String messageKey;

    OperationsCustomList(String messageKey, 
            OperationActionCustomList action) {
        this.action = action;
        this.messageKey = messageKey;
    }
    
    
    
    public void execute(StudentFacade facade, Object param, Consumer<Object> callback) 
            throws InvalidListOperationException, EmptyListException, ElementNotFoundException {
        action.execute(facade, param, callback);
    }
    
    public String getMessage() {
        return messageKey;
    }
    
    public OperationActionCustomList getAction() {
        return action;
    }
}