package org.javafx.map.controllers;

import org.javafx.map.model.FactoryObject;
import org.javafx.map.utils.JpaApi;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.beans.value.ObservableValue;

/**
 * @author Сергей
 */
public class FactoryObjectChangesController {
    

    //    TODO: переместить все константы
    private boolean operationIsAdd;
    private boolean isAdded;
    private FactoryObject addedFactoryObject;
    private double x;
    private double y;

    @FXML
    private TextField objectNameTextField;
    @FXML
    private Pane containerForGridPanes;

    private Label startExploitationDate;
    private DatePicker startExploitationDateValue;
    private Label lastReconstructionDate;
    private DatePicker lastReconstructionDateValue;
    private Label lastReconstructionMadeBy;
    private TextField lastReconstructionMadeByValue;
    private Label lastRepairsDate;
    private DatePicker lastRepairsDateValue;
    private Label lastRepairsMadeBy;
    private TextArea lastRepairsMadeByValue;
    private Label perfomance;
    private TextArea perfomanceValue;
    //  5x2
    private Label numberOfEmployees;
    private TextField numberOfEmployeesValue;
    private Label numberOfTeams;
    private TextField numberOfTeamsValue;
    private Label teamsSchedule;
    private TextArea teamsScheduleValue;
    private Label maxNumberOfEmployeesInTeam;
    private TextField maxNumberOfEmployeesInTeamValue;
    private Label minNumberOfEmployeesInTeam;
    private TextField minNumberOfEmployeesInTeamValue;
    //6x2
    private Label objectAppointment;
    private TextArea objectAppointmentValue;
    private Label productionMethod;
    private TextArea productionMethodValue;
    private Label technologicalBlocks;
    private TextArea technologicalBlocksValue;
    private Label resourceComeFromObjects;
    private TextArea resourceComeFromObjectsValue;
    private Label resourceGoToObjects;
    private TextArea resourceGoToObjectsValue;
    private Label auxiliaryObjects;
    private TextArea auxiliaryObjectsValue;
    //3x2
    private Label objectLocation;
    private TextArea objectLocationValue;
    private Label enterInObjectFromStreet;
    private TextArea enterInObjectFromStreetValue;
    private Label operatorLocation;
    private TextArea operatorLocationValue;
    //3x2
    private Label mostDangerousEquipment;
    private TextArea mostDangerousEquipmentValue;
    private Label mostPossibleEmergencySituation;
    private TextArea mostPossibleEmergencySituationValue;
    private Label mostDangerousEmergencySituation;
    private TextArea mostDangerousEmergencySituationValue;

    private VBox oilTypeAndQuantityVBox;
    private VBox personalProtectiveEquipmentTypeAndQuantityVBox;
    private VBox stateOfEmergencyWhatAndWhenHappenedVBox;

    @FXML
    private Button addButton;

    private Stage dialogStage;

    private final double upperPadding = 30;
    private final double textAreaHeight = 180; //видно 8 строки

