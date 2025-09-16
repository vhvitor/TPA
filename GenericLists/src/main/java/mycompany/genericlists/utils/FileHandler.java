/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author vitor
 */
public abstract class FileHandler<T> {
    
    protected final File file;
    
    /**
     * Load data from file and return a list of objects.
     *
     * @return List of objects parsed from file
     * @throws IOException if file reading fails
     */
    public FileHandler(File file) {
        this.file = file;
    }
    
    public abstract List<T> load() throws IOException;
}
