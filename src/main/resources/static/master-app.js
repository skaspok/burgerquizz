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

$(function () {
        $( "#buttonTest" ).click(function() { startVideo(); });
        connect();
    });