    @FXML
    private void initialize() {
        GridPane firstGridPane = new GridPane();
        startExploitationDate = new Label("Год ввода в эксплуатацию:");
        startExploitationDateValue = new DatePicker(LocalDate.now());
        startExploitationDateValue.setEditable(false);
        lastReconstructionDate = new Label("Год последней реконструкции:");
        lastReconstructionDateValue = new DatePicker(LocalDate.now());
        lastReconstructionDateValue.setEditable(false);
        lastReconstructionMadeBy = new Label("Кем выполнялась последняя реконструкция:");
        lastReconstructionMadeByValue = new TextField("");
        lastRepairsDate = new Label("Когда производился последний ремонт:");
        lastRepairsDateValue = new DatePicker(LocalDate.now());
        lastRepairsDateValue.setEditable(false);
        lastRepairsMadeBy = new Label("Кем производился последний ремонт:");
        lastRepairsMadeByValue = new TextArea("");
        lastRepairsMadeByValue.setPrefHeight(textAreaHeight);
        perfomance = new Label("Производительность / объем (для резервуарных парков):");
        perfomanceValue = new TextArea("");
        perfomanceValue.setPrefHeight(textAreaHeight);
        firstGridPane.add(startExploitationDate, 0, 0);
        firstGridPane.add(startExploitationDateValue, 0, 1);
        firstGridPane.add(lastReconstructionDate, 0, 2);
        firstGridPane.add(lastReconstructionDateValue, 0, 3);
        firstGridPane.add(lastReconstructionMadeBy, 0, 4);
        firstGridPane.add(lastReconstructionMadeByValue, 0, 5);
        firstGridPane.add(lastRepairsDate, 0, 6);
        firstGridPane.add(lastRepairsDateValue, 0, 7);
        firstGridPane.add(lastRepairsMadeBy, 0, 8);
        firstGridPane.add(lastRepairsMadeByValue, 0, 9);
        firstGridPane.add(perfomance, 0, 10);
        firstGridPane.add(perfomanceValue, 0, 11);
        firstGridPane.setPadding(new Insets(5, 5, 5, 5));

        numberOfEmployees = new Label("Общая численность работников на ОПО, чел.:");
        numberOfEmployees.setPadding(new Insets(upperPadding, 0, 0, 0));
        numberOfEmployeesValue = new TextField("");
        numberOfTeams = new Label("количество смен (бригад):");
        numberOfTeamsValue = new TextField("");
        teamsSchedule = new Label("расписание смен (со скольки до скольки):");
        teamsScheduleValue = new TextArea("");
        teamsScheduleValue.setPrefHeight(textAreaHeight);
        maxNumberOfEmployeesInTeam = new Label("Максимальное кол-во работников на ОПО в смену, чел.:");
        maxNumberOfEmployeesInTeamValue = new TextField("");
        minNumberOfEmployeesInTeam = new Label("Минимальное кол-во работников на ОПО в смену, чел.:");
        minNumberOfEmployeesInTeamValue = new TextField("");
        firstGridPane.add(numberOfEmployees, 0, 12);
        firstGridPane.add(numberOfEmployeesValue, 0, 13);
        firstGridPane.add(numberOfTeams, 0, 14);
        firstGridPane.add(numberOfTeamsValue, 0, 15);
        firstGridPane.add(teamsSchedule, 0, 16);
        firstGridPane.add(teamsScheduleValue, 0, 17);
        firstGridPane.add(maxNumberOfEmployeesInTeam, 0, 18);
        firstGridPane.add(maxNumberOfEmployeesInTeamValue, 0, 19);
        firstGridPane.add(minNumberOfEmployeesInTeam, 0, 20);
        firstGridPane.add(minNumberOfEmployeesInTeamValue, 0, 21);

        objectAppointment = new Label("Назначение установки:");
        objectAppointment.setPadding(new Insets(upperPadding, 0, 0, 0));
        objectAppointmentValue = new TextArea("");
        objectAppointmentValue.setPrefHeight(textAreaHeight);
        productionMethod = new Label("Метод производства:");
        productionMethodValue = new TextArea("");
        productionMethodValue.setPrefHeight(textAreaHeight);
        technologicalBlocks = new Label("Технологические блоки:");
        technologicalBlocksValue = new TextArea("");
        technologicalBlocksValue.setPrefHeight(textAreaHeight);
        resourceComeFromObjects = new Label("Нефтепродукт поступает с установок (перечислить):");
        resourceComeFromObjectsValue = new TextArea("");
        resourceComeFromObjectsValue.setPrefHeight(textAreaHeight);
        resourceGoToObjects = new Label("Нефтепродукт поступает на установки (перечислить):");
        resourceGoToObjectsValue = new TextArea("");
        resourceGoToObjectsValue.setPrefHeight(textAreaHeight);
        auxiliaryObjects = new Label("Вспомогательные установки:");
        auxiliaryObjectsValue = new TextArea("");
        auxiliaryObjectsValue.setPrefHeight(textAreaHeight);
        firstGridPane.add(objectAppointment, 0, 22);
        firstGridPane.add(objectAppointmentValue, 0, 23);
        firstGridPane.add(productionMethod, 0, 24);
        firstGridPane.add(productionMethodValue, 0, 25);
        firstGridPane.add(technologicalBlocks, 0, 26);
        firstGridPane.add(technologicalBlocksValue, 0, 27);
        firstGridPane.add(resourceComeFromObjects, 0, 28);
        firstGridPane.add(resourceComeFromObjectsValue, 0, 29);
        firstGridPane.add(resourceGoToObjects, 0, 30);
        firstGridPane.add(resourceGoToObjectsValue, 0, 31);
        firstGridPane.add(auxiliaryObjects, 0, 32);
        firstGridPane.add(auxiliaryObjectsValue, 0, 33);

        objectLocation = new Label("Месторасположение установки (квадрат дорог):");
        objectLocation.setPadding(new Insets(upperPadding, 0, 0, 0));
        objectLocationValue = new TextArea("");
        objectLocationValue.setPrefHeight(textAreaHeight);
        enterInObjectFromStreet = new Label("Въезд на установку с дороги №:");
        enterInObjectFromStreetValue = new TextArea("");
        enterInObjectFromStreetValue.setPrefHeight(textAreaHeight);
        operatorLocation = new Label("Месторасположение операторной (квадрат дорог / ближайший перекресток):");
        operatorLocationValue = new TextArea("");
        operatorLocationValue.setPrefHeight(textAreaHeight);
        firstGridPane.add(objectLocation, 0, 34);
        firstGridPane.add(objectLocationValue, 0, 35);
        firstGridPane.add(enterInObjectFromStreet, 0, 36);
        firstGridPane.add(enterInObjectFromStreetValue, 0, 37);
        firstGridPane.add(operatorLocation, 0, 38);
        firstGridPane.add(operatorLocationValue, 0, 39);

        mostDangerousEquipment = new Label("Наиболее опасное оборудование (перечислить):");
        mostDangerousEquipment.setPadding(new Insets(upperPadding, 0, 0, 0));
        mostDangerousEquipmentValue = new TextArea("");
        mostDangerousEquipmentValue.setPrefHeight(textAreaHeight);
        mostPossibleEmergencySituation = new Label("Наиболее вероятная аварийная ситуация:");
        mostPossibleEmergencySituationValue = new TextArea("");
        mostPossibleEmergencySituationValue.setPrefHeight(textAreaHeight);
        mostDangerousEmergencySituation = new Label("Наиболее опасная аварийная ситуация:");
        mostDangerousEmergencySituationValue = new TextArea("");
        mostDangerousEmergencySituationValue.setPrefHeight(textAreaHeight);
        firstGridPane.add(mostDangerousEquipment, 0, 40);
        firstGridPane.add(mostDangerousEquipmentValue, 0, 41);
        firstGridPane.add(mostPossibleEmergencySituation, 0, 42);
        firstGridPane.add(mostPossibleEmergencySituationValue, 0, 43);
        firstGridPane.add(mostDangerousEmergencySituation, 0, 44);
        firstGridPane.add(mostDangerousEmergencySituationValue, 0, 45);

        firstGridPane.setPadding(new Insets(5, 5, 5, 5));

        oilTypeAndQuantityVBox = new VBox();
        oilTypeAndQuantityVBox.setPadding(new Insets(upperPadding, 0, 0, 0));
        HBox controllAddingOil = new HBox();
        Button addOilButton = new Button("+");
        addOilButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Label oilTypeLabel = new Label("Вид: ");
                Label oilQuantityLabel = new Label(", количество в тоннах: ");
                HBox itemOfTheOilTypeAndQuantity = new HBox();
                itemOfTheOilTypeAndQuantity.getChildren().add(oilTypeLabel);
                TextArea oilTypeValue = new TextArea();
                oilTypeValue.setPrefHeight(45);
                oilTypeValue.setPrefWidth(oilTypeValue.getPrefWidth() + 300);
                itemOfTheOilTypeAndQuantity.getChildren().add(oilTypeValue);
                itemOfTheOilTypeAndQuantity.getChildren().add(oilQuantityLabel);
                itemOfTheOilTypeAndQuantity.getChildren().add(new TextField());
                Button deleteOilButton = new Button("-");
                deleteOilButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        oilTypeAndQuantityVBox.getChildren().remove(itemOfTheOilTypeAndQuantity);
                    }
                });
                itemOfTheOilTypeAndQuantity.getChildren().add(deleteOilButton);
                oilTypeAndQuantityVBox.getChildren().add(itemOfTheOilTypeAndQuantity);
            }
        });
        controllAddingOil.getChildren().add(addOilButton);
        controllAddingOil.getChildren().add(new Label("Нефтепродукт на ОПО:"));
        oilTypeAndQuantityVBox.getChildren().add(controllAddingOil);

        personalProtectiveEquipmentTypeAndQuantityVBox = new VBox();
        personalProtectiveEquipmentTypeAndQuantityVBox.setPadding(new Insets(20, 0, 0, 0));
        HBox controllAddingPersonalProtectiveEquipment = new HBox();
        Button addPersonalProtectiveEquipmentButton = new Button("+");
        addPersonalProtectiveEquipmentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Label personalProtectiveEquipmentTypeLabel = new Label("Вид СИЗ: ");
                Label personalProtectiveEquipmentQuantityLabel = new Label(", количество: ");
                HBox itemOfThePersonalProtectiveEquipmentType = new HBox();
                itemOfThePersonalProtectiveEquipmentType.getChildren().add(personalProtectiveEquipmentTypeLabel);
                TextArea personalProtectiveEquipmentTypeValue = new TextArea();
                personalProtectiveEquipmentTypeValue.setPrefHeight(45);
                personalProtectiveEquipmentTypeValue.setPrefWidth(personalProtectiveEquipmentTypeValue.getPrefWidth() + 300);
                itemOfThePersonalProtectiveEquipmentType.getChildren().add(personalProtectiveEquipmentTypeValue);
                itemOfThePersonalProtectiveEquipmentType.getChildren().add(personalProtectiveEquipmentQuantityLabel);
                itemOfThePersonalProtectiveEquipmentType.getChildren().add(new TextField());
                Button deleteOilButton = new Button("-");
                deleteOilButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        personalProtectiveEquipmentTypeAndQuantityVBox.getChildren().remove(itemOfThePersonalProtectiveEquipmentType);
                    }
                });
                itemOfThePersonalProtectiveEquipmentType.getChildren().add(deleteOilButton);
                personalProtectiveEquipmentTypeAndQuantityVBox.getChildren().add(itemOfThePersonalProtectiveEquipmentType);
            }
        });
        controllAddingPersonalProtectiveEquipment.getChildren().add(addPersonalProtectiveEquipmentButton);
        controllAddingPersonalProtectiveEquipment.getChildren().add(new Label("Наличие средств индивидуальной защиты:"));
        personalProtectiveEquipmentTypeAndQuantityVBox.getChildren().add(controllAddingPersonalProtectiveEquipment);

        stateOfEmergencyWhatAndWhenHappenedVBox = new VBox();
        stateOfEmergencyWhatAndWhenHappenedVBox.setPadding(new Insets(20, 0, 0, 0));
        HBox controllAddingStateOfEmergency = new HBox();
        Button addStateOfEmergencyButton = new Button("+");
        addStateOfEmergencyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Label stateOfEmergencyWhatHappenedLabel = new Label("ЧТо произошло: ");
                Label stateOfEmergencyWhenHappenedLabel = new Label(". Когда произошло: ");
                HBox itemOfTheStateOfEmergencyWhatHappened = new HBox();
                itemOfTheStateOfEmergencyWhatHappened.getChildren().add(stateOfEmergencyWhatHappenedLabel);
                TextArea stateOfEmergencyWhatHappenedValue = new TextArea();
                stateOfEmergencyWhatHappenedValue.setPrefHeight(45);
                stateOfEmergencyWhatHappenedValue.setPrefWidth(stateOfEmergencyWhatHappenedValue.getPrefWidth() + 300);
                itemOfTheStateOfEmergencyWhatHappened.getChildren().add(stateOfEmergencyWhatHappenedValue);
                itemOfTheStateOfEmergencyWhatHappened.getChildren().add(stateOfEmergencyWhenHappenedLabel);
                itemOfTheStateOfEmergencyWhatHappened.getChildren().add(new DatePicker(LocalDate.now()));
                Button deleteOilButton = new Button("-");
                deleteOilButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        stateOfEmergencyWhatAndWhenHappenedVBox.getChildren().remove(itemOfTheStateOfEmergencyWhatHappened);
                    }
                });
                itemOfTheStateOfEmergencyWhatHappened.getChildren().add(deleteOilButton);
                stateOfEmergencyWhatAndWhenHappenedVBox.getChildren().add(itemOfTheStateOfEmergencyWhatHappened);
            }
        });
        controllAddingStateOfEmergency.getChildren().add(addStateOfEmergencyButton);
        controllAddingStateOfEmergency.getChildren().add(new Label("ЧС (происшествия), произошедшие на ОПО (за последние 3 года):"));
        stateOfEmergencyWhatAndWhenHappenedVBox.getChildren().add(controllAddingStateOfEmergency);

        firstGridPane.add(oilTypeAndQuantityVBox, 0, 46);
        firstGridPane.add(personalProtectiveEquipmentTypeAndQuantityVBox, 0, 47);
        firstGridPane.add(stateOfEmergencyWhatAndWhenHappenedVBox, 0, 48);

        containerForGridPanes.getChildren().add(firstGridPane);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setMinWidth(650);
        this.dialogStage.setMinHeight(600);
        dialogStage.widthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            perfomanceValue.setPrefWidth(dialogStage.getWidth() - 100);
        });
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setOperationIsAdd(boolean operation) {
        this.operationIsAdd = operation;
        if (!operation) {
            addButton.setText("Применить изменения");
        }
    }

    public void setFactoryObject(FactoryObject factoryObject) {
        addedFactoryObject = factoryObject;
        objectNameTextField.setText(addedFactoryObject.getName());
        startExploitationDateValue.setValue(addedFactoryObject.getStartExploitationDate());
        lastReconstructionDateValue.setValue(addedFactoryObject.getLastReconstructionDate());
        lastReconstructionMadeByValue.setText(addedFactoryObject.getLastReconstructionMadeBy());
        lastRepairsDateValue.setValue(addedFactoryObject.getLastRepairsDate());
        lastRepairsMadeByValue.setText(addedFactoryObject.getLastRepairsMadeBy());
        perfomanceValue.setText(addedFactoryObject.getPerfomance());
        numberOfEmployeesValue.setText(String.valueOf(addedFactoryObject.getNumberOfEmployees()));
        numberOfTeamsValue.setText(String.valueOf(addedFactoryObject.getNumberOfTeams()));
        teamsScheduleValue.setText(addedFactoryObject.getTeamsSchedule());
        maxNumberOfEmployeesInTeamValue.setText(String.valueOf(addedFactoryObject.getMaxNumberOfEmployeesInTeam()));
        minNumberOfEmployeesInTeamValue.setText(String.valueOf(addedFactoryObject.getMinNumberOfEmployeesInTeam()));
        objectAppointmentValue.setText(addedFactoryObject.getObjectAppointment());
        productionMethodValue.setText(addedFactoryObject.getProductionMethod());
        technologicalBlocksValue.setText(addedFactoryObject.getTechnologicalBlocks());
        resourceComeFromObjectsValue.setText(addedFactoryObject.getResourceComeFromObjects());
        resourceGoToObjectsValue.setText(addedFactoryObject.getResourceGoToObjects());
        auxiliaryObjectsValue.setText(addedFactoryObject.getAuxiliaryObjects());
        objectLocationValue.setText(addedFactoryObject.getObjectLocation());
        enterInObjectFromStreetValue.setText(addedFactoryObject.getEnterInObjectFromStreet());
        operatorLocationValue.setText(addedFactoryObject.getOperatorLocation());
        mostDangerousEquipmentValue.setText(addedFactoryObject.getMostDangerousEquipment());
        mostPossibleEmergencySituationValue.setText(addedFactoryObject.getMostPossibleEmergencySituation());
        mostDangerousEmergencySituationValue.setText(addedFactoryObject.getMostDangerousEmergencySituation());

        if (addedFactoryObject.getOilTypeAndQuantity() != null && !addedFactoryObject.getOilTypeAndQuantity().isEmpty()) {
            Set<String> oilTypes = addedFactoryObject.getOilTypeAndQuantity().keySet();
            Iterator<String> oilTypeIterator = oilTypes.iterator();
            while (oilTypeIterator.hasNext()) {
                String oilType = oilTypeIterator.next();
                if (StringUtils.isNotBlank(oilType)) {
                    double oilQuantity = addedFactoryObject.getOilTypeAndQuantity().get(oilType);
                    HBox itemOfTheOilTypeAndQuantity = new HBox();
                    itemOfTheOilTypeAndQuantity.getChildren().add(new Label("Вид: "));
                    itemOfTheOilTypeAndQuantity.getChildren().add(new TextField(oilType));
                    itemOfTheOilTypeAndQuantity.getChildren().add(new Label(", количество в тоннах: "));
                    itemOfTheOilTypeAndQuantity.getChildren().add(new TextField(String.valueOf(oilQuantity)));
                    Button deleteOilButton = new Button("-");
                    deleteOilButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            oilTypeAndQuantityVBox.getChildren().remove(itemOfTheOilTypeAndQuantity);
                        }
                    });
                    itemOfTheOilTypeAndQuantity.getChildren().add(deleteOilButton);
                    oilTypeAndQuantityVBox.getChildren().add(itemOfTheOilTypeAndQuantity);
                }
            }
        }
        if (addedFactoryObject.getPersonalProtectiveEquipmentTypeAndQuantity() != null && !addedFactoryObject.getPersonalProtectiveEquipmentTypeAndQuantity().isEmpty()) {
//                personalProtectiveEquipmentTypeAndQuantityVBox
            Set<String> personalProtectiveEquipmentTypes = addedFactoryObject.getPersonalProtectiveEquipmentTypeAndQuantity().keySet();
            Iterator<String> personalProtectiveEquipmentTypeIterator = personalProtectiveEquipmentTypes.iterator();
            while (personalProtectiveEquipmentTypeIterator.hasNext()) {
                String personalProtectiveEquipmentType = personalProtectiveEquipmentTypeIterator.next();
                if (StringUtils.isNotBlank(personalProtectiveEquipmentType)) {
                    int personalProtectiveEquipmentQuantity = addedFactoryObject.getPersonalProtectiveEquipmentTypeAndQuantity().get(personalProtectiveEquipmentType);
                    HBox itemOfThePersonalProtectiveEquipmentType = new HBox();
                    itemOfThePersonalProtectiveEquipmentType.getChildren().add(new Label("Вид СИЗ: "));
                    itemOfThePersonalProtectiveEquipmentType.getChildren().add(new TextField(personalProtectiveEquipmentType));
                    itemOfThePersonalProtectiveEquipmentType.getChildren().add(new Label(", количество: "));
                    itemOfThePersonalProtectiveEquipmentType.getChildren().add(new TextField(String.valueOf(personalProtectiveEquipmentQuantity)));
                    personalProtectiveEquipmentTypeAndQuantityVBox.getChildren().add(itemOfThePersonalProtectiveEquipmentType);
                }
            }
        }
        if (addedFactoryObject.getStateOfEmergencyWhatAndWhenHappened() != null && !addedFactoryObject.getStateOfEmergencyWhatAndWhenHappened().isEmpty()) {
//                stateOfEmergencyWhatAndWhenHappenedVBox
            Set<String> statesOfEmergencyWhatHappened = addedFactoryObject.getStateOfEmergencyWhatAndWhenHappened().keySet();
            Iterator<String> statesOfEmergencyWhatHappenedIterator = statesOfEmergencyWhatHappened.iterator();
            while (statesOfEmergencyWhatHappenedIterator.hasNext()) {
                String stateOfEmergencyWhatHappened = statesOfEmergencyWhatHappenedIterator.next();
                if (StringUtils.isNotBlank(stateOfEmergencyWhatHappened)) {
                    LocalDate stateOfEmergencyWhenHappened = addedFactoryObject.getStateOfEmergencyWhatAndWhenHappened().get(stateOfEmergencyWhatHappened);
                    HBox itemOfTheStateOfEmergencyWhatHappened = new HBox();
                    itemOfTheStateOfEmergencyWhatHappened.getChildren().add(new Label("ЧТо произошло: "));
                    itemOfTheStateOfEmergencyWhatHappened.getChildren().add(new TextField(stateOfEmergencyWhatHappened));
                    itemOfTheStateOfEmergencyWhatHappened.getChildren().add(new Label(". Когда произошло: "));
                    itemOfTheStateOfEmergencyWhatHappened.getChildren().add(new DatePicker(stateOfEmergencyWhenHappened));
                    stateOfEmergencyWhatAndWhenHappenedVBox.getChildren().add(itemOfTheStateOfEmergencyWhatHappened);
                }
            }
        }
    }

    public boolean isAddedFactoryObject() {
        return isAdded;
    }

    public FactoryObject getAddedFactoryObject() {
        return addedFactoryObject;
    }

    @FXML
    private void handleAddButton() {
        if (objectNameTextField.getText() != null && !objectNameTextField.getText().isEmpty()) {
            if (operationIsAdd) {
                addedFactoryObject = new FactoryObject();
                addedFactoryObject.setX(x);
                addedFactoryObject.setY(y);
                addedFactoryObject.setName(objectNameTextField.getText());

                addedFactoryObject.setStartExploitationDate(startExploitationDateValue.getValue());
                addedFactoryObject.setLastReconstructionDate(lastReconstructionDateValue.getValue());
                addedFactoryObject.setLastReconstructionMadeBy(lastReconstructionMadeByValue.getText());
                addedFactoryObject.setLastRepairsDate(lastRepairsDateValue.getValue());
                addedFactoryObject.setLastRepairsMadeBy(lastRepairsMadeByValue.getText());
                addedFactoryObject.setPerfomance(perfomanceValue.getText());
                addedFactoryObject.setNumberOfEmployees(getInteger(numberOfEmployeesValue.getText()));
                addedFactoryObject.setNumberOfTeams(getInteger(numberOfTeamsValue.getText()));
                addedFactoryObject.setTeamsSchedule(teamsScheduleValue.getText());
                addedFactoryObject.setMaxNumberOfEmployeesInTeam(getInteger(maxNumberOfEmployeesInTeamValue.getText()));
                addedFactoryObject.setMinNumberOfEmployeesInTeam(getInteger(minNumberOfEmployeesInTeamValue.getText()));
                addedFactoryObject.setObjectAppointment(objectAppointmentValue.getText());
                addedFactoryObject.setProductionMethod(productionMethodValue.getText());
                addedFactoryObject.setTechnologicalBlocks(technologicalBlocksValue.getText());
                addedFactoryObject.setResourceComeFromObjects(resourceComeFromObjectsValue.getText());
                addedFactoryObject.setResourceGoToObjects(resourceGoToObjectsValue.getText());
                addedFactoryObject.setAuxiliaryObjects(auxiliaryObjectsValue.getText());
                addedFactoryObject.setObjectLocation(objectLocationValue.getText());
                addedFactoryObject.setEnterInObjectFromStreet(enterInObjectFromStreetValue.getText());
                addedFactoryObject.setOperatorLocation(operatorLocationValue.getText());
                addedFactoryObject.setMostDangerousEquipment(mostDangerousEquipmentValue.getText());
                addedFactoryObject.setMostPossibleEmergencySituation(mostPossibleEmergencySituationValue.getText());
                addedFactoryObject.setMostDangerousEmergencySituation(mostDangerousEmergencySituationValue.getText());

                Map<String, Double> oilTypeAndQuantity = new HashMap<>();
//                oilTypeAndQuantity.put("oilType2", 22.0);
                ObservableList oilTypeAndQuantityList = oilTypeAndQuantityVBox.getChildren();
                for (int i = 1; i < oilTypeAndQuantityList.size(); i++) {
                    HBox item = (HBox) oilTypeAndQuantityList.get(i);
                    ObservableList oilValue = item.getChildren();
                    String oilType = ((TextArea) oilValue.get(1)).getText();
                    double oilQuantity = getDouble(((TextField) oilValue.get(3)).getText());
                    if (StringUtils.isNotBlank(oilType)) {
                        oilTypeAndQuantity.put(oilType, oilQuantity);
                    }
                }
                addedFactoryObject.setOilTypeAndQuantity(oilTypeAndQuantity);

                Map<String, Integer> personalProtectiveEquipmentTypeAndQuantity = new HashMap<>();
//                personalProtectiveEquipmentTypeAndQuantity.put("personalProtectiveEquipmentType2", 221);
                ObservableList personalProtectiveEquipmentTypeAndQuantityList = personalProtectiveEquipmentTypeAndQuantityVBox.getChildren();
                for (int i = 1; i < personalProtectiveEquipmentTypeAndQuantityList.size(); i++) {
                    HBox item = (HBox) personalProtectiveEquipmentTypeAndQuantityList.get(i);
                    ObservableList personalProtectiveEquipmentValue = item.getChildren();
                    String personalProtectiveEquipmentType = ((TextArea) personalProtectiveEquipmentValue.get(1)).getText();
                    int personalProtectiveEquipmentQuantity = getInteger(((TextField) personalProtectiveEquipmentValue.get(3)).getText());
                    if (StringUtils.isNotBlank(personalProtectiveEquipmentType)) {
                        personalProtectiveEquipmentTypeAndQuantity.put(personalProtectiveEquipmentType, personalProtectiveEquipmentQuantity);
                    }
                }
                addedFactoryObject.setPersonalProtectiveEquipmentTypeAndQuantity(personalProtectiveEquipmentTypeAndQuantity);

                Map<String, LocalDate> stateOfEmergencyWhatAndWhenHappened = new HashMap<>();
//                stateOfEmergencyWhatAndWhenHappened.put("personalProtectiveEquipmentType2", LocalDate.now());
                ObservableList stateOfEmergencyWhatAndWhenHappenedList = stateOfEmergencyWhatAndWhenHappenedVBox.getChildren();
                for (int i = 1; i < stateOfEmergencyWhatAndWhenHappenedList.size(); i++) {
                    HBox item = (HBox) stateOfEmergencyWhatAndWhenHappenedList.get(i);
                    ObservableList stateOfEmergencyWhatAndWhenHappenedValue = item.getChildren();
                    String stateOfEmergencyWhatHappened = ((TextArea) stateOfEmergencyWhatAndWhenHappenedValue.get(1)).getText();
                    LocalDate stateOfEmergencyWhenHappened = ((DatePicker) stateOfEmergencyWhatAndWhenHappenedValue.get(3)).getValue();
                    if (StringUtils.isNotBlank(stateOfEmergencyWhatHappened)) {
                        stateOfEmergencyWhatAndWhenHappened.put(stateOfEmergencyWhatHappened, stateOfEmergencyWhenHappened);
                    }
                }
                addedFactoryObject.setStateOfEmergencyWhatAndWhenHappened(stateOfEmergencyWhatAndWhenHappened);
                JpaApi.beginTransaction();
                JpaApi.persist(addedFactoryObject);

//                добавление связей между объектами
//                FactoryObject factoryObject = (FactoryObject)JpaApi.find(FactoryObject.class, Long.valueOf(1));
//                factoryObject.getLinkedFactoryObjects().add(addedFactoryObject);
//                addedFactoryObject.getLinkedFactoryObjects().add(factoryObject);
                JpaApi.closeTransaction();

                isAdded = true;
            } else {
                addedFactoryObject.setName(objectNameTextField.getText());

                addedFactoryObject.setStartExploitationDate(startExploitationDateValue.getValue());
                addedFactoryObject.setLastReconstructionDate(lastReconstructionDateValue.getValue());
                addedFactoryObject.setLastReconstructionMadeBy(lastReconstructionMadeByValue.getText());
                addedFactoryObject.setLastRepairsDate(lastRepairsDateValue.getValue());
                addedFactoryObject.setLastRepairsMadeBy(lastRepairsMadeByValue.getText());
                addedFactoryObject.setPerfomance(perfomanceValue.getText());
                addedFactoryObject.setNumberOfEmployees(getInteger(numberOfEmployeesValue.getText()));
                addedFactoryObject.setNumberOfTeams(getInteger(numberOfTeamsValue.getText()));
                addedFactoryObject.setTeamsSchedule(teamsScheduleValue.getText());
                addedFactoryObject.setMaxNumberOfEmployeesInTeam(getInteger(maxNumberOfEmployeesInTeamValue.getText()));
                addedFactoryObject.setMinNumberOfEmployeesInTeam(getInteger(minNumberOfEmployeesInTeamValue.getText()));
                addedFactoryObject.setObjectAppointment(objectAppointmentValue.getText());
                addedFactoryObject.setProductionMethod(productionMethodValue.getText());
                addedFactoryObject.setTechnologicalBlocks(technologicalBlocksValue.getText());
                addedFactoryObject.setResourceComeFromObjects(resourceComeFromObjectsValue.getText());
                addedFactoryObject.setResourceGoToObjects(resourceGoToObjectsValue.getText());
                addedFactoryObject.setAuxiliaryObjects(auxiliaryObjectsValue.getText());
                addedFactoryObject.setObjectLocation(objectLocationValue.getText());
                addedFactoryObject.setEnterInObjectFromStreet(enterInObjectFromStreetValue.getText());
                addedFactoryObject.setOperatorLocation(operatorLocationValue.getText());
                addedFactoryObject.setMostDangerousEquipment(mostDangerousEquipmentValue.getText());
                addedFactoryObject.setMostPossibleEmergencySituation(mostPossibleEmergencySituationValue.getText());
                addedFactoryObject.setMostDangerousEmergencySituation(mostDangerousEmergencySituationValue.getText());

                Map<String, Double> oilTypeAndQuantity = new HashMap<>();
//                oilTypeAndQuantity.put("oilType2", 22.0);
                ObservableList oilTypeAndQuantityList = oilTypeAndQuantityVBox.getChildren();
                for (int i = 1; i < oilTypeAndQuantityList.size(); i++) {
                    HBox item = (HBox) oilTypeAndQuantityList.get(i);
                    ObservableList oilValue = item.getChildren();
                    String oilType = ((TextArea) oilValue.get(1)).getText();
                    double oilQuantity = getDouble(((TextField) oilValue.get(3)).getText());
                    if (StringUtils.isNotBlank(oilType)) {
                        oilTypeAndQuantity.put(oilType, oilQuantity);
                    }
                }
                addedFactoryObject.setOilTypeAndQuantity(oilTypeAndQuantity);

                Map<String, Integer> personalProtectiveEquipmentTypeAndQuantity = new HashMap<>();
//                personalProtectiveEquipmentTypeAndQuantity.put("personalProtectiveEquipmentType2", 221);
                ObservableList personalProtectiveEquipmentTypeAndQuantityList = personalProtectiveEquipmentTypeAndQuantityVBox.getChildren();
                for (int i = 1; i < personalProtectiveEquipmentTypeAndQuantityList.size(); i++) {
                    HBox item = (HBox) personalProtectiveEquipmentTypeAndQuantityList.get(i);
                    ObservableList personalProtectiveEquipmentValue = item.getChildren();
                    String personalProtectiveEquipmentType = ((TextArea) personalProtectiveEquipmentValue.get(1)).getText();
                    int personalProtectiveEquipmentQuantity = getInteger(((TextField) personalProtectiveEquipmentValue.get(3)).getText());
                    if (StringUtils.isNotBlank(personalProtectiveEquipmentType)) {
                        personalProtectiveEquipmentTypeAndQuantity.put(personalProtectiveEquipmentType, personalProtectiveEquipmentQuantity);
                    }
                }
                addedFactoryObject.setPersonalProtectiveEquipmentTypeAndQuantity(personalProtectiveEquipmentTypeAndQuantity);

                Map<String, LocalDate> stateOfEmergencyWhatAndWhenHappened = new HashMap<>();
//                stateOfEmergencyWhatAndWhenHappened.put("personalProtectiveEquipmentType2", LocalDate.now());
                ObservableList stateOfEmergencyWhatAndWhenHappenedList = stateOfEmergencyWhatAndWhenHappenedVBox.getChildren();
                for (int i = 1; i < stateOfEmergencyWhatAndWhenHappenedList.size(); i++) {
                    HBox item = (HBox) stateOfEmergencyWhatAndWhenHappenedList.get(i);
                    ObservableList stateOfEmergencyWhatAndWhenHappenedValue = item.getChildren();
                    String stateOfEmergencyWhatHappened = ((TextArea) stateOfEmergencyWhatAndWhenHappenedValue.get(1)).getText();
                    LocalDate stateOfEmergencyWhenHappened = ((DatePicker) stateOfEmergencyWhatAndWhenHappenedValue.get(3)).getValue();
                    if (StringUtils.isNotBlank(stateOfEmergencyWhatHappened)) {
                        stateOfEmergencyWhatAndWhenHappened.put(stateOfEmergencyWhatHappened, stateOfEmergencyWhenHappened);
                    }
                }
                addedFactoryObject.setStateOfEmergencyWhatAndWhenHappened(stateOfEmergencyWhatAndWhenHappened);

                JpaApi.beginTransaction();
                FactoryObject factoryObjectFromDB = (FactoryObject) JpaApi.find(FactoryObject.class, addedFactoryObject.getId());
                factoryObjectFromDB.setName(objectNameTextField.getText());
                factoryObjectFromDB.setStartExploitationDate(startExploitationDateValue.getValue());
                factoryObjectFromDB.setLastReconstructionDate(lastReconstructionDateValue.getValue());
                factoryObjectFromDB.setLastReconstructionMadeBy(lastReconstructionMadeByValue.getText());
                factoryObjectFromDB.setLastRepairsDate(lastRepairsDateValue.getValue());
                factoryObjectFromDB.setLastRepairsMadeBy(lastRepairsMadeByValue.getText());
                factoryObjectFromDB.setPerfomance(perfomanceValue.getText());
                factoryObjectFromDB.setNumberOfEmployees(getInteger(numberOfEmployeesValue.getText()));
                factoryObjectFromDB.setNumberOfTeams(getInteger(numberOfTeamsValue.getText()));
                factoryObjectFromDB.setTeamsSchedule(teamsScheduleValue.getText());
                factoryObjectFromDB.setMaxNumberOfEmployeesInTeam(getInteger(maxNumberOfEmployeesInTeamValue.getText()));
                factoryObjectFromDB.setMinNumberOfEmployeesInTeam(getInteger(minNumberOfEmployeesInTeamValue.getText()));
                factoryObjectFromDB.setObjectAppointment(objectAppointmentValue.getText());
                factoryObjectFromDB.setProductionMethod(productionMethodValue.getText());
                factoryObjectFromDB.setTechnologicalBlocks(technologicalBlocksValue.getText());
                factoryObjectFromDB.setResourceComeFromObjects(resourceComeFromObjectsValue.getText());
                factoryObjectFromDB.setResourceGoToObjects(resourceGoToObjectsValue.getText());
                factoryObjectFromDB.setAuxiliaryObjects(auxiliaryObjectsValue.getText());
                factoryObjectFromDB.setObjectLocation(objectLocationValue.getText());
                factoryObjectFromDB.setEnterInObjectFromStreet(enterInObjectFromStreetValue.getText());
                factoryObjectFromDB.setOperatorLocation(operatorLocationValue.getText());
                factoryObjectFromDB.setMostDangerousEquipment(mostDangerousEquipmentValue.getText());
                factoryObjectFromDB.setMostPossibleEmergencySituation(mostPossibleEmergencySituationValue.getText());
                factoryObjectFromDB.setMostDangerousEmergencySituation(mostDangerousEmergencySituationValue.getText());
                factoryObjectFromDB.setOilTypeAndQuantity(oilTypeAndQuantity);
                factoryObjectFromDB.setPersonalProtectiveEquipmentTypeAndQuantity(personalProtectiveEquipmentTypeAndQuantity);
                factoryObjectFromDB.setStateOfEmergencyWhatAndWhenHappened(stateOfEmergencyWhatAndWhenHappened);
                JpaApi.persist(factoryObjectFromDB);

//                добавление связей между объектами
//                FactoryObject factoryObject = (FactoryObject)JpaApi.find(FactoryObject.class, Long.valueOf(1));
//                factoryObject.getLinkedFactoryObjects().add(addedFactoryObject);
//                addedFactoryObject.getLinkedFactoryObjects().add(factoryObject);
                JpaApi.closeTransaction();
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

    private int getInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private double getDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

}
