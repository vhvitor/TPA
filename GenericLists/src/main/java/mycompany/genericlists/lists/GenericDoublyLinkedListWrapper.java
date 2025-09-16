/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.lists;

/**
 *
 * @author vitor
 */
public class GenericDoublyLinkedListWrapper<T> implements ListWrapper<T> {
    private final GenericDoublyLinkedList<T> list;

    public GenericDoublyLinkedListWrapper(GenericDoublyLinkedList<T> list) {
        this.list = list;
    }

    @Override
    public void add(T element) throws Exception {
        list.add(element);
    }

    @Override
    public void remove(T element) throws Exception {
        list.remove(element);
    }

    @Override
    public boolean contains(T element) throws Exception {
        return list.contains(element);
    }

    @Override
    public T search(T element) throws Exception {
        return list.search(element);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterable<T> getAll() {
        return list;
    }
}