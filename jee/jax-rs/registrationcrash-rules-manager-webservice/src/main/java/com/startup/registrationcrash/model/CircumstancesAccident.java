package com.startup.registrationcrash.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "CIRCUMSTANCES_ACCIDENT")
@XmlRootElement(name = "circumstancesAccidents")
public class CircumstancesAccident implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "CA_ID")
    private long id;
    @Column(name = "CA_VEHICLE_STOOD")
    private boolean vehicleStood;
    @Column(name = "CA_DRIVER_ABSENT")
    private boolean driverAbsent;
    @Column(name = "CA_DRIVER_DROVE_AT_PARKING")
    private boolean driverDroveAtParking;
    @Column(name = "CA_DRIVER_MOVE_OUT_FROM_PARKING")
    private boolean driverMoveOutFromParking;
    @Column(name = "CA_DRIVER_MOVE_IN_TO_PARKING")
    private boolean driverMoveInToParking;
    @Column(name = "CA_DRIVER_DROVE_STRAIGHT")
    private boolean driverDroveStraight;
    @Column(name = "CA_DRIVER_DROVE_AT_CROSSROAD")
    private boolean driverDroveAtCrossroad;
    @Column(name = "CA_DRIVER_MOVE_IN_CROSSROAD_WITH_CIRCULAR_MOVEMENT")
    private boolean driverMoveInCrossroadWithCircularMovement;
    @Column(name = "CA_DRIVER_DROVE_AT_CROSSROAD_WITH_CIRCULAR_MOVEMENT")
    private boolean driverDroveAtCrossroadWithCircularMovement;
//    10-é ïóíêò
//    CA_DRIVER_COLLIDED_WITH_VEHICLE_THAT_MOVED_IN = CA_D_C_W_V_THAT_MOVED_IN
    @Column(name = "CA_D_C_W_V_THAT_MOVED_IN_SAME_LANE")
    private boolean driverCollidedWithVehicleThatMovedInSameDirectionInSameLane;
    @Column(name = "CA_D_C_W_V_THAT_MOVED_IN_OTHER_LANE")
    private boolean driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane;
    @Column(name = "CA_DRIVER_CHANGED_LANE")
    private boolean driverChangedLane;
    @Column(name = "CA_DRIVER_OVERTOOK")
    private boolean driverOvertook;
    @Column(name = "CA_DRIVER_TURNED_LEFT")
    private boolean driverTurnedLeft;
    @Column(name = "CA_DRIVER_TURNED_RIGHT")
    private boolean driverTurnedRight;
    @Column(name = "CA_DRIVER_TURNED_BACK")
    private boolean driverTurnedBack;
    @Column(name = "CA_DRIVER_DROVE_IN_REVERSE_GEAR")
    private boolean driverDroveInReverseGear;
//    18-É ÏÓÍÊÒ
    @Column(name = "CA_DRIVER_DROVE_ALONG_ONCOMING_TRAFFIC_LANE")
    private boolean driverDroveAlongOncomingTrafficLane;
    @Column(name = "CA_SECOND_VEHICLE_WAS_ON_THE_LEFT_OF_ME")
    private boolean secondVehicleWasOnTheLeftOfMe;
    @Column(name = "CA_DRIVER_FAILED_TO_COMPLY_WITH_REQUIREMENT_OF_PRIORITY_MARK")
    private boolean DriverFailedToComplyWithRequirementOfPriorityMark;
    @Column(name = "CA_DRIVER_HAVE_DRIVEN_ON_IMMOVABLE_OBJECT")
    private boolean driverHaveDrivenOnImmovableObject;
    @Column(name = "CA_RED_LIGHT_WAS_SWITCHING_ON_WHEN_DRIVER_STAND_OR_WAS_STOPPED")
    private boolean redLightWasSwitchingOnWhenDriverStandOrWasStopped;
