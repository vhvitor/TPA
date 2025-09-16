/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.models;

/**
 *
 * @author vitor
 */
public class Student {
    private String name;
    private long id;
    
    
    /* ------- Constructors ------- */
    
    public Student(String name, long id) {
        this.name = name;
        this.id = id;
    }
    
    
    
    /* ------- Getters and Setters ------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    
    /* ------- Public API ------- */
    
    @Override
    public String toString() {
        return "Nome: " + name + "\n" +
                "ID: " + id;
    }
    
    public static Student parseStudent(String line) {
        String[] parts = line.split(",", 2);
        int id = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        return new Student(name, id);
    }

}
