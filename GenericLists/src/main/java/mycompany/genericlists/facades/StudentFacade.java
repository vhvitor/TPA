/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.facades;

import java.util.List;
import mycompany.genericlists.events.StudentInsertEvent;
import mycompany.genericlists.exceptions.InvalidListOperationException;
import mycompany.genericlists.models.Student;
import mycompany.genericlists.models.StudentService;

/**
 *
 * @author vitor
 */
public class StudentFacade {
    
    
    // Service
    private StudentService studentService;
    
    
    public void handleStudentAdded(StudentInsertEvent event) throws InvalidListOperationException {
        if (studentService == null) {
            studentService = new StudentService(
                    event.getComparator(), event.isOrdered());
        } 
        
        studentService.add(event.getStudent());
    }
    
    
   /* public List<Student> getAllStudents() {
        return studentService != null ? studentService.getAll() : List.of();
    }*/

    public StudentService getService() {
        return studentService;
    }
}
