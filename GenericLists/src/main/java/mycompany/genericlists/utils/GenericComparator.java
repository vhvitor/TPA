/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.utils;

import java.util.Comparator;
import java.util.function.Function;

/**
 *
 * @author vitor
 * @param <T>
 * @param <U>
 */
public class GenericComparator<T, U extends Comparable<? super U>> implements Comparator<T> {
    
    private final Function<T, U> keyExtractor;
    
    public GenericComparator(Function<T, U> keyExtractor) {
        this.keyExtractor = keyExtractor;
    }

    @Override
    public int compare(T obj1, T obj2) {
        return keyExtractor.apply(obj1).compareTo(keyExtractor.apply(obj2));
    }
    
    
}
