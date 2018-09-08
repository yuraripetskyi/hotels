let phoneDiv = document.getElementById("phone_div");
phoneDiv.style.height="auto";
let phoneButton = document.getElementById("addphone");
var iter = 0;
phoneButton.onclick = function () {
    let input = document.createElement("input");
    input.setAttribute("name","phones");
    input.setAttribute("placeholder","Phone");
    input.style.cssFloat="left";
    iter++;
    phoneDiv.appendChild(input);
    if(iter >3){
        phoneButton.disabled=true;
    }
};


let phoneDiv = document.getElementById("phone_div");
phoneDiv.style.height="auto";
let phoneButton = document.getElementById("addphone");
var iter = 0;
phoneButton.onclick = function () {
    let input = document.createElement("input");
    input.setAttribute("name","phones");
    input.setAttribute("placeholder","Phone");
    input.style.cssFloat="left";
    iter++;
    phoneDiv.appendChild(input);
    if(iter >3){
        phoneButton.disabled=true;
    }
};