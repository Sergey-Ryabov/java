package org.javafx.custom.dialogs;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Сергей
 */
public class DialogController {
       
    private boolean okClicked;

    public static enum DialogType {
        INFO, WARN, CONFIRM
    }

    private static final Image INFO_IMG = new Image(DialogController.class.getResource("/img/info.png").toString());
    private static final Image WARN_IMG = new Image(DialogController.class.getResource("/img/warn.png").toString());
    private static final Image CONFIRM_IMG = new Image(DialogController.class.getResource("/img/confirm.png").toString());

    @FXML
    private Label headerLabel;
    @FXML
    private Label contentLabel;
    @FXML
    private ImageView imageView;

    @FXML
    private Button cancelButton;

    private Stage dialogStage;

    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMinWidth(double val) {
        this.dialogStage.setMinWidth(val);
    }

    public void setMinHeight(double val) {
        this.dialogStage.setMinHeight(val);
    }

    public void setMaxWidth(double val) {
        this.dialogStage.setMaxWidth(val);
    }

    public void setMaxHeight(double val) {
        this.dialogStage.setMaxHeight(val);
    }

    public void setTitle(String title) {
        dialogStage.setTitle(title);
    }

    public void setHeader(String header) {
        if (header == null || "".equals(header)) {
            headerLabel.setText("need set header!!!");
        } else {
            headerLabel.setText(header);
        }
    }

    public void setContent(String content) {
        if (content == null || "".equals(content)) {
            contentLabel.setVisible(false);
        } else {
            contentLabel.setText(content);
        }
    }

    public void setDialogType(DialogType dialogType) {
        if (dialogType != null) {
            switch (dialogType) {
                case INFO:
                    imageView.setImage(INFO_IMG);
                    break;
                case WARN:
                    imageView.setImage(WARN_IMG);
                    break;
                case CONFIRM:
                    imageView.setImage(CONFIRM_IMG);
                    cancelButton.setVisible(true);
                    break;
            }
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOkButton() {
        okClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancelButton() {
        dialogStage.close();
    }

    public static DialogController createDialog(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(DialogController.class.getResource("/fxml/Dialog_Scene.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(200);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        primaryStage.setScene(scene);

        DialogController controller = loader.getController();
        controller.setDialogStage(primaryStage);

        return controller;
    }

    public static DialogController createDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(DialogController.class.getResource("/fxml/Dialog_Scene.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setMinWidth(400);
        stage.setMinHeight(200);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(new Stage());

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setScene(scene);

        DialogController controller = loader.getController();
        controller.setDialogStage(stage);

        return controller;
    }

    public void showAndWait() {
        dialogStage.showAndWait();
    }

    public void show() {
        dialogStage.show();
    }

}
