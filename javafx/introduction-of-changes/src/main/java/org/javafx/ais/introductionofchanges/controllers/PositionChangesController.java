package org.javafx.ais.introductionofchanges.controllers;

import org.javafx.ais.introductionofchanges.utils.JpaApi;
import org.javafx.ais.introductionofchanges.model.Employee;
import org.javafx.ais.introductionofchanges.model.Position;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Сергей
 */
public class PositionChangesController {

    private boolean operationIsAdd;
    private boolean isAdded;
    private Position position;
    private Position addedPosition;
    private ObservableList<Employee> employeesData;

    @FXML
    private TextField positionNameTextField;
    @FXML
    private Button addButton;

    private Stage dialogStage;

    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setMinWidth(400);
        this.dialogStage.setMinHeight(150);
        this.dialogStage.setMaxWidth(400);
        this.dialogStage.setMaxHeight(150);
    }

    public void setOperationIsAdd(boolean operation) {
        this.operationIsAdd = operation;
        if (!operation) {
            addButton.setText("Применить изменения");
        }
    }

    public void setPosition(Position position) {
        this.position = position;
        positionNameTextField.setText(position.getName());
    }

    public void setEmployeesData(ObservableList<Employee> employeesData) {
        this.employeesData = employeesData;
    }

    public boolean isAddedPosition() {
        return isAdded;
    }

    public Position getAddedPosition() {
        return addedPosition;
    }

    @FXML
    private void handleAddButton() {
        if (StringUtils.isNotBlank(positionNameTextField.getText())) {
            if (operationIsAdd) {
                addedPosition = new Position(positionNameTextField.getText());
                JpaApi.beginTransaction();
                JpaApi.persist(addedPosition);
                JpaApi.closeTransaction();
                isAdded = true;
            } else {
                for (int i = 0; i < employeesData.size(); i++) {
                    if (employeesData.get(i).getPosition() != null
                            && position.getId() == employeesData.get(i).getPosition().getId()) {
                        employeesData.get(i).setPosition(position);
                    }
                }
                JpaApi.beginTransaction();
                ((Position) JpaApi.find(Position.class, position.getId())).setName(positionNameTextField.getText());
                JpaApi.closeTransaction();
                position.setName(positionNameTextField.getText());
            }
            dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание");
            alert.setHeaderText("Заполните название");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleCancelButton() {
        dialogStage.close();
    }

}
