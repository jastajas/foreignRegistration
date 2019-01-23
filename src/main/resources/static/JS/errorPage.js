var percentageVal = document.getElementById("percentageVal");
var progressBar = document.getElementById("progressBar");
var progressBarContent = document.getElementById("progressBarContent");
var i;
var percent = 0;
var errorBarWidth = 0;

function counter(){
    i = setInterval(function(){downCount();}, 50);
}

function downCount(){
    percent = percent + 1;
    percentageVal.textContent = percent + "%";
    errorBarWidth = errorBarWidth + 3;
    progressBarContent.style.width = errorBarWidth + "px";
    if(percent == 100){
        clearInterval(i);

        //location.href = "/";
        location.reload();
    }
}

counter();
