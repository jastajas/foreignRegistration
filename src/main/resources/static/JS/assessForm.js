/** Assessment Order Form Object JavaScript Library */
var switcherPosition = 0;
var allCtdWidth = 75;
var allCtdText = 26;
var partCtdWidth = 0;

var switchButton = document.getElementById("insideButtonID");
var boxSwitcher = document.getElementById("switcherBoxId");


var switcherInterval;
if (boxSwitcher != null) {
    boxSwitcher.addEventListener("click", runSwitcher);
}

/** Function for opening assessment order form and for adding event listeners of its fields */
function showForm() {
    if (document.getElementById("basicAssessmentFormBckGd") == null) {
        alert("Błąd formularza. Zgłoś problem przez formularz kontaktowy.");
        return false;
    }

    if (null != document.getElementById("productBasicForm")) {
        document.getElementById("productBasicForm").addEventListener("change", sitchOffAssessmentList);
    }
    if (null != document.getElementById("countryBasicForm")) {
        document.getElementById("countryBasicForm").addEventListener("change", sitchOffAssessmentList);
    }

    if (null != document.getElementById("destinedProductStatusBasicForm")) {
        document.getElementById("destinedProductStatusBasicForm").addEventListener("change",
            function () {
            sitchOffAssessmentList();
                hideShowMedicineElements(document.getElementById("destinedProductStatusBasicForm"));
            })
    }
    if (null != document.getElementById("requiredQalificationBasicForm")) {
        document.getElementById("requiredQalificationBasicForm").addEventListener("change", sitchOffAssessmentList);
    }

    var inputFieldAvailabilityStatus = document.getElementsByName("avStatus");
    var inputFieldCTDscope = document.getElementsByName("dossierType");

    for (var h = 0; h < inputFieldAvailabilityStatus.length; h++) {
        inputFieldAvailabilityStatus[h].addEventListener("change", sitchOffAssessmentList);
    }
    for (var h = 0; h < inputFieldCTDscope.length; h++) {
        inputFieldCTDscope[h].addEventListener("change", sitchOffAssessmentList);
    }
    document.getElementById("basicAssessmentFormBckGd").style.display = "flex";
}

/** Method for preparing assessment order form to order of the assessment for current/active general dossier order */

function showProcessList() {

    if (currentProcessId == null) {
        alert("The process id is not provided. Please take another action attempt. If problem occurs again, please contact with IT.");
        return false;
    }
    var procesInput = createFilledInputTextElementNonVisible(currentProcessId, "processID");
    procesInput.id = "inputProcessID";
    document.getElementById("basicAssessmentForm").appendChild(procesInput);
    var inactiveInputsList = ["clientBasicForm", "cooperationBasicForm", "productBasicForm", "destinedNameBasicForm", "originalQalificationBasicForm"];
    for (var i = 0; i < inactiveInputsList.length; i++) {
        document.getElementById(inactiveInputsList[i]).disabled = true;
    }
    showForm();
}

/** Method for closing assessment */
function closeAddAssessForm() {
    if (null != document.getElementById("inputProcessID")) {
        document.getElementById("basicAssessmentForm").removeChild(document.getElementById("inputProcessID"));
        var inactiveInputsList = ["clientBasicForm", "cooperationBasicForm", "productBasicForm", "destinedNameBasicForm", "originalQalificationBasicForm"];
        for (var i = 0; i < inactiveInputsList.length; i++) {
            document.getElementById(inactiveInputsList[i]).disabled = false;
        }
    }
    document.getElementById("basicAssessmentFormBckGd").style.display = "none";
}


function runSwitcher() {
    sitchOffAssessmentList();
    var startVal = switcherPosition;
    switcherInterval = setInterval(function () {
        changeSwitcherPosition(startVal);
    }, 1);
}

function changeSwitcherPosition(startPoint) {
    var allCtdParts = document.getElementById("allCTD");
    var partCtd = document.getElementById("partCTD");
    var ctdPartsDiv = document.getElementById("ctdPartsDiv");
    var listCtdParts = document.getElementsByClassName("checkCTDInput");
    if (startPoint == 0) {
        if (switcherPosition < 75) {
            switcherPosition++;
            allCtdWidth--;
            allCtdText++;
            partCtdWidth++;
            allCtdParts.style.width = allCtdWidth + "px";
            allCtdParts.style.left = allCtdText + "px";
            switchButton.style.left = switcherPosition + "px";
            partCtd.style.width = partCtdWidth + "px";
        } else {
            clearInterval(switcherInterval);
            for (var z = 0; z < listCtdParts.length; z++) {
                listCtdParts[z].checked = false;
            }
            ctdPartsDiv.style.display = "flex";
        }
    } else {
        if (switcherPosition > 0) {
            switcherPosition--;
            allCtdWidth++;
            allCtdText--;
            partCtdWidth--;
            switchButton.style.left = switcherPosition + "px";
            partCtd.style.width = partCtdWidth + "px";
            allCtdParts.style.width = allCtdWidth + "px";
            allCtdParts.style.left = allCtdText + "px";
        } else {
            clearInterval(switcherInterval);
            for (var s = 0; s < listCtdParts.length; s++) {
                listCtdParts[s].checked = true;
            }
            ctdPartsDiv.style.display = "none";
        }
    }
}

