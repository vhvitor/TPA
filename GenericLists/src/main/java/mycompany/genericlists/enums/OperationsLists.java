/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mycompany.genericlists.enums;

import mycompany.genericlists.models.Student;
import mycompany.genericlists.facades.StudentFacade;

/**
 *
 * @author vitor
 */
public enum OperationsLists {
    ADD_LAST(0, "Inserir no fim", 
            (facade, student, arraySelected, linkedSelected) -> {
                
        if (arraySelected) facade.addLast(facade.getArrayList(), student);
        if (linkedSelected) facade.addLast(facade.getLinkedList(), student);
        
        return null;
    }),
    
    ADD_FIRST(1, "Inserir no início", 
            (facade, student, arraySelected, linkedSelected) -> {
                
        if (arraySelected) facade.addFirst(facade.getArrayList(), student);
        if (linkedSelected) facade.addFirst(facade.getLinkedList(), student);
        
        return null;
    }),
    
    ADD_MIDDLE(2, "Inserir no meio", 
              (facade, student, arraySelected, linkedSelected) -> {
                
        if (arraySelected) facade.addMiddle(facade.getArrayList(), student);
        if (linkedSelected) facade.addMiddle(facade.getLinkedList(), student);
        
        return null;
    }),
    
    GET_LAST(3, "Buscar último", 
              (facade, student, arraySelected, linkedSelected) -> {
        
        Student studentFound = null;
                  
        if (arraySelected) studentFound = facade.getLast(facade.getArrayList());
        if (linkedSelected) studentFound = facade.getLast(facade.getLinkedList());
        
        return studentFound;
    }),
    
    GET_PENULTIMATE(4, "Buscar penúltimo", 
              (facade, student, arraySelected, linkedSelected) -> {
        
        Student studentFound = null;
        
        if (arraySelected) studentFound = facade.getPenultimate(facade.getArrayList());
        if (linkedSelected) studentFound = facade.getPenultimate(facade.getLinkedList());
        
        return studentFound;
    }),
    
    GET_MIDDLE(5, "Buscar no meio", 
              (facade, student, arraySelected, linkedSelected) -> {
                  
        Student studentFound = null;
                
        if (arraySelected) studentFound = facade.getMiddle(facade.getArrayList());
        if (linkedSelected) studentFound = facade.getMiddle(facade.getLinkedList());
        
        return studentFound;
    });
    
    private final int index;
    private final String description;
    private final OperationActionLists action;

    OperationsLists(int index, String description, OperationActionLists actionLists) {
        this.index = index;
        this.description = description;
        this.action = actionLists;
    }
    
    public void execute(StudentFacade facade, Student student, boolean arraySelected, boolean linkedSelected) throws Exception {
        action.execute(facade, student, arraySelected, linkedSelected);
    }
    
    public OperationActionLists getAction() {
        return action;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getIndex() {
        return index;
    }

    @Override
    public String toString() { return description; }
}