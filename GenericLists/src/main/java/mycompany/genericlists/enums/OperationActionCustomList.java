/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.enums;

import java.util.function.Consumer;
import mycompany.genericlists.exceptions.ElementNotFoundException;
import mycompany.genericlists.exceptions.EmptyListException;
import mycompany.genericlists.exceptions.InvalidListOperationException;
import mycompany.genericlists.facades.StudentFacade;

/**
 *
 * @author vitor
 */
@FunctionalInterface
public interface OperationActionCustomList {
    void execute(StudentFacade facade, Object param, Consumer<Object> callback) 
            throws InvalidListOperationException, EmptyListException, ElementNotFoundException;
}
