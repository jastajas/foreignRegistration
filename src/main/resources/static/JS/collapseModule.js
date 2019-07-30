/** set of methods for horizontal collapse module */
(function () {
    var collapseBars = document.querySelectorAll(".collapseBar");

    collapseBars[0].addEventListener("click", function () {
        hideShowCard(1);
    });
    collapseBars[1].addEventListener("click", function () {
        hideShowCard(2);
    });
    collapseBars[2].addEventListener("click", function () {
        hideShowCard(3);
    });
    collapseBars[3].addEventListener("click", function () {
        hideShowCard(4);
    });

    if (null != document.getElementById("departAssessFormCancelBtn")) {
        document.getElementById("departAssessFormCancelBtn").addEventListener("click", closeDepartAssessForm);
    }
    if (null != document.getElementById("closeRequirementFormBtn")) {
        document.getElementById("closeRequirementFormBtn").addEventListener("click", closeRequirementForm);
    }
    if (null != document.getElementById("closeAddAssessSubjectBtn")) {
        document.getElementById("closeAddAssessSubjectBtn").addEventListener("click", closeAddAssessSubjectForm);
    }
})();

/** method for closing departments assessment of dossier form. At first form is hidden. Then all added elements (costs and man-hours) are
 * removed. In the end assessment description and criticality assessment are cleared*/
function closeDepartAssessForm() {
    document.getElementById("bckgdAddDepartAssessForm").style.display = "none";
    var tableElementList = document.getElementById("costTableBody").children;

    while (tableElementList.length > 1) {
        tableElementList[0].parentElement.removeChild(tableElementList[0]);
    }
    var tableElementList = document.getElementById("mhTableBody").children;

    while (tableElementList.length > 1) {
        tableElementList[0].parentElement.removeChild(tableElementList[0]);
    }
    document.getElementById("addDepartAssessFormDescription").value = "";
    document.getElementById("addDepartAssessFormImportance").value = "";
}


function hideShowCard(btnNo) {
    var collapseDiv = document.getElementById("collapseDiv" + btnNo);
    var styleCollapseDiv = window.getComputedStyle(collapseDiv, null);
    var heightCollapseDiv = styleCollapseDiv.getPropertyValue("height");


    if (heightCollapseDiv == "0px") {
        collapseDiv.style.height = collapseDiv.scrollHeight + "px";
    } else {
        collapseDiv.style.height = "0px";
    }

    if (btnNo == 4 && heightCollapseDiv == "0px") {
        var changedBar = document.getElementById("collapseBar" + btnNo);
        changedBar.style.borderRadius = "0px 0px 0px 0px";
    } else if (btnNo == 4 && heightCollapseDiv != "0px") {
        var changedBar = document.getElementById("collapseBar" + btnNo);
        changedBar.style.borderRadius = "0px 0px 6px 6px";
    }
}

var formIdSuplier = 1;
var currencySelect = document.getElementById("selectCostCurrency");

currencySelect.addEventListener("change", selectSelectedOption);


document.getElementById("addCostButton").addEventListener("click", function () {
    addItemToTable("addCostRow", ["costSubject", "costValue", "costCurrency"], "costTableRow", "cost", 3);
});
document.getElementById("addMhButton").addEventListener("click", function () {
    addItemToTable("addMhRow", ["mhSubject", "mhValue"], "mhTableRow", "integer", 3);
});

function selectSelectedOption() {
    var tempIndex = currencySelect.selectedIndex;
    for (i = 0; i < currencySelect.options.length; i++) {
        currencySelect.options[i].removeAttribute("selected");
    }
    currencySelect.options[tempIndex].setAttribute("selected", true);
}

function addItemToTable(idPatternRow, parametersNames, destinedRowClass, regExpType, checkedColumn) {
    var patternRow = document.getElementById(idPatternRow);

    for (var i = 1; i < patternRow.childElementCount - 1; i++) {

        if (patternRow.children[i].firstElementChild.value == "") {
            alert("Nie dodano wiersza - brak wprowadzonych danych.");
            return false;
        }
        if (regExpType != "cost" && regExpType != "integer") {
            continue;
        } else if (checkedColumn - 1 != i) {
            continue;
        } else {
            switch (regExpType) {
                case "cost":
                    if (
                        isCostValueCorrect(patternRow.children[i].firstElementChild.value)
                    ) {
                        continue;
                    }
                    break;
                case "integer":
                    if (
                        isIntegerValueCorrect(
                            patternRow.children[i].firstElementChild.value
                        )
                    ) {
                        continue;
                    }
            }
            alert("Nie dodano wiersza - nieprawidlowy format liczby.");
            return false;
        }
    }
    var newRowToInsert = createNewRow(parametersNames, patternRow, destinedRowClass, formIdSuplier);
    patternRow.parentElement.insertBefore(newRowToInsert, patternRow);
    for (var i = 1; i < patternRow.childElementCount - 1; i++) {
        patternRow.children[i].firstElementChild.value = "";
    }
    formIdSuplier++;
    var defaultVal = "";
    if (regExpType == "cost") {
        defaultVal = "0.00";
    } else if (regExpType == "integer") {
        defaultVal = 0;
    }
    patternRow.children[checkedColumn - 1].firstElementChild.value = defaultVal;
    prepareOrdinalsNumbersRows(destinedRowClass);
}


