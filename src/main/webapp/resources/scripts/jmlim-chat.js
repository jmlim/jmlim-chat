

(function($) {
	/**
	 * DOM객체만 로드 후에 socketUrl 업데이트 후 접근.
	 */
	$(document).ready(function() {
		updateUrl(jmlim.chat.contextPath + '/chat/chat-handler');
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

	var ws = null;
	var url = null;

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

		// 웹소켓 객체 생성.
		ws = new WebSocket(url);
		// 소켓 접근시 할 동작.
		ws.onopen = function() {
			// 현재 비어 있음.
			setConnected(true);
			// printMessage('Info: connection opened.');
		};

		// 메시지가 서버로부터 넘어왔을 시 할 동작.
		ws.onmessage = function(event) {
			var data = event.data;
			// 스트링으로 넘어온 데이터를 json 으로 파싱.
			data = $.parseJSON(data);

			if (data != null && data != "") {
				printMessage(data.message, data.userName);

				// 유저정보가 json으로 넘어왔을 경우
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

	/**
	 * url 정보 업데이트
	 * */
	function updateUrl(urlPath) {
		// 프로토콜이 http 일 경우
		if (window.location.protocol == 'http:') {
			url = 'ws://' + window.location.host + urlPath;
		}
		// 프로토콜이 https 일 경우
		else {
			url = 'wss://' + window.location.host + urlPath;
		}
	}

	// 메시지를 보냄.
	function sendMessage() {
		if (ws != null) {
			var btnInput = $("#btn-input");
			var message = btnInput.val();

			if (message != null && message != "") {
				printMessage(message);

				//**** 웹소켓 객체에 메시지 전송. ***
				ws.send(message);
				btnInput.val("");
			}
		} else {
			alert('connection not established, please connect.');
		}
	}

	/**
	 * 유저정보를 오른쪽에 업데이트
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
				"src" : jmlim.chat.contextPath + "/sign/uploaded?imageId=" + jmlim.chat.currentUser.image,
				"alt" : "User Avatar",
				"class" : "img-circle"
			});

			var userBody = $(document.createElement("div")).attr({
				"class" : "user-body clearfix"
			});

			var userBodyHeader = $(document.createElement("div")).addClass(
					"header");
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

	/**
	 * 전송받은 내용을 뿌림.
	 */
	function printMessage(message, userName) {
		var chat = $(".chat-main ul.chat");
		var wrap = $(document.createElement("li")).addClass("left").addClass(
				"clearfix");

		var chatImgSpan = $(document.createElement("span"))
				.addClass("chat-img").addClass("pull-left");

		var chatImg = $(document.createElement("img")).attr({
			"src" : "/jmlim-chat/sign/uploaded?imageId=" + jmlim.chat.currentUser.image,
			"alt" : "User Avatar",
			"class" : "img-circle"
		});

		var chatBody = $(document.createElement("div")).addClass("chat-body")
				.addClass("clearfix");

		var chatBodyHeader = $(document.createElement("div"))
				.addClass("header");
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

		userName = userName ? userName : jmlim.chat.currentUser.name;
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

		// 실제로 뿌려지는 내용
		/*
		 * <li class="left clearfix"> <span class="chat-img pull-left"> <img
		 * src="http://placehold.it/50/55C1E7/fff&text=U" alt="User Avatar"
		 * class="img-circle" /> </span> <div class="chat-body clearfix"> <div
		 * class="header"> <strong class="primary-font">Masud</strong> <small
		 * class="pull-right text-muted"> <span class="glyphicon
		 * glyphicon-time"></span>12 mins ago </small> </div> <p>Lorem ipsum
		 * dolor sit amet, consectetur adipiscing elit. Curabitur bibendum
		 * ornare dolor, quis ullamcorper ligula sodales. </p> </div> </li>
		 */
	}
})(jQuery);