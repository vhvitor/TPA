/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.exception;

/**
 * Exception thrown when an element is not found in the list.
 * 
 * @author vitor
 */
public class ElementNotFoundException extends ListException {
    
    /**
     * Constructor for the exception.
     * 
     * @param message detailed message describing the missing element.
     */
    public ElementNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause, for exception chaining.
     *
     * @param message detailed message
     * @param cause the original exception
     */
    public ElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
