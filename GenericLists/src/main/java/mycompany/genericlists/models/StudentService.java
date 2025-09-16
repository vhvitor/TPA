/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.models;

import mycompany.genericlists.exception.InvalidListOperationException;
import java.util.Comparator;
import java.util.List;
import mycompany.genericlists.utils.ExceptionMessages;

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
        System.out.println("Antes de adicionar: " + list.getSize());
        System.out.println("Itens no updateListView:");
        for (Student s : getAll()) {
            System.out.println(s);
            System.out.println("Comparando novo ID " + student.getId() + " com existente " + s.getId());
            if (student.getId() == s.getId()) {
                throw new InvalidListOperationException("student.exists");
            }
        }
            
        list.add(student);
        System.out.println("Depois de adicionar: " + list.getSize());
    }
    
    public static void addStudentToList(List<Student> list, Student student) {
    for (Student s : list) {
        if (s.getId() == student.getId()) {
            System.out.println("Student with ID " + student.getId() + " already exists. Skipping...");
            return; // or throw exception if you want
        }
    }
    list.add(student);
}
}
