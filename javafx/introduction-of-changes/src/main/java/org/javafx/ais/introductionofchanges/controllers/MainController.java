package org.javafx.ais.introductionofchanges.controllers;

import org.javafx.ais.introductionofchanges.model.Position;
import org.javafx.ais.introductionofchanges.model.ChangesInAlertLists;
import org.javafx.ais.introductionofchanges.model.AlertList;
import org.javafx.ais.introductionofchanges.model.Employee;
import org.javafx.ais.introductionofchanges.model.BrowsingItem;
import org.javafx.ais.introductionofchanges.model.ChangeItemInChangesInAlertLists;
import org.javafx.ais.introductionofchanges.model.SystemUser;
import org.javafx.ais.introductionofchanges.model.Division;
import org.javafx.ais.introductionofchanges.model.Department;
import org.javafx.ais.introductionofchanges.MainApp;
import org.javafx.ais.introductionofchanges.notification.NotificationTask;
import org.javafx.ais.introductionofchanges.utils.Constants;
import org.javafx.ais.introductionofchanges.utils.JpaApi;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainController implements Initializable {

    //notification
    private List<NotificationTask> notificationTaskList = new LinkedList<>();
    private ScheduledExecutorService scheduledExecutorService;

    //data
    private SystemUser systemUser;
    private ObservableList<Position> positionsData = FXCollections.observableArrayList();
    private ObservableList<Employee> employeesData = FXCollections.observableArrayList();
    private ObservableList<Employee> employeesDataOnVacation = FXCollections.observableArrayList();
    private ObservableList<Employee> employeesDataActing = FXCollections.observableArrayList();
    private ObservableList<Division> divisionsData = FXCollections.observableArrayList();
    private ObservableList<Department> departmentsData = FXCollections.observableArrayList();
    private ObservableList<AlertList> alertListsData = FXCollections.observableArrayList();
    private ObservableList<ChangesInAlertLists> changesInAlertListsData = FXCollections.observableArrayList();
    private ObservableList<BrowsingItem> browsingItemsData = FXCollections.observableArrayList();
    private ObservableList<ChangeItemInChangesInAlertLists> changeItemInChangesInAlertListsData = FXCollections.observableArrayList();

    //fields
    //    menu action handlers
    @FXML
    private MenuItem changeUserMenuItem;

    //    tabs action handlers
//    tab1
    @FXML
    private TableView changesTable;
    @FXML
    private TableColumn changesIdTableColumn;
    @FXML
    private TableColumn changesEmployeeOnVacationTableColumn;
    @FXML
    private TableColumn changesEmployeeActingTableColumn;
    @FXML
    private TableColumn changesStartDateTableColumn;
    @FXML
    private TableColumn changesEndDateTableColumn;
    @FXML
    private TableColumn changesGroundsTableColumn;

    //    tab2
    @FXML
    private TableView alertListsTable;
    @FXML
    private TableColumn alertListIdTableColumn;
    @FXML
    private TableColumn alertListNameTableColumn;

    //    tab3
    @FXML
    private ChoiceBox journalsChoiceBox;
    @FXML
    private ScrollPane journalsScrollPane;
    private VBox journalFirstLayout;
    private VBox journalSecondLayout;
    private TableView journalFirstTableView;
    private TableView journalSecondTableView;

    //    tab4
//    @FXML
//    private TableView archiveChangesTable;
    //    tab5
    @FXML
    private Tab catalogsTab;
    @FXML
    private ChoiceBox catalogChoiceBox;
    @FXML
    private ScrollPane catalogScrollPane;
    private VBox catalogFirstLayout;
    private TableView catalogFirstTableView;
    private TableColumn positionIdTableColumn;
    private TableColumn positionNameTableColumn;
    private VBox catalogSecondLayout;
    private TableView catalogSecondTableView;
    private TableColumn employeeIdTableColumn;
    private TableColumn employeeNameTableColumn;
    private TableColumn employeeSurnameTableColumn;
    private TableColumn employeePatronimicTableColumn;
    private TableColumn employeePhoneNumberTableColumn;
    private TableColumn employeeСellPhoneNumberTableColumn;
    private TableColumn employeePositionTableColumn;
    private TableColumn employeeDivisionTableColumn;

    private VBox catalogThirdLayout;
    private TableView catalogThirdTableView;

    private TableColumn divisionIdTableColumn;
    private TableColumn divisionNameTableColumn;
    private TableColumn divisionDepartmentTableColumn;
    private VBox catalogFourthLayout;
    private TableView catalogFourthTableView;
    private TableColumn departmentIdTableColumn;
    private TableColumn departmentNameTableColumn;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                scheduledExecutorService.shutdown();
            }
        });
    }

    //actionHandlers
