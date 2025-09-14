/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.exception;

/**
 * Exception thrown when an invalid operation is performed on the list.
 * Example: inserting a null element or using an invalid index
 *
 * @author vitor
 */
public class InvalidListOperationException extends ListException {
    
    /**
     * Constructor for the exception.
     * 
     * @param message detailed message describing the invalid operation.
     */
    public InvalidListOperationException(String message) {
        super(message);
    }

    public InvalidListOperationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
