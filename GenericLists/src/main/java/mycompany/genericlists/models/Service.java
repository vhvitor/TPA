/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.models;

import java.util.Comparator;
import mycompany.genericlists.lists.GenericDoublyLinkedList;
import mycompany.genericlists.utils.GenericComparator;

/**
 *
 * @author vitor
 * @param <T>
 * @param <U>
 */
public class Service<T, U> {
    private final GenericDoublyLinkedList<T> list;
    
    public Service(Comparator comparator, boolean ordered) {
        list = new GenericDoublyLinkedList<>(comparator, true);
        
    }
}