//    menu action handlers
    @FXML
    private void handleChangeUserMenuItemAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Authorization_Scene.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setMinWidth(400);
        stage.setMinHeight(200);
        stage.setTitle(Constants.APP_NAME);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(new Stage());
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle(Constants.APP_NAME);
        stage.setScene(scene);

        AuthorizationController controller = loader.getController();
        controller.setDialogStage(stage);
        controller.setMainController(this);

        stage.show();
    }

    @FXML
    private void handleExitMenuItemAction(ActionEvent event) {
        //save changes and close connections 
        System.exit(0);
    }

    @FXML
    private void handleHelpMenuItemAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Справка");
        alert.setHeaderText("Если у вас возникли какие-либо вопросы обращайтесь к разработчику или консультанту.");
        alert.showAndWait();
    }

    @FXML
    private void handleAboutProgramMenuItemAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("АИС внесения изменений в АСО 'Рупор'.\nРазработчик - Рябов С.С., консультант - Рябов М.С.");
        alert.setContentText(
                "Данная программа предназначена для внесения изменений в списки системы оповещения (первая вкладка)."
                + "\nПосле внесения изменений в списки системы оповещения, во 2-ой вкладке вместо сотрудника, который"
                + " ушёл в отпуск, будет отображаться сотрудник исполняющий обязанности."
                + "\nВ момент наступления окончания периода замещения, сработает оповещение (появится информационное окно),"
                + " а так же в списках оповещения будет вновь отображаться сотрудник, который был в отпуске.");
        alert.showAndWait();
    }

    //    buttons action handlers
    @FXML
    private void handleChangeUserButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Authorization_Scene.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.setMinWidth(400);
        stage.setMinHeight(200);
        stage.setTitle(Constants.APP_NAME);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(new Stage());
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle(Constants.APP_NAME);
        stage.setScene(scene);

        AuthorizationController controller = loader.getController();
        controller.setDialogStage(stage);
        controller.setMainController(this);

        stage.show();
    }

    @FXML
    private void handleHelpButtonAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Справка");
        alert.setHeaderText("Если у вас возникли какие-либо вопросы обращайтесь к разработчику или консультанту.");
        alert.showAndWait();
    }

    //    tabs action handlers
