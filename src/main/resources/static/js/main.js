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
    if(iter >=2){
        phoneButton.disabled=true;
    }
};



let roomDiv = document.getElementById("room_div");
roomDiv.style.height="auto";
let roomButton = document.getElementById("addroom");
var iter1 = 0;
var room;
roomButton.onclick = function () {
    let input = document.createElement("input");
    let input1 = document.createElement("input");
    let div1 = document.createElement("div");
    input.setAttribute("name","prices");
    room = iter1+2;
    input.setAttribute("placeholder","Enter price of your room " + room);
    div1.appendChild(input);
    input1.setAttribute("name","rooms");
    input1.setAttribute("placeholder","Enter count of your room "+ room);
    div1.appendChild(input1);
    let div = document.createElement("div");
    div.innerHTML="<select id=\"select\"> <option value=\"TYPE_ECONOM\">Econom</option>\n" +
        "            <option value=\"TYPE_STANDART\">Standart</option>\n" +
        "            <option value=\"TYPE_LUXE\">Luxe</option>\n" +
        "        </select>";
    roomDiv.appendChild(div1);
    roomDiv.appendChild(div);

    iter1++;
    if(iter1 >=2){
        roomButton.disabled=true;
    }
};
let none = document.getElementById("nonein");
let select = document.getElementById("select");
let iterat = 0;
select.onchange=function () {
    let input = document.createElement("input");
    input.setAttribute("name","types");
    input.setAttribute("id","inp"+iterat);
    input.setAttribute("placeholder",this.value);
    input.value=this.value;
    none.appendChild(input);
    iterat++;
};