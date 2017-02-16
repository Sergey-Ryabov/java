package org.javafx.ais.introductionofchanges.controllers;

import org.javafx.ais.introductionofchanges.MainApp;
import org.javafx.ais.introductionofchanges.model.Department;
import org.javafx.ais.introductionofchanges.model.Division;
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
public class DepartmentChangesController {

    private boolean operationIsAdd;
    private boolean isAdded;
    private ObservableList<Division> divisionsData;
    private ObservableList<Department> departmentsData;
    private Department department;
    private Department addedDepartment;
    private Set<Division> selectedDivisionsForAddingSet;
    private Set<Division> selectedDivisionsForRemovingSet;
    private ObservableList<Division> selectedDivisionsObservableList;
    private Stage dialogStage;

    @FXML
    private TextField departmentNameTextField;

    @FXML
    private TreeView divisionsTreeView;

    @FXML
    private Button removeDivisionsFromDepartmentsButton;

    @FXML
    private Button addButton;

    @FXML
    private void initialize() {
        selectedDivisionsForAddingSet = new HashSet<Division>();
        selectedDivisionsObservableList = FXCollections.observableArrayList();
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
            removeDivisionsFromDepartmentsButton.setVisible(false);
        }
    }

    public void setDepartment(Department department) {
        this.department = department;

        departmentNameTextField.setText(department.getName());

        if (department.getDivisions() != null && !department.getDivisions().isEmpty()) {
            Division divisionRoot = new Division();
            divisionRoot.setName("Отделы");
            TreeItem<Division> rootItem = new TreeItem<Division>(divisionRoot);
            Iterator<Division> iterator = department.getDivisions().iterator();
            while (iterator.hasNext()) {
                Division division = iterator.next();
                selectedDivisionsForAddingSet.add(division);
                selectedDivisionsObservableList.add(division);
                rootItem.getChildren().add(new TreeItem<Division>(division));
            }
            divisionsTreeView.setRoot(rootItem);
        } else {
            removeDivisionsFromDepartmentsButton.setVisible(false);
        }

    }

    public void setDivisions(ObservableList<Division> divisionsData) {
        this.divisionsData = divisionsData;
    }

    public void setDepartments(ObservableList<Department> departmentsData) {
        this.departmentsData = departmentsData;
    }

    public boolean isAddedDepartment() {
        return isAdded;
    }

    public Department getAddedDepartment() {
        return addedDepartment;
    }

    @FXML
    private void handleAddDivisionsToDepartmentsButtonAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Records_To_Model_Scene.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Выбрать отделы");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(new Stage());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        AddRecordToModelController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setObservableList(divisionsData);
        controller.setFilterCount(1);
        String[] labesNames = {"Название отдела"};
        controller.setFiltersLabesNames(labesNames);
        controller.setLabelText("Выберите отделы, которые хотите добавить в департамент");

        dialogStage.showAndWait();
        if (controller.isOkPressed() && !controller.getSelectedData().isEmpty()) {
//            selectedDivisionsForAddingSet = new HashSet<Division>();
            ObservableList selectedDivisionsObservableList = controller.getSelectedData();
            selectedDivisionsForAddingSet.addAll(selectedDivisionsObservableList);
            
             Division divisionRoot = new Division();
            divisionRoot.setName("Отделы");
            Iterator<Division> iterator = selectedDivisionsForAddingSet.iterator();
            TreeItem<Division> rootItem = new TreeItem<Division>(divisionRoot);
            while (iterator.hasNext()) {
                Division division = iterator.next();
                rootItem.getChildren().add(new TreeItem<Division>(division));
            }

            divisionsTreeView.setRoot(rootItem);
            if (!operationIsAdd) {
                removeDivisionsFromDepartmentsButton.setVisible(true);
            }

        }
    }

    @FXML
    private void handleRemoveDivisionsFromDepartmentsButtonAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Records_To_Model_Scene.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Выбрать отделы");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(new Stage());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        AddRecordToModelController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setObservableList(selectedDivisionsObservableList);
        controller.setFilterCount(1);
        String[] labesNames = {"Название отдела"};
        controller.setFiltersLabesNames(labesNames);
        controller.setLabelText("Выберите отделы, которые хотите исключить из департамента");

        dialogStage.showAndWait();
        if (controller.isOkPressed() && !controller.getSelectedData().isEmpty()) {
            selectedDivisionsForRemovingSet = new HashSet<Division>();
            for (int i = 0; i < controller.getSelectedData().size(); i++) {
                Division division = (Division) controller.getSelectedData().get(i);
                selectedDivisionsForAddingSet.remove(division);
                selectedDivisionsObservableList.remove(division);
                selectedDivisionsForRemovingSet.add(division);
            }
            Division divisionRoot = new Division();
            divisionRoot.setName("Отделы");
            TreeItem<Division> rootItem = new TreeItem<Division>(divisionRoot);
            for (int i = 0; i < selectedDivisionsObservableList.size(); i++) {
                Division division = (Division) selectedDivisionsObservableList.get(i);
                selectedDivisionsForAddingSet.add(division);
                rootItem.getChildren().add(new TreeItem<Division>(division));
            }
            divisionsTreeView.setRoot(rootItem);
            if (selectedDivisionsForAddingSet.size() > 0) {
                removeDivisionsFromDepartmentsButton.setVisible(true);
            } else {
                removeDivisionsFromDepartmentsButton.setVisible(false);
            }
        }
    }

    @FXML
    private void handleAddButtonAction() {
        if (StringUtils.isNotBlank(departmentNameTextField.getText())) {
            if (operationIsAdd) {
//                adding users
                addedDepartment = new Department();
                addedDepartment.setName(departmentNameTextField.getText());
                addedDepartment.setDivisions(selectedDivisionsForAddingSet);

                JpaApi.beginTransaction();
                JpaApi.persist(addedDepartment);
                if (selectedDivisionsForAddingSet != null && !selectedDivisionsForAddingSet.isEmpty()) {
                    transferDivisionInNewDepartment(addedDepartment);
                }
                JpaApi.closeTransaction();
                isAdded = true;
                dialogStage.close();
            } else {
//                editing
                JpaApi.beginTransaction();
                Department departmentFromDB = (Department) JpaApi.find(Department.class, department.getId());
                departmentFromDB.setName(departmentNameTextField.getText());
                department.setDivisions(selectedDivisionsForAddingSet);
                if (selectedDivisionsForAddingSet != null && !selectedDivisionsForAddingSet.isEmpty()) {
                    transferDivisionInNewDepartment(department);
                }
                if (selectedDivisionsForRemovingSet != null && !selectedDivisionsForRemovingSet.isEmpty()) {
                    removeDivisionFromDepartment(department);
                }
                JpaApi.closeTransaction();
                department.setName(departmentNameTextField.getText());
                department.setDivisions(selectedDivisionsForAddingSet);
                dialogStage.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание");
            alert.setHeaderText("Заполните как минимум название департамента.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancelButtonAction() {
        dialogStage.close();
    }

    private void transferDivisionInNewDepartment(Department newDepartment) {
        Iterator<Division> iterator = selectedDivisionsForAddingSet.iterator();
        while (iterator.hasNext()) {
            Division division = iterator.next();
            if (division.getDepartment() != null) {
                long oldDepartmentId = division.getDepartment().getId();
                if (oldDepartmentId != newDepartment.getId()) {
                    for (int i = 0; i < departmentsData.size(); i++) {
                        if (oldDepartmentId == departmentsData.get(i).getId()) {
                            departmentsData.get(i).getDivisions().remove(division);
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < divisionsData.size(); i++) {
                        if (divisionsData.get(i).getDepartment() != null
                                && oldDepartmentId == divisionsData.get(i).getDepartment().getId()) {
                            divisionsData.get(i).setDepartment(newDepartment);
                        }
                    }
                }
            }
            division.setDepartment(newDepartment);
            ((Division) JpaApi.find(Division.class, division.getId())).setDepartment(newDepartment);
        }
    }

    private void removeDivisionFromDepartment(Department department) {
        Iterator<Division> iterator = selectedDivisionsForRemovingSet.iterator();
        while (iterator.hasNext()) {
            Division division = iterator.next();
            department.getDivisions().remove(division);
            division.setDepartment(null);
            ((Division) JpaApi.find(Division.class, division.getId())).setDepartment(null);
        }
    }
}
