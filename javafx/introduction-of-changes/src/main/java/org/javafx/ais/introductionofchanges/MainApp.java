package org.javafx.ais.introductionofchanges;

import org.javafx.ais.introductionofchanges.controllers.AuthorizationController;
import org.javafx.ais.introductionofchanges.utils.Constants;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Authorization_Scene.fxml"));
        AnchorPane root = (AnchorPane) loader.load();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.exit(0);
            }
        });

        stage.setMinWidth(400);
        stage.setMinHeight(200);
        stage.setTitle(Constants.APP_NAME);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setScene(scene);

        AuthorizationController controller = loader.getController();
        controller.setDialogStage(stage);

        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
