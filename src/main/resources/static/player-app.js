var stompClient = null;

var token = null;
var team = null;

function setConnected() {

   //TODO activer le buzzer
}

function connect() {
    var socket = new SockJS('/burgerquizz-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        console.log("A connected");

        sendToken();
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }

    //TODO deactiver le buzzer et afficher un message

    console.log("Disconnected");
}

function sendBuzz() {
    stompClient.send("/server/buzz/"+token, {}, token);
}

function sendToken(){

    //After connection send token to be identified
    //And know which team
    token = Math.random().toString(36).substr(2);
    console.log("A send Token : "+ token );

    stompClient.subscribe('/bg/new-ack/'+token, function (message) {
                   team = message.body
                   console.log('A acknowledge' + team);

                   setConnected(true);

                   //set team name
                    $( "#team" ).text( "Team " +team );

                    //suscribe to buzz result
                   stompClient.subscribe('/bg/buzz-result/'+token, function (result) {
                               console.log('Buzz result')
                               console.dir(result)
                               buzzResult(result);
                           });

                });

    stompClient.send("/server/new-player/"+token, {}, token);
}

function buzzResult(message) {
    //TODO Displays win or not
}

$(function () {
//    $("form").on('submit', function (e) {
//        e.preventDefault();
//    });
//    $( "#connect" ).click(function() {  });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#buzzer" ).click(function() { sendBuzz(); });

    connect();
});