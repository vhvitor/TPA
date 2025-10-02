/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import mycompany.genericlists.models.Student;

/**
 *
 * @author vitor
 */
public class StudentFileHandler {
    
    private final File file;
    
    public StudentFileHandler(File file) {
        this.file = file;
    }
    
    public void load(List<Student> students) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                Student student = Student.parseStudent(line);
                students.add(student);
            }
        }
    }
}
