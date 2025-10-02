/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.facades;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import mycompany.genericlists.dtos.StudentDTO;
import mycompany.genericlists.exceptions.ElementNotFoundException;
import mycompany.genericlists.exceptions.EmptyListException;
import mycompany.genericlists.exceptions.InvalidListOperationException;
import mycompany.genericlists.models.Student;
import mycompany.genericlists.models.StudentService;
import mycompany.genericlists.utils.StudentFileHandler;

/**
 *
 * @author vitor
 */
public class StudentFacade {
    
    // Custom Service (add manualmente)
    private StudentService studentService;
    
    // Java Colections (file add)
    List<Student> arrayList;
    List<Student> linkedList;
    
    
    /* ------- Constructors ------- */
    
    public StudentFacade() {
    }
    
    
    
    /* ------- Getters and Setters ------- */
    
    public StudentService getService() {
        return studentService;
    }
    
    public List<Student> getArrayList() {
        return arrayList;
    }

    public List<Student> getLinkedList() {
        return linkedList;
    }
    
    public void setTransfer(StudentDTO transfer) {
    }

    
    
    
    /* ------- Public API ------- */
    
    public void makeStudentService(StudentDTO transfer) throws InvalidListOperationException {
        if (studentService == null) {
            studentService = new StudentService(
                    transfer.getComparator(), transfer.isOrdered());
        }
    }
    
    public void makeLists(File file, boolean makeArray, boolean makeLinked) throws IOException {
        StudentFileHandler handler = new StudentFileHandler(file);

        if (makeArray && arrayList == null) {
            arrayList = new ArrayList<>();
            handler.load(arrayList);
        }
        
        if (makeLinked && linkedList == null) {
            linkedList = new LinkedList<>();
            handler.load(linkedList);
        }
    }
    
    
    
    /* ------- Lists Operations ------- */
    
    public void add(Student student) throws InvalidListOperationException {
        studentService.add(student);
    }
    
    public void remove(Student student) throws InvalidListOperationException, EmptyListException {
        studentService.remove(student);
    }
    
    public Student search(Student student) throws InvalidListOperationException, EmptyListException, ElementNotFoundException {
        return studentService.search(student);
    }
    
    public void addLast(List<Student> list, 
                            Student student) 
                            throws InvalidListOperationException {
        
        if (list == null) {
            throw new InvalidListOperationException("list.none");
        }
        list.addLast(student);
    }

    
    public void addFirst(List<Student> list, 
                                Student student) 
                                throws InvalidListOperationException {
        
        if (list == null) {
            throw new InvalidListOperationException("list.none");
        }
        list.addFirst(student);
    }
    
    public void addMiddle(List<Student> list, 
                                Student student) 
                                throws InvalidListOperationException {
        
        if (list == null) {
            throw new InvalidListOperationException("list.none");
        }
        
        int middle = list.size() / 2;
        list.add(middle, student);
    }
    
    public Student getLast(List<Student> list) 
            throws InvalidListOperationException {
        if (list == null || list.isEmpty()) {
            throw new InvalidListOperationException("list.none");
        }
        return list.getLast();
    }
    
    public Student getPenultimate(List<Student> list) 
                            throws InvalidListOperationException {
        
        if (list == null || list.size() < 2) {
            throw new InvalidListOperationException("list.none");
        } 
        
        return list.get(list.size() - 2);
    }

    public Student getMiddle(List<Student> list) 
            throws InvalidListOperationException {
        if (list == null || list.isEmpty()) {
            throw new InvalidListOperationException("list.none");
        }
        int middle = list.size() / 2;
        return list.get(middle);
    }
}
