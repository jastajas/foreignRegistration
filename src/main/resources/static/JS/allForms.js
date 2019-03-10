var formIdSuplier = 1;

if (null != document.querySelector("#kanbanFormButtons button[type='button']")) {
    document.querySelector("#kanbanFormButtons button[type='button']").addEventListener("click", function () {
        closeKanbanForm();
    })
}

function closeKanbanForm() {
    document.getElementById("bckgdKanbanPopUp").style.display = "none";
    document.getElementById("kanbanFormTaskName").value = "";
    document.getElementById("kanbanFormTaskDescription").value = "";
    document.getElementById("kanbanFormTaskDeadline").value = "";
    document.getElementById("kanbanFormUser").value = "";
    document.getElementById("kanbanFormMH").value = "";
    var tableElementList = document.querySelector("#kanbanFormTaskRelation tbody").children;
    while (tableElementList.length > 1) {
        tableElementList[0].parentElement.removeChild(tableElementList[0]);
    }
    tableElementList[0].querySelectorAll("select")[0].value = "";
    tableElementList[0].querySelectorAll("select")[1].value = "";
    document.getElementById("taskKanbanFormId").value = "";

    setOptionsDisable("taskNameToRelation", "#kanbanAddRelationRow");
}

function showKanbanForm() {
    document.getElementById("bckgdKanbanPopUp").style.display = "block";
}


if (null != document.getElementById("kanbanAddRelationBtn")) {
    document.getElementById("kanbanAddRelationBtn").addEventListener("click", function () {
        addItemToTable("kanbanAddRelationRow", ["taskNameToRelation", "relationTypeToRelation"], "taskRelationTableRow", "none", 0);
        setOptionsDisable("taskNameToRelation", "#kanbanAddRelationRow");
    });
}

var selectTaskToRelation = document.querySelector("#kanbanAddRelationRow td:nth-child(2) select");
if (null != selectTaskToRelation) {
    selectTaskToRelation.addEventListener("change", function () {
        selectSelectedOption(selectTaskToRelation);
    })
}
var selectTypeOfRelation = document.querySelector("#kanbanAddRelationRow td:nth-child(3) select");
if (null != selectTypeOfRelation) {
    selectTypeOfRelation.addEventListener("change", function () {
        selectSelectedOption(selectTypeOfRelation);
    })
}

function selectSelectedOption(changedObject) {
    var tempIndex = changedObject.selectedIndex;
    for (var i = 0; i < changedObject.options.length; i++) {
        changedObject.options[i].removeAttribute("selected");
    }
    changedObject.options[tempIndex].setAttribute("selected", true);
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

    try {
        if (regExpType != "none") {
            patternRow.children[checkedColumn - 1].firstElementChild.value = defaultVal;
        }
    } catch (err) {
        alert("RegExpType configuration error. Contact with Admin.")
    }

    prepareOrdinalsNumbersRows(destinedRowClass);
}

function removeTempRow(idCostToRemove, classRowToRemove) {
    var costRowToRemove = document.getElementById(idCostToRemove);
    costRowToRemove.parentNode.removeChild(costRowToRemove);
    prepareOrdinalsNumbersRows(classRowToRemove);
}

