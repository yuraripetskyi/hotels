let phoneDiv = document.getElementById("phone_div");
phoneDiv.style.height = "auto";
let phoneButton = document.getElementById("addphone");
var iter = 0;
phoneButton.onclick = function () {
    let input = document.createElement("input");
    input.setAttribute("name", "phones");
    input.setAttribute("placeholder", "Phone");
    input.style.cssFloat = "left";
    iter++;
    phoneDiv.appendChild(input);
    if (iter >= 2) {
        phoneButton.disabled = true;
    }
};


let roomDiv = document.getElementById("room_div");
roomDiv.style.height = "auto";
let roomButton = document.getElementById("addroom");
var iter1 = 0;
var room;
let none = document.getElementById("nonein");
roomButton.onclick = function () {
    let inputPrices = document.createElement("input");
    let inputRoom = document.createElement("input");
    let divMain = document.createElement("div");
    inputPrices.setAttribute("name", "prices");
    room = iter1 + 2;
    inputPrices.setAttribute("placeholder", "Enter price of your room " + room);
    divMain.appendChild(inputPrices);
    inputRoom.setAttribute("name", "rooms");
    inputRoom.setAttribute("placeholder", "Enter count of your room " + room);
    divMain.appendChild(inputRoom);
    let divSelect = document.createElement("div");
    divSelect.innerHTML = "<select class=\"select\"> <option value=\"TYPE_ECONOM\">Econom</option>\n" +
        "            <option value=\"TYPE_STANDART\">Standart</option>\n" +
        "            <option value=\"TYPE_LUXE\">Luxe</option>\n" +
        "        </select>";
    roomDiv.appendChild(divMain);
    roomDiv.appendChild(divSelect);

    iter1++;
    if (iter1 >= 1000) {
        roomButton.disabled = true;
    }
};
let submit = document.getElementById("sb_btn");
submit.onclick=function () {
    let select = document.getElementsByClassName("select");
    for (let selectElement of select) {
        let inputSelect = document.createElement("input");
        inputSelect.setAttribute("name", "types");
        inputSelect.style.display="none";
        inputSelect.value = selectElement.value;
        none.appendChild(inputSelect);
    }
};
// submit.onclick = function () {
//     let selectmain = document.getElementById("select");
//     let input_selectmain = document.getElementById("input_select");
//     input_selectmain.value = selectmain.value;
//     console.log(input_selectmain.id + " - " + input_selectmain.value);
//     for (let i = 0; i <= iter1; i++) {
//         let select = document.getElementById("select" + i);
//         let input_select = document.getElementById("input_select" + i);
//         input_select.value = select.value;
//         console.log(input_select.id + " - " + input_select.value);
//     }
// };
