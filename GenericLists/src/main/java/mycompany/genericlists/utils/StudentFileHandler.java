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
public class StudentFileHandler extends FileHandler<Student>{
    
    public StudentFileHandler(File file) {
        super(file);
    }
    
    @Override
    public List<Student> load() throws IOException {
        List<Student> students = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                Student student = Student.parseStudent(line);
                students.add(student);
            }
        }
        
        return students;
    }
    
    public ArrayList<Student> makeArrayList() throws IOException {
        return new ArrayList<>(load());
    }
    
    public LinkedList<Student> makeLinkedList() throws IOException {
        return new LinkedList<>(load());
    }
}
