(function($) {
	$(document).ready(function() {
		updateUrl('/jmlim-chat/chat/chat-handler');
		connect();

		$("#btn-input").keyup(function(event) {
			if (event.keyCode == "13") {
				sendMessage();
			}
		});

		$("#btn-chat").click(function(event) {
			sendMessage();
		});
	});
})(jQuery);

var ws = null;
var url = null;
var transports = [];

function setConnected(connected) {
	// 연결이 되었을 때 버튼과 채팅방 변화.
	// document.getElementById('btn-input').disabled = !connected;
	// document.getElementById('disconnect').disabled = !connected;
	// document.getElementById('echo').disabled = !connected;
}

// 웹 소켓에 연결하는 함수.
function connect() {
	if (!url) {
		alert('Select whether to use W3C WebSocket');
		return;
	}

	ws = new WebSocket(url);

	// 소켓 접근시 할 동작.
	ws.onopen = function() {
		setConnected(true);
		// printMessage('Info: connection opened.');
	};

	// 메시지가 서버로부터 넘어왔을 시 할 동작.
	ws.onmessage = function(event) {
		var data = event.data;
		data = $.parseJSON(data);
		console.log(data);
		if (data != null && data != "") {
			printMessage(data.message, data.userName);
			if (data.currentUsers) {
				printCurrentUsers(data.currentUsers);
			}
		}
	};

	// 소켓 연결이 끊어졌을 시 할 동작.
	ws.onclose = function(event) {
		setConnected(false);
		// printMessage('Info: connection closed.');
		printMessage(event);
	};
}

// 연결 끊기.
function disconnect() {
	if (ws != null) {
		ws.close();
		ws = null;
	}
	setConnected(false);
}

// 메시지를 보냄.
function sendMessage() {
	if (ws != null) {
		var btnInput = $("#btn-input");
		var message = btnInput.val();

		if (message != null && message != "") {
			printMessage(message);
			ws.send(message);
			btnInput.val("");
		}
	} else {
		alert('connection not established, please connect.');
	}
}

function updateUrl(urlPath) {
	/*
	 * if (urlPath.indexOf('sockjs') != -1) { url = urlPath;
	 * document.getElementById('sockJsTransportSelect').style.visibility =
	 * 'visible'; } else {
	 */
	if (window.location.protocol == 'http:') {
		url = 'ws://' + window.location.host + urlPath;
	} else {
		url = 'wss://' + window.location.host + urlPath;
	}
	// document.getElementById('sockJsTransportSelect').style.visibility
	// = 'hidden';
	// }
}

/*
 * function updateTransport(transport) { transports = (transport == 'all') ? [] : [
 * transport ]; }
 */

function printCurrentUsers(currentUsersObj) {
	var currentUsers = $(".chat-main ul.current-users");
	// 자식노드 전부 삭제 후에 현재 유저를 넣음.
	currentUsers.empty();
	$.each(currentUsersObj, function(key, value) {
		var wrap = $(document.createElement("li")).attr({
			"class" : "left clearfix"
		});
		var userImgSpan = $(document.createElement("span")).attr({
			"class" : "user-img pull-left"
		});

		var userImg = $(document.createElement("img")).attr({
			"src" : "http://placehold.it/50/55C1E7/fff&text=U",
			"alt" : "User Avatar",
			"class" : "img-circle"
		});

		var userBody = $(document.createElement("div")).attr({
			"class" : "user-body clearfix"
		});

		var userBodyHeader = $(document.createElement("div"))
				.addClass("header");
		var primaryFont = $(document.createElement("strong")).addClass(
				"primary-font");

		userImgSpan.append(userImg);
		primaryFont.text(value);

		userBodyHeader.append(primaryFont);
		userBody.append(userBodyHeader);

		wrap.append(userImgSpan);
		wrap.append(userBody);

		currentUsers.append(wrap);
	});
}

function printMessage(message, userName) {
	var chat = $(".chat-main ul.chat");
	var wrap = $(document.createElement("li")).addClass("left").addClass(
			"clearfix");

	var chatImgSpan = $(document.createElement("span")).addClass("chat-img")
			.addClass("pull-left");

	var chatImg = $(document.createElement("img")).attr({
		"src" : "http://placehold.it/50/55C1E7/fff&text=U",
		"alt" : "User Avatar",
		"class" : "img-circle"
	});

	var chatBody = $(document.createElement("div")).addClass("chat-body")
			.addClass("clearfix");

	var chatBodyHeader = $(document.createElement("div")).addClass("header");
	var primaryFont = $(document.createElement("strong")).addClass(
			"primary-font");
	var textMuted = $(document.createElement("small")).attr({
		"class" : "pull-right text-muted"
	});
	var glyphiconTime = $(document.createElement("span")).attr({
		"class" : "glyphicon glyphicon-time"
	});
	var content = $(document.createElement("p")).text(message);

	chatImgSpan.append(chatImg);

	userName = userName ? userName : "나";
	primaryFont.text(userName);
	chatBodyHeader.append(primaryFont);
	textMuted.append(glyphiconTime).text("12 mins ago");
	chatBodyHeader.append(textMuted);

	chatBody.append(chatBodyHeader);
	chatBody.append(content);

	wrap.append(chatImgSpan);
	wrap.append(chatBody);

	chat.append(wrap);

	var panelBody = $(".panel-body");
	var chatHeight = panelBody.find(".chat").height();
	panelBody.scrollTop(chatHeight);
	/*
	 * <li class="left clearfix"> <span class="chat-img pull-left"> <img
	 * src="http://placehold.it/50/55C1E7/fff&text=U" alt="User Avatar"
	 * class="img-circle" /> </span> <div class="chat-body clearfix"> <div
	 * class="header"> <strong class="primary-font">Masud</strong> <small
	 * class="pull-right text-muted"> <span class="glyphicon glyphicon-time"></span>12
	 * mins ago </small> </div> <p>Lorem ipsum dolor sit amet, consectetur
	 * adipiscing elit. Curabitur bibendum ornare dolor, quis ullamcorper ligula
	 * sodales. </p> </div> </li>
	 */
}