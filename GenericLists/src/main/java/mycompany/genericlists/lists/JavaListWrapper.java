/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.lists;

import java.util.List;

/**
 *
 * @author vitor
 */
public class JavaListWrapper<T> implements ListWrapper<T> {
    private final List<T> list;

    public JavaListWrapper(List<T> list) {
        this.list = list;
    }

    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public void remove(T element) {
        list.remove(element);
    }

    @Override
    public boolean contains(T element) {
        return list.contains(element);
    }

    @Override
    public T search(T element) throws Exception {
        if (!list.contains(element)) throw new Exception("Element not found");
        return element;
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