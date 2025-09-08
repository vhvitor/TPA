/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.utils;

import java.util.ResourceBundle;

/**
 * Utility class to retireve exception messages from a properties file.
 *
 * @author vitor
 */
public class ExceptionMessages {
    
    private static final ResourceBundle bundle = 
            ResourceBundle.getBundle("exception_messages");
    
    /**
     * Retrieves a message from the properties file by key.
     * 
     * @param key the key of the message
     * @return the message as a string
     */
    public static String get(String key) {
        return bundle.getString(key);
    }
}