//    23-24 ïóíêòû
    @Column(name = "CA_OTHER")
    private String otherCircumstances;

    public CircumstancesAccident() {
    }

    public CircumstancesAccident(long id, boolean vehicleStood, boolean driverAbsent,
            boolean driverDroveAtParking, boolean driverMoveOutFromParking,
            boolean driverMoveInToParking, boolean driverDroveStraight,
            boolean driverDroveAtCrossroad, boolean driverMoveInCrossroadWithCircularMovement,
            boolean driverDroveAtCrossroadWithCircularMovement,
            boolean driverCollidedWithVehicleThatMovedInSameDirectionInSameLane,
            boolean driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane,
            boolean driverChangedLane, boolean driverOvertook,
            boolean driverTurnedLeft, boolean driverTurnedRight,
            boolean driverTurnedBack, boolean driverDroveInReverseGear,
            boolean driverDroveAlongOncomingTrafficLane,
            boolean secondVehicleWasOnTheLeftOfMe,
            boolean DriverFailedToComplyWithRequirementOfPriorityMark,
            boolean driverHaveDrivenOnImmovableObject,
            boolean redLightWasSwitchingOnWhenDriverStandOrWasStopped,
            String otherCircumstances) {
        this.id = id;
        this.vehicleStood = vehicleStood;
        this.driverAbsent = driverAbsent;
        this.driverDroveAtParking = driverDroveAtParking;
        this.driverMoveOutFromParking = driverMoveOutFromParking;
        this.driverMoveInToParking = driverMoveInToParking;
        this.driverDroveStraight = driverDroveStraight;
        this.driverDroveAtCrossroad = driverDroveAtCrossroad;
        this.driverMoveInCrossroadWithCircularMovement = driverMoveInCrossroadWithCircularMovement;
        this.driverDroveAtCrossroadWithCircularMovement = driverDroveAtCrossroadWithCircularMovement;
        this.driverCollidedWithVehicleThatMovedInSameDirectionInSameLane = driverCollidedWithVehicleThatMovedInSameDirectionInSameLane;
        this.driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane = driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane;
        this.driverChangedLane = driverChangedLane;
        this.driverOvertook = driverOvertook;
        this.driverTurnedLeft = driverTurnedLeft;
        this.driverTurnedRight = driverTurnedRight;
        this.driverTurnedBack = driverTurnedBack;
        this.driverDroveInReverseGear = driverDroveInReverseGear;
        this.driverDroveAlongOncomingTrafficLane = driverDroveAlongOncomingTrafficLane;
        this.secondVehicleWasOnTheLeftOfMe = secondVehicleWasOnTheLeftOfMe;
        this.DriverFailedToComplyWithRequirementOfPriorityMark = DriverFailedToComplyWithRequirementOfPriorityMark;
        this.driverHaveDrivenOnImmovableObject = driverHaveDrivenOnImmovableObject;
        this.redLightWasSwitchingOnWhenDriverStandOrWasStopped = redLightWasSwitchingOnWhenDriverStandOrWasStopped;
        this.otherCircumstances = otherCircumstances;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isVehicleStood() {
        return vehicleStood;
    }

    public void setVehicleStood(boolean vehicleStood) {
        this.vehicleStood = vehicleStood;
    }

    public boolean isDriverAbsent() {
        return driverAbsent;
    }

    public void setDriverAbsent(boolean driverAbsent) {
        this.driverAbsent = driverAbsent;
    }

    public boolean isDriverDroveAtParking() {
        return driverDroveAtParking;
    }

    public void setDriverDroveAtParking(boolean driverDroveAtParking) {
        this.driverDroveAtParking = driverDroveAtParking;
    }

    public boolean isDriverMoveOutFromParking() {
        return driverMoveOutFromParking;
    }

    public void setDriverMoveOutFromParking(boolean driverMoveOutFromParking) {
        this.driverMoveOutFromParking = driverMoveOutFromParking;
    }

    public boolean isDriverMoveInToParking() {
        return driverMoveInToParking;
    }

    public void setDriverMoveInToParking(boolean driverMoveInToParking) {
        this.driverMoveInToParking = driverMoveInToParking;
    }

    public boolean isDriverDroveStraight() {
        return driverDroveStraight;
    }

    public void setDriverDroveStraight(boolean driverDroveStraight) {
        this.driverDroveStraight = driverDroveStraight;
    }

    public boolean isDriverDroveAtCrossroad() {
        return driverDroveAtCrossroad;
    }

    public void setDriverDroveAtCrossroad(boolean driverDroveAtCrossroad) {
        this.driverDroveAtCrossroad = driverDroveAtCrossroad;
    }

    public boolean isDriverMoveInCrossroadWithCircularMovement() {
        return driverMoveInCrossroadWithCircularMovement;
    }

    public void setDriverMoveInCrossroadWithCircularMovement(boolean driverMoveInCrossroadWithCircularMovement) {
        this.driverMoveInCrossroadWithCircularMovement = driverMoveInCrossroadWithCircularMovement;
    }

    public boolean isDriverDroveAtCrossroadWithCircularMovement() {
        return driverDroveAtCrossroadWithCircularMovement;
    }

    public void setDriverDroveAtCrossroadWithCircularMovement(boolean driverDroveAtCrossroadWithCircularMovement) {
        this.driverDroveAtCrossroadWithCircularMovement = driverDroveAtCrossroadWithCircularMovement;
    }

    public boolean isDriverCollidedWithVehicleThatMovedInSameDirectionInSameLane() {
        return driverCollidedWithVehicleThatMovedInSameDirectionInSameLane;
    }

    public void setDriverCollidedWithVehicleThatMovedInSameDirectionInSameLane(boolean driverCollidedWithVehicleThatMovedInSameDirectionInSameLane) {
        this.driverCollidedWithVehicleThatMovedInSameDirectionInSameLane = driverCollidedWithVehicleThatMovedInSameDirectionInSameLane;
    }

    public boolean isDriverCollidedWithVehicleThatMovedInSameDirectionInOtherLane() {
        return driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane;
    }

    public void setDriverCollidedWithVehicleThatMovedInSameDirectionInOtherLane(boolean driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane) {
        this.driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane = driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane;
    }

    public boolean isDriverChangedLane() {
        return driverChangedLane;
    }

    public void setDriverChangedLane(boolean driverChangedLane) {
        this.driverChangedLane = driverChangedLane;
    }

    public boolean isDriverOvertook() {
        return driverOvertook;
    }

    public void setDriverOvertook(boolean driverOvertook) {
        this.driverOvertook = driverOvertook;
    }

    public boolean isDriverTurnedLeft() {
        return driverTurnedLeft;
    }

    public void setDriverTurnedLeft(boolean driverTurnedLeft) {
        this.driverTurnedLeft = driverTurnedLeft;
    }

    public boolean isDriverTurnedRight() {
        return driverTurnedRight;
    }

    public void setDriverTurnedRight(boolean driverTurnedRight) {
        this.driverTurnedRight = driverTurnedRight;
    }

    public boolean isDriverTurnedBack() {
        return driverTurnedBack;
    }

    public void setDriverTurnedBack(boolean driverTurnedBack) {
        this.driverTurnedBack = driverTurnedBack;
    }

    public boolean isDriverDroveInReverseGear() {
        return driverDroveInReverseGear;
    }

    public void setDriverDroveInReverseGear(boolean driverDroveInReverseGear) {
        this.driverDroveInReverseGear = driverDroveInReverseGear;
    }

    public boolean isDriverDroveAlongOncomingTrafficLane() {
        return driverDroveAlongOncomingTrafficLane;
    }

    public void setDriverDroveAlongOncomingTrafficLane(boolean driverDroveAlongOncomingTrafficLane) {
        this.driverDroveAlongOncomingTrafficLane = driverDroveAlongOncomingTrafficLane;
    }

    public boolean isSecondVehicleWasOnTheLeftOfMe() {
        return secondVehicleWasOnTheLeftOfMe;
    }

    public void setSecondVehicleWasOnTheLeftOfMe(boolean secondVehicleWasOnTheLeftOfMe) {
        this.secondVehicleWasOnTheLeftOfMe = secondVehicleWasOnTheLeftOfMe;
    }

    public boolean isDriverFailedToComplyWithRequirementOfPriorityMark() {
        return DriverFailedToComplyWithRequirementOfPriorityMark;
    }

    public void setDriverFailedToComplyWithRequirementOfPriorityMark(boolean DriverFailedToComplyWithRequirementOfPriorityMark) {
        this.DriverFailedToComplyWithRequirementOfPriorityMark = DriverFailedToComplyWithRequirementOfPriorityMark;
    }

    public boolean isDriverHaveDrivenOnImmovableObject() {
        return driverHaveDrivenOnImmovableObject;
    }

    public void setDriverHaveDrivenOnImmovableObject(boolean driverHaveDrivenOnImmovableObject) {
        this.driverHaveDrivenOnImmovableObject = driverHaveDrivenOnImmovableObject;
    }

    public boolean isRedLightWasSwitchingOnWhenDriverStandOrWasStopped() {
        return redLightWasSwitchingOnWhenDriverStandOrWasStopped;
    }

    public void setRedLightWasSwitchingOnWhenDriverStandOrWasStopped(boolean redLightWasSwitchingOnWhenDriverStandOrWasStopped) {
        this.redLightWasSwitchingOnWhenDriverStandOrWasStopped = redLightWasSwitchingOnWhenDriverStandOrWasStopped;
    }

    public String getOtherCircumstances() {
        return otherCircumstances;
    }

    public void setOtherCircumstances(String otherCircumstances) {
        this.otherCircumstances = otherCircumstances;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CircumstancesAccident other = (CircumstancesAccident) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
