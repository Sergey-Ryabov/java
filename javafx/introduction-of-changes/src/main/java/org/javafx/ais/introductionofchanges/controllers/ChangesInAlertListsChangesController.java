package org.javafx.ais.introductionofchanges.controllers;

import org.javafx.ais.introductionofchanges.MainApp;
import org.javafx.ais.introductionofchanges.model.AlertList;
import org.javafx.ais.introductionofchanges.model.ChangesInAlertLists;
import org.javafx.ais.introductionofchanges.model.Employee;
import org.javafx.ais.introductionofchanges.utils.JpaApi;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;

/**
 *
 * @author Сергей
 */
public class ChangesInAlertListsChangesController {

    private Employee employeeOnVacation;
    private Employee employeeActing;
    private ObservableList<Employee> employeesData;
    private ObservableList<Employee> employeesDataOnVacation;
    private ObservableList<Employee> employeesDataActing;
    private boolean operationIsAdd;
    private boolean isAdded;
    private ChangesInAlertLists changesInAlertLists;
    private ChangesInAlertLists addedChangesInAlertLists;
    private Stage dialogStage;

    @FXML
    private TextField employeeOnVacationTextField;
    @FXML
    private TextField employeeActingTextField;
    @FXML
    private DatePicker beginDatePicker;
    @FXML
    private TextField beginHoursTextField;
    @FXML
    private TextField beginMinutsTextField;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField endHoursTextField;
    @FXML
    private TextField endMinutsTextField;
    @FXML
    private TextField groundsTextField;
    @FXML
    private TreeView alertListsTreeView;
    @FXML
    private Button addButton;

    @FXML
    private void initialize() {
        beginDatePicker.setEditable(false);
        endDatePicker.setEditable(false);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setMinWidth(800);
        this.dialogStage.setMinHeight(600);
    }

    public void setChangesInAlertLists(ChangesInAlertLists changesInAlertLists) {
        this.changesInAlertLists = changesInAlertLists;

        employeeOnVacation = changesInAlertLists.getEmployeeOnVacation();
        employeeOnVacationTextField.setText(changesInAlertLists.getEmployeeOnVacation().toString());
        employeeActing = changesInAlertLists.getEmployeeActing();
        employeeActingTextField.setText(changesInAlertLists.getEmployeeActing().toString());
        beginDatePicker.setValue(changesInAlertLists.getStartDateTime().toLocalDate());
        beginHoursTextField.setText(String.valueOf(changesInAlertLists.getStartDateTime().getHour()));
        beginMinutsTextField.setText(String.valueOf(changesInAlertLists.getStartDateTime().getMinute()));
        endDatePicker.setValue(changesInAlertLists.getEndDateTime().toLocalDate());
        endHoursTextField.setText(String.valueOf(changesInAlertLists.getEndDateTime().getHour()));
        endMinutsTextField.setText(String.valueOf(changesInAlertLists.getEndDateTime().getMinute()));
        groundsTextField.setText(changesInAlertLists.getGrounds());


        if (!changesInAlertLists.getEmployeeActing().getAlertListsInEmployee().isEmpty()) {
            AlertList alertListRoot = new AlertList();
            alertListRoot.setName("Списки оповещения");
            TreeItem<AlertList> rootItem = new TreeItem<AlertList>(alertListRoot);
            Iterator<AlertList> iterator = changesInAlertLists.getEmployeeActing().getAlertListsInEmployee().iterator();
            while (iterator.hasNext()) {
                AlertList alertList = iterator.next();
                rootItem.getChildren().add(new TreeItem<AlertList>(alertList));
            }
            alertListsTreeView.setRoot(rootItem);
        }

    }

    public void setEmployees(ObservableList<Employee> employeesData) {
        this.employeesData = employeesData;
    }

    public void setEmployeesOnVacation(ObservableList<Employee> employeesDataOnVacation) {
        this.employeesDataOnVacation = employeesDataOnVacation;
    }

    public void setEmployeesActing(ObservableList<Employee> employeesDataActing) {
        this.employeesDataActing = employeesDataActing;
    }

