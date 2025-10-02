/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.enums;

import mycompany.genericlists.facades.StudentFacade;
import mycompany.genericlists.models.Student;

/**
 *
 * @author vitor
 */
@FunctionalInterface
public interface OperationActionLists {
    Student execute(StudentFacade facade, Student student, boolean arraySelected, boolean linkedSelected) throws Exception;
}