//    tab1
    @FXML
    private void handleAddRecordInChangesTabButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_Changes_In_AlertLists_Scene.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Внести изменения в списки оповещения");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(new Stage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ChangesInAlertListsChangesController controller = loader.getController();
            controller.setEmployees(employeesData);
            controller.setEmployeesOnVacation(employeesDataOnVacation);
            controller.setEmployeesActing(employeesDataActing);
            controller.setDialogStage(dialogStage);
            controller.setOperationIsAdd(true);

            dialogStage.showAndWait();
            if (controller.isAddedChangesInAlertLists()) {
                changesInAlertListsData.add(controller.getAddedChangesInAlertLists());
                LocalDateTime now = LocalDateTime.now();
                ZonedDateTime zdt = now.atZone(ZoneId.of("Europe/Samara"));
                long nowInMilliseconds = zdt.toInstant().toEpochMilli();

                NotificationTask notificationTask
                        = new NotificationTask(controller.getAddedChangesInAlertLists(), notificationTaskList,
                                employeesData, employeesDataOnVacation, employeesDataActing);
                notificationTaskList.add(notificationTask);

                long delay = nowInMilliseconds - controller.getAddedChangesInAlertLists().getEndDateTime().atZone(ZoneId.of("Europe/Samara")).toInstant().toEpochMilli();;

                if (delay >= 0) {
                    scheduledExecutorService.schedule(notificationTask, 3, TimeUnit.SECONDS);
                } else {
                    delay = delay * (-1);
                    scheduledExecutorService.schedule(notificationTask, delay, TimeUnit.MILLISECONDS);
                }
            }

            refreshChangesInAlertListTableView();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание");
            alert.setHeaderText("Ошибка при загрузке экрана.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditRecordInChangesTabButtonAction(ActionEvent event) {
        if (changesTable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_Changes_In_AlertLists_Scene.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Редактировать изменения в списках оповещения");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(new Stage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                ChangesInAlertLists editedChangesInAlertLists = (ChangesInAlertLists) changesTable.getSelectionModel().getSelectedItem();
                ChangesInAlertListsChangesController controller = loader.getController();
                controller.setEmployees(employeesData);
                controller.setEmployeesOnVacation(employeesDataOnVacation);
                controller.setEmployeesActing(employeesDataActing);
                controller.setDialogStage(dialogStage);
                controller.setOperationIsAdd(false);
                controller.setChangesInAlertLists(editedChangesInAlertLists);

                dialogStage.showAndWait();

                refreshChangesInAlertListTableView();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Ошибка при загрузке экрана.");
                alert.showAndWait();
            }
        }
    }

    //    tab2
    @FXML
    private void handleAddRecordInAlertListsTabButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_AlertList_Scene.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить список системы оповещения");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(new Stage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AlertListChangesController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOperationIsAdd(true);
            controller.setEmployees(employeesData);
            controller.setEmployeesActing(employeesDataActing);

            dialogStage.showAndWait();

            if (controller.isAddedAlertList()) {
                alertListsData.add(controller.getAddedAlertList());
            }
            refreshAlertListTableView();
            refreshEmployeeTableView();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание");
            alert.setHeaderText("Ошибка при загрузке экрана.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditRecordInAlertListsTabButtonAction(ActionEvent event) {
        if (alertListsTable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_AlertList_Scene.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Редактировать список системы оповещения");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(new Stage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                AlertListChangesController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setOperationIsAdd(false);
                controller.setEmployees(employeesData);
                controller.setEmployeesActing(employeesDataActing);
                controller.setAlertList((AlertList) alertListsTable.getSelectionModel().getSelectedItem());

                dialogStage.showAndWait();

                refreshAlertListTableView();
                refreshEmployeeTableView();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Ошибка при загрузке экрана.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleDeleteRecordInAlertListsTabButtonAction(ActionEvent event) {

        if (alertListsTable.getSelectionModel().getSelectedItem() != null) {
            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Подтверждение удаления");
            confirmAlert.setHeaderText("Необходимо подтвердить удаление.");
            confirmAlert.setContentText("Вы уверены, что хотите удалить этот список оповещения?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                AlertList alertList = (AlertList) alertListsTable.getSelectionModel().getSelectedItem();
                try {
                    JpaApi.beginTransaction();
                    for (int i = 0; i < employeesData.size(); i++) {
                        ((Employee) JpaApi.find(Employee.class, employeesData.get(i).getId())).getAlertListsInEmployee().remove(alertList);
                        employeesData.get(i).getAlertListsInEmployee().remove(alertList);
                    }
                    JpaApi.remove(JpaApi.find(AlertList.class, alertList.getId()));
                    JpaApi.closeTransaction();
                    alertListsData.remove(alertList);

                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Внимание");
                    alert.setHeaderText("Вы не можите удалить данный список оповещения.");
//                    alert.setContentText("До тех пор пока он числится в");
                    alert.showAndWait();

                    JpaApi.closeEntityManager();
                }
            }
        }
    }

    @FXML
    private void handleAddDeleteEmployeeInAlertListsButtonAction() {
        if (alertListsTable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Records_To_Model_Scene.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Добавление сотрудников в список оповещения");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(new Stage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                AddRecordToModelController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setLabelText("Выберите сотрудников");
                controller.setFilterCount(3);
                String[] labesNames = {"Фамилия", "Имя", "Отчество"};
                controller.setFiltersLabesNames(labesNames);
                controller.setObservableList(employeesDataActing);

                dialogStage.showAndWait();

                if (controller.isOkPressed() && !controller.getSelectedData().isEmpty()) {
                    AlertList alertList = (AlertList) alertListsTable.getSelectionModel().getSelectedItem();
                    JpaApi.beginTransaction();
                    AlertList alertListFromDB = (AlertList) JpaApi.find(AlertList.class, alertList.getId());
                    for (int i = 0; i < controller.getSelectedData().size(); i++) {
                        Employee employee = (Employee) controller.getSelectedData().get(i);
                        Employee employeeFromDB = (Employee) JpaApi.find(Employee.class, employee.getId());
                        alertListFromDB.getEmployees().add(employeeFromDB);
                        employeeFromDB.getAlertListsInEmployee().add(alertListFromDB);
                        alertList.getEmployees().add(employee);
                        employee.getAlertListsInEmployee().add(alertList);
                    }
                    JpaApi.closeTransaction();
                }
                refreshAlertListTableView();
                refreshEmployeeTableView();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Ошибка при загрузке экрана.");
                alert.showAndWait();
            }
        }
    }

    //    tab3
    //    tab4
    //    tab5
    @FXML
    private void handleAddRecordToCatalogButtonAction(ActionEvent event) throws IOException {
        System.out.println("handleAddRecordToCatalogButtonAction!");
        if (catalogChoiceBox.getSelectionModel().getSelectedIndex() == 0) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_Position_Scene.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Добавить должность");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(new Stage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                PositionChangesController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setOperationIsAdd(true);

                dialogStage.showAndWait();
                if (controller.isAddedPosition()) {
                    positionsData.add(controller.getAddedPosition());
                }
                refreshEmployeeTableView();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Ошибка при загрузке экрана.");
                alert.showAndWait();
            }
        } else if (catalogChoiceBox.getSelectionModel().getSelectedIndex() == 1) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_Employee_Scene.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Добавить сотрудника");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(new Stage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                EmployeeChangesController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setOperationIsAdd(true);
                controller.setPositions(positionsData);
                controller.setDivisions(divisionsData);

                dialogStage.showAndWait();
                if (controller.isAddedEmployee()) {
                    employeesData.add(controller.getAddedEmployee());
                    employeesDataActing.add(controller.getAddedEmployee());
                }
                refreshEmployeeTableView();
                refreshDivisionTableView();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Ошибка при загрузке экрана.");
                alert.showAndWait();
            }

        } else if (catalogChoiceBox.getSelectionModel().getSelectedIndex() == 2) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_Division_Scene.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Добавить отдел");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(new Stage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                DivisionChangesController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setOperationIsAdd(true);
                controller.setDepartments(departmentsData);
                controller.setEmployees(employeesData);
                controller.setDivisions(divisionsData);

                dialogStage.showAndWait();
                if (controller.isAddedDivision()) {
                    divisionsData.add(controller.getAddedDivision());
                }
                refreshEmployeeTableView();
                refreshDivisionTableView();
                refreshDepartmentTableView();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Ошибка при загрузке экрана.");
                alert.showAndWait();
            }
        } else if (catalogChoiceBox.getSelectionModel().getSelectedIndex() == 3) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_Department_Scene.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Добавить департамент");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(new Stage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                DepartmentChangesController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setOperationIsAdd(true);
                controller.setDivisions(divisionsData);
                controller.setDepartments(departmentsData);

                dialogStage.showAndWait();
                if (controller.isAddedDepartment()) {
                    departmentsData.add(controller.getAddedDepartment());
                }
                refreshDivisionTableView();
                refreshDepartmentTableView();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Ошибка при загрузке экрана.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleEditRecordToCatalogButtonAction(ActionEvent event) {

        if (catalogChoiceBox.getSelectionModel().getSelectedIndex() == 0
                && catalogFirstTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_Position_Scene.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Редактировать должность");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(new Stage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                PositionChangesController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setEmployeesData(employeesData);

                Position position = (Position) catalogFirstTableView.getSelectionModel().getSelectedItem();
                controller.setPosition(position);
                controller.setOperationIsAdd(false);

                dialogStage.showAndWait();
                refreshPositionTableView();
                refreshEmployeeTableView();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Ошибка при загрузке экрана.");
                alert.showAndWait();
            }
        } else if (catalogChoiceBox.getSelectionModel().getSelectedIndex() == 1
                && catalogSecondTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_Employee_Scene.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Редактировать информацию о сотруднике");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(new Stage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                EmployeeChangesController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setOperationIsAdd(false);
                controller.setPositions(positionsData);
                controller.setDivisions(divisionsData);
                controller.setEmployee((Employee) catalogSecondTableView.getSelectionModel().getSelectedItem());

                dialogStage.showAndWait();
                refreshEmployeeTableView();
                refreshDivisionTableView();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Ошибка при загрузке экрана.");
                alert.showAndWait();
            }

        } else if (catalogChoiceBox.getSelectionModel().getSelectedIndex() == 2
                && catalogThirdTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_Division_Scene.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Редактировать отдел");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(new Stage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                DivisionChangesController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setOperationIsAdd(false);
                controller.setDepartments(departmentsData);
                controller.setEmployees(employeesData);
                controller.setDivisions(divisionsData);
                controller.setDivision((Division) catalogThirdTableView.getSelectionModel().getSelectedItem());

                dialogStage.showAndWait();
                refreshEmployeeTableView();
                refreshDivisionTableView();
                refreshDepartmentTableView();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Ошибка при загрузке экрана.");
                alert.showAndWait();
            }
        } else if (catalogChoiceBox.getSelectionModel().getSelectedIndex() == 3
                && catalogFourthTableView.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_Department_Scene.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Редактировать информацию о департаменте");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(new Stage());
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                DepartmentChangesController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setOperationIsAdd(false);
                controller.setDivisions(divisionsData);
                controller.setDepartments(departmentsData);
                controller.setDepartment((Department) catalogFourthTableView.getSelectionModel().getSelectedItem());

                dialogStage.showAndWait();

                refreshDivisionTableView();
                refreshDepartmentTableView();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Внимание");
                alert.setHeaderText("Ошибка при загрузке экрана.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleDeleteRecordToCatalogButtonAction(ActionEvent event) {
        if (catalogChoiceBox.getSelectionModel().getSelectedIndex() == 0
                && catalogFirstTableView.getSelectionModel().getSelectedItem() != null) {
            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Подтверждение удаления");
            confirmAlert.setHeaderText("Необходимо водтвердить удаление.");
            confirmAlert.setContentText("Вы уверены, что хотите удалить эту должность?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Position position = (Position) catalogFirstTableView.getSelectionModel().getSelectedItem();
                try {
                    JpaApi.beginTransaction();
                    JpaApi.remove(JpaApi.find(Position.class, position.getId()));
                    JpaApi.closeTransaction();
                    positionsData.remove(position);
                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Внимание");
                    alert.setHeaderText("Вы не можите удалить данную должность.");
                    alert.setContentText("До тех пор пока она назначена у сотрудника.");
                    alert.showAndWait();

                    JpaApi.closeEntityManager();
                }
            }
        } else if (catalogChoiceBox.getSelectionModel().getSelectedIndex() == 1
                && catalogSecondTableView.getSelectionModel().getSelectedItem() != null) {
            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Подтверждение удаления");
            confirmAlert.setHeaderText("Необходимо водтвердить удаление.");
            confirmAlert.setContentText("Вы уверены, что хотите удалить этого сотрудника?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Employee employee = (Employee) catalogSecondTableView.getSelectionModel().getSelectedItem();
                try {
                    JpaApi.beginTransaction();
                    JpaApi.remove(JpaApi.find(Employee.class, employee.getId()));
                    JpaApi.closeTransaction();
                    employeesData.remove(employee);
                    employeesDataOnVacation.remove(employee);
                    employeesDataActing.remove(employee);
                    for (int i = 0; i < alertListsData.size(); i++) {
                        alertListsData.get(i).getEmployees().remove(employee);
                    }
                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Внимание");
                    alert.setHeaderText("Вы не можите удалить этого сотрудника.");
                    alert.showAndWait();

                    JpaApi.closeEntityManager();
                }
            }

        } else if (catalogChoiceBox.getSelectionModel().getSelectedIndex() == 2
                && catalogThirdTableView.getSelectionModel().getSelectedItem() != null) {

            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Подтверждение удаления");
            confirmAlert.setHeaderText("Необходимо подтвердить удаление.");
            confirmAlert.setContentText("Вы уверены, что хотите удалить этот отдел?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Division division = (Division) catalogThirdTableView.getSelectionModel().getSelectedItem();
                try {
                    JpaApi.beginTransaction();
                    JpaApi.remove(JpaApi.find(Division.class, division.getId()));
                    JpaApi.closeTransaction();
                    divisionsData.remove(division);
                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Внимание");
                    alert.setHeaderText("Вы не можите удалить отдел, до тех пор пока в нём есть сотрудники");
                    alert.showAndWait();

                    JpaApi.closeEntityManager();
                }
            }

        } else if (catalogChoiceBox.getSelectionModel().getSelectedIndex() == 3
                && catalogFourthTableView.getSelectionModel().getSelectedItem() != null) {

            Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Подтверждение удаления");
            confirmAlert.setHeaderText("Необходимо подтвердить удаление.");
            confirmAlert.setContentText("Вы уверены, что хотите удалить этот департамент?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Department department = (Department) catalogFourthTableView.getSelectionModel().getSelectedItem();
                try {
                    JpaApi.beginTransaction();
                    JpaApi.remove(JpaApi.find(Department.class, department.getId()));
                    JpaApi.closeTransaction();
                    departmentsData.remove(department);
                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Внимание");
                    alert.setHeaderText("Вы не можите удалить департамент, до тех пор пока в нём есть отделы");
                    alert.showAndWait();

                    JpaApi.closeEntityManager();
                }
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        initializeTab1();
        initializeTab2();
        initializeTab3();
//        initializeTab4();
        initializeTab5();

    }

    void initializeData() {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zdt = now.atZone(ZoneId.of("Europe/Samara"));
        long nowInMilliseconds = zdt.toInstant().toEpochMilli();

//       initializeData
        JpaApi.beginTransaction();
        List<ChangesInAlertLists> changesInAlertListses = JpaApi.getResultListFromQuery("from ChangesInAlertLists");

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        
        for (ChangesInAlertLists newChangesInAlertLists : changesInAlertListses) {
            changesInAlertListsData.add(newChangesInAlertLists);
            if (newChangesInAlertLists.isNeedNotificationYet()) {

                NotificationTask notificationTask = new NotificationTask(newChangesInAlertLists, notificationTaskList,
                        employeesData, employeesDataOnVacation, employeesDataActing);
                notificationTaskList.add(notificationTask);

                long delay = nowInMilliseconds - newChangesInAlertLists.getEndDateTime().atZone(ZoneId.of("Europe/Samara")).toInstant().toEpochMilli();;

                if (delay >= 0) {
                    scheduledExecutorService.schedule(notificationTask, 3, TimeUnit.SECONDS);
                } else {
                    delay = delay * (-1);
                    scheduledExecutorService.schedule(notificationTask, delay, TimeUnit.MILLISECONDS);
                }

            }
        }
        alertListsData.addAll(JpaApi.getResultListFromQuery("from AlertList"));
        positionsData.addAll(JpaApi.getResultListFromQuery("from Position"));
        List<Employee> employess = JpaApi.getResultListFromQuery("from Employee");
        Employee newEmployee = null;
        for (Object m : employess) {
            newEmployee = (Employee) m;
            employeesData.add(newEmployee);
            if (newEmployee.isOnVacation()) {
                employeesDataOnVacation.add(newEmployee);
            } else {
                employeesDataActing.add(newEmployee);
            }
        }
        browsingItemsData.addAll(JpaApi.getResultListFromQuery("from BrowsingItem"));
        divisionsData.addAll(JpaApi.getResultListFromQuery("from Division"));
        departmentsData.addAll(JpaApi.getResultListFromQuery("from Department"));
        JpaApi.closeTransaction();
    }

    void initializeTab1() {
        //        tab1
        changesIdTableColumn.setMinWidth(20);
        changesIdTableColumn.setMaxWidth(50);
        changesIdTableColumn.setResizable(true);
        changesIdTableColumn.setCellValueFactory(new PropertyValueFactory<AlertList, Long>("id"));
        changesEmployeeOnVacationTableColumn.setCellValueFactory(new PropertyValueFactory<AlertList, Employee>("employeeOnVacation"));
        changesEmployeeOnVacationTableColumn.setMinWidth(200);
        changesEmployeeActingTableColumn.setCellValueFactory(new PropertyValueFactory<AlertList, Employee>("employeeActing"));
        changesEmployeeActingTableColumn.setMinWidth(200);
        changesStartDateTableColumn.setCellValueFactory(new PropertyValueFactory<AlertList, LocalDateTime>("startDateTime"));
        changesStartDateTableColumn.setMinWidth(100);
        changesEndDateTableColumn.setCellValueFactory(new PropertyValueFactory<AlertList, LocalDateTime>("endDateTime"));
        changesEndDateTableColumn.setMinWidth(100);
        changesGroundsTableColumn.setCellValueFactory(new PropertyValueFactory<AlertList, String>("grounds"));
        changesGroundsTableColumn.setMinWidth(200);

        changesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        changesTable.setItems(changesInAlertListsData);
    }

    void initializeTab2() {
//        tab2
        alertListIdTableColumn.setMinWidth(20);
        alertListIdTableColumn.setMaxWidth(50);
        alertListIdTableColumn.setResizable(true);
        alertListIdTableColumn.setCellValueFactory(new PropertyValueFactory<AlertList, Long>("id"));
        alertListNameTableColumn.setCellValueFactory(new PropertyValueFactory<AlertList, String>("name"));
        alertListsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        alertListsTable.setItems(alertListsData);
    }

    void initializeTab3() {
        //        tab3
        journalsScrollPane.setFitToWidth(true);
        journalFirstLayout = new VBox();
        journalFirstTableView = new TableView<BrowsingItem>();

        TableColumn browsingItemIdTableColumn = new TableColumn<BrowsingItem, Long>("№");
        browsingItemIdTableColumn.setMinWidth(20);
        browsingItemIdTableColumn.setMaxWidth(50);
        browsingItemIdTableColumn.setResizable(true);
        browsingItemIdTableColumn.setCellValueFactory(new PropertyValueFactory<BrowsingItem, Long>("id"));
        journalFirstTableView.getColumns().add(browsingItemIdTableColumn);

        TableColumn browsingItemEmployeeTableColumn = new TableColumn<BrowsingItem, Employee>("Сотрудник");
        browsingItemEmployeeTableColumn.setCellValueFactory(new PropertyValueFactory<BrowsingItem, Employee>("employee"));
        journalFirstTableView.getColumns().add(browsingItemEmployeeTableColumn);

        TableColumn browsingItemDateTableColumn = new TableColumn<BrowsingItem, LocalDateTime>("Дата");
        browsingItemDateTableColumn.setCellValueFactory(new PropertyValueFactory<BrowsingItem, LocalDateTime>("localDateTime"));
        journalFirstTableView.getColumns().add(browsingItemDateTableColumn);

        journalFirstTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        journalFirstTableView.setItems(browsingItemsData);
        journalFirstLayout.getChildren().add(journalFirstTableView);

        journalSecondLayout = new VBox();
        journalSecondTableView = new TableView<ChangeItemInChangesInAlertLists>();

        TableColumn changeItemIdTableColumn = new TableColumn<ChangeItemInChangesInAlertLists, Long>("№");
        changeItemIdTableColumn.setMinWidth(20);
        changeItemIdTableColumn.setMaxWidth(50);
        changeItemIdTableColumn.setResizable(true);
        changeItemIdTableColumn.setCellValueFactory(new PropertyValueFactory<ChangeItemInChangesInAlertLists, Long>("id"));
        journalSecondTableView.getColumns().add(changeItemIdTableColumn);

        TableColumn changeItemEmployeeTableColumn = new TableColumn<ChangeItemInChangesInAlertLists, Employee>("Сотрудник");
        changeItemEmployeeTableColumn.setCellValueFactory(new PropertyValueFactory<ChangeItemInChangesInAlertLists, Employee>("employeeWhoMadeChange"));
        journalSecondTableView.getColumns().add(changeItemEmployeeTableColumn);

        TableColumn changeItemDateTableColumn = new TableColumn<ChangeItemInChangesInAlertLists, LocalDateTime>("Дата");
        changeItemDateTableColumn.setCellValueFactory(new PropertyValueFactory<ChangeItemInChangesInAlertLists, LocalDateTime>("changeDate"));
        journalSecondTableView.getColumns().add(changeItemDateTableColumn);

        TableColumn changeItemChangesInAlertListsTableColumn = new TableColumn<ChangeItemInChangesInAlertLists, ChangesInAlertLists>("Изменения  в ChangesInAlertLists");
        changeItemChangesInAlertListsTableColumn.setCellValueFactory(new PropertyValueFactory<ChangeItemInChangesInAlertLists, ChangesInAlertLists>("changesInAlertLists"));
        journalSecondTableView.getColumns().add(changeItemChangesInAlertListsTableColumn);

        TableColumn changeItemСhangesInChangesInAlertListsTableColumn = new TableColumn<ChangeItemInChangesInAlertLists, String>("Изменения в списках оповещения");
        changeItemСhangesInChangesInAlertListsTableColumn.setCellValueFactory(new PropertyValueFactory<ChangeItemInChangesInAlertLists, String>("changesInChangesInAlertLists"));
        journalSecondTableView.getColumns().add(changeItemСhangesInChangesInAlertListsTableColumn);

        journalSecondTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        journalSecondLayout.getChildren().add(journalSecondTableView);

        journalsChoiceBox.setItems(FXCollections.observableArrayList("Журнал посещений", "Журнал изменений"));
        journalsChoiceBox.getSelectionModel().selectFirst();
        journalsScrollPane.setContent(journalFirstLayout);
        journalsChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() == 0) {
                    journalsScrollPane.setContent(journalFirstLayout);
                } else {
                    journalsScrollPane.setContent(journalSecondLayout);
                }
            }
        });

    }

    //    void initializeTab4() {
