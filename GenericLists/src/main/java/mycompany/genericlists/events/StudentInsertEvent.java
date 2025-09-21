/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.events;

import java.util.Comparator;
import mycompany.genericlists.models.Student;

/**
 *
 * @author vitor
 */
public class StudentInsertEvent {
    private final Student student;
    private final Comparator<Student> comparator;
    private final boolean ordered;

    public StudentInsertEvent(Student student, Comparator<Student> comparator, boolean ordered) {
        this.student = student;
        this.comparator = comparator;
        this.ordered = ordered;
    }

    public Student getStudent() { return student; }
    public Comparator<Student> getComparator() { return comparator; }
    public boolean isOrdered() { return ordered; }
}
