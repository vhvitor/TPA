/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.models;

import mycompany.genericlists.exceptions.ElementNotFoundException;
import mycompany.genericlists.exceptions.EmptyListException;
import mycompany.genericlists.exceptions.InvalidListOperationException;
import java.util.Comparator;
import java.util.List;
import mycompany.genericlists.lists.GenericDoublyLinkedList;

/**
 *
 * @author vitor
 * @param <T>
 */
public class Service<T> {
    final GenericDoublyLinkedList<T> list;
    
    
    
    /* ------- Constructors ------- */
    
    public Service(Comparator<T> comparator, boolean ordered) {
        this.list = new GenericDoublyLinkedList<>(comparator, ordered);
    }
    
    
    /* ------- Public API ------- */
    
    /**
     * 
     * @param element
     * @throws InvalidListOperationException 
     */
    public void add(T element) throws InvalidListOperationException {
        list.add(element);
    }
    
    /**
     * 
     * @param element
     * @throws InvalidListOperationException
     * @throws EmptyListException 
     */
    public void remove(T element) 
            throws InvalidListOperationException, EmptyListException {
        
        list.remove(element);
    }
    
    /**
     * 
     * @param element
     * @return
     * @throws InvalidListOperationException 
     */
    public boolean contains(T element) throws InvalidListOperationException {
        return list.contains(element);
    }
    
    /**
     * 
     * @param element
     * @return
     * @throws InvalidListOperationException
     * @throws EmptyListException 
     * @throws mycompany.genericlists.exceptions.ElementNotFoundException 
     */
    public T search(T element) 
            throws InvalidListOperationException, EmptyListException, ElementNotFoundException {
        
        return list.search(element);
    }
    
    /**
     * 
     * @return 
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    /**
    * Returns an Iterable over the elements of the list.
    * This allows subclasses and clients to iterate over all elements.
    * 
    * @return 
    */
   public Iterable<T> getAll() {
       return list; // se GenericDoublyLinkedList implementa Iterable<T>
   }
}