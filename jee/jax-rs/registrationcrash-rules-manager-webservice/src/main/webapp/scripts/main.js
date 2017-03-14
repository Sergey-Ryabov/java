var domen = "http://localhost:45419/registrationcrash-rules-manager-webservice/api/";
var DASH = "-";
var COLON = ":";
var chosenPanel = 1;
var animationInterval = 500;
var createRuleText = "СОЗДАНИЕ ПРАВИЛА";
var editRuleText = "РЕДАКТИРОВАНИЕ ПРАВИЛА";

var currentMenuItem = 1;
var menu = document.getElementById("menu").getElementsByTagName("li");
var goToCreateNewRuleBtn = document.getElementById("goToCreateNewRuleBtn");
var removeCurrentRuleBtn = document.getElementById("removeCurrentRuleBtn");
var panel_1 = document.getElementById("panel_1");
var createNewRuleBtn = document.getElementById("createNewRuleBtn");
var editRuleBtn = document.getElementById("editRuleBtn");
var newRuleTable = document.getElementById("newRuleTable");
var results1 = document.getElementById("results-1");
var results2 = document.getElementById("results-2");
var description = document.getElementById("description");

var panel_2 = document.getElementById("panel_2");
var rulesTable = document.getElementById("rulesTable");

var rulesContentData; // static text
var rulesData; // rules
var currentRule;

getContentForNewRuleTable();

function changePanel(menuItem, needShowRuleInfo, goToCreatingNewRule) {
    if (currentMenuItem !== menuItem) {
        if (menuItem === 1)
        {
            panel_1.setAttribute("class", "show min-width-500 min-height-300");
            panel_2.setAttribute("class", "hide");
            chosenPanel = 1;
            menu[0].setAttribute("class", "activeTab");
            menu[1].setAttribute("class", "");

            if (needShowRuleInfo) {
                createNewRuleBtn.setAttribute("class", "hide");
                editRuleBtn.setAttribute("class", "margin-left-20 margin-t-b-10px");
                goToCreateNewRuleBtn.setAttribute("class", "margin-right-20 margin-t-b-10px float-right");
                removeCurrentRuleBtn.setAttribute("class", "margin-t-b-10px margin-right-20 float-right");
                menu[0].innerText = editRuleText;
                showCurrentRuleContent();
            } else {
                menu[0].innerText = createRuleText;
                resetFirstPanel();
            }
        } else
        {
            menu[0].innerText = createRuleText;
            panel_2.setAttribute("class", "show min-width-500 min-height-300");
            panel_1.setAttribute("class", "hide");
            chosenPanel = 2;
            menu[0].setAttribute("class", "");
            menu[1].setAttribute("class", "activeTab");
            goToCreateNewRuleBtn.setAttribute("class", "hide");
            removeCurrentRuleBtn.setAttribute("class", "hide");
            getRules();
        }
        currentMenuItem = menuItem;
    } else if (goToCreatingNewRule) {
//                    showFirstPanel(needShowRuleInfo);
        resetFirstPanel();
    }

}

function resetFirstPanel() {
    getContentForNewRuleTable();
    createNewRuleBtn.setAttribute("class", "margin-left-20 margin-t-b-10px");
    editRuleBtn.setAttribute("class", "hide");
    goToCreateNewRuleBtn.setAttribute("class", "hide");
    removeCurrentRuleBtn.setAttribute("class", "hide");
    results2.checked = false;
    results1.checked = false;
    description.value = "";
}

function createNewRule() {
    var newRule = getDataFromForm();
    if (checkInputData(newRule))
    {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 201)
            {
                var returnedObj = JSON.parse(this.responseText);
//                         TODO: add returned Rule to rules List

//clear fields
                resetFirstPanel();
                alert('Правило создано');
            }
        };
        xhttp.open("POST", domen + "rules/", true);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.send(JSON.stringify(newRule));
    } else {
        alert('Заполните корректно поля создаваемого правила');
    }
}

