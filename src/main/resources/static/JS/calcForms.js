(function () {
    if (null != document.getElementById("selectPackCalcFormCancel")) {
        document.getElementById("selectPackCalcFormCancel").addEventListener("click", hidePackageFormForCalc);
    }
})();

/** CALCULATION MODEL: PackSize, Unit */
function PackageSize(id, size, unit) {
    this.id = id;
    this.size = size;
    this.unit = unit;
}

function Unit(id, unit) {
    this.id = id;
    this.unit = unit;
}

/** CALCULATION CONTROLLER */

/** open and close functions for shorted form of calculation order */
function showPackageFormForCalc(assessmentId, processId) {
    document.getElementById("selectPackCalcFormAssessId").value = assessmentId;
    document.getElementById("selectPackCalcFormProcesId").value = processId;

    getPacksSizes("http://localhost:8080/api/assessment/packSize/byAssess/" + assessmentId, "selectPackCalcFormContainer");
}

function hidePackageFormForCalc() {
    document.getElementById("bckgdSelectPackCalcForm").style.display = "none";

    clearCheckBoxesContainer("selectPackCalcFormContainer");
}

/** open and close functions for full form of calculation order */
function showCalcOrderForm() {
    createProcessList("http://localhost:8080/api/process", "orderCalcFormOrders");
    document.getElementById("bckgdOrderCalcForm").style.display = "block";
}

function hideCalcOrderForm() {
    document.getElementById("bckgdOrderCalcForm").style.display = "none";
    clearDownDropList("orderCalcFormAssess");
    clearDownDropList("orderCalcFormOrders");
    document.querySelector("label[for=\"orderCalcFormPackContainer\"]").style.display = "none";
    if (document.getElementById("orderCalcFormPackContainer").childElementCount) {
        clearCheckBoxesContainer("orderCalcFormPackContainer");
    }
}

/** method for getting assessment list from server for particular process and implementing it in full calc form as a down-drop list */
function prepareAssessmentsInCalcForm() {
    if (document.getElementById("orderCalcFormPackContainer").childElementCount) {
        clearCheckBoxesContainer("orderCalcFormPackContainer");
    }
    document.querySelector("label[for=\"orderCalcFormPackContainer\"]").style.display = "none";
    clearDownDropList("orderCalcFormAssess");
    var processId = document.getElementById("orderCalcFormOrders").value;

    if (processId != "") {
        createAssessmentList("/api/assessment/process/" + processId, "orderCalcFormAssess");
    }
}

/** method for getting packages list from server for particular assessment and implementing it in full calc form as a checkbox list */
function preparePackListInCalcForm() {
    clearCheckBoxesContainer("orderCalcFormPackContainer");
    var assessmentId = document.getElementById("orderCalcFormAssess").value;
    if (assessmentId == "") {
        document.querySelector("label[for=\"orderCalcFormPackContainer\"]").style.display = "none";
    } else {
        getPacksSizes("http://localhost:8080/api/assessment/packSize/byAssess/" + assessmentId, "orderCalcFormPackContainer");
        document.querySelector("label[for=\"orderCalcFormPackContainer\"]").style.display = "flex";
    }
}

/** validator for full calc form in scope passing package size parameter. Rest of parameters are validate by HTML attributes 'required' */
function checkCalcFormValidity() {
    var packages = document.getElementById("orderCalcFormPackContainer").children;

    if (packages.length == 0) {
        alert("No package found. Please add packages in assessment");
        return false;
    }

    for (var i = 0; i < packages.length; i++) {
        if (packages[i].firstElementChild.checked) {
            return true;
        }
    }
    alert("No package is selected. Please select");
    return false;
}

/** CALCULATION METHODS */


function getPacksSizes(urlAPI, idContainer) {

    var xhttp = new XMLHttpRequest();
    xhttp.open("get", urlAPI, true);
    xhttp.responseType = "json";
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.onload = function () {

        if (xhttp.status === 404) {
            alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
        } else {
            var packList = createPackCheckboxOptions(xhttp.response);
            for (var j = 0; j < packList.length; j++) {
                document.getElementById(idContainer).appendChild(packList[j]);
            }

        }
        document.getElementById("bckgdSelectPackCalcForm").style.display = "block";
    }
    xhttp.onerror = function () {
        alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
    }
    xhttp.send();
}


function createPackCheckboxOptions(packagesList) {

    var packInputs = new Array();

    for (var i = 0; i < packagesList.length; i++) {

        var packLabel = document.createElement("label");
        var packInput = document.createElement("input");

        packInput.setAttribute("type", "checkbox");
        packInput.setAttribute("name", "packageSizeIds");
        packInput.value = packagesList[i].id;
        packLabel.appendChild(packInput);

        var packText = document.createTextNode(packagesList[i].size + " " + packagesList[i].unit.unit);
        packLabel.appendChild(packText);

        packInputs.push(packLabel);
    }
    return packInputs;
}

function createProcessOptions(response) {

    var optionProcessList = new Array();

    for (var i = 0; i < response.length; i++) {
        var processOption = document.createElement("option");
        processOption.value = response[i].id;
        processOption.textContent = response[i].product.name + " / " + response[i].product.drug_form + " / " + response[i].client.name;
        optionProcessList.push(processOption);
    }

    return optionProcessList;
}

function createProcessList(urlAPI, idContainer) {

    var xhttp = new XMLHttpRequest();
    xhttp.open("get", urlAPI, true);
    xhttp.responseType = "json";
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.onload = function () {

        if (xhttp.status === 404) {
            alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
        } else {
            var processList = createProcessOptions(xhttp.response);
            var container = document.getElementById(idContainer);
            for (var i = 0; i < processList.length; i++) {
                container.appendChild(processList[i]);
            }
        }
    }
    xhttp.onerror = function () {
        alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
    }
    xhttp.send();
}

function createAssessmentOptions(response) {

    var optionAssessmentList = new Array();

    for (var i = 0; i < response.length; i++) {
        var assessmentOption = document.createElement("option");
        assessmentOption.value = response[i].id;
        assessmentOption.textContent = response[i].assessmentNo + " / " + response[i].registration_country.country_name + " / " + response[i].destined_product_status.product_status;
        optionAssessmentList.push(assessmentOption);
    }

    return optionAssessmentList;
}

function createAssessmentList(urlAPI, idContainer) {

    var xhttp = new XMLHttpRequest();
    xhttp.open("get", urlAPI, true);
    xhttp.responseType = "json";
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.onload = function () {

        if (xhttp.status === 404) {
            alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
        } else {
            var assessmentList = createAssessmentOptions(xhttp.response);
            var container = document.getElementById(idContainer);
            for (var i = 0; i < assessmentList.length; i++) {
                container.appendChild(assessmentList[i]);
            }
        }
    }
    xhttp.onerror = function () {
        alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
    }
    xhttp.send();
}

function clearDownDropList(idList) {
    var downDropList = document.getElementById(idList);
    while (downDropList.children.length > 1) {
        downDropList.removeChild(downDropList.lastElementChild);
    }
}

function clearCheckBoxesContainer(idContainer) {
    var selectContainer = document.getElementById(idContainer);

    while (selectContainer.childElementCount) {
        selectContainer.removeChild(selectContainer.firstElementChild);
    }
}


