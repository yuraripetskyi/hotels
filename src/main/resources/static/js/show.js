let submit = document.getElementById("showForgotBox");
let box = document.getElementById("forgotBox");
let i = 0;
submit.onclick = function(){

    // i++;
    // if(i%2 == 1){
        submit.text("hide");
        box.style.display="block";
    // }else {
    //     submit.text("Troubles with enter");
    //     box.style.display="none";
    // }

};



// $('showForgotBox').toggle(function(){
//     $('showForgotBox').text('Hide');
// },function(){
//     $('showForgotBox').text('Troubles with enter?');
// });