function checkInputData(rule) {
    console.info('checkInputData, rule = ', rule);
    if (rule.result && (rule.result === 1 || rule.result === 2)
            && isExistCheckedField(rule.firstDriverAnswer) && isExistCheckedField(rule.secondDriverAnswer)) {
        return true;
    }
    return false;
}

function isExistCheckedField(object) {
    for (var property in object) {
        if (object.hasOwnProperty(property) && object[property] === true) {
            return true;
        }
    }
    return false;
}

function getRule(event) {
    console.info(event);
    var idParts = event.srcElement.id.split(DASH);
    if (rulesData && rulesData.rows && rulesData.rows.length > 0) {
        currentRule = rulesData.rows[idParts[1] - 1];

        changePanel(1, true);
    } else {
        //need send request to server
        var ruleId = rulesTable.rows[idParts[1] - 1].cells[0].innerText;
        if (ruleId)
        {
//                    var xhttp = new XMLHttpRequest();
//                    xhttp.onreadystatechange = function () {
//                        if (this.readyState == 4 && this.status == 200)
//                        {
//                            consile.info("server response = " + this.responseText);
//                            currentRule = JSON.parse(this.responseText);
//                            //                        document.getElementById("demo").innerHTML = this.responseText;
//                        }
//                    };
//                    xhttp.open("GET", domen + "rules/" + ruleId, true);
//                    xhttp.setRequestHeader("Content-type", "application/json");
//                    xhttp.send();

        }
    }
}

function editRule(event) {
    var editedRule = getDataFromForm();
    editedRule.id = currentRule.id;
    editedRule.firstDriverAnswer.id = currentRule.firstDriverAnswer.id;
    editedRule.secondDriverAnswer.id = currentRule.secondDriverAnswer.id;
    if (checkInputData(editedRule))
    {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200)
            {
                var returnedObj = JSON.parse(this.responseText);
//                          add returned Rule to rules List
                console.info("returnedObj = ", returnedObj);
                 alert('Правило отредактировано');
//clear fields
//                            resetFirstPanel();
            }
        };
        xhttp.open("PUT", domen + "rules/" + currentRule.id, true);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.send(JSON.stringify(editedRule));
    } else {
        alert('Заполните корректно поля изменяемого правила');
    }
}

//for second panel
function deleteRuleByEvent(event) {
    var idParts = event.srcElement.id.split(DASH);
    var ruleId = rulesTable.rows[idParts[1]].cells[0].innerText;
    if (ruleId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200)
            {
                console.warn("rulesData.rows[idParts[1] - 1] = ", rulesData.rows[idParts[1] - 1]);
                rulesTable.deleteRow(idParts[1]);
                rulesData.rows.splice(idParts[1] - 1, 1);
                fillTable(rulesData, rulesTable);
            }
        };
        xhttp.open("DELETE", domen + "rules/" + ruleId, true);
        xhttp.setRequestHeader("Content-type", "application/json");
        xhttp.send();
    }
}

//for first panel
function deleteRule() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200)
        {
            changePanel(2, false, false);
        }
    };
    xhttp.open("DELETE", domen + "rules/" + currentRule.id, true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send();
}

function getContentForNewRuleTable() {
    // format   {"header":{"firstDriverAnswer": "������ ��������", "actionName":"��������", "secondDriverAnswer":"������ ��������"}, "rows": [{"actionName": "�������� �����"},{...}]}
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202)
        {
            rulesContentData = JSON.parse(this.responseText);
            fillTable(rulesContentData, newRuleTable);
        }
    };
    xhttp.open("GET", domen + "rules/contentForNewRule", true);
    xhttp.send();

