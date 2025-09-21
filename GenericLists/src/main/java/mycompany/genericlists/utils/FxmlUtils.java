/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.utils;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author vitor
 */
public class FxmlUtils {
    public static <C> Parent loadFXML(String fxml, C controller) throws IOException {
    String fxmlPath = "/view/" + fxml + ".fxml";
    URL resource = controller.getClass().getResource(fxmlPath);
    if (resource == null) {
        throw new IOException("FXML resource not found: " + fxmlPath + " (from " + controller.getClass().getName() + ")");
    }

    FXMLLoader loader = new FXMLLoader(resource);

    loader.setControllerFactory(clazz -> {
        // debug output
        System.out.println("[FXMLLoader] requested controller class: " + clazz.getName());
        if (controller != null && clazz.isInstance(controller)) {
            System.out.println("[FXMLLoader] returning provided controller instance: " + controller.getClass().getName());
            return controller;
        }
        try {
            Object newInstance = clazz.getDeclaredConstructor().newInstance();
            System.out.println("[FXMLLoader] instantiated controller: " + newInstance.getClass().getName());
            return newInstance;
        } catch (Exception ex) {
            throw new RuntimeException("Could not create controller for " + clazz, ex);
        }
    });

    return loader.load();
}

}
