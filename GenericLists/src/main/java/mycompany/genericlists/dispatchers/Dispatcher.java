/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.dispatchers;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vitor
 */
public class Dispatcher<K> {
    private final Map<K, Object> actions = new HashMap<>();

    public <T> void register(K key, T action) {
        actions.put(key, action);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(K key) {
        return (T) actions.get(key);
    }
}


