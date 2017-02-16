package org.javafx.ais.introductionofchanges.controllers;

import org.javafx.ais.introductionofchanges.model.Division;
import org.javafx.ais.introductionofchanges.model.Employee;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Сергей
 */
public class AddRecordToModelController {

    private int filterCount;
    private ObservableList<Object> data;
    private ObservableList<Object> filteredData;
    private CopyOnWriteArraySet filteredSet;
    private ObservableList<Object> selectedData;
    private boolean isOkPressed;
    private Stage dialogStage;

    @FXML
    private Label chooseLabel;

    @FXML
    private CheckBox isShowFiltersCheckBox;

    @FXML
    private Pane filterPane;
    @FXML
    private Label filter1Label;

    @FXML
    private Label filter2Label;

    @FXML
    private Label filter3Label;

    @FXML
    private TextField filter1TextField;

    @FXML
    private TextField filter2TextField;

    @FXML
    private TextField filter3TextField;

    @FXML
    private ListView objectListView;

    @FXML
    private void initialize() {
        filterPane.setVisible(false);
        objectListView.setPrefHeight(objectListView.getPrefHeight() + 115);
        selectedData = FXCollections.observableArrayList();
        filteredData = FXCollections.observableArrayList();
        filteredSet = new CopyOnWriteArraySet<>();
        objectListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        filter1TextField.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldVal,
                                Object newVal) {
                search((String) oldVal, (String) newVal, 1);
            }
        });
        filter2TextField.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldVal,
                                Object newVal) {
                search((String) oldVal, (String) newVal, 2);
            }
        });
        filter3TextField.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldVal,
                                Object newVal) {
                search((String) oldVal, (String) newVal, 3);
            }
        });
    }

    private void search(String oldVal, String newVal, int filterNumber) {
        if (!data.isEmpty()) {
            if (newVal.length() > oldVal.length()) {
                searchWhenCharAdded(newVal, filterNumber);
            } else {
                searchWhenCharDeleted();
            }
            if (filteredSet.isEmpty()
                    && StringUtils.isBlank(filter1TextField.getText())
                    && StringUtils.isBlank(filter2TextField.getText())
                    && StringUtils.isBlank(filter3TextField.getText())) {
                objectListView.setItems(data);
            } else {
                filteredData.addAll(filteredSet);
                objectListView.setItems(filteredData);
            }
        }
    }

    private void searchWhenCharAdded(String newVal, int filterNumber) {
        boolean useFilteredData = false;
        if (StringUtils.isBlank(filter1TextField.getText())
                && StringUtils.isBlank(filter2TextField.getText())
                && StringUtils.isBlank(filter3TextField.getText())) {
            filteredData.clear();
            filteredSet.clear();
        } else if (!filteredData.isEmpty()) {
            filteredSet.addAll(filteredData);
            filteredData.clear();
            useFilteredData = true;
        }
        if (StringUtils.isNotBlank(filter1TextField.getText()) && filterNumber == 1) {
            if (data.get(0) instanceof Division) {
                if (useFilteredData) {
                    for (Iterator iterator = filteredSet.iterator(); iterator.hasNext(); ) {
                        Division division = (Division) iterator.next();
                        if (!StringUtils.containsIgnoreCase(division.getName(), newVal)) {
                            filteredSet.remove(division);
                        }
                    }
                } else {
                    for (Object object : data) {
                        Division division = (Division) object;
                        if (StringUtils.containsIgnoreCase(division.getName(), newVal)) {
                            filteredSet.add(division);
                        }
                    }
                }
            } else if (data.get(0) instanceof Employee) {
                if (useFilteredData) {
                    for (Iterator iterator = filteredSet.iterator(); iterator.hasNext(); ) {
                        Employee employee = (Employee) iterator.next();
                        if (!StringUtils.containsIgnoreCase(employee.getSurname(), newVal)) {
                            filteredSet.remove(employee);
                            useFilteredData = true;
                        }
                    }
                } else {
                    for (Object object : data) {
                        Employee employee = (Employee) object;
                        if (StringUtils.containsIgnoreCase(employee.getSurname(), newVal)) {
                            filteredSet.add(employee);
                            useFilteredData = true;
                        }
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(filter2TextField.getText()) && filterNumber == 2) {
            if (data.get(0) instanceof Employee) {
                if (useFilteredData) {
                    for (Object object : filteredSet) {
                        Employee employee = (Employee) object;
                        if (!StringUtils.containsIgnoreCase(employee.getName(), newVal)) {
                            filteredSet.remove(employee);
                            useFilteredData = true;
                        }
                    }
                } else {
                    for (Object object : data) {
                        Employee employee = (Employee) object;
                        if (StringUtils.containsIgnoreCase(employee.getName(), newVal)) {
                            filteredSet.add(employee);
                            useFilteredData = true;
                        }
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(filter3TextField.getText()) && filterNumber == 3) {
            if (data.get(0) instanceof Employee) {
                if (useFilteredData) {
                    for (Object object : filteredSet) {
                        Employee employee = (Employee) object;
                        if (!StringUtils.containsIgnoreCase(employee.getPatronimic(), newVal)) {
                            filteredSet.remove(employee);
                        }
                    }
                } else {
                    for (Object object : data) {
                        Employee employee = (Employee) object;
                        if (StringUtils.containsIgnoreCase(employee.getPatronimic(), newVal)) {
                            filteredSet.add(employee);
                        }
                    }
                }
            }
        }
    }

    private void searchWhenCharDeleted() {
        boolean useFilteredData = false;
        filteredData.clear();
        filteredSet.clear();
        if (StringUtils.isNotBlank(filter1TextField.getText())) {
            if (data.get(0) instanceof Division) {
                for (Object object : data) {
                    Division division = (Division) object;
                    if (StringUtils.containsIgnoreCase(division.getName(), filter1TextField.getText())) {
                        filteredSet.add(division);
                    }
                }
            } else if (data.get(0) instanceof Employee) {
                for (Object object : data) {
                    Employee employee = (Employee) object;
                    if (StringUtils.containsIgnoreCase(employee.getSurname(), filter1TextField.getText())) {
                        filteredSet.add(employee);
                        useFilteredData = true;
                    }
                }

            }
        }
        if (StringUtils.isNotBlank(filter2TextField.getText())) {
            if (data.get(0) instanceof Employee) {
                if (useFilteredData) {
                    for (Object object : filteredSet) {
                        Employee employee = (Employee) object;
                        if (!StringUtils.containsIgnoreCase(employee.getName(), filter2TextField.getText())) {
                            filteredSet.remove(employee);
                            useFilteredData = true;
                        }
                    }
                } else {
                    for (Object object : data) {
                        Employee employee = (Employee) object;
                        if (StringUtils.containsIgnoreCase(employee.getName(), filter2TextField.getText())) {
                            filteredSet.add(employee);
                            useFilteredData = true;
                        }
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(filter3TextField.getText())) {
            if (data.get(0) instanceof Employee) {
                if (useFilteredData) {
                    for (Object object : filteredSet) {
                        Employee employee = (Employee) object;
                        if (!StringUtils.containsIgnoreCase(employee.getPatronimic(), filter3TextField.getText())) {
                            filteredSet.remove(employee);
                        }
                    }
                } else {
                    for (Object object : data) {
                        Employee employee = (Employee) object;
                        if (StringUtils.containsIgnoreCase(employee.getPatronimic(), filter3TextField.getText())) {
                            filteredSet.add(employee);
                        }
                    }
                }
            }
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setMinWidth(500);
        this.dialogStage.setMinHeight(600);
    }

    public void setSelectionMode(SelectionMode selectionMode) {
        objectListView.getSelectionModel().setSelectionMode(selectionMode);
    }

    public void setFilterCount(int filterCount) {
        this.filterCount = filterCount;
    }

    public void setFiltersLabesNames(String[] labelsNames) {
        switch (filterCount) {
            case 1:
                filter1Label.setText(labelsNames[0]);
                filter2Label.setVisible(false);
                filter3Label.setVisible(false);
                break;
            case 2:
                filter1Label.setText(labelsNames[0]);
                filter2Label.setText(labelsNames[1]);
                filter3Label.setVisible(false);

                break;
            case 3:
                filter1Label.setText(labelsNames[0]);
                filter2Label.setText(labelsNames[1]);
                filter3Label.setText(labelsNames[2]);
        }
    }

    public void setObservableList(ObservableList data) {
        objectListView.setItems(data);
        this.data = data;
    }

    public void setLabelText(String text) {
        chooseLabel.setText(text);
    }

    public boolean isOkPressed() {
        return isOkPressed;
    }

    public ObservableList<Object> getSelectedData() {
        return selectedData;
    }

    @FXML
    private void handleIsShowFiltersCheckBoxAction() {
        if (isShowFiltersCheckBox.isSelected()) {
            filterPane.setVisible(true);

            switch (filterCount) {
                case 1:
                    filter1Label.setVisible(true);
                    filter2Label.setVisible(false);
                    filter3Label.setVisible(false);
                    filter1TextField.setVisible(true);
                    filter2TextField.setVisible(false);
                    filter3TextField.setVisible(false);
                    objectListView.setPrefHeight(objectListView.getPrefHeight() - 35);
                    break;
                case 2:
                    filter1Label.setVisible(true);
                    filter2Label.setVisible(true);
                    filter3Label.setVisible(false);
                    filter1TextField.setVisible(true);
                    filter2TextField.setVisible(true);
                    filter3TextField.setVisible(false);
                    objectListView.setPrefHeight(objectListView.getPrefHeight() - 75);
                    break;
                default:
                    filter1Label.setVisible(true);
                    filter2Label.setVisible(true);
                    filter3Label.setVisible(true);
                    filter1TextField.setVisible(true);
                    filter2TextField.setVisible(true);
                    filter3TextField.setVisible(true);
                    objectListView.setPrefHeight(objectListView.getPrefHeight() - 115);
            }
        } else {
            filterPane.setVisible(false);
            switch (filterCount) {
                case 1:
                    objectListView.setPrefHeight(objectListView.getPrefHeight() + 35);
                    break;
                case 2:
                    objectListView.setPrefHeight(objectListView.getPrefHeight() + 75);
                    break;
                default:
                    objectListView.setPrefHeight(objectListView.getPrefHeight() + 115);
            }
        }
    }

    @FXML
    private void handleOkButtonAction() {
        selectedData.setAll(objectListView.getSelectionModel().getSelectedItems());
        isOkPressed = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancelButtonAction() {
        dialogStage.close();
    }

}
