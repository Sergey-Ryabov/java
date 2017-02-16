package org.javafx.ais.introductionofchanges.controllers;

import org.javafx.ais.introductionofchanges.MainApp;
import org.javafx.ais.introductionofchanges.model.Department;
import org.javafx.ais.introductionofchanges.model.Division;
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
public class DivisionChangesController {

    private boolean operationIsAdd;
    private boolean isAdded;
    private ObservableList<Division> divisionsData;
    private ObservableList<Employee> employeesData;
    private Set<Employee> selectedEmployeesForAddingSet;
    private Set<Employee> selectedEmployeesForRemovingSet;
    private ObservableList<Employee> selectedEmployeesObservableList;
    private Division division;
    private Division addedDivision;
    private Department oldDepartment;
    private Stage dialogStage;

    @FXML
    private TextField divisionNameTextField;

    @FXML
    private TreeView employeesTreeView;

    @FXML
    private ComboBox departmentComboBox;

    @FXML
    private Button removeEmployeesFromDivisionButton;

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
            removeEmployeesFromDivisionButton.setVisible(false);
        }
    }

    public void setDivision(Division division) {
        this.division = division;

        divisionNameTextField.setText(division.getName());

        if (division.getDepartment() != null) {
            departmentComboBox.getSelectionModel().select(division.getDepartment());
            oldDepartment = (Department) departmentComboBox.getSelectionModel().getSelectedItem();
        }

        if (division.getEmployees() != null && !division.getEmployees().isEmpty()) {
            Employee employeeRoot = new Employee();
            employeeRoot.setName("Сотрудники");
            TreeItem<Employee> rootItem = new TreeItem<Employee>(employeeRoot);
            Iterator<Employee> iterator = division.getEmployees().iterator();
            while (iterator.hasNext()) {
                Employee employee = iterator.next();
                selectedEmployeesForAddingSet.add(employee);
                selectedEmployeesObservableList.add(employee);
                rootItem.getChildren().add(new TreeItem<Employee>(employee));
            }
            employeesTreeView.setRoot(rootItem);
        } else {
            removeEmployeesFromDivisionButton.setVisible(false);
        }
    }

    public void setEmployees(ObservableList<Employee> employeesData) {
        this.employeesData = employeesData;
    }

    public void setDivisions(ObservableList<Division> divisionsData) {
        this.divisionsData = divisionsData;
    }

    public void setDepartments(ObservableList<Department> departmentsData) {
        departmentComboBox.setItems(departmentsData);
    }

    public boolean isAddedDivision() {
        return isAdded;
    }

    public Division getAddedDivision() {
        return addedDivision;
    }

    @FXML
    private void handleAddEmployeeToDivisionButtonAction() throws IOException {
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
        controller.setObservableList(employeesData);
        controller.setFilterCount(3);
        String[] labesNames = {"Фамилия", "Имя", "Отчество"};
        controller.setFiltersLabesNames(labesNames);
        controller.setLabelText("Выберите сотрудников, которых хотите добавить в отдел");

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
                removeEmployeesFromDivisionButton.setVisible(true);
            }
        }
    }

    @FXML
    private void handleRemoveEmployeesFromDivisionButtonAction() throws IOException {
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
        controller.setLabelText("Выберите сотрудников, которых хотите исключить из отдела");

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
                removeEmployeesFromDivisionButton.setVisible(true);
            } else {
                removeEmployeesFromDivisionButton.setVisible(false);
            }
        }
    }

    @FXML
    private void handleAddButtonAction() {
        if (StringUtils.isNotBlank(divisionNameTextField.getText())) {
            if (operationIsAdd) {
//                adding users
//                System.out.println("add operation");
                addedDivision = new Division();
                addedDivision.setName(divisionNameTextField.getText());
                addedDivision.setEmployees(selectedEmployeesForAddingSet);
                if (departmentComboBox.getSelectionModel().getSelectedIndex() != -1) {
                    Department department = (Department) departmentComboBox.getSelectionModel().getSelectedItem();
                    addedDivision.setDepartment(department);
                    department.getDivisions().add(addedDivision);
                }

                JpaApi.beginTransaction();
                JpaApi.persist(addedDivision);
                if (selectedEmployeesForAddingSet != null && !selectedEmployeesForAddingSet.isEmpty()) {
                    transferEmployeesInNewDivision(addedDivision);
                }
                JpaApi.closeTransaction();
                isAdded = true;
                dialogStage.close();
            } else {
//                editing
//                System.out.println("edit operation");

                JpaApi.beginTransaction();
                Division divisionFromDB = (Division) JpaApi.find(Division.class, division.getId());
                divisionFromDB.setName(divisionNameTextField.getText());
                divisionFromDB.setEmployees(selectedEmployeesForAddingSet);
                if (departmentComboBox.getSelectionModel().getSelectedIndex() != -1) {
                    Department department = (Department) departmentComboBox.getSelectionModel().getSelectedItem();
                    divisionFromDB.setDepartment(department);
                }
                if (selectedEmployeesForAddingSet != null && !selectedEmployeesForAddingSet.isEmpty()) {
                    transferEmployeesInNewDivision(division);
                }
                if (selectedEmployeesForRemovingSet != null && !selectedEmployeesForRemovingSet.isEmpty()) {
                    removeEmployeeFromDivision(division);
                }
                JpaApi.closeTransaction();

                division.setName(divisionNameTextField.getText());
                division.setEmployees(selectedEmployeesForAddingSet);
                if (departmentComboBox.getSelectionModel().getSelectedIndex() != -1) {
                    if (oldDepartment != null) {
                        oldDepartment.getDivisions().remove(division);
                    }
                    Department department = (Department) departmentComboBox.getSelectionModel().getSelectedItem();
                    department.getDivisions().add(division);
                    division.setDepartment(department);
                }
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

    private void transferEmployeesInNewDivision(Division newDivision) {
        Iterator<Employee> iterator = selectedEmployeesForAddingSet.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getDivision() != null) {
                long oldDivisionId = employee.getDivision().getId();
                if (oldDivisionId != newDivision.getId()) {
                    for (int i = 0; i < divisionsData.size(); i++) {
                        if (oldDivisionId == divisionsData.get(i).getId()) {
                            divisionsData.get(i).getEmployees().remove(employee);
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < employeesData.size(); i++) {
                        if (employeesData.get(i).getDivision() != null
                                && oldDivisionId == employeesData.get(i).getDivision().getId()) {
                            employeesData.get(i).setDivision(newDivision);
                        }
                    }
                }
            }
            employee.setDivision(newDivision);
            ((Employee) JpaApi.find(Employee.class, employee.getId())).setDivision(newDivision);
        }
    }

    private void removeEmployeeFromDivision(Division division) {
        Iterator<Employee> iterator = selectedEmployeesForRemovingSet.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            division.getEmployees().remove(employee);
            employee.setDivision(null);
            ((Employee) JpaApi.find(Employee.class, employee.getId())).setDivision(null);
        }
    }
}
