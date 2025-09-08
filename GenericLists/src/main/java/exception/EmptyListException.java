/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 * Exception thrown when an operation cannot be performed because list is empty.
 *
 * @author vitor
 */
public class EmptyListException extends ListException {
    
    /**
     * Constructor for the exception
     * 
     * @param message detailed message describing the invalid operation
     */
    public EmptyListException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause, for exception chaining.
     *
     * @param message detailed message
     * @param cause the original exception
     */
    public EmptyListException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
