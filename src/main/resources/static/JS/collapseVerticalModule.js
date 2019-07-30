/** Model.*/
function Task(name, description, deadline, manEffort, taskOwner, /*status,*/ process) {
    this.name = name;
    this.description = description;
    this.deadline = deadline;
    this.manEffort = manEffort;
    this.taskOwner = taskOwner;
    this.status/* = status*/;
    this.process = process;
}

function RelatedTask(id) {
    this.id = id;
}


function TaskRelation(relatedTask, relationType) {
    this.relatedTask = new RelatedTask(relatedTask);
    this.relationType = relationType;
}

function User(id) {
    this.id = id;
}

function Status(id) {
    this.id = id;
}

function Process(id) {
    this.id = id;
}

/** Event listeners*/
document.getElementById("bodyId").addEventListener("click", function (ev) {
    document.getElementById("kanbanContextMenu").style.display = "none";
})
document.getElementById("bodyId").addEventListener("contextmenu", function (ev) {
    document.getElementById("kanbanContextMenu").style.display = "none";
})

var kanbanForm = document.getElementById("kanbanForm");
if (null != kanbanForm) {

    kanbanForm.addEventListener("submit", function (ev) {
        ev.preventDefault();
        var deadline = kanbanForm.querySelector("input[name='deadline']");
        if (!isDateBeforeToday(new Date(deadline.value), new Date(Date.now())) || document.getElementById("kanbanFormMH").value < 0) {
            alert("Wrong date. Deadline can't be before current day.");
        }

        var task = new Task(kanbanForm.querySelector("input[name='taskName']").value,
            kanbanForm.querySelector("textarea[name='taskDescription']").value,
            kanbanForm.querySelector("input[name='deadline']").value,
            kanbanForm.querySelector("input[name='manEffort']").value,
            new User(kanbanForm.querySelector("select[name='taskOwner']").value),
            /*new Status(17),*/
            new Process(1));

        var relationNodes = document.querySelectorAll(".taskRelationTableRow");

        if (relationNodes.length) {
            task.relatedTaskRelations = new Array();
            for (var i = 0; i < relationNodes.length; i++) {
                task.relatedTaskRelations.push(new TaskRelation(relationNodes[i].children[1].firstElementChild.value, relationNodes[i].children[2].firstElementChild.value))
            }
        }
        if (document.getElementById("taskKanbanFormId").value == "") {
            task.status = new Status(17);
            saveNewTask(task, "http://localhost:8080/api/process/saveTask", "post");
        } else {
            task.id = document.getElementById("taskKanbanFormId").value;
            updateTask(task, "http://localhost:8080/api/process/updateTask", "put");
        }

        closeKanbanForm();
    })
}

/** Basic collapse method */

function hideShowCard(btnNo) {

    var collapseDiv = document.getElementById("collapseDiv" + btnNo);
    var styleCollapseDiv = window.getComputedStyle(collapseDiv, null);
    var heightCollapseDiv = styleCollapseDiv.getPropertyValue("width");
    var restCollapse = document.getElementsByClassName("collapseDiv");

    if (heightCollapseDiv == "0px") {
        collapseDiv.style.width = "100%";
    }
    for (var l = 0; l < restCollapse.length; l++) {

        if (l != (btnNo - 1)) {
            restCollapse[l].style.width = "0px";
        }
    }
}

