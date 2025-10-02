/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.models;

import mycompany.genericlists.exceptions.ElementNotFoundException;
import mycompany.genericlists.exceptions.EmptyListException;
import mycompany.genericlists.exceptions.InvalidListOperationException;
import java.util.Comparator;
import mycompany.genericlists.lists.GenericDoublyLinkedList;

/**
 *
 * @author vitor
 * @param <T>
 */
public abstract class Service<T> {
    protected final GenericDoublyLinkedList<T> list;
    
    
    
    /* ------- Constructors ------- */
    
    public Service(Comparator<T> comparator, boolean ordered) {
        this.list = new GenericDoublyLinkedList<>(comparator, ordered);
    }
    
    
    /* ------- Public API ------- */
    
    public abstract void add(T element) throws InvalidListOperationException;
    
    /**
     * 
     * @param element
     * @throws InvalidListOperationException
     * @throws EmptyListException 
     */
    public abstract void remove(T element) throws InvalidListOperationException, EmptyListException;
    
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
    
    
    
    /* ------- Getters and Setters ------- */
    
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