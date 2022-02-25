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
        stompClient.subscribe('/topic/' + document.getElementById("room_code").value, function (greeting) {
            showGreeting(JSON.parse(greeting.body));
        });
    });
} 

function Send_changes() {
    current_cursor = $("#message").prop("selectionStart") - 1;
    stompClient.send("/app/" + document.getElementById("room_code").value, {}, JSON.stringify({'from_user': document.getElementById("position").value,
        'cursor_move': 1, 'change_type': 'add', 'Init_index': current_cursor, 'End_index': current_cursor + 1,
    'context': 'a', 'size': 1}));
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

function showGreeting(changes) {
    if (changes.from_user != document.getElementById("position").value) {
        if (changes.change_type == "add") {
            current_cursor = $("#message").prop("selectionStart");
            current_content = document.getElementById("message").value;
            if (current_content == null) {
                console.log("reset current content");
                current_content = "";
            }
            current_content = current_content.slice(0, changes.Init_index) + changes.context + current_content.slice(changes.Init_index);
            console.log("current content is " + current_content);
            console.log("original cursor is at index " + current_cursor);
            current_cursor += changes.cursor_move;
            console.log("now cursor is at index " + current_cursor);
            document.getElementById("message").value = current_content;
            document.getElementById("message").selectionEnd = current_cursor;
        }
    }
}

function become_recruiter() {
    window.localStorage.setItem("whoami", "recruiter");
}

function become_interviewee() {
    window.localStorage.setItem("whoami", "interviewee");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});