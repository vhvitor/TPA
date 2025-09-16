/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.lists;

/**
 *
 * @author vitor
 */
public interface ListWrapper<T> {
    void add(T element) throws Exception;
    void remove(T element) throws Exception;
    boolean contains(T element) throws Exception;
    T search(T element) throws Exception;
    boolean isEmpty();
    Iterable<T> getAll();
}