function isCostValueCorrect(checkedCostValue) {
    var amountPattern1 = new RegExp("^[0-9]{1,}[/.][0-9]{1,2}$");
    var amountPattern2 = new RegExp("^[0]{1,}[/.][0]{2}$");
    if (
        !amountPattern1.test(checkedCostValue) ||
        amountPattern2.test(checkedCostValue)
    ) {
        return false;
    }
    return true;
}

function isIntegerValueCorrect(checkedIntegerValue) {
    var integerPattern1 = new RegExp("^[0-9]{1,}$");
    var integerPattern2 = new RegExp("^[0]{1,}$");
    return (integerPattern1.test(checkedIntegerValue) && !integerPattern2.test(checkedIntegerValue));
}

function createNewRow(paramNames,
                      patternRow,
                      idDestinedRowName,
                      idDestinedRow) {
    var clonedRow = patternRow.cloneNode(true);
    var internalIter = 0;
    for (var g = 0; g < clonedRow.childElementCount; g++) {
        if (g !== 0 && g !== clonedRow.childElementCount - 1) {
            clonedRow.children[g].firstElementChild.setAttribute(
                "name",
                paramNames[internalIter]
            );
            clonedRow.children[g].firstElementChild.disabled = true;
            internalIter++;
        } else if (g === clonedRow.childElementCount - 1) {
            clonedRow.children[g].firstElementChild.textContent = "REMOVE";
            clonedRow.children[g].firstElementChild.setAttribute(
                "onclick",
                "removeTempRow('" + idDestinedRowName + idDestinedRow + "', '" + idDestinedRowName + "')"
            );
        }
    }
    clonedRow.setAttribute("class", idDestinedRowName);
    clonedRow.id = idDestinedRowName + idDestinedRow;
    return clonedRow;
}

function removeTempRow(idCostToRemove, classRowToRemove) {
    var costRowToRemove = document.getElementById(idCostToRemove);
    costRowToRemove.parentNode.removeChild(costRowToRemove);
    prepareOrdinalsNumbersRows(classRowToRemove);
}

function makeAssessment(tailNo) {
    var oneTail = document.getElementById("assessmentTail" + tailNo);
    var titleTail = oneTail.textContent;

    document.getElementById("departAssessFormId").value = tailNo;

    var formTitleNode = document.getElementById("departAssessFormTitle");
    formTitleNode.textContent = titleTail;

    document.getElementById("bckgdAddDepartAssessForm").style.display = "block";
}

function editAssessmentDepartment(idEditRow) {
    var sourceDataRow = document.getElementById('departmentAssessment' + idEditRow);

    document.getElementById("departAssessFormId").value = idEditRow;
    document.getElementById("departAssessFormTitle").textContent = sourceDataRow.children[0].textContent;
    document.getElementById("addDepartAssessFormDescription").value = sourceDataRow.children[1].textContent;
    document.getElementById("addDepartAssessFormImportance").value = sourceDataRow.children[4].textContent;

    var departAssessCostList = sourceDataRow.children[3].firstElementChild.children;

    if (departAssessCostList.length > 0) {
        for (var i = 0; i < departAssessCostList.length; i++) {

            var costComponents1 = departAssessCostList[i].textContent.split(" ");

            document.getElementById("addCostRow").children[2].firstElementChild.value = costComponents1[0];
            document.getElementById("addCostRow").children[3].firstElementChild.value = costComponents1[1];

            var costComponents2 = departAssessCostList[i].textContent.split(" - ");
            document.getElementById("addCostRow").children[1].firstElementChild.value = costComponents2[1];
            selectSelectedOption();
            addItemToTable("addCostRow", ["costSubject", "costValue", "costCurrency"], "costTableRow", "cost", 3);
            //  document.getElementById("costTableBody").children[i]
            //   .appendChild(createFilledInputTextElementNonVisible(departAssessCostList[i].id.substr(24), "idAssessmentCostMH"));
        }
        prepareOrdinalsNumbersRows("costTableRow");
    }
    var departAssessMhList = sourceDataRow.children[2].firstElementChild.children;

    if (departAssessMhList.length > 0) {
        for (var i = 0; i < departAssessMhList.length; i++) {

            var mhComponents = departAssessMhList[i].textContent.split(" - ");

            document.getElementById("addMhRow").children[1].firstElementChild.value = mhComponents[1];
            document.getElementById("addMhRow").children[2].firstElementChild.value = mhComponents[0];

            addItemToTable("addMhRow", ["mhSubject", "mhValue"], "mhTableRow", "integer", 3);
            //document.getElementById("mhTableBody").children[i]
            // .appendChild(createFilledInputTextElementNonVisible(departAssessMhList[i].id.substr(22), "idAssessmentCostMH"));
        }
        prepareOrdinalsNumbersRows("mhTableRow");
    }

    document.getElementById("bckgdAddDepartAssessForm").style.display = "block";
}

