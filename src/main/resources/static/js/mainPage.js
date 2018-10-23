// 'Sorting found room' part
let $sortSubmit = $('#sortSubmit');

$sortSubmit.click(function () {
    let $alert = $('#alert');
    let $selector = $('.selector');
    let $commonBox = $('#commonBox');
    let $inlineFormCustomSelect = $('#inlineFormCustomSelect');
    let $finder = $('.finder');
    let $countOfGuests = $('.countOfGuests');
    var $date1 = $('#date1');
    var $date2= $('#date2');

    var obj = {
        finder:$finder.val(),
        countOfGuests:$countOfGuests.val(),
        from_date:$date1.val(),
        to_date:$date2.val(),
        sort:$inlineFormCustomSelect.val()
    };
    let jsonObj = JSON.stringify(obj);
    $.ajax({
        url: 'http://localhost:8080/sortBy',
        method: 'POST',
        contentType:'text/plain',
        data: jsonObj,
        success: function (result){
            if(!$commonBox.empty()){
                let $div = $('<div/>',{
                    text: ''
                });
                $commonBox.append($div);
            }
            if(!$alert.empty()){
                let $div = $('<div/>',{
                    text: ''
                });
                $alert.append($div);
            }
            if(result.length == 0) {
                let $div = $('<div/>', {
                    class: 'alert alert-danger d-flex justify-content-center',
                    text: 'Danger! You entered incorrect data'
                });
                $alert.append($div);
                $selector.removeClass('d-flex').addClass('d-none');
            }else {
                $(result).each(function (index,obj) {
                    let $card = $('<div/>',{
                        class:'card'
                    });
                    let $cardBody = $('<div/>',{
                        class:'card-body'
                    });
                    let listImages = obj.hotel.images;
                    let $img = $('<img/>',{
                        class:'card-img-top',
                        src:listImages[0].image
                    });
                    let $title = $('<h5/>',{
                        class:'card-title',
                        text:'Hotelname : '+ obj.hotel.name
                    });
                    let $description = $('<p/>',{
                        class:'card-text',
                        text:'City : ' + obj.hotel.city + ' '  + ' Count of room : ' + obj.roominess + ' Price : ' + obj.price
                    });
                    let $booking = $('<a/>',{
                        class:'btn btn-primary',
                        text:'Booking!',
                        href:'/book/room/' + obj.id +'/'+ $date1.val().toString()+'/'+ $date2.val().toString()
                    });
                    $commonBox.append($card);
                    $card.append($img,$cardBody);
                    $cardBody.append($title,$description,$booking);
                });
            }},
        error : function (error) {
            console.log(error);
        }
    })
});

// 'Find room' Part

let $submit = $('#submit');
$submit.click(function () {
    let $alert = $('#alert');
    let $commonBox = $('#commonBox');
    let $selector = $('.selector');
    let $finder = $('.finder');
    let $countOfGuests = $('.countOfGuests');
    var $date1 = $('#date1');
    var $date2= $('#date2');


    var obj = {
        finder:$finder.val(),
        countOfGuests:$countOfGuests.val(),
        from_date:$date1.val(),
        to_date:$date2.val()
    };
    let jsonObj = JSON.stringify(obj);
    $.ajax({
        url: 'http://localhost:8080/',
        method: 'POST',
        contentType:'text/plain',
        data: jsonObj,
        success: function (result) {
            if(!$commonBox.empty()){
                let $div = $('<div/>',{
                    text: ''
                });
                $commonBox.append($div);
            }
            if(!$alert.empty()){
                let $div = $('<div/>',{
                    text: ''
                });
                $alert.append($div);
            }
            if(result.length == 0) {
                let $div = $('<div/>', {
                    class: 'alert alert-danger d-flex justify-content-center',
                    text: 'Danger! You entered incorrect data'
                });
                $alert.append($div);
                $selector.removeClass('d-flex').addClass('d-none');
            }else {
                $selector.removeClass('d-none').addClass('d-flex');
                $(result).each(function (index,obj) {
                    let $card = $('<div/>',{
                        class:'card'
                    });
                    let $cardBody = $('<div/>',{
                        class:'card-body'
                    });
                    let listImages = obj.hotel.images;
                    let $img = $('<img/>',{
                        class:'card-img-top',
                        src:listImages[0].image
                    });
                    let $title = $('<h5/>',{
                        class:'card-title',
                        text:'Hotelname : '+ obj.hotel.name
                    });
                    let $description = $('<p/>',{
                        class:'card-text',
                        text:'City : ' + obj.hotel.city + ' '  + ' Count of room : ' + obj.roominess + ' Price : ' + obj.price
                    });
                    let $booking = $('<a/>',{
                        class:'btn btn-primary',
                        text:'Booking!',
                        href:'/book/room/' + obj.id +'/'+ $date1.val().toString()+'/'+ $date2.val().toString()
                    });
                    $commonBox.append($card);
                    $card.append($img,$cardBody);
                    $cardBody.append($title,$description,$booking);
                })
            }},
        error : function (error) {
            console.log(error);
        }

    });
});

//Find room's booking for administration

let $find = $('#find');
$find.click(function () {
    // let $alert = $('#alert');
    let $roomId = $('#roomId');
    let $commonBox = $('#commonBox');
    var $date1 = $('#date1');
    var $date2 = $('#date2');

    var obj = {
        date_from:$date1.val(),
        date_to:$date2.val(),
        roomId:$roomId.val()
    };
    let jsonObj = JSON.stringify(obj);
    $.ajax({
        url: 'http://localhost:8080/findRoomsForAdmin',
        method: 'POST',
        contentType:'text/plain',
        data: jsonObj,
        success: function (result) {
            if(!$commonBox.empty()){
                let $div = $('<div/>',{
                    text: ''
                });
                $commonBox.append($div);
            }
            if(result.length == 0){
                    let $card = $('<div/>',{
                        class:'card alert alert-success'
                    });
                    let $discription = $('  <div/>',{
                        text: 'Room is free for this time'
                    });
                    $commonBox.append($card);
                    $card.append($discription);
            }else {
                $(result).each(function (index,book) {
                    let $card = $('<div/>',{
                        class:'card alert alert-warning'
                    });
                    let $cardBody = $('<div/>',{
                        class:'card-body'
                    });
                    let $title = $('<h6/>',{
                        class:'card-title',
                        text:'Date from : ' + book.date_from + ' Date to : ' + book.date_to
                    });
                    let $button = $('<a/>',{
                        class:'alert-link my-3',
                        text:'Delete book!',
                        href: '/book/delete/' + book.id +"/room/" + $roomId.val()
                    });
                    let $description = $('<div/>',{
                        class: 'card-text',
                        text:' @Name of guest: ' + book.guest.name + ' @Surname of guest: '+ book.guest.surname
                    });
                    $commonBox.append($card);
                    $card.append($cardBody);
                    $cardBody.append($title,$description,$button);
                })
            }},
        error : function (error) {
            console.log(error);
        }
    });
});