//                var jsonStr = '{"header":{"firstDriverAnswer": "������ ��������1", "actionName":"��������", "secondDriverAnswer":"������ ��������"}, "rows": [{"firstDriverAnswer": "t-checkbox", "secondDriverAnswer":"t-checkbox","actionName": "�������� �����"}, {"firstDriverAnswer": "t-checkbox", "secondDriverAnswer":"t-checkbox","actionName": "�������� �����"}]}';
//                var jsonStr = '{"header":{"firstDriverAnswer":"������ ������� ��������","actionName":"��������","secondDriverAnswer":"������ ������� ��������"},"rows":[{"firstDriverAnswer":"t-checkbox","actionName":"�������� �����","secondDriverAnswer":"t-checkbox"},{"firstDriverAnswer":"t-checkbox","actionName":"�������� �����","secondDriverAnswer":"t-checkbox"},{"firstDriverAnswer":"t-checkbox","actionName":"�������� �������","secondDriverAnswer":"t-checkbox"},{"firstDriverAnswer":"t-checkbox","actionName":"�������� ������","secondDriverAnswer":"t-checkbox"}]}';
//                rulesContentData = JSON.parse(jsonStr);
//                fillTable(rulesContentData, newRuleTable);
}

function showCurrentRuleContent() {
    fillTable(rulesContentData, newRuleTable);
    console.warn("currentRule = ", currentRule);
    for (i = 1; i < newRuleTable.rows.length; i++) {
        var row = newRuleTable.rows[i];
        var actionName = row.cells[1].childNodes[0].getAttribute("name");
        if (currentRule.firstDriverAnswer[actionName] === true) {
            row.cells[0].childNodes[0].setAttribute("checked", "checked");
        }
        if (currentRule.secondDriverAnswer[actionName] === true) {
            row.cells[2].childNodes[0].setAttribute("checked", "checked");
        }
    }
    if (currentRule.result === 1) {
        results1.checked = true;
        results2.checked = false;
    } else {
        results1.checked = false;
        results2.checked = true;
    }
    description.value = currentRule.description;
}

function getRules() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202)
        {
            rulesData = JSON.parse(this.responseText);
            fillTable(rulesData, rulesTable);
        }
    };
    xhttp.open("GET", domen + "rules/", true);
    xhttp.send();

    //
// old formate
//                var jsonStr = '{"header":{"ruleNumber": "�����", "ruleResult": "���������", "ruleDescription": "��������", "actions": "��������"}, "rows": [{"ruleNumber": 123, "ruleResult": 1, "ruleDescription": "�������� �������", "actions": "t-button-edit|t-button-delete"}]}';
//                rulesData = JSON.parse(jsonStr);
//                fillTable0(rulesData, rulesTable);

//{"header":{"id":"�����","result":"���������","description":"��������","actions":"��������"},"rows":[{"id":1,"firstDriverAnswer":{"id":1,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"secondDriverAnswer":{"id":2,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"result":1,"description":"some texteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee12345","actions":"t-button:ev-click-edit:v-edit|t-button:ev-click-delete:v-delete"},{"id":2,"firstDriverAnswer":{"id":3,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"secondDriverAnswer":{"id":4,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"result":1,"description":"some texteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee1234","actions":"t-button:ev-click-edit:v-edit|t-button:ev-click-delete:v-delete"},{"id":6,"firstDriverAnswer":{"id":11,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"secondDriverAnswer":{"id":12,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"result":1,"description":"some texteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee","actions":"t-button:ev-click-edit:v-edit|t-button:ev-click-delete:v-delete"},{"id":7,"firstDriverAnswer":{"id":13,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"secondDriverAnswer":{"id":14,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"result":1,"description":"some texteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee","actions":"t-button:ev-click-edit:v-edit|t-button:ev-click-delete:v-delete"},{"id":8,"firstDriverAnswer":{"id":15,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"secondDriverAnswer":{"id":16,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"result":1,"description":"some texteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee1","actions":"t-button:ev-click-edit:v-edit|t-button:ev-click-delete:v-delete"},{"id":9,"firstDriverAnswer":{"id":17,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"secondDriverAnswer":{"id":18,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"result":1,"description":"some texteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee12","actions":"t-button:ev-click-edit:v-edit|t-button:ev-click-delete:v-delete"},{"id":10,"firstDriverAnswer":{"id":19,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"secondDriverAnswer":{"id":20,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"result":1,"description":"some texteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee123","actions":"t-button:ev-click-edit:v-edit|t-button:ev-click-delete:v-delete"},{"id":11,"firstDriverAnswer":{"id":21,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"secondDriverAnswer":{"id":22,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"result":1,"description":"some texteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee1234","actions":"t-button:ev-click-edit:v-edit|t-button:ev-click-delete:v-delete"},{"id":12,"firstDriverAnswer":{"id":23,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"secondDriverAnswer":{"id":24,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"result":1,"description":"some texteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee12345","actions":"t-button:ev-click-edit:v-edit|t-button:ev-click-delete:v-delete"}]}
    // new formate
