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

function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    ev.target.appendChild(document.getElementById(data));
}