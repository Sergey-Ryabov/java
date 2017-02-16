package org.javafx.ais.introductionofchanges.controllers;

import org.javafx.ais.introductionofchanges.MainApp;
import org.javafx.ais.introductionofchanges.model.AlertList;
import org.javafx.ais.introductionofchanges.model.Employee;
import org.javafx.ais.introductionofchanges.utils.JpaApi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Сергей
 */
public class AlertListChangesController {

    private boolean operationIsAdd;
    private boolean isAdded;
    private ObservableList<Employee> employeesData;
    private ObservableList<Employee> employeesDataActing;
    private Set<Employee> selectedEmployeesForAddingSet;
    private Set<Employee> selectedEmployeesForRemovingSet;
    private ObservableList<Employee> selectedEmployeesObservableList;
    private AlertList alertList;
    private AlertList addedAlertList;
    private Stage dialogStage;

    @FXML
    private TextField alertListNameTextField;
    @FXML
    private TreeView employeesTreeView;
    @FXML
    private Button removeEmployeesFromAlertListButton;
    @FXML
    private Button addButton;

    @FXML
    private void initialize() {
        selectedEmployeesForAddingSet = new HashSet<Employee>();
        selectedEmployeesObservableList = FXCollections.observableArrayList();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setMinWidth(500);
        this.dialogStage.setMinHeight(400);
    }

    public void setOperationIsAdd(boolean operation) {
        this.operationIsAdd = operation;
        if (!operation) {
            addButton.setText("Применить изменения");
        } else {
            removeEmployeesFromAlertListButton.setVisible(false);
        }
    }

    public void setAlertList(AlertList alertList) {
        this.alertList = alertList;

        alertListNameTextField.setText(alertList.getName());

        if (alertList.getEmployees() != null && !alertList.getEmployees().isEmpty()) {
            Employee employeeRoot = new Employee();
            employeeRoot.setName("Сотрудники");
            TreeItem<Employee> rootItem = new TreeItem<Employee>(employeeRoot);
            Iterator<Employee> iterator = alertList.getEmployees().iterator();
            while (iterator.hasNext()) {
                Employee employee = iterator.next();
                if (!employee.isOnVacation()) {
                    selectedEmployeesForAddingSet.add(employee);
                    selectedEmployeesObservableList.add(employee);

                    if (rootItem.getChildren().isEmpty()) {
                        rootItem.getChildren().add(new TreeItem<Employee>(employee));
                    } else if (!isTreeItemContainEmployee(rootItem, employee)) {
                        rootItem.getChildren().add(new TreeItem<Employee>(employee));
                    }
                } else if (employee.getChangesInAlertLists() != null) {
                    if (rootItem.getChildren().isEmpty()) {
                        rootItem.getChildren().add(new TreeItem<Employee>(employee.getChangesInAlertLists().getEmployeeActing()));
                    } else if (!isTreeItemContainEmployee(rootItem, employee.getChangesInAlertLists().getEmployeeActing())) {
                        rootItem.getChildren().add(new TreeItem<Employee>(employee.getChangesInAlertLists().getEmployeeActing()));
                    }
                } else {
//                        System.out.println("dddddddddddddddddddd");
                }
            }
            employeesTreeView.setRoot(rootItem);
        } else {
            removeEmployeesFromAlertListButton.setVisible(false);
        }
    }

    public void setEmployees(ObservableList<Employee> employeesData) {
        this.employeesData = employeesData;
    }

    public void setEmployeesActing(ObservableList<Employee> employeesDataActing) {
        this.employeesDataActing = employeesDataActing;
    }

    public boolean isAddedAlertList() {
        return isAdded;
    }

    public AlertList getAddedAlertList() {
        return addedAlertList;
    }

    @FXML
    private void handleAddEmployeeToAlertListButtonAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Records_To_Model_Scene.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Выбрать сотрудников");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(new Stage());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        AddRecordToModelController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setObservableList(employeesDataActing);
        controller.setFilterCount(3);
        String[] labesNames = {"Фамилия", "Имя", "Отчество"};
        controller.setFiltersLabesNames(labesNames);
        controller.setLabelText("Выберите сотрудников, которых хотите добавить в список оповещения");

