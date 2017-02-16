package org.javafx.ais.introductionofchanges.controllers;

import org.javafx.ais.introductionofchanges.MainApp;
import org.javafx.ais.introductionofchanges.model.BrowsingItem;
import org.javafx.ais.introductionofchanges.model.SystemUser;
import org.javafx.ais.introductionofchanges.utils.Constants;
import org.javafx.ais.introductionofchanges.utils.JpaApi;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 *
 * @author Сергей
 */
public class AuthorizationController {

    private SystemUser systemUser;
    private static boolean isMainSceneWasOpened;
    private MainController mainController;

    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button registrationButton;
    @FXML
    private Button enterButton;

    private Stage dialogStage;

    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setMinWidth(400);
        this.dialogStage.setMinHeight(170);
        this.dialogStage.setMaxWidth(400);
        this.dialogStage.setMaxHeight(170);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleEnterButtonAction() throws IOException {
        if (StringUtils.isNotBlank(loginTextField.getText())
                && StringUtils.isNotBlank(passwordTextField.getText())) {
            try {
                JpaApi.beginTransaction();
                TypedQuery<SystemUser> query = JpaApi.createQuery(
                        "SELECT su FROM SystemUser su WHERE su.login=:login AND su.password=:password", SystemUser.class);
                systemUser = query.setParameter("login", loginTextField.getText())
                        .setParameter("password", passwordTextField.getText()).getSingleResult();
                BrowsingItem browsingItem = new BrowsingItem(systemUser, LocalDateTime.now());
                JpaApi.persist(browsingItem);
                JpaApi.closeTransaction();

                if (!isMainSceneWasOpened) {
                    FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Main_Scene.fxml"));
                    AnchorPane page = (AnchorPane) loader.load();
                    Stage mainStage = new Stage();

                    mainStage.setTitle(Constants.APP_NAME);
                    mainStage.initModality(Modality.APPLICATION_MODAL);
                    mainStage.initOwner(new Stage());
                    Scene scene = new Scene(page);
                    mainStage.setScene(scene);

                    mainController = loader.getController();
                    mainController.setSystemUser(systemUser);
                    mainController.setDialogStage(mainStage);

                    mainStage.show();
                    mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {
                            System.exit(0);
                        }
                    });

                    mainStage.show();
                    dialogStage.close();
                    isMainSceneWasOpened = true;
                } else {
                    mainController.setSystemUser(systemUser);
                    mainController.setBrowsingItem(browsingItem);
                    dialogStage.close();
                }

            } catch (NoResultException ex) {
//                System.out.println("no user with this login");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Пользователь с таким логином не зарегистрирован в системе.");
                alert.showAndWait();
                JpaApi.closeTransaction();
            }
        }
    }

    @FXML
    private void handleRegistrationButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_Employee_Scene.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage registrationStage = new Stage();
            registrationStage.setTitle("Регистрация");
            registrationStage.initModality(Modality.APPLICATION_MODAL);
            registrationStage.initOwner(new Stage());
            Scene scene = new Scene(page);
            registrationStage.setScene(scene);

            EmployeeChangesController controller = loader.getController();
            controller.setDialogStage(registrationStage);
            controller.setOperationIsAddSystemUser(true);

            registrationStage.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание");
            alert.setHeaderText("Ошибка при загрузке экрана регистрации.");
            alert.showAndWait();
        }

    }

}
