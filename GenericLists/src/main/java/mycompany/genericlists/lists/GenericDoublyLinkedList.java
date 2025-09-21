/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.lists;

import mycompany.genericlists.exceptions.ElementNotFoundException;
import mycompany.genericlists.exceptions.EmptyListException;
import mycompany.genericlists.exceptions.InvalidListOperationException;
import mycompany.genericlists.utils.ExceptionMessages;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vitor
 * @param <T>
 */
public class GenericDoublyLinkedList<T> implements Iterable<T>{

    private Node<T> nose;                                                       // first element (head of the list)
    private Node<T> tail;                                                       // last element
    private Comparator<T> comparator;                                           // Comparator used for ordering and searching
    private int size;                                                           // number of elements in the list

    private final boolean ordered;                                              // flag: true if list is ordered, false otherwise

    /* ------- Constructors ------- */
    
    /**
     * Constructor for the gereric doubly Linked List
     *
     * @param comparator comparator used to order/search elements
     * @param ordered if true, list maintains elements in sorted order
     */
    public GenericDoublyLinkedList(Comparator<T> comparator, boolean ordered) {
        this.comparator = comparator;
        this.ordered = ordered;
        nose = tail = null;
        size = 0;
    }

    /* ------- Getters ------- */
    
    /**
     * Gets the current size of the list
     *
     * @return number of elements in the list
     */
    public int getSize() {
        return size;
    }
    
    

    /* ------- Public API ------- */
    
    /**
     * Adds a new element to the list. 
     * 
     * <p>
     * If the list is ordered, the element is inserted at the correct position
     * according to the comparator. If the list is unordered, the element is 
     * added at the end of the list.
     * </p>
     *
     * @param element the element to be added; must not be null.
     * @throws InvalidListOperationException if the element is null.
     */
    public void add(T element) throws InvalidListOperationException {
        // Validate input
        if (element == null) {
            throw new InvalidListOperationException(
                    ExceptionMessages.get("element.null"));
        }
        
        Node<T> new_node = new Node<T>(element);                                // Create a new node with the element
        
        try {
            if (this.isEmpty()) {
                initialize(new_node);
                size++;
                return;
            }
        
            if(!ordered) {
                insertNode(tail, new_node);
                size++;
                return;
            }
            
            if(comparator.compare(nose.data, element) >= 0) {
                updateNose(new_node);
            } else {
                Node<T> current_node = nose.next;
                
                while (current_node != tail && 
                        comparator.compare(current_node.data, element) < 0) {
                    current_node = current_node.next;
                }
                
                int comp = comparator.compare(current_node.data, element);
                
                insertNode((comp > 0) ? current_node.previous : current_node, new_node);
            }
            
            size++;
        } 
        
        catch (Exception e) {
            // Propagate exception with meaningful message
            throw new InvalidListOperationException(
                    ExceptionMessages.get("node.insert.error"), e);
        }
    }
    
    /**
     * Removes the first occurrence of the spedified element from the list
     *
     * <p>
     * This method supports both ordered and unordered lists, and correctly
     * handles circular doubly linked lists. Input validation, empty list check,
     * and "element not found" handling are delegated to {@code getNode}.
     * </p>
     * 
     * @param element the element to remove; must not be null.
     * @throws InvalidListOperationException if the element is null or not found.
     * @throws EmptyListException if the list is empty.
     */
    public void remove(T element) throws 
            InvalidListOperationException, EmptyListException {
        
        try {
            // Locate the node to be removed
            Node<T> target_node = getNode(element);
            // Special case: only one element in the list
            if (size == 1) {
                nose = tail = null;
            } else {
                // Unlink the node from the circular structure
                target_node.next.previous = target_node.previous;
                target_node.previous.next = target_node.next;

                // Update nose or tail if necessary
                if (target_node == nose) nose = target_node.next;
                if (target_node == tail) tail = target_node.previous;
            }

            size --;                                                            // Update the size of the list
        } catch (InvalidListOperationException | EmptyListException e) {
            // Re-throw known exceptions without altering their context
            throw e;
        } catch (Exception e) {
            // Wrap any unexpected issue into a meaningful custom exception
            throw new InvalidListOperationException(
                    ExceptionMessages.get("element.remove.error"), e);
        }
    }
    
