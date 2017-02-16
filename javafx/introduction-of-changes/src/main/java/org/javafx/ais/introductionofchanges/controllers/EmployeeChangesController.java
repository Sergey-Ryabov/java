package org.javafx.ais.introductionofchanges.controllers;

import org.javafx.ais.introductionofchanges.model.Position;
import org.javafx.ais.introductionofchanges.model.AlertList;
import org.javafx.ais.introductionofchanges.model.Employee;
import org.javafx.ais.introductionofchanges.model.SystemUser;
import org.javafx.ais.introductionofchanges.model.Division;
import org.javafx.ais.introductionofchanges.utils.JpaApi;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;

/**
 *
 * @author Сергей
 */
public class EmployeeChangesController {

    private boolean isAddSystemUser;
    private boolean operationIsAdd;
    private boolean isAdded;
    private Employee employee;
    private Employee addedEmployee;
    private Division oldDivision;
    private Stage dialogStage;

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField patronimicTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField cellPhoneNumberTextField;

    @FXML
    private Label positionLabel;
    @FXML
    private ComboBox positionComboBox;
    @FXML
    private Label divisionLabel;
    @FXML
    private ComboBox divisionComboBox;

    @FXML
    private TreeView alertListsTreeView;

    @FXML
    private Label loginLabel;
    @FXML
    private TextField loginTextField;

    @FXML
    private Label passwordLabel;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button addButton;

    @FXML
    private void initialize() {
        loginLabel.setVisible(false);
        loginTextField.setVisible(false);
        passwordLabel.setVisible(false);
        passwordTextField.setVisible(false);
        alertListsTreeView.setPrefHeight(alertListsTreeView.getPrefHeight() + 50);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setMinWidth(500);
        this.dialogStage.setMinHeight(500);
    }

    public void setOperationIsAddSystemUser(boolean isAddSystemUser) {
        this.isAddSystemUser = isAddSystemUser;
        this.operationIsAdd = isAddSystemUser;
        dialogStage.setMinHeight(450);
        dialogStage.setMaxHeight(450);
        loginLabel.setVisible(true);
        loginTextField.setVisible(true);
        passwordLabel.setVisible(true);
        passwordTextField.setVisible(true);
        positionLabel.setVisible(false);
        positionComboBox.setVisible(false);
        divisionLabel.setVisible(false);
        divisionComboBox.setVisible(false);
        alertListsTreeView.setVisible(false);
    }

    public void setOperationIsAdd(boolean operation) {
        this.operationIsAdd = operation;
        if (!operation) {
            addButton.setText("Применить изменения");
        }
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;

        nameTextField.setText(employee.getName());
        surnameTextField.setText(employee.getSurname());
        patronimicTextField.setText(employee.getPatronimic());
        phoneNumberTextField.setText(employee.getPhoneNumber());
        cellPhoneNumberTextField.setText(employee.getCellPhoneNumber());

        if (employee.getPosition() != null) {
            positionComboBox.getSelectionModel().select(employee.getPosition());
        }
        if (employee.getDivision() != null) {
            divisionComboBox.getSelectionModel().select(employee.getDivision());
            oldDivision = (Division) divisionComboBox.getSelectionModel().getSelectedItem();
        }
        if (employee.getAlertListsInEmployee() != null && !employee.getAlertListsInEmployee().isEmpty()) {
            AlertList alertListRoot = new AlertList();
            alertListRoot.setName("Списки оповещения");
            TreeItem<AlertList> rootItem = new TreeItem<AlertList>(alertListRoot);
            Iterator<AlertList> iterator = employee.getAlertListsInEmployee().iterator();
            while (iterator.hasNext()) {
                AlertList alertList = iterator.next();
                rootItem.getChildren().add(new TreeItem<AlertList>(alertList));
            }
            alertListsTreeView.setRoot(rootItem);
        }
//        System.out.println(employee.getChangesInAlertLists());
    }

    public void setPositions(ObservableList<Position> positionsData) {
        positionComboBox.setItems(positionsData);
    }

    public void setDivisions(ObservableList<Division> divisionsData) {
        divisionComboBox.setItems(divisionsData);
    }

    public boolean isAddedEmployee() {
        return isAdded;
    }

    public Employee getAddedEmployee() {
        return addedEmployee;
    }


