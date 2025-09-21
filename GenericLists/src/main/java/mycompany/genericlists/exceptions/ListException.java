/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.exceptions;

/**
 * Base class for all exceptions related to the generic list.
 * All specific list exceptions  should inherit from ths class.
 * 
 * @author vitor
 */
public class ListException extends Exception {
    /**
     * Constructor for the base exception.
     * 
     * @param message detailed message describing the cause of the exception
     */
    public ListException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause, for exception chaining.
     *
     * @param message detailed message
     * @param cause the original exception
     */
    public ListException(String message, Throwable cause) {
        super(message, cause);
    }
}