    /**
     * Checks whether the list contains the specified element.
     * 
     * <p>
     * This method delegates the search to {@link #search(T)}.
     * If the element is not found or the list is empty, it returns {@code false}.
     * If the element is {@code null}, the exception from {@link #search(t) is
     * propagated.
     * </p>
     * 
     * @param element the element to check; mus not be null
     * @return {@code true} if the element exists in the list, 
     * {@code false} otherwise.
     * @throws InvalidListOperationException if the element is null
     */
    public boolean contains(T element) throws InvalidListOperationException {
        try {
            // Delegate to search; if found, return true
            search(element);
            return true;
        } 
        
        // Element not found or list is empty -> return false
        catch (ElementNotFoundException | EmptyListException e) {
            return false;
        }
    }
    
    /**
     * Searches for an element in the list.
     * 
     * <p>
     * This method iterates through the list comparing each item against the
     * provided element using the conrfigured comparator. If  is the element
     * is found, it is returned. If the list is ordered, the search is
     * optimized: as soon as an item greater than the target element is found,
     * the method throws an {@link ElementNotFoundException}.
     * </p>
     * 
     * @param element the element to search; must not be {@code null}
     * @return the found element
     * @throws InvalidListOperationException if the element is {@code null}
     * @throws EmptyListException if the list is empty
     * @throws ElementNotFoundException if the element does not exist in the list
     */
    public T search(T element) throws InvalidListOperationException, 
            EmptyListException, ElementNotFoundException {
        
        if (element == null) {
            throw new InvalidListOperationException(
                    ExceptionMessages.get("element.null"));
        }
        
        if (isEmpty()) {
            throw new EmptyListException(
                    ExceptionMessages.get("element.not.found"));
        }
        
        // Iterate through the list items
        for (T item : this) {
            int cmp = comparator.compare(item, element);
            
            if (cmp == 0) return item;                                          // element found
            
            /* If the list is ordered and current item is already greater,
             * we know the element is not present and can stop searching. */
            if (ordered && cmp > 0) {
                throw new ElementNotFoundException(
                    ExceptionMessages.get("element.not.found"));
            }
        }
        
        // If the loop finishes without returning, the element is not found
        throw new ElementNotFoundException(
                ExceptionMessages.get("element.not.found"));
    }
    
    public boolean isEmpty() {
        return getSize() == 0;
    }
    
    /**
    * Returns an iterator over the elements in this list in proper sequence.
    *
    * <p>
    * This allows the list to be used in enhanced for-loops (for-each) and
    * any context that requires an {@link Iterable}.
    * </p>
    *
    * @return an {@link Iterator} over the elements in this list.
    */
   @Override
   public Iterator<T> iterator() {
       return new GenericDoublyLinkedListIterator();
   }
    
    
    
    /* ------- Private helper methods ------- */
    
    /**
     * Initializes the list with a single node.
     * Sets both nose (head) and tail to the new node and links the node circularly.
     * 
     * @param new_node the node to initialize the list
     * @throws InvalidListOperationException if new_node is null.
     */
    private void initialize(Node<T> new_node) throws InvalidListOperationException {
        if (new_node == null) {
            throw new InvalidListOperationException(
                    ExceptionMessages.get("list.null"));
        }
        
        // Set nose and tail to the new node
        this.nose = this.tail = new_node;
        
        // Circular linking
        new_node.next = this.tail;
        new_node.previous = this.tail;
        
        size ++;                                                                // Increment the size of the list
    }
    
    /**
     * Updates the nose (head) of the list with a new node.
     * Adjusts the previous and next references to maintain circular doubly
     * linked structure.
     * 
     * @param new_nose the new node to set as nose.
     * @throws InvalidListOperationException if new_nose is null.
     */
    private void updateNose(Node<T> new_nose) throws InvalidListOperationException {
        if (new_nose == null) {
            throw new InvalidListOperationException(
                    ExceptionMessages.get("node.null"));
        }
        
        // Link the new node to the old nose and tail
        new_nose.next = nose;
        new_nose.previous = nose.previous;
        
        // Update old head and tail references
        nose.previous = new_nose;
        tail.next = new_nose;
        
        nose = new_nose;                                                        // Set new node as nose
    }
    