    @FXML
    private void handleAddButtonAction() {
        if (StringUtils.isNotBlank(nameTextField.getText())
                && StringUtils.isNotBlank(surnameTextField.getText())
                && (StringUtils.isNotBlank(phoneNumberTextField.getText()) || StringUtils.isNotBlank(cellPhoneNumberTextField.getText()))) {
            if (operationIsAdd) {
//                adding users
                if (isAddSystemUser) {
                    //system user
                    if (StringUtils.isNotBlank(loginTextField.getText())
                            && StringUtils.isNotBlank(passwordTextField.getText())) {
                        addedEmployee = new SystemUser(loginTextField.getText(), passwordTextField.getText());
                        addedEmployee.setName(nameTextField.getText());
                        addedEmployee.setSurname(surnameTextField.getText());
                        addedEmployee.setPatronimic(patronimicTextField.getText());
                        addedEmployee.setPhoneNumber(phoneNumberTextField.getText());
                        addedEmployee.setCellPhoneNumber(cellPhoneNumberTextField.getText());

                        if (positionComboBox.getSelectionModel().getSelectedIndex() != -1) {
                            Position position = (Position) positionComboBox.getSelectionModel().getSelectedItem();
                            addedEmployee.setPosition(position);
                        }
                        if (divisionComboBox.getSelectionModel().getSelectedIndex() != -1) {
                            Division division = (Division) divisionComboBox.getSelectionModel().getSelectedItem();
                            addedEmployee.setDivision(division);
                        }

                        try {
                            JpaApi.beginTransaction();
                            JpaApi.persist(addedEmployee);
                            JpaApi.closeTransaction();
                            isAdded = true;
                            dialogStage.close();
                        }catch (Exception e){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Внимание");
                            alert.setHeaderText("Пользователь с таким логином уже зарегистрирован в системе.");
                            alert.setContentText("Пожалуйста, введите другой логин.");

                            alert.showAndWait();
                            JpaApi.closeTransaction();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Внимание");
                        alert.setHeaderText("Заполните логин и пароль");
                        alert.showAndWait();
                    }
                } else {
//                    employee
                    addedEmployee = new Employee();
                    addedEmployee.setName(nameTextField.getText());
                    addedEmployee.setSurname(surnameTextField.getText());
                    addedEmployee.setPatronimic(patronimicTextField.getText());
                    addedEmployee.setPhoneNumber(phoneNumberTextField.getText());
                    addedEmployee.setCellPhoneNumber(cellPhoneNumberTextField.getText());

                    if (positionComboBox.getSelectionModel().getSelectedIndex() != -1) {
                        Position position = (Position) positionComboBox.getSelectionModel().getSelectedItem();
                        addedEmployee.setPosition(position);
                    }
                    if (divisionComboBox.getSelectionModel().getSelectedIndex() != -1) {
                        Division division = (Division) divisionComboBox.getSelectionModel().getSelectedItem();
                        addedEmployee.setDivision(division);
                        division.getEmployees().add(addedEmployee);
                    }

                    JpaApi.beginTransaction();
                    JpaApi.persist(addedEmployee);
                    JpaApi.closeTransaction();
                    isAdded = true;
                    dialogStage.close();
                }
            } else {
//                editing
                JpaApi.beginTransaction();
                Employee employeeFromDB = (Employee) JpaApi.find(Employee.class, employee.getId());
                employeeFromDB.setName(nameTextField.getText());
                employeeFromDB.setSurname(surnameTextField.getText());
                employeeFromDB.setPatronimic(patronimicTextField.getText());
                employeeFromDB.setPhoneNumber(phoneNumberTextField.getText());
                employeeFromDB.setCellPhoneNumber(cellPhoneNumberTextField.getText());
                if (positionComboBox.getSelectionModel().getSelectedIndex() != -1) {
                    Position position = (Position) positionComboBox.getSelectionModel().getSelectedItem();
                    employeeFromDB.setPosition(position);
                }
                if (divisionComboBox.getSelectionModel().getSelectedIndex() != -1) {
                    Division division = (Division) divisionComboBox.getSelectionModel().getSelectedItem();
                    employeeFromDB.setDivision(division);
                }
                JpaApi.closeTransaction();

                employee.setName(nameTextField.getText());
                employee.setSurname(surnameTextField.getText());
                employee.setPatronimic(patronimicTextField.getText());
                employee.setPhoneNumber(phoneNumberTextField.getText());
                employee.setCellPhoneNumber(cellPhoneNumberTextField.getText());

                if (positionComboBox.getSelectionModel().getSelectedIndex() != -1) {
                    Position position = (Position) positionComboBox.getSelectionModel().getSelectedItem();
                    employee.setPosition(position);
                }
                if (divisionComboBox.getSelectionModel().getSelectedIndex() != -1) {
                    if (oldDivision != null) {
                        oldDivision.getEmployees().remove(employee);
                    }
                    Division division = (Division) divisionComboBox.getSelectionModel().getSelectedItem();
                    division.getEmployees().add(employee);
                    employee.setDivision(division);
                }
                dialogStage.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание");
            alert.setHeaderText("Заполните как минимум фамилию, имя и телефон");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancelButtonAction() {
        dialogStage.close();
    }

}
