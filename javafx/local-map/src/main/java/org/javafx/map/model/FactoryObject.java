package org.javafx.map.model;

import java.io.Serializable;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Сергей
 */
@Entity
@Table(name = "FACTORY_OBJECT")
public class FactoryObject implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "FACTORY_OBJECT_ID")
    private long id;
    @Column(name = "FACTORY_OBJECT_X")
    private double x;
    @Column(name = "FACTORY_OBJECT_Y")
    private double y;
    //7
    @Column(name = "FACTORY_OBJECT_NAME", nullable = false)
    private String name;
    @Column(name = "FACTORY_OBJECT_START_EXPLOITATION_DATE")
    private LocalDate startExploitationDate;
    @Column(name = "FACTORY_OBJECT_LAST_RECONSTRUCTION_DATE")
    private LocalDate lastReconstructionDate;
    @Column(name = "FACTORY_OBJECT_LAST_RECONSTRUCTION_MADE_BY")
    private String lastReconstructionMadeBy;
    @Column(name = "FACTORY_OBJECT_LAST_REPAIRS_DATE")
    private LocalDate lastRepairsDate;
    @Column(name = "FACTORY_OBJECT_LAST_REPAIRS_MADE_BY", length = 4000)
    private String lastRepairsMadeBy;
    @Column(name = "FACTORY_OBJECT_PERFOMANCE", length = 1000)
    private String perfomance;
    //  5
    @Column(name = "FACTORY_OBJECT_NUMBER_OF_EMPLOYEES")
    private int numberOfEmployees;
    @Column(name = "FACTORY_OBJECT_NUMBER_OF_TEAMS")
    private int numberOfTeams;
    @Column(name = "FACTORY_OBJECT_TEAMS_SCHEDULE", length = 4000)
    private String teamsSchedule;
    @Column(name = "FACTORY_OBJECT_MAX_NUMBER_OF_EMPLOYEES_IN_TEAM")
    private int maxNumberOfEmployeesInTeam;
    @Column(name = "FACTORY_OBJECT_MIN_NUMBER_OF_EMPLOYEES_IN_TEAM")
    private int minNumberOfEmployeesInTeam;
    //6
    @Column(name = "FACTORY_OBJECT_APPOINTMENT", length = 4000)
    private String objectAppointment;
    @Column(name = "FACTORY_OBJECT_PRODUCTION_METHOD", length = 1000)
    private String productionMethod;
    @Column(name = "FACTORY_OBJECT_TECHNOLOGICAL_BLOCKS", length = 1000)
    private String technologicalBlocks;
    @Column(name = "FACTORY_OBJECT_RESOURCE_COME_FROM_OBJECTS", length = 1000)
    private String resourceComeFromObjects;
    @Column(name = "FACTORY_OBJECT_RESOURCE_GO_TO_OBJECTS", length = 1000)
    private String resourceGoToObjects;
    @Column(name = "FACTORY_OBJECT_AUXILIARY_OBJECTS", length = 1000)
    private String auxiliaryObjects;
    //3
    @Column(name = "FACTORY_OBJECT_LOCATION", length = 1000)
    private String objectLocation;
    @Column(name = "FACTORY_OBJECT_ENTER_FROM_STREET", length = 1000)
    private String enterInObjectFromStreet;
    @Column(name = "FACTORY_OBJECT_OPERATOR_LOCATION", length = 1000)
    private String operatorLocation;
    //3
    @Column(name = "FACTORY_OBJECT_MOST_DANGEROUS_EQUIPMENT", length = 4000)
    private String mostDangerousEquipment;
    @Column(name = "FACTORY_OBJECT_MOST_POSSIBLE_EMERGENCY_SITUATION", length = 4000)
    private String mostPossibleEmergencySituation;
    @Column(name = "FACTORY_OBJECT_MOST_DANGEROUS_EMERGENCY_SITUATION", length = 4000)
    private String mostDangerousEmergencySituation;
    //3
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "OIL_TYPE_AND_QUANTITY")
    @MapKeyColumn(name = "OIL_TYPE", length = 1000)
    private Map<String, Double> oilTypeAndQuantity;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERSONAL_PROTECTIVE_EQUIPMENT_TYPE_AND_QUANTITY")
    @MapKeyColumn(name = "PERSONAL_PROTECTIVE_EQUIPMENT_TYPE", length = 1000)
    private Map<String, Integer> personalProtectiveEquipmentTypeAndQuantity;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "STATE_OF_EMERGENCY_WHAT_AND_WHEN_HAPPENED")
    @MapKeyColumn(name = "STATE_OF_EMERGENCY_WHAT_HAPPENED", length = 4000)
    private Map<String, LocalDate> stateOfEmergencyWhatAndWhenHappened;
    //СЕТ ДЛЯ ХРАНЕНИЯ СВЯЗЕЙ МЕЖДУ ОБЪЕКТАМИ
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "LINKED_FACTORY_OBJECT",
            joinColumns = {
                @JoinColumn(name = "FACTORY_OBJECT_ID", nullable = true)},
            inverseJoinColumns = {
                @JoinColumn(name = "LINKED_FACTORY_OBJECT_ID", nullable = true)})
    private Set<FactoryObject> linkedFactoryObjects = new HashSet<FactoryObject>();
    //ВСПОМОГАТЕЛЬНЫЙ СЕТ
    @ManyToMany(mappedBy = "linkedFactoryObjects")
    private Set<FactoryObject> binderLinkedFactoryObjects = new HashSet<FactoryObject>();


    public FactoryObject() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartExploitationDate() {
        return startExploitationDate;
    }

    public void setStartExploitationDate(LocalDate startExploitationDate) {
        this.startExploitationDate = startExploitationDate;
    }

    public LocalDate getLastReconstructionDate() {
        return lastReconstructionDate;
    }

    public void setLastReconstructionDate(LocalDate lastReconstructionDate) {
        this.lastReconstructionDate = lastReconstructionDate;
    }

    public String getLastReconstructionMadeBy() {
        return lastReconstructionMadeBy;
    }

    public void setLastReconstructionMadeBy(String lastReconstructionMadeBy) {
        this.lastReconstructionMadeBy = lastReconstructionMadeBy;
    }

    public LocalDate getLastRepairsDate() {
        return lastRepairsDate;
    }

    public void setLastRepairsDate(LocalDate lastRepairsDate) {
        this.lastRepairsDate = lastRepairsDate;
    }

    public String getLastRepairsMadeBy() {
        return lastRepairsMadeBy;
    }

    public void setLastRepairsMadeBy(String lastRepairsMadeBy) {
        this.lastRepairsMadeBy = lastRepairsMadeBy;
    }

    public String getPerfomance() {
        return perfomance;
    }

    public void setPerfomance(String perfomance) {
        this.perfomance = perfomance;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public String getTeamsSchedule() {
        return teamsSchedule;
    }

    public void setTeamsSchedule(String teamsSchedule) {
        this.teamsSchedule = teamsSchedule;
    }

    public int getMaxNumberOfEmployeesInTeam() {
        return maxNumberOfEmployeesInTeam;
    }

    public void setMaxNumberOfEmployeesInTeam(int maxNumberOfEmployeesInTeam) {
        this.maxNumberOfEmployeesInTeam = maxNumberOfEmployeesInTeam;
    }

    public int getMinNumberOfEmployeesInTeam() {
        return minNumberOfEmployeesInTeam;
    }

    public void setMinNumberOfEmployeesInTeam(int minNumberOfEmployeesInTeam) {
        this.minNumberOfEmployeesInTeam = minNumberOfEmployeesInTeam;
    }

    public String getObjectAppointment() {
        return objectAppointment;
    }

    public void setObjectAppointment(String objectAppointment) {
        this.objectAppointment = objectAppointment;
    }

    public String getProductionMethod() {
        return productionMethod;
    }

    public void setProductionMethod(String productionMethod) {
        this.productionMethod = productionMethod;
    }

    public String getTechnologicalBlocks() {
        return technologicalBlocks;
    }

    public void setTechnologicalBlocks(String technologicalBlocks) {
        this.technologicalBlocks = technologicalBlocks;
    }

    public String getResourceComeFromObjects() {
        return resourceComeFromObjects;
    }

    public void setResourceComeFromObjects(String resourceComeFromObjects) {
        this.resourceComeFromObjects = resourceComeFromObjects;
    }

    public String getResourceGoToObjects() {
        return resourceGoToObjects;
    }

    public void setResourceGoToObjects(String resourceGoToObjects) {
        this.resourceGoToObjects = resourceGoToObjects;
    }

    public String getAuxiliaryObjects() {
        return auxiliaryObjects;
    }

    public void setAuxiliaryObjects(String auxiliaryObjects) {
        this.auxiliaryObjects = auxiliaryObjects;
    }

    public String getObjectLocation() {
        return objectLocation;
    }

    public void setObjectLocation(String objectLocation) {
        this.objectLocation = objectLocation;
    }

    public String getEnterInObjectFromStreet() {
        return enterInObjectFromStreet;
    }

    public void setEnterInObjectFromStreet(String enterInObjectFromStreet) {
        this.enterInObjectFromStreet = enterInObjectFromStreet;
    }

    public String getOperatorLocation() {
        return operatorLocation;
    }

    public void setOperatorLocation(String operatorLocation) {
        this.operatorLocation = operatorLocation;
    }

    public String getMostDangerousEquipment() {
        return mostDangerousEquipment;
    }

    public void setMostDangerousEquipment(String mostDangerousEquipment) {
        this.mostDangerousEquipment = mostDangerousEquipment;
    }

    public String getMostPossibleEmergencySituation() {
        return mostPossibleEmergencySituation;
    }

    public void setMostPossibleEmergencySituation(String mostPossibleEmergencySituation) {
        this.mostPossibleEmergencySituation = mostPossibleEmergencySituation;
    }

    public String getMostDangerousEmergencySituation() {
        return mostDangerousEmergencySituation;
    }

    public void setMostDangerousEmergencySituation(String mostDangerousEmergencySituation) {
        this.mostDangerousEmergencySituation = mostDangerousEmergencySituation;
    }

    public Map<String, Double> getOilTypeAndQuantity() {
        return oilTypeAndQuantity;
    }

    public void setOilTypeAndQuantity(Map<String, Double> oilTypeAndQuantity) {
        this.oilTypeAndQuantity = oilTypeAndQuantity;
    }

    public Map<String, Integer> getPersonalProtectiveEquipmentTypeAndQuantity() {
        return personalProtectiveEquipmentTypeAndQuantity;
    }

    public void setPersonalProtectiveEquipmentTypeAndQuantity(Map<String, Integer> personalProtectiveEquipmentTypeAndQuantity) {
        this.personalProtectiveEquipmentTypeAndQuantity = personalProtectiveEquipmentTypeAndQuantity;
    }

    public Map<String, LocalDate> getStateOfEmergencyWhatAndWhenHappened() {
        return stateOfEmergencyWhatAndWhenHappened;
    }

    public void setStateOfEmergencyWhatAndWhenHappened(Map<String, LocalDate> stateOfEmergencyWhatAndWhenHappened) {
        this.stateOfEmergencyWhatAndWhenHappened = stateOfEmergencyWhatAndWhenHappened;
    }

    public Set<FactoryObject> getLinkedFactoryObjects() {
        return linkedFactoryObjects;
    }

    public void setLinkedFactoryObjects(Set<FactoryObject> linkedFactoryObjects) {
        this.linkedFactoryObjects = linkedFactoryObjects;
    }

    public Set<FactoryObject> getBinderLinkedFactoryObjects() {
        return binderLinkedFactoryObjects;
    }

    public void setBinderLinkedFactoryObjects(Set<FactoryObject> binderLinkedFactoryObjects) {
        this.binderLinkedFactoryObjects = binderLinkedFactoryObjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactoryObject)) {
            return false;
        }

        FactoryObject that = (FactoryObject) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
