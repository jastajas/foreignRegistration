
var addAssessButton = document.getElementById("addAssessButton");
var sideMenuButton = document.getElementById("sideMenuButton");
var addNewOrderBtn = document.getElementById("addNewOrderBtn");


if (addAssessButton != null) {
    addAssessButton.addEventListener("transitionend", function () {
        showBtnContent(addAssessButton, "addAssessBtnIcon", "addAssessOptions");
    });
    addAssessButton.addEventListener("mouseleave", function () {
        hideBtnContent("addAssessBtnIcon", "addAssessOptions");
    });
}
if (sideMenuButton != null) {
    sideMenuButton.addEventListener("transitionend", function () {
        showBtnContent(sideMenuButton, "sideMenuBtnIcon", "sideMenuOptions");
    });
    sideMenuButton.addEventListener("mouseleave", function () {
        hideBtnContent("sideMenuBtnIcon", "sideMenuOptions");
    });
}
if (addNewOrderBtn != null) {
    addNewOrderBtn.addEventListener("click", function () {
        showForm();
    });
}


function showBtnContent(objectBtn, objectIconID, objectListID) {

    var sideButtonStyle = window.getComputedStyle(objectBtn, null);
    var widthOfButton = sideButtonStyle.getPropertyValue("width");

    if (widthOfButton == "250px") {
        document.getElementById(objectIconID).style.display = "none";
        document.getElementById(objectListID).style.display = "flex";
    }
}

function hideBtnContent(objectIconID, objectListID) {
    document.getElementById(objectIconID).style.display = "flex";
    document.getElementById(objectListID).style.display = "none";
}

//}
