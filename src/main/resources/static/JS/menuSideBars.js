//  window.onload = function () {
var filterTypes = document.getElementsByClassName("typeFilter");

if (document.getElementById("hoverSection1") != null) {
    document.getElementById("hoverSection1").addEventListener("click", function () {
        showCard(1);
    });
}

if (document.getElementById("hoverSection2") != null) {
    document.getElementById("hoverSection2").addEventListener("click", function () {
        showCard(2);
    });
}

if (document.getElementById("closeWin1") != null) {
    document.getElementById("closeWin1").addEventListener("click", function () {
        closeCard(1);
    });
}
if (document.getElementById("closeWin2") != null) {
    document.getElementById("closeWin2").addEventListener("click", function () {
        closeCard(2);
    });
}

if (filterTypes != null && filterTypes.length != 0) {
    filterTypes[0].addEventListener("click", function () {
        changeSearchParam(0);
    });
    filterTypes[1].addEventListener("click", function () {
        changeSearchParam(1);
    });
    filterTypes[2].addEventListener("click", function () {
        changeSearchParam(2);
    });
    filterTypes[3].addEventListener("click", function () {
        changeSearchParam(3);
    });
    filterTypes[4].addEventListener("click", function () {
        changeSearchParam(4);
    });
    filterTypes[5].addEventListener("click", function () {
        changeSearchParam(5);
    });
    filterTypes[6].addEventListener("click", function () {
        changeSearchParam(6);
    });
    filterTypes[7].addEventListener("click", function () {
        changeSearchParam(7);
    });
    filterTypes[8].addEventListener("click", function () {
        changeSearchParam(8);
    });
}

function showCard(elementNo) {
    var hoverObject = document.getElementById("hoverSection" + elementNo);
    var cardStatus = window.getComputedStyle(hoverObject, null);
    var cardWidth = cardStatus.getPropertyValue("width");
    if (cardWidth != "400px") {
        hoverObject.style.width = "400px";
        hoverObject.style.height = (hoverObject.scrollHeight + 10) + "px";
    }
}

function closeCard(cardNo) {
    var bookMark = document.getElementById("hoverSection" + cardNo);
    var cardStatus = window.getComputedStyle(bookMark, null);
    var cardWidth = cardStatus.getPropertyValue("width");
    if (cardWidth == "400px") {
        bookMark.style.width = "30px";
        bookMark.style.height = "300px";
    }
}

// document.onkeydown = function () {
//     var evt = window.event;
//     if (evt.keyCode == 27) {
//         bookMark.style.width = "30px";
//         bookMark.style.height = "300px";
//     }
// }
// document.onkeydown = function (ev) {
//     ev = ev || window.event;
//     if (ev.keyCode == 27) {
//         if(document.getElementById("hoverSection1") != null){
//             closeCard(1);
//         }
//         if(document.getElementById("hoverSection2") != null){
//             closeCard(2);
//         }
//         closePopWin();
//     }
// }

function changeSearchParam(filterNo) {
    var searchBox = document.getElementById("searchBox");
    var selectBox = document.getElementById("selectStatus");
    var dateRange = document.getElementById("dateRange");
    var selectCooperation = document.getElementById("selectCooperation");
    var pickedFilter = document.getElementById("filterType" + filterNo);

    if (filterNo >= 0 && filterNo < 6) {
        searchBox.name = pickedFilter.value;
    }

    var styleVarSearchBox = window.getComputedStyle(searchBox, null);
    var displaySearchBox = styleVarSearchBox.getPropertyValue("display");

    var styleVarDateBox = window.getComputedStyle(dateRange, null);
    var displayDateBox = styleVarDateBox.getPropertyValue("display");

    var styleVarSelectCooperation = window.getComputedStyle(selectCooperation, null);
    var displaySelectCooperation = styleVarSelectCooperation.getPropertyValue("display");

    if (filterNo < 6 && displaySearchBox != "inline") {
        searchBox.style.display = "inline";
        selectBox.style.display = "none";
        selectBox.value = "";
        dateRange.style.display = "none";
        dateRange.firstElementChild.value = "";
        dateRange.lastElementChild.value = "";
        selectCooperation.style.display = "none";
        selectCooperation.value = "";
    } else if (filterNo == 6 && displayDateBox != "inline") {
        searchBox.style.display = "none";
        searchBox.value = "";
        selectBox.style.display = "none";
        selectBox.value = "";
        selectCooperation.style.display = "none";
        selectCooperation.value = "";
        dateRange.style.display = "inline";
    } else if (filterNo == 7 && (displaySearchBox == "inline" || displayDateBox == "inline" || displaySelectCooperation == "inline")) {
        searchBox.style.display = "none";
        searchBox.value = "";
        selectCooperation.style.display = "none";
        selectCooperation.value = "";
        dateRange.style.display = "none";
        dateRange.firstElementChild.value = "";
        dateRange.lastElementChild.value = "";
        selectBox.style.display = "inline";
    } else if (filterNo == 8 && displaySelectCooperation != "inline") {
        searchBox.style.display = "none";
        searchBox.value = "";
        selectBox.style.display = "none";
        selectBox.value = "";
        dateRange.style.display = "none";
        dateRange.firstElementChild.value = "";
        dateRange.lastElementChild.value = "";
        selectCooperation.style.display = "inline";
    }

    switch (filterNo) {
        case 0:
            searchBox.placeholder = "Input Keyword";
            break;
        case 1:
            searchBox.placeholder = "Input Client Name";
            break;
        case 2:
            searchBox.placeholder = "Input HL Product Name";
            break;
        case 3:
            searchBox.placeholder = "Input Client Product Name";
            break;
        case 4:
            searchBox.placeholder = "Input Order Number";
            break;
        case 5:
            searchBox.placeholder = "Input Assessment Number";
    }
}

//}