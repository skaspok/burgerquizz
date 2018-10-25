var stompClient = null;




function connect() {
    var socket = new SockJS('/burgerquizz-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        console.log("Master connected");
    });
}

function startVideo(){
    console.log('startVideo')

    stompClient.send("/server/video", {}, {});
}
function minusMayo(){
    stompClient.send("/server/points", {}, JSON.stringify({team : 'Mayo', points : -1}));
}

function plusMayo(){
    stompClient.send("/server/points", {}, JSON.stringify({team : 'Mayo', points : 1}));
}

function minusKetchup(){
    stompClient.send("/server/points", {}, JSON.stringify({team : 'Ketchup', points : -1}));
}

function plusKetchup(){
    stompClient.send("/server/points", {}, JSON.stringify({team : 'Ketchup', points : 1}));
}

$(function () {
        $( "#buttonTest" ).click(function() { startVideo(); });
        $( "#minus-mayo" ).click(function() { minusMayo(); });
        $( "#plus-mayo" ).click(function() { plusMayo(); });
        $( "#minus-ketchup" ).click(function() { minusKetchup(); });
        $( "#plus-ketchup" ).click(function() { plusKetchup(); });


        connect();
    });