//                var jsonStr = '{"header":{"ruleNumber": "�����", "ruleResult": "���������", "ruleDescription": "��������", "actions": "��������"}, "rows": [{"ruleNumber": 3, "ruleResult": 1, "ruleDescription": "�������� �������", "actions": "t-button:ev-click-edit:v-edit|t-button:ev-click-delete:v-delete"}]}';
//                var jsonStr = '{"header":{"id":"�����","result":"���������","description":"��������","actions":"��������"},"rows":[{"id":1,"firstDriverAnswer":{"id":1,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"secondDriverAnswer":{"id":2,"vehicleStood":false,"driverAbsent":true,"driverDroveAtParking":false,"driverMoveOutFromParking":false,"driverMoveInToParking":false,"driverDroveStraight":false,"driverDroveAtCrossroad":false,"driverMoveInCrossroadWithCircularMovement":false,"driverDroveAtCrossroadWithCircularMovement":false,"driverCollidedWithVehicleThatMovedInSameDirectionInSameLane":false,"driverCollidedWithVehicleThatMovedInSameDirectionInOtherLane":false,"driverChangedLane":false,"driverOvertook":false,"driverTurnedLeft":false,"driverTurnedRight":false,"driverTurnedBack":false,"driverDroveInReverseGear":false,"driverDroveAlongOncomingTrafficLane":false,"secondVehicleWasOnTheLeftOfMe":false,"driverHaveDrivenOnImmovableObject":false,"redLightWasSwitchingOnWhenDriverStandOrWasStopped":false,"otherCircumstances":null,"driverFailedToComplyWithRequirementOfPriorityMark":false},"result":1,"description":"some text"}]}';
//                rulesData = JSON.parse(jsonStr);
//                fillTable2(rulesData, rulesTable);
}

function fillTable(data, table) {
    if (data && data.header && data.rows)
    {
        table.innerHTML = "";
        var tr, th;
        //                    add header
        tr = document.createElement('tr');
        tr.style.height = 30 + "px";
        tr.style.color = "white";
        tr.style.background = "rgb(66, 133,244)";
//                    tr.style.background = "rgb(38,49,81)";

        var headerProperty;
        for (headerProperty in data.header) {
            if (data.header.hasOwnProperty(headerProperty))
            {
                th = document.createElement('th');
                th.appendChild(document.createTextNode(data.header[headerProperty]));
                tr.appendChild(th);
            }
        }

        table.appendChild(tr);
        //                    add rows
        for (i = 0; i < data.rows.length; i++)
        {
            tr = document.createElement('tr');
//                        tr.style.height = 50 + "px";
            if (!(i & 1)) {
                tr.style.background = "rgb(235,235,240)";
            }

            for (headerProperty in data.header) {
                for (rowObjectProperty in data.rows[i]) {
                    if (data.header.hasOwnProperty(headerProperty) && headerProperty === rowObjectProperty)
                    {
                        appendContentToTr(tr, data, i, rowObjectProperty);
                    }
                }
                table.appendChild(tr);
            }
        }
    }
}