function createNewRow(paramNames,
                      patternRow,
                      idDestinedRowName,
                      idDestinedRow) {
    var clonedRow = patternRow.cloneNode(true);
    var internalIter = 0;
    for (var g = 0; g < clonedRow.childElementCount; g++) {
        if (g != 0 && g != clonedRow.childElementCount - 1) {
            clonedRow.children[g].firstElementChild.setAttribute(
                "name",
                paramNames[internalIter]
            );
            clonedRow.children[g].firstElementChild.disabled = true;
            internalIter++;
        } else if (g == clonedRow.childElementCount - 1) {
            clonedRow.children[g].firstElementChild.textContent = "REMOVE";
            clonedRow.children[g].firstElementChild.onclick = function () {
                removeTempRow(idDestinedRowName + idDestinedRow, idDestinedRowName);
                setOptionsDisable("taskNameToRelation", "#kanbanAddRelationRow");
            }
        }
    }
    clonedRow.setAttribute("class", idDestinedRowName);
    clonedRow.id = idDestinedRowName + idDestinedRow;
    return clonedRow;
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

function setOptionsDisable(fieldName, idPatternField) {
    var sourceList = document.querySelector(idPatternField).children[1].firstElementChild.children;
    for (var j = 0; j < sourceList.length; j++) {
        var usedElements = document.getElementsByName(fieldName);
        if (usedElements.length) {
            for (var i = 0; i < usedElements.length; i++) {
                if (sourceList[j].value == usedElements[i].value) {
                    sourceList[j].disabled = true;
                    break;
                }
                sourceList[j].disabled = false;
            }
        } else {
            sourceList[j].disabled = false;
        }
    }
}

function prepareOrdinalsNumbersRows(allRowsClassName) {

    var destinedRowList = document.getElementsByClassName(allRowsClassName);
    for (var i = 0; i < destinedRowList.length; i++) {
        destinedRowList[i].firstElementChild.innerHTML = i + 1;
    }
}

/** Kanban Edit Form - filling out  */
function editKanbanTask(idTask) {

    var selectedKanbanTask;

    if (!finalTasksList.length) {
        alert("zle sie dzieje");
    }
    for (var i = 0; 0 < finalTasksList.length; i++) {
        if (finalTasksList[i].id == idTask) {
            selectedKanbanTask = finalTasksList[i];
            break;
        }
    }
    if (selectedKanbanTask == undefined) {
        return false;
    }

    document.getElementById("taskKanbanFormId").value = idTask;
    document.getElementById("kanbanFormTaskName").value = selectedKanbanTask.name;
    document.getElementById("kanbanFormTaskDescription").value = selectedKanbanTask.description;
    document.getElementById("kanbanFormTaskDeadline").value = selectedKanbanTask.deadline;
    document.getElementById("kanbanFormUser").value = selectedKanbanTask.taskOwner.id;
    document.getElementById("kanbanFormMH").value = selectedKanbanTask.manEffort;

    var assignedTasksList = new Array();

    for (var i = 0; i < finalTaskRelatinsList.length; i++) {
        console.log(finalTaskRelatinsList[i].mainTask.id);
        console.log(finalTaskRelatinsList[i].relatedTask.id);
        if (idTask == finalTaskRelatinsList[i].mainTask.id || idTask == finalTaskRelatinsList[i].relatedTask.id) {
            assignedTasksList.push(finalTaskRelatinsList[i]);
        }
    }

    if (assignedTasksList.length) {
        for (var i = 0; i < assignedTasksList.length; i++) {

            document.getElementById("kanbanAddRelationRow").children[1].firstElementChild.value = assignedTasksList[i].mainTask.id == idTask ? assignedTasksList[i].relatedTask.id : assignedTasksList[i].mainTask.id;
            var tempRelationType;
            if (assignedTasksList[i].mainTask.id == idTask && (assignedTasksList[i].relationType == "SF" || assignedTasksList[i].relationType == "FS")) {
                tempRelationType = assignedTasksList[i].relationType == "SF" ? "FS" : "SF";
            } else {
                tempRelationType = assignedTasksList[i].relationType;
            }

            document.getElementById("kanbanAddRelationRow").children[2].firstElementChild.value = tempRelationType;
            selectSelectedOption(document.getElementById("kanbanAddRelationRow").children[1].firstElementChild);
            selectSelectedOption(document.getElementById("kanbanAddRelationRow").children[2].firstElementChild);
            addItemToTable("kanbanAddRelationRow", ["taskNameToRelation", "relationTypeToRelation"], "taskRelationTableRow", "none", 0);
        }
        prepareOrdinalsNumbersRows("taskRelationTableRow");
    }

    document.getElementById("bckgdKanbanPopUp").style.display = "block";
}