function hideShowMedicineElements(destinedProductStatus) {

    if (destinedProductStatus.value != "1" && switcherPosition == 75) {
        runSwitcher();
    }
    if (destinedProductStatus.value != "1") {
        document.getElementById("availabilityProdDiv").style.display = "none";
        document.getElementById("originalQalificDiv").style.display = "none";
        document.getElementById("requiredQalificDiv").style.display = "none";
        document.getElementById("ctdSwitcherDiv").style.display = "none";
        document.getElementById("availStatNone").checked = true;
        document.getElementById("originalQalification").value = "";
        document.getElementById("requiredQalification").value = "";
    } else {
        document.getElementById("availabilityProdDiv").style.display = "flex";
        document.getElementById("originalQalificDiv").style.display = "flex";
        document.getElementById("requiredQalificDiv").style.display = "flex";
        document.getElementById("ctdSwitcherDiv").style.display = "flex";
    }

}

var idToAddElement = 0;

function requirementsCatcher(numBtn) {
    if (numBtn == 1) {
        var reqName = document.getElementById("rquirementID").value;
        document.getElementById("rquirementID").value = "";
        var reqListDiv = document.getElementById("reqListDiv");
        var reqListTableBody = document.getElementById("reqTableBody");
    } else if (numBtn == 2) {
        var reqName = valueCorrector(document.getElementById("packageID").value);
        document.getElementById("packageID").value = "";
        var unitVal = document.getElementById("packageUnitId").value;
        document.getElementById("packageUnitId").value = "";
        var reqListDiv = document.getElementById("packListDiv");
        var reqListTableBody = document.getElementById("packTableBody");
    }

    if (reqName == "") {
        alert("Nie dodano wymagania bo brak wprowadzanych danych");
    } else if (unitVal == "") {
        alert("Nie dodano wymagania bo brak jednostki");
    } else if (!isCorrectNumber(reqName) && numBtn == 2) {
        alert("Nie dodano wymagania bo podano niewłaściwą wartość");
    } else {
        idToAddElement++;

        if (numBtn == 1) {
            reqListTableBody.appendChild(createSimpleTableRow(3, ["lpColumnReq", "valueColumnPack", "removeCol"], reqName, null, idToAddElement, 1));
        } else {
            reqListTableBody.appendChild(createSimpleTableRow(4, ["lpColumnPack", "valueColumnPack", "unitColumnPack", "removeCol"], reqName, unitVal, idToAddElement, 2));
        }

        if (reqListTableBody.childElementCount > 0) {
            reqListDiv.style.display = "flex";
        }

        if (numBtn == 1) {
            prepareOrdinalsNo("lpColumnReq");
        } else {
            prepareOrdinalsNo("lpColumnPack");
        }
    }
}

function valueCorrector(numberVal) {

    var wrongValPattern1 = new RegExp("^[/.][0-9]{1,}$");
    var wrongValPattern2 = new RegExp("^[0-9]{1,}[/.]$");

    if (wrongValPattern1.test(numberVal)) {
        numberVal = '0' + numberVal;
    } else if (wrongValPattern2.test(numberVal)) {
        numberVal = numberVal + '0';
    }

    return numberVal;
}

function removeRequirement(idElementToRemove, tableID) {
    var elementToRemove = document.getElementById(
        "columnRemove" + idElementToRemove
    );
    elementToRemove.parentNode.removeChild(elementToRemove);
    var tempTable;
    var entireTableDiv;
    if (tableID == 1) {
        prepareOrdinalsNo("lpColumnReq");
        var tempTable = document.getElementById("reqTableBody");
        var entireTableDiv = "reqListDiv";
    } else {
        prepareOrdinalsNo("lpColumnPack");
        var tempTable = document.getElementById("packTableBody");
        var entireTableDiv = "packListDiv";
    }
    if (tempTable.childElementCount == 0) {
        var hiddenTable = document.getElementById(entireTableDiv);
        hiddenTable.style.display = "none";
    }
}

function prepareOrdinalsNo(columnClassName) {
    var rowList = document.getElementsByClassName(columnClassName);
    for (i = 0; i < rowList.length; i++) {
        rowList[i].innerHTML = i + 1;
    }
}