        dialogStage.showAndWait();
        if (controller.isOkPressed() && !controller.getSelectedData().isEmpty()) {
            ObservableList selectedEmployeesObservableList = controller.getSelectedData();
            selectedEmployeesForAddingSet.addAll(selectedEmployeesObservableList);

            Employee employeeRoot = new Employee();
            employeeRoot.setName("Сотрудники");
            Iterator<Employee> iterator = selectedEmployeesForAddingSet.iterator();
            TreeItem<Employee> rootItem = new TreeItem<Employee>(employeeRoot);
            while (iterator.hasNext()) {
                Employee employee = iterator.next();
                rootItem.getChildren().add(new TreeItem<Employee>(employee));
            }
            employeesTreeView.setRoot(rootItem);
            if (!operationIsAdd) {
                removeEmployeesFromAlertListButton.setVisible(true);
            }
        }
    }

    @FXML
    private void handleRemoveEmployeesFromAlertListButtonAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Records_To_Model_Scene.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Выбрать сотрудников");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(new Stage());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        AddRecordToModelController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setObservableList(selectedEmployeesObservableList);
        controller.setFilterCount(3);
        String[] labesNames = {"Фамилия", "Имя", "Отчество"};
        controller.setFiltersLabesNames(labesNames);
        controller.setLabelText("Выберите сотрудников, которых хотите исключить из списка оповещения");

        dialogStage.showAndWait();
        if (controller.isOkPressed() && !controller.getSelectedData().isEmpty()) {
            selectedEmployeesForRemovingSet = new HashSet<Employee>();
            for (int i = 0; i < controller.getSelectedData().size(); i++) {
                Employee employee = (Employee) controller.getSelectedData().get(i);
                selectedEmployeesForAddingSet.remove(employee);
                selectedEmployeesObservableList.remove(employee);
                selectedEmployeesForRemovingSet.add(employee);
            }
            Employee employeeRoot = new Employee();
            employeeRoot.setName("Сотрудники");
            TreeItem<Employee> rootItem = new TreeItem<Employee>(employeeRoot);
            for (int i = 0; i < selectedEmployeesObservableList.size(); i++) {
                Employee employee = (Employee) selectedEmployeesObservableList.get(i);
                selectedEmployeesForAddingSet.add(employee);
                rootItem.getChildren().add(new TreeItem<Employee>(employee));
            }
            employeesTreeView.setRoot(rootItem);
            if (selectedEmployeesForAddingSet.size() > 0) {
                removeEmployeesFromAlertListButton.setVisible(true);
            } else {
                removeEmployeesFromAlertListButton.setVisible(false);
            }
        }
    }

    @FXML
    private void handleAddButtonAction() {
        if (StringUtils.isNotBlank(alertListNameTextField.getText())) {
            if (operationIsAdd) {
//                adding users
//                System.out.println("add operation");
                addedAlertList = new AlertList();
                addedAlertList.setName(alertListNameTextField.getText());
                addedAlertList.setEmployees(selectedEmployeesForAddingSet);
                JpaApi.beginTransaction();
                JpaApi.persist(addedAlertList);
                if (selectedEmployeesForAddingSet != null && !selectedEmployeesForAddingSet.isEmpty()) {
                    addEmployeesInNewAlertList(addedAlertList);
                }
                JpaApi.closeTransaction();
                isAdded = true;
                dialogStage.close();
            } else {
//                editing
//                System.out.println("edit operation");

                JpaApi.beginTransaction();
                AlertList alertListFromDB = (AlertList) JpaApi.find(AlertList.class, alertList.getId());
                alertListFromDB.setName(alertListNameTextField.getText());
                alertListFromDB.setEmployees(selectedEmployeesForAddingSet);
                if (selectedEmployeesForAddingSet != null && !selectedEmployeesForAddingSet.isEmpty()) {
                    addEmployeesInNewAlertList(alertList);
                }
                if (selectedEmployeesForRemovingSet != null && !selectedEmployeesForRemovingSet.isEmpty()) {
                    removeEmployeeFromAlertList(alertList);
                }
                JpaApi.closeTransaction();

                alertList.setName(alertListNameTextField.getText());
                alertList.setEmployees(selectedEmployeesForAddingSet);
                dialogStage.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание");
            alert.setHeaderText("Заполните как минимум название отдела.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancelButtonAction() {
        dialogStage.close();
    }

    private void addEmployeesInNewAlertList(AlertList addedAlertList) {
        Iterator<Employee> iterator = selectedEmployeesForAddingSet.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            employee.getAlertListsInEmployee().add(addedAlertList);
            ((Employee) JpaApi.find(Employee.class, employee.getId())).getAlertListsInEmployee().add(addedAlertList);
        }
    }

    private void removeEmployeeFromAlertList(AlertList alertList) {
        Iterator<Employee> iterator = selectedEmployeesForRemovingSet.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            employee.getAlertListsInEmployee().remove(alertList);
            ((Employee) JpaApi.find(Employee.class, employee.getId())).getAlertListsInEmployee().remove(alertList);
            alertList.getEmployees().remove(employee);
        }
    }

    private boolean isTreeItemContainEmployee(TreeItem<Employee> rootItem, Employee employee) {
        for (int i = 0; i < rootItem.getChildren().size(); i++) {
            if (rootItem.getChildren().get(i).getValue().equals(employee)) {
                return true;
            }
        }
        return false;
    }

}