    /**
     * Inserts a new node after the given reference node.
     * 
     * @param reference_node the node after wich the new node will be inserted
     * @param new_node the node to be inserted.
     * @throws InvalidListOperationException if either reference_node or
     * new_node is null.
     */
    private void insertNode(Node<T> reference_node, Node<T> new_node) throws 
            InvalidListOperationException{
        
        if (reference_node == null || new_node == null) {
            throw new InvalidListOperationException(
                    ExceptionMessages.get("node.null"));
        }
        
        if (reference_node == tail) tail = new_node;
        
        // Set the new node's previous and next references
        new_node.previous = reference_node;
        new_node.next = reference_node.next;
        
        // Update the adjacent nodes to point to the new node.
        new_node.next.previous = new_node;
        reference_node.next = new_node;
    }
    
    /**
     * Searches for a node containing the given element in the list
     * <p>
     * This method supports both ordered and unordered lists, and correctly 
     * handles circular doubly linked lists. If the list is ordered, the search
     * will terminate early if it determines that the element cannot exist in 
     * the list.
     * </p>
     * 
     * @param element the element to search; must not be null.
     * @return the node containing the element.
     * @throws InvalidListOperationException if the element is null or not found
     * in the list.
     * @throws EmptyListException if the list is empty.
     */
    private Node<T> getNode(T element) throws InvalidListOperationException, EmptyListException {
        // Validate that the search element is not null
        if (element == null) {
            throw new InvalidListOperationException(
                    ExceptionMessages.get("element.null"));
        }
        
        // Validate that the list is not empty
        if (this.isEmpty()) {
            throw new EmptyListException(ExceptionMessages.get("list.empty"));
        }
        
        Node<T> current_node = nose;
        
        // Travere the circular list
        do {
            int cmp = comparator.compare(current_node.data, element);           // Compare current node's data with the search element
            
            if (cmp == 0) return current_node;                                  // Element found, return the corresponding node
            
            /* If the list is ordered and the current node's data exceeds
            the element, the element cannot exist in the list
            */
            if (ordered && cmp > 0) {
                throw new InvalidListOperationException(
                        ExceptionMessages.get("element.not.found"));
            }
            
            current_node = current_node.next;                                   // Move to the next node
        } while (current_node != nose);                                         // Stop after a full circular traversal
        
        // Element not found after full traversal
        throw new InvalidListOperationException(
                ExceptionMessages.get("element.not.found"));
    }

    
    
    /* ------- Inner Node class ------- */
    
    private static class Node<T> {
        T data;                                                                 // the data stored in this node
        Node<T> next;                                                           // reference to the next node
        Node<T> previous;                                                       // reference to the previous node

        Node(T data) {
            this.data = data;
            this.next = previous = null;                                        // both next and ore
        }
    }
    
    
    
    /* ------- Inner Iterator class ------- */
    
    /**
 * Iterator for the GenericDoublyLinkedList.
 * <p>
 * This inner class provides a way to traverse the list in a
 * sequential manner, supporting the Iterable interface. It
 * can access the private members of the outer list class,
 * including nodes, without exposing them externally.
 * </p>
 *
 * @param <T> the type of elements stored in the list
 */
private class GenericDoublyLinkedListIterator implements Iterator<T> {

    // Current node being visited
    private Node<T> current = nose;

    // Flag to track the first iteration (for circular lists)
    private boolean firstIteration = true;

    /**
     * Checks if there is a next element in the iteration.
     *
     * @return true if there is a next element, false otherwise
     */
    @Override
    public boolean hasNext() {
        return current != null && (firstIteration || current != nose);
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the list
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in the list");
        }

        T data = current.data;
        current = current.next;
        firstIteration = false;
        return data;
    }

    /**
     * Remove operation is not supported by this iterator.
     *
     * @throws UnsupportedOperationException if invoked
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove not supported");
    }
}

}
