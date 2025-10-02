/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.dtos;

import java.util.Comparator;
import mycompany.genericlists.models.Student;

/**
 *
 * @author vitor
 */
public class StudentDTO {
    private final Student student;
    private final Comparator<Student> comparator;
    private final boolean ordered;

    public StudentDTO(Student student, Comparator<Student> comparator, boolean ordered) {
        this.student = student;
        this.comparator = comparator;
        this.ordered = ordered;
    }
    
    public StudentDTO(Student student) {
        this.student = student;
        comparator = null;
        ordered = false;
    }

    public Student getStudent() { return student; }
    public Comparator<Student> getComparator() { return comparator; }
    public boolean isOrdered() { return ordered; }
}
