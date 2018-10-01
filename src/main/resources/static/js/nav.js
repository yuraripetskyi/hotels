window.onscroll = function() {
    let scrolled = window.pageYOffset || document.documentElement.scrollTop;
    let header = document.getElementById("for-header");
    let logo = document.getElementById("logo");
    let main_li = document.getElementsByClassName("main_li");
    if(scrolled >=1){
        header.setAttribute("style","background: #333333d9 !important;\n" +
            "    height: 80px;\n" +
            "    position: fixed;\n" +
            "    width: 100%;\n" +
            "    transition: 0.5s;"+
            "z-index: 999999;;");
        logo.setAttribute("style","color: white !important");
        for (let li of main_li) {
            li.setAttribute("style","color: white !important");
        }
    }
    else {
        header.setAttribute("style","background: #ffffff00 !important;\n" +
            "    height: 80px;\n" +
            "    position: fixed;\n" +
            "    width: 100%;"+
            "z-index: 999999;;");
        logo.setAttribute("style","color: #293a6b !important");
        for (let li of main_li) {
            li.setAttribute("style","color: #293a6b !important");
        }
    }
};