    public void setOperationIsAdd(boolean operation) {
        this.operationIsAdd = operation;
        if (!operation) {
            addButton.setText("Применить изменения");
            beginDatePicker.setOnShown(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    beginDatePicker.hide();
                }
            });
            beginHoursTextField.setEditable(false);
            beginMinutsTextField.setEditable(false);
            endDatePicker.setOnShown(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    endDatePicker.hide();
                }
            });
            endHoursTextField.setEditable(false);
            endMinutsTextField.setEditable(false);
        } else {
//            removeEmployeesFromDivisionButton.setVisible(false);
        }
    }

    public boolean isAddedChangesInAlertLists() {
        return isAdded;
    }

    public ChangesInAlertLists getAddedChangesInAlertLists() {
        return addedChangesInAlertLists;
    }

    @FXML
    private void handleChooseEmployeeOnVacationButtonAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Records_To_Model_Scene.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Выбрать сотрудника в отпуске");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(new Stage());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        AddRecordToModelController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setSelectionMode(SelectionMode.SINGLE);
        controller.setObservableList(employeesData);
        controller.setFilterCount(3);
        String[] labesNames = {"Фамилия", "Имя", "Отчество"};
        controller.setFiltersLabesNames(labesNames);
        controller.setLabelText("Выберите сотрудников, которых хотите добавить в отдел");

        dialogStage.showAndWait();
        if (controller.isOkPressed() && !controller.getSelectedData().isEmpty()) {
            employeeOnVacation = (Employee) controller.getSelectedData().get(0);
            employeeOnVacationTextField.setText(employeeOnVacation.toString());
            if (!employeeOnVacation.getAlertListsInEmployee().isEmpty()) {
                AlertList alertListRoot = new AlertList();
                alertListRoot.setName("Списки оповещения");
                TreeItem<AlertList> rootItem = new TreeItem<AlertList>(alertListRoot);
                Iterator<AlertList> iterator = employeeOnVacation.getAlertListsInEmployee().iterator();
                while (iterator.hasNext()) {
                    AlertList alertList = iterator.next();
                    rootItem.getChildren().add(new TreeItem<AlertList>(alertList));
                }
                alertListsTreeView.setRoot(rootItem);
            }
        }
    }

    @FXML
    private void handleChooseEmployeeActingButtonAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Records_To_Model_Scene.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Выбрать сотрудника испольняющего обязанности");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(new Stage());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        AddRecordToModelController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setSelectionMode(SelectionMode.SINGLE);
        controller.setObservableList(employeesData);
        controller.setFilterCount(3);
        String[] labesNames = {"Фамилия", "Имя", "Отчество"};
        controller.setFiltersLabesNames(labesNames);
        controller.setLabelText("Выберите сотрудников, которых хотите добавить в отдел");

        dialogStage.showAndWait();
        if (controller.isOkPressed() && !controller.getSelectedData().isEmpty()) {
            employeeActing = (Employee) controller.getSelectedData().get(0);
            employeeActingTextField.setText(employeeActing.toString());
//            selectedEmployeesForAddingSet = new HashSet<Employee>();
        }
    }

    @FXML
    private void handleAddButtonAction() throws IOException {
//        System.out.println("handleAddButtonAction");
        if (StringUtils.isNotBlank(employeeOnVacationTextField.getText())
                && beginDatePicker.getValue() != null
                && StringUtils.isNotBlank(beginHoursTextField.getText())
                && beginHoursTextField.getText().length() < 3
                && StringUtils.containsOnly(beginHoursTextField.getText(), "0123456789")
                && StringUtils.isNotBlank(beginMinutsTextField.getText())
                && beginMinutsTextField.getText().length() < 3
                && StringUtils.containsOnly(beginMinutsTextField.getText(), "0123456789")
                && endDatePicker.getValue() != null
                && StringUtils.isNotBlank(endHoursTextField.getText())
                && endHoursTextField.getText().length() < 3
                && StringUtils.containsOnly(endHoursTextField.getText(), "0123456789")
                && StringUtils.isNotBlank(endMinutsTextField.getText())
                && endMinutsTextField.getText().length() < 3
                && StringUtils.containsOnly(endMinutsTextField.getText(), "0123456789")
                && StringUtils.isNotBlank(employeeActingTextField.getText())
                && StringUtils.isNotBlank(groundsTextField.getText())) {
            if (operationIsAdd) {
//                adding users
//                System.out.println("add operation");
//                System.out.println(employeeActing.getChangesInAlertLists());
                JpaApi.beginTransaction();
                employeeOnVacation.setIsOnVacation(true);
                employeesDataOnVacation.add(employeeOnVacation);
                employeesDataActing.remove(employeeOnVacation);
                addedChangesInAlertLists = new ChangesInAlertLists();
                addedChangesInAlertLists.setEmployeeOnVacation(employeeOnVacation);
                addedChangesInAlertLists.setEmployeeActing(employeeActing);

                addedChangesInAlertLists.setStartDateTime(LocalDateTime.of(beginDatePicker.getValue(),
                        LocalTime.of(Integer.parseInt(beginHoursTextField.getText()), Integer.parseInt(beginMinutsTextField.getText()))));
                addedChangesInAlertLists.setEndDateTime(LocalDateTime.of(endDatePicker.getValue(),
                        LocalTime.of(Integer.parseInt(endHoursTextField.getText()), Integer.parseInt(endMinutsTextField.getText()))));
                addedChangesInAlertLists.setGrounds(groundsTextField.getText());

                JpaApi.persist(addedChangesInAlertLists);
                employeeOnVacation.setChangesInAlertLists(addedChangesInAlertLists);
                Employee employeeOnVacationFromDB = (Employee) JpaApi.find(Employee.class, employeeOnVacation.getId());
                employeeOnVacationFromDB.setIsOnVacation(true);
                employeeOnVacationFromDB.setChangesInAlertLists(addedChangesInAlertLists);
                JpaApi.closeTransaction();
                isAdded = true;
                dialogStage.close();
            } else {
//                System.out.println("edit operation");
                JpaApi.beginTransaction();
                ChangesInAlertLists changesInAlertListsDB
                        = (ChangesInAlertLists) JpaApi.find(ChangesInAlertLists.class, changesInAlertLists.getId());
                if (!changesInAlertListsDB.getEmployeeOnVacation().equals(employeeOnVacation)) {
//                    System.out.println("return employee 1 ");
//вернули из отпуска
                    Employee oldEmployeeOnVacationFromDB = (Employee) JpaApi.find(Employee.class, changesInAlertLists.getEmployeeOnVacation().getId());
                    oldEmployeeOnVacationFromDB.setIsOnVacation(false);
                    oldEmployeeOnVacationFromDB.setChangesInAlertLists(null);
//отправили в отпуск
                    Employee newEmployeeOnVacationFromDB = (Employee) JpaApi.find(Employee.class, employeeOnVacation.getId());
                    newEmployeeOnVacationFromDB.setIsOnVacation(true);
                    newEmployeeOnVacationFromDB.setChangesInAlertLists(changesInAlertListsDB);
                    changesInAlertListsDB.setEmployeeOnVacation(newEmployeeOnVacationFromDB);
                }
                if (!changesInAlertListsDB.getEmployeeActing().equals(employeeActing)) {
//                    ставим другого исполняющего обязанности
//                    System.out.println("return employee 2s ");
                    Employee newEmployeeActing = (Employee) JpaApi.find(Employee.class, employeeActing.getId());
                    changesInAlertListsDB.setEmployeeActing(newEmployeeActing);
                }

                changesInAlertListsDB.setStartDateTime(LocalDateTime.of(beginDatePicker.getValue(),
                        LocalTime.of(Integer.parseInt(beginHoursTextField.getText()), Integer.parseInt(beginMinutsTextField.getText()))));
                changesInAlertListsDB.setEndDateTime(LocalDateTime.of(endDatePicker.getValue(),
                        LocalTime.of(Integer.parseInt(endHoursTextField.getText()), Integer.parseInt(endMinutsTextField.getText()))));
                changesInAlertListsDB.setGrounds(groundsTextField.getText());
                JpaApi.persist(changesInAlertListsDB);
                JpaApi.closeTransaction();

                if (!changesInAlertLists.getEmployeeOnVacation().equals(employeeOnVacation)) {
                    //                    возвращаем из отпуска
                    Employee oldEmployeeOnVacation = null;
                    for (int i = 0; i < employeesDataOnVacation.size(); i++) {
                        if (employeesDataOnVacation.get(i).equals(changesInAlertLists.getEmployeeOnVacation())) {
                            oldEmployeeOnVacation = employeesDataOnVacation.get(i);
                        }
                    }
                    oldEmployeeOnVacation.setIsOnVacation(false);
                    oldEmployeeOnVacation.setChangesInAlertLists(null);
                    employeesDataOnVacation.remove(oldEmployeeOnVacation);
                    employeesDataActing.add(oldEmployeeOnVacation);

//отправили в отпуск нового
                    employeeOnVacation.setIsOnVacation(true);
                    employeesDataActing.remove(employeeOnVacation);
                    employeesDataOnVacation.add(employeeOnVacation);
                    changesInAlertLists.setEmployeeOnVacation(employeeOnVacation);
                    employeeOnVacation.setChangesInAlertLists(changesInAlertLists);
                }
                if (!changesInAlertLists.getEmployeeActing().equals(employeeActing)) {
//                    ставим другого исполняющего обязанности
                    changesInAlertLists.setEmployeeActing(employeeActing);
                }

                changesInAlertLists.setStartDateTime(LocalDateTime.of(beginDatePicker.getValue(),
                        LocalTime.of(Integer.parseInt(beginHoursTextField.getText()), Integer.parseInt(beginMinutsTextField.getText()))));
                changesInAlertLists.setEndDateTime(LocalDateTime.of(endDatePicker.getValue(),
                        LocalTime.of(Integer.parseInt(endHoursTextField.getText()), Integer.parseInt(endMinutsTextField.getText()))));
                changesInAlertLists.setGrounds(groundsTextField.getText());

                dialogStage.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание");
            alert.setHeaderText("Заполните корректно все поля.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancelButtonAction() throws IOException {
        dialogStage.close();
    }
}