////       tab4
//    }
    void initializeTab5() {
//        tab5
        catalogScrollPane.setFitToWidth(true);
        catalogFirstLayout = new VBox();
        catalogFirstTableView = new TableView<Position>();

        positionIdTableColumn = new TableColumn<Position, Long>("№");
        positionIdTableColumn.setMinWidth(20);
        positionIdTableColumn.setMaxWidth(50);
        positionIdTableColumn.setResizable(true);
        positionIdTableColumn.setCellValueFactory(new PropertyValueFactory<Position, Long>("id"));
        catalogFirstTableView.getColumns().add(positionIdTableColumn);
        positionNameTableColumn = new TableColumn<Position, Integer>("Название");
        positionNameTableColumn.setCellValueFactory(new PropertyValueFactory<Position, String>("name"));
        catalogFirstTableView.getColumns().add(positionNameTableColumn);
        catalogFirstTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        catalogFirstTableView.setItems(positionsData);
        catalogFirstLayout.getChildren().add(catalogFirstTableView);

        catalogSecondLayout = new VBox();
        catalogSecondTableView = new TableView<Employee>();

        employeeIdTableColumn = new TableColumn<Employee, Long>("№");
        employeeIdTableColumn.setMinWidth(20);
        employeeIdTableColumn.setMaxWidth(50);
        employeeIdTableColumn.setResizable(true);
        employeeIdTableColumn.setCellValueFactory(new PropertyValueFactory<Employee, Long>("id"));
        catalogSecondTableView.getColumns().add(employeeIdTableColumn);
        employeeNameTableColumn = new TableColumn<Employee, String>("Имя");
        employeeNameTableColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        catalogSecondTableView.getColumns().add(employeeNameTableColumn);
        employeeSurnameTableColumn = new TableColumn<Employee, String>("Фамилия");
        employeeSurnameTableColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("surname"));
        catalogSecondTableView.getColumns().add(employeeSurnameTableColumn);
        employeePatronimicTableColumn = new TableColumn<Employee, String>("Отчество");
        employeePatronimicTableColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("patronimic"));
        catalogSecondTableView.getColumns().add(employeePatronimicTableColumn);
        employeePhoneNumberTableColumn = new TableColumn<Employee, String>("Номер телефона");
        employeePhoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("phoneNumber"));
        catalogSecondTableView.getColumns().add(employeePhoneNumberTableColumn);
        employeeСellPhoneNumberTableColumn = new TableColumn<Employee, String>("Номер сотового телефона");
        employeeСellPhoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("cellPhoneNumber"));
        catalogSecondTableView.getColumns().add(employeeСellPhoneNumberTableColumn);
        employeePositionTableColumn = new TableColumn<Employee, Position>("Должность");
        employeePositionTableColumn.setCellValueFactory(new PropertyValueFactory<Employee, Position>("position"));
        catalogSecondTableView.getColumns().add(employeePositionTableColumn);
        employeeDivisionTableColumn = new TableColumn<Employee, Division>("Отдел");
        employeeDivisionTableColumn.setCellValueFactory(new PropertyValueFactory<Employee, Division>("division"));
        catalogSecondTableView.getColumns().add(employeeDivisionTableColumn);
        catalogSecondTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        catalogSecondTableView.setItems(employeesData);
        catalogSecondLayout.getChildren().add(catalogSecondTableView);

        catalogThirdLayout = new VBox();
        catalogThirdTableView = new TableView<Division>();

        divisionIdTableColumn = new TableColumn<Division, Long>("№");
        divisionIdTableColumn.setMinWidth(20);
        divisionIdTableColumn.setMaxWidth(50);
        divisionIdTableColumn.setResizable(true);
        divisionIdTableColumn.setCellValueFactory(new PropertyValueFactory<Division, Long>("id"));
        catalogThirdTableView.getColumns().add(divisionIdTableColumn);
        divisionNameTableColumn = new TableColumn<Division, String>("Название");
        divisionNameTableColumn.setCellValueFactory(new PropertyValueFactory<Division, String>("name"));
        catalogThirdTableView.getColumns().add(divisionNameTableColumn);
        divisionDepartmentTableColumn = new TableColumn<Division, Department>("Департамент");
        divisionDepartmentTableColumn.setCellValueFactory(new PropertyValueFactory<Division, Department>("department"));
        catalogThirdTableView.getColumns().add(divisionDepartmentTableColumn);
        catalogThirdTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        catalogThirdTableView.setItems(divisionsData);
        catalogThirdLayout.getChildren().add(catalogThirdTableView);

        catalogFourthLayout = new VBox();
        catalogFourthTableView = new TableView<Department>();

        departmentIdTableColumn = new TableColumn<Department, Long>("№");
        departmentIdTableColumn.setMinWidth(20);
        departmentIdTableColumn.setMaxWidth(50);
        departmentIdTableColumn.setResizable(true);
        departmentIdTableColumn.setCellValueFactory(new PropertyValueFactory<Department, Long>("id"));
        catalogFourthTableView.getColumns().add(departmentIdTableColumn);
        departmentNameTableColumn = new TableColumn<Department, String>("Название");
        departmentNameTableColumn.setCellValueFactory(new PropertyValueFactory<Department, String>("name"));
        catalogFourthTableView.getColumns().add(departmentNameTableColumn);
        catalogFourthTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        catalogFourthTableView.setItems(departmentsData);
        catalogFourthLayout.getChildren().add(catalogFourthTableView);

        catalogChoiceBox.setItems(FXCollections.observableArrayList("Должности", "Сотрудники", "Отделы", "Департаменты"));
        catalogChoiceBox.getSelectionModel().selectFirst();
        catalogScrollPane.setContent(catalogFirstLayout);
        catalogChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() == 0) {
                    catalogFirstLayout.setPrefHeight(catalogScrollPane.getPrefHeight());
                    catalogScrollPane.setContent(catalogFirstLayout);
                } else if (newValue.intValue() == 1) {
                    catalogSecondLayout.setPrefHeight(catalogScrollPane.getPrefHeight());
                    catalogScrollPane.setContent(catalogSecondLayout);
                } else if (newValue.intValue() == 2) {
                    catalogThirdLayout.setPrefHeight(catalogScrollPane.getPrefHeight());
                    catalogScrollPane.setContent(catalogThirdLayout);
                } else if (newValue.intValue() == 3) {
                    catalogFourthLayout.setPrefHeight(catalogScrollPane.getPrefHeight());
                    catalogScrollPane.setContent(catalogFourthLayout);
                }
            }
        });
    }

    public void refreshChangesInAlertListTableView() {
        changesIdTableColumn.setVisible(false);
        changesIdTableColumn.setVisible(true);
        changesEmployeeOnVacationTableColumn.setVisible(false);
        changesEmployeeOnVacationTableColumn.setVisible(true);
        changesEmployeeActingTableColumn.setVisible(false);
        changesEmployeeActingTableColumn.setVisible(true);
        changesStartDateTableColumn.setVisible(false);
        changesStartDateTableColumn.setVisible(true);
        changesEndDateTableColumn.setVisible(false);
        changesEndDateTableColumn.setVisible(true);
        changesGroundsTableColumn.setVisible(false);
        changesGroundsTableColumn.setVisible(true);
    }

    public void refreshAlertListTableView() {
        alertListIdTableColumn.setVisible(false);
        alertListIdTableColumn.setVisible(true);
        alertListNameTableColumn.setVisible(false);
        alertListNameTableColumn.setVisible(true);
    }

    public void refreshPositionTableView() {
        positionIdTableColumn.setVisible(false);
        positionIdTableColumn.setVisible(true);
        positionNameTableColumn.setVisible(false);
        positionNameTableColumn.setVisible(true);
    }

    public void refreshEmployeeTableView() {
        employeeIdTableColumn.setVisible(false);
        employeeIdTableColumn.setVisible(true);
        employeeNameTableColumn.setVisible(false);
        employeeNameTableColumn.setVisible(true);
        employeeSurnameTableColumn.setVisible(false);
        employeeSurnameTableColumn.setVisible(true);
        employeePatronimicTableColumn.setVisible(false);
        employeePatronimicTableColumn.setVisible(true);
        employeePhoneNumberTableColumn.setVisible(false);
        employeePhoneNumberTableColumn.setVisible(true);
        employeeСellPhoneNumberTableColumn.setVisible(false);
        employeeСellPhoneNumberTableColumn.setVisible(true);
        employeePositionTableColumn.setVisible(false);
        employeePositionTableColumn.setVisible(true);
        employeeDivisionTableColumn.setVisible(false);
        employeeDivisionTableColumn.setVisible(true);
    }

    public void refreshDivisionTableView() {
        divisionIdTableColumn.setVisible(false);
        divisionIdTableColumn.setVisible(true);
        divisionNameTableColumn.setVisible(false);
        divisionNameTableColumn.setVisible(true);
        divisionDepartmentTableColumn.setVisible(false);
        divisionDepartmentTableColumn.setVisible(true);
    }

    public void refreshDepartmentTableView() {
        departmentIdTableColumn.setVisible(false);
        departmentIdTableColumn.setVisible(true);
        departmentNameTableColumn.setVisible(false);
        departmentNameTableColumn.setVisible(true);
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    public void setBrowsingItem(BrowsingItem browsingItem) {
        browsingItemsData.add(browsingItem);
    }

}
