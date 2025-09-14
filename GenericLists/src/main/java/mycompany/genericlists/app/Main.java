/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mycompany.genericlists.app;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import mycompany.genericlists.lists.GenericDoublyLinkedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mycompany.genericlists.controllers.InputController;
import mycompany.genericlists.utils.FxmlUtils;

/**
 *
 * @author vitor
 */
public class Main extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        InputController inputController = new InputController();
        
        scene = new Scene(FxmlUtils.loadFXML("Input", inputController));
        stage.setScene(scene);
        stage.show();
    }

    static <Controller> void setRoot(String fxml, Controller controller) throws IOException {
        scene.setRoot(FxmlUtils.loadFXML(fxml, controller));
    }

    public static void main(String[] args) {
        launch();
    }
}
