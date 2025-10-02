/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.models;

import mycompany.genericlists.exceptions.InvalidListOperationException;
import java.util.Comparator;
import mycompany.genericlists.exceptions.EmptyListException;

/**
 *
 * @author vitor
 */
public class StudentService extends Service<Student>{
    
    public StudentService(Comparator<Student> comparator, boolean ordered) {
        super(comparator, ordered);
    }
    
    @Override
    public void add(Student student) throws InvalidListOperationException{
        
        for (Student s : getAll()) {
            
            if (student.getId() == s.getId()) {
                throw new InvalidListOperationException("student.exists");
            }
        }
            
        list.add(student);
    }

    @Override
    public void remove(Student student) throws InvalidListOperationException, EmptyListException {
        list.remove(student);
    }
}