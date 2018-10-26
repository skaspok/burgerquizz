var stompClient = null;
var videoList = null;

var categoryList = ['intro', 'nuggets', 'addition', 'menus', 'sel-ou-poivre', 'burger-de-la-mort', 'divers'];

function connect() {
    var socket = new SockJS('/burgerquizz-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {

        console.log("Master connected");

        stompClient.subscribe('/bg/videos', function (message) {

            var jsonVideoList = JSON.parse(message.body);

            jsonVideoList['/'].forEach(file => {
                $("#dropdown-divers").append('<li class="video-link"><a href="#">' + file + '</a></li>');
            });

            categoryList.forEach(cat => {
                if (jsonVideoList[cat] !== undefined) {
                    jsonVideoList[cat].forEach(file => {
                        var id = Math.random().toString(36).substr(2);
                        $("#dropdown-" + cat).append('<li id="'+id+'" class="video-link"><a href="#">' + file + '</a></li>');
                        $("#"+id).click(  cat +"/"+file, startVideo );
                    });
                }
            })
           
        });

        stompClient.send("/server/get-videos", {}, {});
    });
}

function startVideo(event) {
    stompClient.send("/server/start-video", {}, event.data);
}
function minusMayo() {
    stompClient.send("/server/points", {}, JSON.stringify({ team: 'Mayo', points: -1 }));
}

function plusMayo() {
    stompClient.send("/server/points", {}, JSON.stringify({ team: 'Mayo', points: 1 }));
}

function minusKetchup() {
    stompClient.send("/server/points", {}, JSON.stringify({ team: 'Ketchup', points: -1 }));
}

function plusKetchup() {
    stompClient.send("/server/points", {}, JSON.stringify({ team: 'Ketchup', points: 1 }));
}

$(function () {
    $("#buttonTest").click(function () { startVideo(); });
    $("#minus-mayo").click(function () { minusMayo(); });
    $("#plus-mayo").click(function () { plusMayo(); });
    $("#minus-ketchup").click(function () { minusKetchup(); });
    $("#plus-ketchup").click(function () { plusKetchup(); });


    connect();
});