function changeAvailability(fieldName, btnName, objectType, objectId) {

    var detailToChange = document.getElementById(fieldName);
    detailToChange.disabled = false;

    detailToChange.style.webkitAppearance = "menulist-button";
    detailToChange.style.mozAppearance = "menulist-button";

    var toReplacedBtn = document.getElementById(btnName);
    toReplacedBtn.textContent = "CONFIRM";
    var onClickValue = "changeAssessmentDetail('" + fieldName + "', '" + btnName + "', '" + objectType + "', " + objectId + ")";
    toReplacedBtn.setAttribute("onclick", onClickValue);
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

function showContextMenuKanban(ev, kanbanId) {
    ev.stopImmediatePropagation();
    ev.preventDefault();

    var contextMenu = document.getElementById("kanbanContextMenu");

    contextMenu.children[0].onclick = function () {
        showKanbanTaskDetails(kanbanId);
    };
    contextMenu.children[1].onclick = function () {
        editKanbanTask(kanbanId);
    };
    contextMenu.children[2].onclick = function () {
        deleteTask(kanbanId);
    };

    contextMenu.style.top = ev.clientY + "px";
    contextMenu.style.left = ev.clientX + "px";
    contextMenu.style.display = "flex";
}

/** Kanban Task Methods*/
/** Saving new task - send task object to server (REST CONTROLLER) by post method (AJAX)*/
function saveNewTask(objectToSend, urlAPI, method) {

    var token = document.getElementsByName("_csrf")[0].content;
    var header = document.getElementsByName("_csrf_header")[0].content;

    var xhttp = new XMLHttpRequest();
    xhttp.open(method, urlAPI, true);
    xhttp.responseType = "json";
    xhttp.setRequestHeader(header, token);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.onload = function () {

        if (xhttp.status === 404) {
            alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
        } else {
            var kanbanTail = createKanbanTail(xhttp.response);
            document.querySelector("#todoBox .kanbanBoxBody").appendChild(kanbanTail);
            document.getElementById("todoBox").firstElementChild.lastElementChild.textContent = Number(document.getElementById("todoBox").firstElementChild.lastElementChild.textContent) + 1;
            if (finalTasksList != undefined && Array.isArray(finalTasksList)) {
                finalTasksList.push(xhttp.response);
            }
            if (objectToSend.relatedTaskRelations != undefined) {
                addRelationsToLocalArray(xhttp.response);
            }
            var optionTask = document.createElement("option");
            optionTask.value = xhttp.response.id;
            optionTask.textContent = xhttp.response.name;
            document.querySelector("#kanbanAddRelationRow").children[1].firstElementChild.appendChild(optionTask);
        }
    };
    xhttp.onerror = function () {
        alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
    }
    xhttp.send(JSON.stringify(objectToSend));
}

/** Update existing task - send task object to server (REST CONTROLLER) by put method (AJAX) */
function updateTask(updateTask, urlAPI, method) {

    var token = document.getElementsByName("_csrf")[0].content;
    var header = document.getElementsByName("_csrf_header")[0].content;

    var xhttp = new XMLHttpRequest();
    xhttp.open(method, urlAPI, true);
    xhttp.responseType = "json";
    xhttp.setRequestHeader(header, token);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.onload = function () {

        if (xhttp.status === 404) {
            alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
        } else {
            var updatedTask = xhttp.response;
            updateKanbanTail(updatedTask);
            updateTaskInTaskList(updatedTask);
            for (var j = 0; j < finalTaskRelatinsList.length; j++) {
                if (finalTaskRelatinsList[j].mainTask.id == updatedTask.id || finalTaskRelatinsList[j].relatedTask.id == updatedTask.id) {
                    finalTaskRelatinsList.splice(j, 1);
                    j--;
                }
            }

            if (updateTask.relatedTaskRelations != undefined) {
                addRelationsToLocalArray(updatedTask);
            }
        }
    };
    xhttp.onerror = function () {
        alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
    }
    xhttp.send(JSON.stringify(updateTask));
}

function deleteTask(taskId) {
    var token = document.getElementsByName("_csrf")[0].content;
    var header = document.getElementsByName("_csrf_header")[0].content;
    var urlAPI = "http://localhost:8080/api/process/deleteTask";

    var xhttp = new XMLHttpRequest();
    xhttp.open('DELETE', urlAPI, true);
    //xhttp.responseType = "json";
    xhttp.setRequestHeader(header, token);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.onload = function () {

        if (xhttp.status === 404) {
            alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
        } else {
            document.getElementById("kanbanTail" + taskId).parentElement.previousElementSibling.lastElementChild.textContent = Number(document.getElementById("kanbanTail" + taskId).parentElement.previousElementSibling.lastElementChild.textContent) - 1;
            document.getElementById("kanbanTail" + taskId).parentElement.removeChild(document.getElementById("kanbanTail" + taskId));
            for (var j = 0; j < finalTaskRelatinsList.length; j++) {
                if (finalTaskRelatinsList[j].mainTask.id == taskId || finalTaskRelatinsList[j].relatedTask.id == taskId) {
                    finalTaskRelatinsList.splice(j, 1);
                    j--;
                }
            }

            for (var i = 0; i < finalTasksList.length; i++) {
                if (finalTasksList[i].id == taskId) {
                    finalTasksList.splice(i, 1);
                    break;
                }
            }

        }
    };
    xhttp.onerror = function () {
        alert("Wystpił błąd połączenia z serwerem. Skontaktuj się z administratorem aplikacji");
    }
    xhttp.send('id=' + taskId);
}

/** create kanban tail. Cloned from pattern and returned as filled tail.*/
function createKanbanTail(task) {

    var clonedTail = document.getElementById("patternKanbanTail").cloneNode(true);
    clonedTail.firstElementChild.textContent = task.name;
    clonedTail.lastElementChild.firstElementChild.textContent = task.taskOwner.name + " " + task.taskOwner.surname;
    clonedTail.lastElementChild.lastElementChild.textContent = task.deadline;
    clonedTail.id = "kanbanTail" + task.id;
    clonedTail.oncontextmenu = function () {
        showContextMenuKanban(event, task.id);
    }
    clonedTail.style.display = "block";
    return clonedTail;
}

function updateKanbanTail(task) {
    var kanbanTail = document.getElementById("kanbanTail" + task.id);
    kanbanTail.firstElementChild.textContent = task.name;
    kanbanTail.lastElementChild.firstElementChild.textContent = task.taskOwner.name + " " + task.taskOwner.surname;
    kanbanTail.lastElementChild.lastElementChild.textContent = task.deadline;
}

function updateTaskInTaskList(task) {
    if (finalTasksList.length) {
        for (var i = 0; i < finalTasksList.length; i++) {
            if (finalTasksList[i].id == task.id) {
                finalTasksList.splice(i, 1);
                break;
            }
        }
    }
    finalTasksList.push(task);
}

function addRelationsToLocalArray(task) {

    var urlAPI = "http://localhost:8080/api/process/getRelationByTask/" + task.id;
    var xhttp = new XMLHttpRequest();
    xhttp.open('GET', urlAPI, true);
    xhttp.responseType = "json";
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.onload = function () {

        if (xhttp.status === 404) {
            alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
        } else {
            if (Array.isArray(xhttp.response) && Array.isArray(finalTaskRelatinsList)) {
                finalTaskRelatinsList = finalTaskRelatinsList.concat(xhttp.response);
            }
        }
    };
    xhttp.onerror = function () {
        alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
    }
    xhttp.send();

}

function isDateBeforeToday(checkingDate, today) {
    if (checkingDate.getFullYear() < today.getFullYear()) {
        return false;
    } else if (checkingDate.getFullYear() == today.getFullYear() && checkingDate.getMonth() < today.getMonth()) {
        return false;
    } else if (checkingDate.getFullYear() == today.getFullYear() && checkingDate.getMonth() == today.getMonth() && checkingDate.getDate() < today.getDate()) {
        return false;
    }
    return true;
}

function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {

    if (ev.target.className == "kanbanBoxBody") {
        ev.preventDefault();
        var data = ev.dataTransfer.getData("text");
        var kanbanCard = document.getElementById(data);
        var nameStatusBox = ev.target.parentElement.id;
        var idStatus;
        switch (nameStatusBox) {
            case "todoBox":
                idStatus = 17;
                break;
            case "inprogressBox":
                idStatus = 18;
                break;
            case "doneBox":
                idStatus = 19;
        }
        if (idStatus != undefined) {
            changeTaskStatus(kanbanCard.id.substring(10), idStatus, ev.target, kanbanCard);
        }
    }
}

function showKanbanTaskDetails(kanbanTaskId) {

    if (finalTasksList == null || finalTasksList == undefined) {
        alert("Nie znaleziono listy zadań");
        return false;
    }
    var task;
    for (var i = 0; i < finalTasksList.length; i++) {
        if (finalTasksList[i].id == kanbanTaskId) {
            task = finalTasksList[i];
            break;
        }
    }
    var tempDeadline = new Date(task.deadline);
    var kanbanDetailsTable = document.getElementById("taskDetailsFormTable");
    kanbanDetailsTable.children[0].lastElementChild.textContent = task.name;
    kanbanDetailsTable.children[1].lastElementChild.textContent = task.description;
    kanbanDetailsTable.children[2].lastElementChild.textContent = tempDeadline.toLocaleDateString();
    kanbanDetailsTable.children[3].lastElementChild.textContent = task.manEffort;
    kanbanDetailsTable.children[4].lastElementChild.textContent = task.taskOwner.name + " " + task.taskOwner.surname;
    kanbanDetailsTable.children[5].lastElementChild.textContent = task.orderingPerson.name + " " + task.orderingPerson.surname;

    document.getElementById("taskDetailsEditBtn").onclick = function () {
        closeTaskDetailsWindow();
        editKanbanTask(kanbanTaskId);
    };
    document.getElementById("taskDetailsDeleteBtn").onclick = function () {
        closeTaskDetailsWindow();
        deleteTask(kanbanTaskId);
    };

    if (finalTaskRelatinsList == null || finalTaskRelatinsList == undefined) {
        document.getElementById("bckgdTaskDetailsForm").style.display = "block";
        return false;
    }

    for (var j = 0; j < finalTaskRelatinsList.length; j++) {
        if (finalTaskRelatinsList[j].mainTask.id == kanbanTaskId || finalTaskRelatinsList[j].relatedTask.id == kanbanTaskId) {
            var relationType;
            var taskName = finalTaskRelatinsList[j].mainTask.id != kanbanTaskId ? finalTaskRelatinsList[j].mainTask.name : finalTaskRelatinsList[j].relatedTask.name;
            switch (finalTaskRelatinsList[j].relationType) {
                case "SF":
                    relationType = finalTaskRelatinsList[j].mainTask.id == kanbanTaskId ? "Start-Finish" : "Finish-Start";
                    break;
                case "FS":
                    relationType = finalTaskRelatinsList[j].mainTask.id == kanbanTaskId ? "Finish-Start" : "Start-Finish";
                    break;
                case "SS":
                    relationType = "Start-Start";
                    break;
                case "FF":
                    relationType = "Finish-Finish";
            }
            var listElement = document.createElement("li");
            listElement.textContent = taskName + " - " + relationType;
            kanbanDetailsTable.children[6].lastElementChild.firstElementChild.appendChild(listElement);
        }
    }
    document.getElementById("bckgdTaskDetailsForm").style.display = "block";
}


function closeTaskDetailsWindow() {
    var elementList = document.querySelector("#relationsListDetailsForm").children;
    while (elementList.length > 0) {
        elementList[0].parentElement.removeChild(elementList[0]);
    }

    document.getElementById("bckgdTaskDetailsForm").style.display = "none";
}

function changeTaskStatus(taskId, statusId, targetKanbanBoard, transferedKanban) {

    var token = document.getElementsByName("_csrf")[0].content;
    var header = document.getElementsByName("_csrf_header")[0].content;
    var urlAPI = "http://localhost:8080/api/process/changeTaskStatus";

    var xhttp = new XMLHttpRequest();
    xhttp.open('PUT', urlAPI, true);
    xhttp.responseType = "json";
    xhttp.setRequestHeader(header, token);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.onload = function () {

        if (xhttp.status === 404) {
            alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
        } else {
            var task = xhttp.response;
            if (task.status.id == statusId) {
                targetKanbanBoard.appendChild(transferedKanban);
                targetKanbanBoard.parentElement.firstElementChild.lastElementChild.textContent = Number(targetKanbanBoard.parentElement.firstElementChild.lastElementChild.textContent) + 1;
                for (var i = 0; i < finalTasksList.length; i++) {
                    if (finalTasksList[i].id == taskId) {
                        var boardName;
                        switch (finalTasksList[i].status.id) {
                            case 17:
                                boardName = "todoBox";
                                break;
                            case 18:
                                boardName = "inprogressBox";
                                break;
                            case 19:
                                boardName = "doneBox";
                        }
                        document.getElementById(boardName).firstElementChild.lastElementChild.textContent = Number(document.getElementById(boardName).firstElementChild.lastElementChild.textContent) - 1;
                        finalTasksList[i].status.id = statusId;
                        break;
                    }
                }

            }
        }
    };
    xhttp.onerror = function () {
        alert("wzstpił błąd wysyłąnia obiektu. Skontaktuj się z administratorem aplikacji");
    }
    xhttp.send('id=' + taskId + '&statusId=' + statusId);
}
