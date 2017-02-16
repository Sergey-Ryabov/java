package org.javafx.map.controllers;

import org.javafx.map.MainApp;
import org.javafx.map.model.FactoryObject;
import org.javafx.map.utils.JpaApi;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class MainController {
    
//    TODO: переместить все константы и добавить поддержку выбора изображения с переключением на другую БД
    private final static String DASH = "-";

    @FXML
    private VBox root_vbox;
    @FXML
    private ListView<String> map_listview;
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
    @FXML
    private MenuButton map_pin;
    @FXML
    private MenuItem pin_info;
    @FXML
    private ImageView imageView;
    @FXML
    private Pane containerForGridPanes;

    //    7x2
    private Label name;
    private TextField nameValue;

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

    private final List<FactoryObject> factoryObjectsList = new LinkedList<>();
    ObservableList<String> factoryObjectNamesList = FXCollections.observableArrayList();
    Group zoomGroup;

    FactoryObject selectedFactoryObject = null;
    final DoubleProperty zoomProperty = new SimpleDoubleProperty(1200);

    private final double upperPadding = 30;
    private final double textAreaHeight = 180; //видно 8 строки

    private boolean isMousePressed = false;
    private boolean isMouseDragged = false;

    @FXML
    void initialize() {
        initializeData();
        initializeUI();

        assert map_listview != null : "fx:id=\"map_listview\" was not injected: check your FXML file 'airportapp.fxml'.";
        assert root_vbox != null : "fx:id=\"root_vbox\" was not injected: check your FXML file 'airportapp.fxml'.";
        assert map_scrollpane != null : "fx:id=\"map_scrollpane\" was not injected: check your FXML file 'airportapp.fxml'.";
        assert map_pin != null : "fx:id=\"map_pin\" was not injected: check your FXML file 'airportapp.fxml'.";
        assert pin_info != null : "fx:id=\"pin_info\" was not injected: check your FXML file 'airportapp.fxml'.";
        assert zoom_slider != null : "fx:id=\"zoom_slider\" was not injected: check your FXML file 'airportapp.fxml'.";

//        svg path
        map_pin.setStyle("-fx-shape: \"M16 0c-5.523 0-10 4.477-10 10 0 10 10 22 10 22s10-12 10-22c0-5.523-4.477-10-10-10zM16 16.125c-3.383 0-6.125-2.742-6.125-6.125s2.742-6.125 6.125-6.125 6.125 2.742 6.125 6.125-2.742 6.125-6.125 6.125zM12.125 10c0-2.14 1.735-3.875 3.875-3.875s3.875 1.735 3.875 3.875c0 2.14-1.735 3.875-3.875 3.875s-3.875-1.735-3.875-3.875z\";    \n"
                + "    -fx-background-insets: 0, 2;\n"
                + "    -fx-background-color: rgba(43, 177, 192, 1), rgba(60, 214, 231, 1);\n"
                + "    -fx-effect: dropshadow( two-pass-box, rgba(0, 0, 0, 0.5), 10, 0.0, 0, 10 );\n"
                + "    -fx-pref-width: 30;\n"
                + "    -fx-pref-height: 45;");

        map_pin.setVisible(false);

        zoom_slider.setMin(1);
        zoom_slider.setMax(4);
        zoom_slider.setValue(1);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));

        // Wrap scroll content in a Group so ScrollPane re-computes scroll bars
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
        map_scrollpane.setPannable(true);

        map_scrollpane.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                event.consume();
                if (event.getDeltaY() > 0) {
                    double sliderVal = zoom_slider.getValue();
                    zoom_slider.setValue(sliderVal += 0.1);
                } else {
                    double sliderVal = zoom_slider.getValue();
                    zoom_slider.setValue(sliderVal + -0.1);
                }
            }
        });
        
    }

    private void initializeData() {
//        (x: 6188.0, y: 5104.0) -- (sceneX: 463.0, sceneY: 254.0) -- (screenX: 841.0, screenY: 356.0)
        InputStream is = null;
        File img = new File("./sources/resources/img/map.jpg");

        try {
            is = new FileInputStream(img);
        } catch (FileNotFoundException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Внимание");
            alert.setContentText("Не найдено изображение: " + img.getAbsolutePath());
        }
        imageView.setImage(new Image(is));
        imageView.setFitWidth(zoomProperty.get() * 2);
        imageView.setFitHeight(zoomProperty.get() * 1);

        JpaApi.beginTransaction();
        factoryObjectsList.addAll(JpaApi.getResultListFromQuery("from FactoryObject"));
        JpaApi.closeTransaction();

        for (FactoryObject factoryObject : factoryObjectsList) {
            factoryObjectNamesList.add(factoryObject.getName());
        }

        Collections.sort(factoryObjectNamesList);
        map_listview.setItems(factoryObjectNamesList);

    }

    private void initializeUI() {
        GridPane firstGridPane = new GridPane();
        name = new Label("Наименование:");
        nameValue = new TextField(DASH);
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
        firstGridPane.add(name, 0, 0);
        firstGridPane.add(nameValue, 0, 1);
        firstGridPane.add(startExploitationDate, 0, 2);
        firstGridPane.add(startExploitationDateValue, 0, 3);
        firstGridPane.add(lastReconstructionDate, 0, 4);
        firstGridPane.add(lastReconstructionDateValue, 0, 5);
        firstGridPane.add(lastReconstructionMadeBy, 0, 6);
        firstGridPane.add(lastReconstructionMadeByValue, 0, 7);
        firstGridPane.add(lastRepairsDate, 0, 8);
        firstGridPane.add(lastRepairsDateValue, 0, 9);
        firstGridPane.add(lastRepairsMadeBy, 0, 10);
        firstGridPane.add(lastRepairsMadeByValue, 0, 11);
        firstGridPane.add(perfomance, 0, 12);
        firstGridPane.add(perfomanceValue, 0, 13);
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
        firstGridPane.add(numberOfEmployees, 0, 14);
        firstGridPane.add(numberOfEmployeesValue, 0, 15);
        firstGridPane.add(numberOfTeams, 0, 16);
        firstGridPane.add(numberOfTeamsValue, 0, 17);
        firstGridPane.add(teamsSchedule, 0, 18);
        firstGridPane.add(teamsScheduleValue, 0, 19);
        firstGridPane.add(maxNumberOfEmployeesInTeam, 0, 20);
        firstGridPane.add(maxNumberOfEmployeesInTeamValue, 0, 21);
        firstGridPane.add(minNumberOfEmployeesInTeam, 0, 22);
        firstGridPane.add(minNumberOfEmployeesInTeamValue, 0, 23);

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
        firstGridPane.add(objectAppointment, 0, 24);
        firstGridPane.add(objectAppointmentValue, 0, 25);
        firstGridPane.add(productionMethod, 0, 26);
        firstGridPane.add(productionMethodValue, 0, 27);
        firstGridPane.add(technologicalBlocks, 0, 28);
        firstGridPane.add(technologicalBlocksValue, 0, 29);
        firstGridPane.add(resourceComeFromObjects, 0, 30);
        firstGridPane.add(resourceComeFromObjectsValue, 0, 31);
        firstGridPane.add(resourceGoToObjects, 0, 32);
        firstGridPane.add(resourceGoToObjectsValue, 0, 33);
        firstGridPane.add(auxiliaryObjects, 0, 34);
        firstGridPane.add(auxiliaryObjectsValue, 0, 35);

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
        firstGridPane.add(objectLocation, 0, 36);
        firstGridPane.add(objectLocationValue, 0, 37);
        firstGridPane.add(enterInObjectFromStreet, 0, 38);
        firstGridPane.add(enterInObjectFromStreetValue, 0, 39);
        firstGridPane.add(operatorLocation, 0, 40);
        firstGridPane.add(operatorLocationValue, 0, 41);

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
        firstGridPane.add(mostDangerousEquipment, 0, 42);
        firstGridPane.add(mostDangerousEquipmentValue, 0, 43);
        firstGridPane.add(mostPossibleEmergencySituation, 0, 44);
        firstGridPane.add(mostPossibleEmergencySituationValue, 0, 45);
        firstGridPane.add(mostDangerousEmergencySituation, 0, 46);
        firstGridPane.add(mostDangerousEmergencySituationValue, 0, 47);

        firstGridPane.setPadding(new Insets(5, 5, 5, 5));

        oilTypeAndQuantityVBox = new VBox();
        oilTypeAndQuantityVBox.setPadding(new Insets(upperPadding, 0, 0, 0));

        personalProtectiveEquipmentTypeAndQuantityVBox = new VBox();
        personalProtectiveEquipmentTypeAndQuantityVBox.setPadding(new Insets(20, 0, 0, 0));

        stateOfEmergencyWhatAndWhenHappenedVBox = new VBox();
        stateOfEmergencyWhatAndWhenHappenedVBox.setPadding(new Insets(20, 0, 0, 0));

        firstGridPane.add(oilTypeAndQuantityVBox, 0, 48);
        firstGridPane.add(personalProtectiveEquipmentTypeAndQuantityVBox, 0, 49);
        firstGridPane.add(stateOfEmergencyWhatAndWhenHappenedVBox, 0, 50);
        containerForGridPanes.getChildren().add(firstGridPane);
//        containerForGridPanes.setPrefWidth(300);

        nameValue.setEditable(false);
        startExploitationDateValue.setEditable(false);
        lastReconstructionDateValue.setEditable(false);
        lastReconstructionMadeByValue.setEditable(false);
        lastRepairsDateValue.setEditable(false);
        lastRepairsMadeByValue.setEditable(false);
        perfomanceValue.setEditable(false);

        numberOfEmployeesValue.setEditable(false);
        numberOfTeamsValue.setEditable(false);
        teamsScheduleValue.setEditable(false);
        maxNumberOfEmployeesInTeamValue.setEditable(false);
        minNumberOfEmployeesInTeamValue.setEditable(false);

        objectAppointmentValue.setEditable(false);
        productionMethodValue.setEditable(false);
        technologicalBlocksValue.setEditable(false);
        resourceComeFromObjectsValue.setEditable(false);
        resourceGoToObjectsValue.setEditable(false);
        auxiliaryObjectsValue.setEditable(false);

        objectLocationValue.setEditable(false);
        enterInObjectFromStreetValue.setEditable(false);
        operatorLocationValue.setEditable(false);

        mostDangerousEquipmentValue.setEditable(false);
        mostPossibleEmergencySituationValue.setEditable(false);
        mostDangerousEmergencySituationValue.setEditable(false);

        nameValue.setVisible(false);
        startExploitationDateValue.setVisible(false);
        lastReconstructionDateValue.setVisible(false);
        lastReconstructionMadeByValue.setVisible(false);
        lastRepairsDateValue.setVisible(false);
        lastRepairsMadeByValue.setVisible(false);
        perfomanceValue.setVisible(false);

        numberOfEmployeesValue.setVisible(false);
        numberOfTeamsValue.setVisible(false);
        teamsScheduleValue.setVisible(false);
        maxNumberOfEmployeesInTeamValue.setVisible(false);
        minNumberOfEmployeesInTeamValue.setVisible(false);

        objectAppointmentValue.setVisible(false);
        productionMethodValue.setVisible(false);
        technologicalBlocksValue.setVisible(false);
        resourceComeFromObjectsValue.setVisible(false);
        resourceGoToObjectsValue.setVisible(false);
        auxiliaryObjectsValue.setVisible(false);

        objectLocationValue.setVisible(false);
        enterInObjectFromStreetValue.setVisible(false);
        operatorLocationValue.setVisible(false);

        mostDangerousEquipmentValue.setVisible(false);
        mostPossibleEmergencySituationValue.setVisible(false);
        mostDangerousEmergencySituationValue.setVisible(false);
    }

    @FXML
    void listClicked(MouseEvent event) {
        String factoryObjectName = map_listview.getSelectionModel().getSelectedItem();
        for (FactoryObject factoryObject : factoryObjectsList) {
            if (factoryObject.getName().equalsIgnoreCase(factoryObjectName)) {
                selectedFactoryObject = factoryObject;
                break;
            }
        }

        if (selectedFactoryObject != null) {
            updateFields();

            // animation scroll to new position
            double mapWidth = zoomGroup.getBoundsInLocal().getWidth();
            double mapHeight = zoomGroup.getBoundsInLocal().getHeight();
            double scrollH = selectedFactoryObject.getX() / mapWidth;
            double scrollV = selectedFactoryObject.getY() / mapHeight;
            final Timeline timeline = new Timeline();
            final KeyValue kv1 = new KeyValue(map_scrollpane.hvalueProperty(), scrollH);
            final KeyValue kv2 = new KeyValue(map_scrollpane.vvalueProperty(), scrollV);
            final KeyFrame kf = new KeyFrame(Duration.millis(500), kv1, kv2);
            timeline.getKeyFrames().add(kf);
            timeline.play();

            // move the pin and set it's info
            double pinW = map_pin.getBoundsInLocal().getWidth();
            double pinH = map_pin.getBoundsInLocal().getHeight();
            map_pin.setLayoutX(selectedFactoryObject.getX() - (pinW / 2));
            map_pin.setLayoutY(selectedFactoryObject.getY() - (pinH));
            pin_info.setText(selectedFactoryObject.getName());
            map_pin.setVisible(true);

            nameValue.setVisible(true);
            startExploitationDateValue.setVisible(true);
            lastReconstructionDateValue.setVisible(true);
            lastReconstructionMadeByValue.setVisible(true);
            lastRepairsDateValue.setVisible(true);
            lastRepairsMadeByValue.setVisible(true);
            perfomanceValue.setVisible(true);

            numberOfEmployeesValue.setVisible(true);
            numberOfTeamsValue.setVisible(true);
            teamsScheduleValue.setVisible(true);
            maxNumberOfEmployeesInTeamValue.setVisible(true);
            minNumberOfEmployeesInTeamValue.setVisible(true);

            objectAppointmentValue.setVisible(true);
            productionMethodValue.setVisible(true);
            technologicalBlocksValue.setVisible(true);
            resourceComeFromObjectsValue.setVisible(true);
            resourceGoToObjectsValue.setVisible(true);
            auxiliaryObjectsValue.setVisible(true);

            objectLocationValue.setVisible(true);
            enterInObjectFromStreetValue.setVisible(true);
            operatorLocationValue.setVisible(true);

            mostDangerousEquipmentValue.setVisible(true);
            mostPossibleEmergencySituationValue.setVisible(true);
            mostDangerousEmergencySituationValue.setVisible(true);
        }
    }

    @FXML
    void zoomIn(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.1);
    }

    @FXML
    void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + -0.1);
    }

    private void zoom(double scaleValue) {
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }

    @FXML
    private void onMousePressed(MouseEvent event) throws IOException {
        isMousePressed = true;
    }

    @FXML
    private void onMouseDragged(MouseEvent event) throws IOException {
        if (isMousePressed) {
            isMouseDragged = true;
        } else {
            isMousePressed = isMouseDragged = false;
        }
    }

    @FXML
    private void onMouseClicked(MouseEvent event) throws IOException {
        if (!(isMousePressed && isMouseDragged)) {

            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_FactoryObject_Scene.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить объект");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(new Stage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            FactoryObjectChangesController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOperationIsAdd(true);
            controller.setX(event.getX());
            controller.setY(event.getY());

            dialogStage.showAndWait();
            if (controller.isAddedFactoryObject()) {
                FactoryObject addedFactoryObject = controller.getAddedFactoryObject();
                factoryObjectsList.add(addedFactoryObject);
                map_listview.getItems().add(addedFactoryObject.getName());
                Collections.sort(map_listview.getItems());
            }
        }
        isMousePressed = isMouseDragged = false;
    }

    @FXML
    private void handleEditObjectButtonAction(ActionEvent event) throws IOException {
        if (selectedFactoryObject != null) {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Add_Edit_FactoryObject_Scene.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактировать объект");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(new Stage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            FactoryObjectChangesController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOperationIsAdd(false);
            controller.setFactoryObject(selectedFactoryObject);

            String selectedFactoryObjectOldName = selectedFactoryObject.getName();
            dialogStage.showAndWait();

            updateFields();
            if (!selectedFactoryObjectOldName.equals(selectedFactoryObject.getName())) {
                for (int i = 0; i < map_listview.getItems().size(); i++) {
                    if (map_listview.getItems().get(i).equals(selectedFactoryObjectOldName)) {
                        map_listview.getItems().set(i, selectedFactoryObject.getName());
                    }
                }
                Collections.sort(map_listview.getItems());
            }
        }
    }

    @FXML
    private void handleDeleteObjectButtonAction(ActionEvent event) {
        if (selectedFactoryObject != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Подтверждение удаления");
            confirmAlert.setHeaderText("Необходимо водтвердить удаление.");
            confirmAlert.setContentText("Вы уверены, что хотите удалить этот объект?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    JpaApi.beginTransaction();
                    JpaApi.remove(JpaApi.find(FactoryObject.class, selectedFactoryObject.getId()));
                    JpaApi.closeTransaction();

                    factoryObjectsList.remove(selectedFactoryObject);
                    map_listview.getItems().remove(selectedFactoryObject.getName());

                    selectedFactoryObject = null;
                    map_pin.setVisible(false);

                    nameValue.setVisible(false);
                    startExploitationDateValue.setVisible(false);
                    lastReconstructionDateValue.setVisible(false);
                    lastReconstructionMadeByValue.setVisible(false);
                    lastRepairsDateValue.setVisible(false);
                    lastRepairsMadeByValue.setVisible(false);
                    perfomanceValue.setVisible(false);

                    numberOfEmployeesValue.setVisible(false);
                    numberOfTeamsValue.setVisible(false);
                    teamsScheduleValue.setVisible(false);
                    maxNumberOfEmployeesInTeamValue.setVisible(false);
                    minNumberOfEmployeesInTeamValue.setVisible(false);

                    objectAppointmentValue.setVisible(false);
                    productionMethodValue.setVisible(false);
                    technologicalBlocksValue.setVisible(false);
                    resourceComeFromObjectsValue.setVisible(false);
                    resourceGoToObjectsValue.setVisible(false);
                    auxiliaryObjectsValue.setVisible(false);

                    objectLocationValue.setVisible(false);
                    enterInObjectFromStreetValue.setVisible(false);
                    operatorLocationValue.setVisible(false);

                    mostDangerousEquipmentValue.setVisible(false);
                    mostPossibleEmergencySituationValue.setVisible(false);
                    mostDangerousEmergencySituationValue.setVisible(false);

                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Внимание");
                    alert.setHeaderText("Вы не можите удалить данную должность.");
                    alert.setContentText("До тех пор пока она назначена у сотрудника.");
                    alert.showAndWait();

                    JpaApi.closeEntityManager();
                }
            }
        } else {
//            System.out.println("selectedFactoryObject = null");
        }
    }

    private void updateFields() {
        nameValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getName())) ? selectedFactoryObject.getName() : DASH));
        startExploitationDateValue.setValue(selectedFactoryObject.getStartExploitationDate());
        lastReconstructionDateValue.setValue(selectedFactoryObject.getLastReconstructionDate());
        lastReconstructionMadeByValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getLastReconstructionMadeBy())) ? selectedFactoryObject.getLastReconstructionMadeBy() : DASH));
        lastRepairsDateValue.setValue(selectedFactoryObject.getLastRepairsDate());
        lastRepairsMadeByValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getLastRepairsMadeBy())) ? selectedFactoryObject.getLastRepairsMadeBy() : DASH));
        perfomanceValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getPerfomance())) ? selectedFactoryObject.getPerfomance() : DASH));

        numberOfEmployeesValue.setText(((StringUtils.isNotBlank(String.valueOf(selectedFactoryObject.getNumberOfEmployees()))) ? String.valueOf(selectedFactoryObject.getNumberOfEmployees()) : DASH));
        numberOfTeamsValue.setText(((StringUtils.isNotBlank(String.valueOf(selectedFactoryObject.getNumberOfTeams()))) ? String.valueOf(selectedFactoryObject.getNumberOfTeams()) : DASH));
        teamsScheduleValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getTeamsSchedule())) ? selectedFactoryObject.getTeamsSchedule() : DASH));
        maxNumberOfEmployeesInTeamValue.setText(((StringUtils.isNotBlank(String.valueOf(selectedFactoryObject.getMaxNumberOfEmployeesInTeam()))) ? String.valueOf(selectedFactoryObject.getMaxNumberOfEmployeesInTeam()) : DASH));
        minNumberOfEmployeesInTeamValue.setText(((StringUtils.isNotBlank(String.valueOf(selectedFactoryObject.getMinNumberOfEmployeesInTeam()))) ? String.valueOf(selectedFactoryObject.getMinNumberOfEmployeesInTeam()) : DASH));

        objectAppointmentValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getObjectAppointment())) ? selectedFactoryObject.getObjectAppointment() : DASH));
        productionMethodValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getProductionMethod())) ? selectedFactoryObject.getProductionMethod() : DASH));
        technologicalBlocksValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getTechnologicalBlocks())) ? selectedFactoryObject.getTechnologicalBlocks() : DASH));
        resourceComeFromObjectsValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getResourceComeFromObjects())) ? selectedFactoryObject.getResourceComeFromObjects() : DASH));
        resourceGoToObjectsValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getResourceGoToObjects())) ? selectedFactoryObject.getResourceGoToObjects() : DASH));
        auxiliaryObjectsValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getAuxiliaryObjects())) ? selectedFactoryObject.getAuxiliaryObjects() : DASH));

        objectLocationValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getObjectLocation())) ? selectedFactoryObject.getObjectLocation() : DASH));
        enterInObjectFromStreetValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getEnterInObjectFromStreet())) ? selectedFactoryObject.getEnterInObjectFromStreet() : DASH));
        operatorLocationValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getOperatorLocation())) ? selectedFactoryObject.getOperatorLocation() : DASH));

        mostDangerousEquipmentValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getMostDangerousEquipment())) ? selectedFactoryObject.getMostDangerousEquipment() : DASH));
        mostPossibleEmergencySituationValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getMostPossibleEmergencySituation())) ? selectedFactoryObject.getMostPossibleEmergencySituation() : DASH));
        mostDangerousEmergencySituationValue.setText(((StringUtils.isNotBlank(selectedFactoryObject.getMostDangerousEmergencySituation())) ? selectedFactoryObject.getMostDangerousEmergencySituation() : DASH));

        oilTypeAndQuantityVBox.getChildren().remove(0, oilTypeAndQuantityVBox.getChildren().size());
        if (selectedFactoryObject.getOilTypeAndQuantity() != null && !selectedFactoryObject.getOilTypeAndQuantity().isEmpty()) {
//                oilTypeAndQuantityVBox
            oilTypeAndQuantityVBox.getChildren().add(new Label("Нефтепродукт на ОПО:"));
            Set<String> oilTypes = selectedFactoryObject.getOilTypeAndQuantity().keySet();
//            containerForGridPanes.setPrefHeight(containerForGridPanes.getHeight() + 100 + oilTypes.size() * 10);
            Iterator<String> oilTypeIterator = oilTypes.iterator();
            while (oilTypeIterator.hasNext()) {
                String oilType = oilTypeIterator.next();
                if (StringUtils.isNotBlank(oilType)) {
                    double oilQuantity = selectedFactoryObject.getOilTypeAndQuantity().get(oilType);
                    HBox itemOfTheOilTypeAndQuantity = new HBox();
                    itemOfTheOilTypeAndQuantity.getChildren().add(new Label("Вид: "));
                    TextArea oilTypeValue = new TextArea(oilType);
                    oilTypeValue.setPrefHeight(45);
                    oilTypeValue.setEditable(false);
                    itemOfTheOilTypeAndQuantity.getChildren().add(oilTypeValue);
                    itemOfTheOilTypeAndQuantity.getChildren().add(new Label(", количество в тоннах: "));
                    itemOfTheOilTypeAndQuantity.getChildren().add(new Label(String.valueOf(oilQuantity)));
                    oilTypeAndQuantityVBox.getChildren().add(itemOfTheOilTypeAndQuantity);
                }
            }
        }
        personalProtectiveEquipmentTypeAndQuantityVBox.getChildren().remove(0, personalProtectiveEquipmentTypeAndQuantityVBox.getChildren().size());
        if (selectedFactoryObject.getPersonalProtectiveEquipmentTypeAndQuantity() != null && !selectedFactoryObject.getPersonalProtectiveEquipmentTypeAndQuantity().isEmpty()) {
//                personalProtectiveEquipmentTypeAndQuantityVBox
            personalProtectiveEquipmentTypeAndQuantityVBox.getChildren().add(new Label("Наличие средств индивидуальной защиты:"));
            Set<String> personalProtectiveEquipmentTypes = selectedFactoryObject.getPersonalProtectiveEquipmentTypeAndQuantity().keySet();
//            containerForGridPanes.setPrefHeight(containerForGridPanes.getHeight() + 100 + personalProtectiveEquipmentTypes.size() * 10);
            Iterator<String> personalProtectiveEquipmentTypeIterator = personalProtectiveEquipmentTypes.iterator();
            while (personalProtectiveEquipmentTypeIterator.hasNext()) {
                String personalProtectiveEquipmentType = personalProtectiveEquipmentTypeIterator.next();
                if (StringUtils.isNotBlank(personalProtectiveEquipmentType)) {
                    int personalProtectiveEquipmentQuantity = selectedFactoryObject.getPersonalProtectiveEquipmentTypeAndQuantity().get(personalProtectiveEquipmentType);
                    HBox itemOfThePersonalProtectiveEquipmentType = new HBox();
                    itemOfThePersonalProtectiveEquipmentType.getChildren().add(new Label("Вид СИЗ: "));
                    TextArea personalProtectiveEquipmentTypeValue = new TextArea(personalProtectiveEquipmentType);
                    personalProtectiveEquipmentTypeValue.setPrefHeight(45);
                    personalProtectiveEquipmentTypeValue.setEditable(false);
                    itemOfThePersonalProtectiveEquipmentType.getChildren().add(personalProtectiveEquipmentTypeValue);
                    itemOfThePersonalProtectiveEquipmentType.getChildren().add(new Label(", количество: "));
                    itemOfThePersonalProtectiveEquipmentType.getChildren().add(new Label(String.valueOf(personalProtectiveEquipmentQuantity)));
                    personalProtectiveEquipmentTypeAndQuantityVBox.getChildren().add(itemOfThePersonalProtectiveEquipmentType);
                }
            }
        }
        stateOfEmergencyWhatAndWhenHappenedVBox.getChildren().remove(0, stateOfEmergencyWhatAndWhenHappenedVBox.getChildren().size());
        if (selectedFactoryObject.getStateOfEmergencyWhatAndWhenHappened() != null && !selectedFactoryObject.getStateOfEmergencyWhatAndWhenHappened().isEmpty()) {
//                stateOfEmergencyWhatAndWhenHappenedVBox
            stateOfEmergencyWhatAndWhenHappenedVBox.getChildren().add(new Label("ЧС (происшествия), произошедшие на ОПО (за последние 3 года):"));
            Set<String> statesOfEmergencyWhatHappened = selectedFactoryObject.getStateOfEmergencyWhatAndWhenHappened().keySet();
//            containerForGridPanes.setPrefHeight(containerForGridPanes.getHeight() + 100 + statesOfEmergencyWhatHappened.size() * 10);
            Iterator<String> statesOfEmergencyWhatHappenedIterator = statesOfEmergencyWhatHappened.iterator();
            while (statesOfEmergencyWhatHappenedIterator.hasNext()) {
                String stateOfEmergencyWhatHappened = statesOfEmergencyWhatHappenedIterator.next();
                if (StringUtils.isNotBlank(stateOfEmergencyWhatHappened)) {
                    LocalDate stateOfEmergencyWhenHappened = selectedFactoryObject.getStateOfEmergencyWhatAndWhenHappened().get(stateOfEmergencyWhatHappened);
                    HBox itemOfTheStateOfEmergencyWhatHappened = new HBox();
                    itemOfTheStateOfEmergencyWhatHappened.getChildren().add(new Label("ЧТо произошло: "));
                    TextArea stateOfEmergencyWhatHappenedValue = new TextArea(stateOfEmergencyWhatHappened);
                    stateOfEmergencyWhatHappenedValue.setPrefHeight(45);
                    stateOfEmergencyWhatHappenedValue.setEditable(false);
                    itemOfTheStateOfEmergencyWhatHappened.getChildren().add(stateOfEmergencyWhatHappenedValue);
                    itemOfTheStateOfEmergencyWhatHappened.getChildren().add(new Label(". Когда произошло: "));
                    itemOfTheStateOfEmergencyWhatHappened.getChildren().add(new Label(stateOfEmergencyWhenHappened.toString()));
                    stateOfEmergencyWhatAndWhenHappenedVBox.getChildren().add(itemOfTheStateOfEmergencyWhatHappened);
                }
            }
        }
    }
}
