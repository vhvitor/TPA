/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author vitor
 */
public class Input {
    public Scanner sInput;

    
    public Input() {
        try {
            this.sInput = new Scanner(new FileInputStream("src/main/resources/input.txt")).useLocale(Locale.US);
        } 
        catch (FileNotFoundException e) {
            // Caso contrário, vai ler do teclado.
            this.sInput = new Scanner(System.in).useLocale(Locale.US);
        }
    }
}