function showRequirementForm() {
    document.getElementById("addRequirementFormBckd").style.display = "block";
}

function closeRequirementForm() {
    if (document.getElementById("addRequirementFormBckd").style.display == "block") {
        document.getElementById("addRequirementFormTextarea").value = "";
        document.getElementById("addRequirementFormBckd").style.display = "none";
    }
}


function showAddAssessmentSubjectForm() {
    document.getElementById("addAssessSubjectFormBckgd").style.display = "block";

}

function closeAddAssessSubjectForm() {
    if (document.getElementById("addAssessSubjectFormBckgd").style.display == "block") {
        document.getElementById("addAssessSubjectFormSubject").value = "";
        document.getElementById("addAssessSubjectFormDepart").value = "";
        document.getElementById("addAssessSubjectFormBckgd").style.display = "none";
    }
}

function changeAvailability(fieldName, btnName, objectType, objectId) {

    var detailToChange = document.getElementById(fieldName);
    detailToChange.disabled = false;
    //detailToChange.autofocus = true;
    detailToChange.style.webkitAppearance = "menulist-button";
    detailToChange.style.mozAppearance = "menulist-button";

    var toReplacedBtn = document.getElementById(btnName);
    toReplacedBtn.textContent = "CONFIRM";
    var onClickValue = "changeAssessmentDetail('" + fieldName + "', '" + btnName + "', '" + objectType + "', " + objectId + ")";
    toReplacedBtn.setAttribute("onclick", onClickValue);

    //toReplacedBtn.parentNode.replaceChild(submitBtn, toReplacedBtn);
}

function showCheckboxSet(fieldsName, btnName, objectType, objectId, visibleDiv, hiddenDiv) {

    document.getElementById(visibleDiv).style.display = "none";
    document.getElementById(hiddenDiv).style.display = "flex";

    var toReplacedBtn = document.getElementById(btnName);
    toReplacedBtn.textContent = "CONFIRM";
    var onClickValue = "changeAssessmentDetailCheckBox('" + fieldsName + "', '" + btnName + "', '" + objectType + "', " + objectId + ", '" + visibleDiv + "', '" + hiddenDiv + "')";
    toReplacedBtn.setAttribute("onclick", onClickValue);

}

function changeAssessmentDetailCheckBox(fieldsName, btnName, objectType, objectId, visibleDiv, hiddenDiv) {
    var paramName = document.getElementsByName(fieldsName);
    var valuesDossier = [];
    //alert(valuesDossier);
    for (var i = 0; i < paramName.length; i++) {
        if (paramName[i].checked) {
            valuesDossier.push(paramName[i].value);
        }
    }
    //alert(valuesDossier);
    var urlAPI = "http://localhost:8080/api/" + objectType + "/" + fieldsName + "/" + objectId;
    var token = document.getElementsByName("_csrf")[0].content;
    var header = document.getElementsByName("_csrf_header")[0].content;
    var xhttp = new XMLHttpRequest();
    xhttp.open("PUT", urlAPI, true);
    xhttp.responseType = "json";
    xhttp.setRequestHeader(header, token);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.onload = function () {
        if (xhttp.status === 404) {
            alert("Object not found");
        }
        document.getElementById(btnName).textContent = "EDIT";
        //var assessmentList = xhttp.response;
        document.getElementById(hiddenDiv).style.display = "none";

        document.getElementById(visibleDiv).style.display = "flex";

        var newOnclickVal = "showCheckboxSet('" + fieldsName + "','" + btnName + "','" + objectType + "'," + objectId + ", '" + visibleDiv + "', '" + hiddenDiv + "')";
        document.getElementById(btnName).setAttribute("onclick", newOnclickVal);
        document.querySelector("#dossierFormValues").parentElement.replaceChild(prepareCTDList(xhttp.response), document.querySelector("#dossierFormValues"));
    };
    xhttp.send(JSON.stringify(valuesDossier));
}