function appendContentToTr(tr, data, i, rowObjectProperty) {
    var td = document.createElement('td');
    if (data.rows[i][rowObjectProperty] !== null && data.rows[i][rowObjectProperty].length > 1 && data.rows[i][rowObjectProperty].charAt(0) === "t" && data.rows[i][rowObjectProperty].charAt(1) === DASH)
    {
//                                        console.info("try split");
//current formate                                        t-button-edit|t-button-delete
// new formate                                          t-button:ev-click-edit:v-someValue|t-button:ev-click-delete:v-someValue
        var cellParts = data.rows[i][rowObjectProperty].split("|");
//                                        console.info("try split by |");
        var inputObject;
        if (cellParts && cellParts.length > 0) {
            var j;
            for (j = 0; j < cellParts.length; j++) {
                var partInfo = cellParts[j].split(COLON);
                if (partInfo && partInfo.length > 0) {
                    var partInfoIndex;
                    for (partInfoIndex = 0; partInfoIndex < partInfo.length; partInfoIndex++) {
                        var partSubInfo = partInfo[partInfoIndex].split(DASH);
                        switch (partSubInfo[0]) {
                            case 't':
                                inputObject = getInput(partSubInfo[1]);
                                inputObject.setAttribute("id", rowObjectProperty + DASH + (i + 1) + DASH + j);
                                break;
                            case 'ev':
                                inputObject.setAttribute("class", 'margin-5px');
                                if (partSubInfo[2] === "delete") {
                                    inputObject.addEventListener(partSubInfo[1], deleteRuleByEvent);
                                } else {
                                    inputObject.addEventListener(partSubInfo[1], getRule);
                                }
                                break;
                            case 'v':
                                if (partSubInfo[1] === 'edit') {
                                    inputObject.setAttribute("title", "Редактировать");
                                    inputObject.setAttribute("style", "background-image: url('./img/edit-icon.png'); width: 30px; height: 30px");
                                } else if (partSubInfo[1] === 'delete') {
                                    inputObject.setAttribute("title", "Удалить");
                                    inputObject.setAttribute("style", "background-image: url('./img/delete-icon.png'); width: 30px; height: 30px");
                                }
                                break;
                        }
                    }
                    td.appendChild(inputObject);
                }
            }
        }
    } else {
        var text = data.rows[i][rowObjectProperty];
        if (rowObjectProperty === 'actionName') {
            var textParts = text.split(COLON);
            var p = document.createElement("p");
            p.appendChild(document.createTextNode(textParts[1]));
            p.setAttribute("name", textParts[0]);
            td.appendChild(p);
        } else {
            td.appendChild(document.createTextNode(text));
        }
    }
    tr.appendChild(td);
}

function getInput(inputType) {
    var inputObject;
    switch (inputType)
    {
        case "checkbox":
            inputObject = document.createElement('input');
            inputObject.setAttribute("type", "checkbox");
            break;
        case "button":
            inputObject = document.createElement('input');
            inputObject.setAttribute("type", "button");
            break;
    }
    return inputObject;
}

function getDataFromForm() {
    var newRule = {};
    if (results1.checked) {
        newRule.result = 1;
    } else if (results2.checked) {
        newRule.result = 2;
    }
    newRule.description = description.value.trim();
    newRule.firstDriverAnswer = {};
    newRule.secondDriverAnswer = {};
    var row, actionName, firstDriverAnswer, secondDriverAnswer;
    for (i = 1; i < newRuleTable.rows.length; i++) {
        row = newRuleTable.rows[i];
        actionName = row.cells[1].childNodes[0].getAttribute("name");
        firstDriverAnswer = row.cells[0].childNodes[0].checked;
        secondDriverAnswer = row.cells[2].childNodes[0].checked;
        newRule.firstDriverAnswer[actionName] = false;
        newRule.secondDriverAnswer[actionName] = false;

        if (firstDriverAnswer === true) {
            newRule.firstDriverAnswer[actionName] = true;
        }
        if (secondDriverAnswer === true) {
            newRule.secondDriverAnswer[actionName] = true;
        }
    }
    return newRule;
}
