var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/room_123', function (greeting) {
            showGreeting(JSON.parse(greeting.body));
        });
    });
} 

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/room_123", {}, JSON.stringify({'user': $("#name").val(), 'content': $("#message").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message.user + ": " + message.content + "</td></tr>");
}

function update_button() {
    document.getElementById('message').addEventListener('keyup', e => {
        console.log('Caret at: ', e.target.selectionStart)
      })
    document.getElementById('message').addEventListener('mouseup', e => {
    console.log('Caret at: ', e.target.selectionStart)
    })
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});