function createSimpleTableRow(noOfColumns, classColumns, mainValue, unitValue, idAddedElement, buttonNo) {

    var simpleRow = document.createElement("tr");

    for (var p = 0; p < noOfColumns; p++) {
        var simpleCol = document.createElement("td");
        simpleCol.setAttribute("class", classColumns[p]);

        if (p == 1) {
            var inputMainElement;
            switch (noOfColumns) {
                case 3:
                    inputMainElement = document.createElement("textarea");
                    inputMainElement.setAttribute("name", "requirement");
                    break;
                case 4:
                    inputMainElement = document.createElement("input");
                    inputMainElement.setAttribute("type", "text");
                    inputMainElement.setAttribute("name", "packageSize");
                    break;
            }
            inputMainElement.value = mainValue;
            inputMainElement.setAttribute("class", "requirementInput");
            inputMainElement.disabled = true;
            simpleCol.appendChild(inputMainElement);

        } else if (p == (noOfColumns - 1)) {
            var btnColumn = document.createElement("button");
            btnColumn.textContent = "Remove";
            btnColumn.setAttribute("onclick",
                "removeRequirement(" + idAddedElement + "," + buttonNo + ")");
            simpleCol.appendChild(btnColumn);
        } else if (p == 2) {
            var inputSecondElement = document.createElement("input");
            inputSecondElement.value = unitValue;
            inputSecondElement.disabled = true;
            inputSecondElement.setAttribute("name", "packageUnit");
            simpleCol.appendChild(inputSecondElement);
        }

        simpleRow.appendChild(simpleCol);

    }
    simpleRow.setAttribute("id", "columnRemove" + idAddedElement);
    return simpleRow;
}

function isCorrectNumber(checkingValue) {
    var amountPattern = new RegExp("^[0-9]{1,}[/.][0-9]{1,}$");
    var amountPattern2 = new RegExp("^[/.][0-9]{1,}$");
    var amountPattern3 = new RegExp("^[0-9]{1,}[/.]$");
    var amountPattern4 = new RegExp("^[0-9]{1,}$");

    if (amountPattern.test(checkingValue) || amountPattern2.test(checkingValue) || amountPattern3.test(checkingValue) || amountPattern4.test(checkingValue)) {
        return true;
    }
    return false;
}

function showOrdersList() {

    if (document.getElementById("checkBoxOrderList").checked) {
        getOrderList();
    } else {
        //dodać czyszczenie listy jako metoda
        document
            .getElementById("orderListId")
            .setAttribute("class", "entityForm hiddenAddFormElement");
    }
}

function getOrderList() {
    var assessmentList;
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "http://localhost:8080/api/assessment", true);
    xhttp.responseType = "json";
    //xhttp.setRequestHeader(header, token);
    xhttp.onload = function () {
        //console.log('DONE', xhttp.readyState); // readyState will be 4
        //console.log(xhttp.responseType);
        assessmentList = xhttp.response;
        console.log(assessmentList);
        if (xhttp.status === 404) {
            alert("Dupa blada");
        }
        makeOrderList(assessmentList);
        document.getElementById("orderListId").setAttribute("class", "entityForm visibleAddFormElement");
    };
    xhttp.send(null);
}


function makeOrderList(ajaxResponseObj) {
    var ordersInput = document.getElementById("assignedOrder");
    if (ordersInput.length > 0) {
        while (ordersInput.hasChildNodes()) {
            ordersInput.removeChild(ordersInput.firstChild);
        }
    }
    var firstOrderBlankObj = document.createElement("option");
    firstOrderBlankObj.textContent = "Select...";
    firstOrderBlankObj.value = "";
    ordersInput.appendChild(firstOrderBlankObj);
    for (var q = 0; q < ajaxResponseObj.length; q++) {
        var orderSimpleObj = document.createElement("option");
        orderSimpleObj.textContent = ajaxResponseObj[q].assessmentNo;
        orderSimpleObj.id = ajaxResponseObj[q].id;
        ordersInput.appendChild(orderSimpleObj);
    }
}


function sitchOffAssessmentList() {
    if (document.getElementById("checkBoxOrderList").checked) {
        document.getElementById("checkBoxOrderList").checked = false;
        document.getElementById("orderListId").setAttribute("class", "entityForm hiddenAddFormElement");
        document.getElementById("orderListId").children[1].value = "0";
    }
}

function prepareBasicAssessmentFormInputAsEnable(firstClass, secondClass, thirdClass) {

    var requirementsEnable = document.getElementsByName(firstClass);

    if (requirementsEnable != null) {
        for (var i = 0; i < requirementsEnable.length; i++) {
            requirementsEnable[i].disabled = false;
        }
    }

    var packSizeEnable = document.getElementsByName(secondClass);
    var packUnitEnable = document.getElementsByName(thirdClass);

    if (packSizeEnable != null) {
        for (var i = 0; i < packSizeEnable.length; i++) {
            packSizeEnable[i].disabled = false;
            packUnitEnable[i].disabled = false;
        }
    }

}


function createFilledInputTextElementNonVisible(valueInput, nameInput) {
    var newInputElement = document.createElement("input");
    newInputElement.setAttribute("type", "text");
    newInputElement.setAttribute("name", nameInput);
    newInputElement.value = valueInput;
    newInputElement.style.display = "none";
    return newInputElement;
}