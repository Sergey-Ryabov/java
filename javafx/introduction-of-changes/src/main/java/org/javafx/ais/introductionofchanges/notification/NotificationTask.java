package org.javafx.ais.introductionofchanges.notification;

import org.javafx.ais.introductionofchanges.model.ChangesInAlertLists;
import org.javafx.ais.introductionofchanges.model.Employee;
import org.javafx.ais.introductionofchanges.utils.JpaApi;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by ryabov on 20.01.2016.
 */
public class NotificationTask implements Callable<Boolean> {

    private boolean isNeedNotificationYet;
    private ChangesInAlertLists changesInAlertLists;
    private List<NotificationTask> notificationTaskList;
    private ObservableList<Employee> employeesData;
    private ObservableList<Employee> employeesDataOnVacation;
    private ObservableList<Employee> employeesDataActing;

    public NotificationTask(ChangesInAlertLists changesInAlertLists, List<NotificationTask> notificationTaskList,
            ObservableList<Employee> employeesData,
            ObservableList<Employee> employeesDataOnVacation,
            ObservableList<Employee> employeesDataActing) {
        isNeedNotificationYet = true;
        this.changesInAlertLists = changesInAlertLists;
        this.notificationTaskList = notificationTaskList;

        this.employeesData = employeesData;
        this.employeesDataOnVacation = employeesDataOnVacation;
        this.employeesDataActing = employeesDataActing;
    }

    public boolean isNeedNotificationYet() {
        return isNeedNotificationYet;
    }

    public void setIsNeedNotificationYet(boolean isNeedNotificationYet) {
        this.isNeedNotificationYet = isNeedNotificationYet;
    }

    public ChangesInAlertLists getChangesInAlertLists() {
        return changesInAlertLists;
    }

    @Override
    public Boolean call() {
        Platform.runLater(
                () -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Оповещение");
            alert.setHeaderText("Обратите внимение на то, что " + changesInAlertLists.getEmployeeOnVacation()
                    + " сегодня вновь должен приступить к своим обязанностям.");
            alert.showAndWait();
        });

        JpaApi.beginTransaction();
        ChangesInAlertLists changesInAlertListsDB
                = (ChangesInAlertLists) JpaApi.find(ChangesInAlertLists.class, changesInAlertLists.getId());
        changesInAlertListsDB.setIsNeedNotificationYet(false);
        JpaApi.persist(changesInAlertListsDB);
        Employee employeeOnVacationFromDB = (Employee) JpaApi.find(Employee.class, changesInAlertListsDB.getEmployeeOnVacation().getId());
        employeeOnVacationFromDB.setIsOnVacation(false);
        employeeOnVacationFromDB.setChangesInAlertLists(null);
        JpaApi.closeTransaction();
        changesInAlertLists.setIsNeedNotificationYet(false);
        Employee employeeOnVacation = null;
        for (Employee employee : employeesData) {
            if (changesInAlertLists.getEmployeeOnVacation().equals(employee)) {
                employeeOnVacation = employee;
                employeeOnVacation.setIsOnVacation(false);
                employeeOnVacation.setChangesInAlertLists(null);
                break;
            }
        }
        employeesDataActing.add(employeeOnVacation);
        employeesDataOnVacation.remove(employeeOnVacation);

        notificationTaskList.remove(this);
        return isNeedNotificationYet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationTask)) {
            return false;
        }

        NotificationTask that = (NotificationTask) o;

        return changesInAlertLists.equals(that.changesInAlertLists);
    }

    @Override
    public int hashCode() {
        return changesInAlertLists.hashCode();
    }
}