function prepareCTDList(assessmentObj) {

    var ctdElements = [];

    if (assessmentObj.s_module) {
        var simpleCTDtail = document.createElement("p");
        simpleCTDtail.textContent = "S Module";
        ctdElements.push(simpleCTDtail);
    }
    if (assessmentObj.clinical_module) {
        var simpleCTDtail = document.createElement("p");
        simpleCTDtail.textContent = "Clinical Module";
        ctdElements.push(simpleCTDtail);
    }
    if (assessmentObj.nonclinical_module) {
        var simpleCTDtail = document.createElement("p");
        simpleCTDtail.textContent = "Nonclinical Module";
        ctdElements.push(simpleCTDtail);
    }
    if (assessmentObj.quality_module) {
        var simpleCTDtail = document.createElement("p");
        simpleCTDtail.textContent = "Quality Module";
        ctdElements.push(simpleCTDtail);
    }
    var ctdInsert;
    if (ctdElements.length) {
        ctdInsert = document.createElement("div");

        ctdInsert.setAttribute("class", "flexRowCenter");
        for (var i = 0; i < ctdElements.length; i++) {
            ctdElements[i].setAttribute("class", "dossierTail");
            ctdInsert.appendChild(ctdElements[i]);
        }
    } else {
        ctdInsert = document.createElement("p");
        ctdInsert.textContent = "CTD nie jest wymagane";
    }

    var externalCTDbox = document.createElement("div");
    externalCTDbox.id = "dossierFormValues";
    externalCTDbox.appendChild(ctdInsert);
    return externalCTDbox;
}


function changeAssessmentDetail(changedField, btnName, objectType, objectId) {

    var paramName = document.getElementById(changedField).getAttribute("name");
    var paramValue = document.getElementById(changedField).value;
    var urlAPI = "http://localhost:8080/api/" + objectType + "/" + objectId + "/" + paramName + "/" + paramValue;
    var token = document.getElementsByName("_csrf")[0].content;
    var header = document.getElementsByName("_csrf_header")[0].content;


    var xhttp = new XMLHttpRequest();
    xhttp.open("PUT", urlAPI, true);
    xhttp.responseType = "json";
    xhttp.setRequestHeader(header, token);
    xhttp.onload = function () {
        //console.log('DONE', xhttp.readyState); // readyState will be 4
        if (xhttp.status === 404) {
            alert("Object not found");
        }
        document.getElementById(btnName).textContent = "EDIT";
        document.getElementById(changedField).disabled = true;
        document.getElementById(changedField).style.webkitAppearance = "none";
        document.getElementById(changedField).style.mozAppearance = "none";
        var newOnclickVal = "changeAvailability('" + changedField + "','" + btnName + "','" + objectType + "'," + objectId + ")";
        document.getElementById(btnName).setAttribute("onclick", newOnclickVal);
    };
    xhttp.send(null);
}

function prepareOrdinalsNumbersRows(allRowsClassName) {
    var destinedRowList = document.getElementsByClassName(allRowsClassName);
    for (i = 0; i < destinedRowList.length; i++) {
        destinedRowList[i].firstElementChild.innerHTML = i + 1;
    }
}

function makeInputEnable(costTableRow, mhTableRow) {

    var costInputList = document.getElementsByClassName(className1);
    var currencyInputList = document.getElementsByClassName(className2);

    for (var i = 0; i < costInputList.length; i++) {
        costInputList[i].disabled = false;
        currencyInputList[i].disabled = false;
    }
}

function makeAllInputsEnable(tablesIdList) {


    var inputTypesName = ["input", "textarea", "select"];

    for (var i = 0; i < tablesIdList.length; i++) {

        var simpleTable = document.getElementById(tablesIdList[i]);

        for (var j = 0; j < inputTypesName.length; j++) {
            var allInputsTable = simpleTable.getElementsByTagName(inputTypesName[j]);
            for (var k = 0; k < allInputsTable.length; k++) {
                allInputsTable[k].disabled = false;
            }
        }
    }
}
