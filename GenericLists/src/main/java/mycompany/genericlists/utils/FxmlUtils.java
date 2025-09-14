/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mycompany.genericlists.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author vitor
 */
public class FxmlUtils {
    public static <Controller> Parent loadFXML(String fxml, Controller controller) throws IOException {
        String fxmlPath = "/view/" + fxml + ".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(controller.getClass().getResource(fxmlPath));
        
        try {
            // Define o controlador para o fxml que será carregado
            fxmlLoader.setControllerFactory(param -> controller);

            // Retorna o conteúdo carregado (a árvore de componentes)
            return fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o FXML: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
