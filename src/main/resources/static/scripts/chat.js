(function($) {
    var stompClient = null;
    var name = null;
	/**
	 * DOM객체만 로드 후에 socketUrl 업데이트 후 접근.
	 */
	$(document).ready(function() {
        var socket = new SockJS('/chat/chat-handler');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, connectionSuccess, connectionClose);

		$("#btn-input").keyup(function(event) {
			if (event.keyCode == "13") {
				sendMessage(event);
			}
		});

		$("#btn-chat").click(function(event) {
			sendMessage(event);
		});
	});

    function connectionSuccess() {
        stompClient.subscribe('/topic/chatting', onMessageReceived);

        stompClient.send("/app/chat.newUser", {}, JSON.stringify({
            sender : name,
            type : 'newUser'
        }));
	}

	function connectionClose(message) {
    	console.log(message);
	    alert("서버와의 접속이 끊어졌습니다. \n" + message);
	}

    function sendMessage(event) {
        var btnInput = $("#btn-input");
        var messageContent = btnInput.val();

        if (messageContent && stompClient) {
            var chatMessage = {
                sender : name,
                content : messageContent,
                type : 'CHAT'
            };

            stompClient.send("/app/chat.sendMessage", {}, JSON
                    .stringify(chatMessage));
            btnInput.val('');
        }
        event.preventDefault();
    }

    function onMessageReceived(payload) {
        var data = JSON.parse(payload.body);
        console.log('message', data);
        if(["Leave","newUser","CHAT"].includes(data.type)) {
            if(["Leave","newUser"].includes(data.type)) {
                // 채팅방 인원 정보 갱신.
                //alert(data.type)
                console.log('datatype', data.type);
                stompClient.send("/app/chat.callParticipants", {}, JSON.stringify({}))
            }

            name =  data.sender;
            var dateTime = moment(data.dateTime).format("YYYY-MM-DD HH:mm:ss");
            printMessage(data.content, data.sender, dateTime);
            return;
        }

        printParticipants(data);
    }

	/**
	 * 전송받은 내용을 뿌림.
	 */
	function printMessage(message, userName, dateTime) {
		var chat = $(".chat-main ul.chat");
		var wrap = $(document.createElement("li")).addClass("left").addClass(
				"clearfix");

		var chatImgSpan = $(document.createElement("span"))
				.addClass("chat-img").addClass("pull-left");

		var chatImg = $(document.createElement("img")).attr({
			//"src" : "/jmlim-chat/sign/uploaded?imageId=" + jmlim.chat.currentUser.image,
			"src": "http://placehold.it/50/55C1E7/fff&text=U",
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
		textMuted.append(glyphiconTime).text(dateTime);
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

	/**
     * 유저정보를 오른쪽에 업데이트
     */
    function printParticipants(participantData) {
        var participantUsers = $(".chat-main ul.participant-users");
        // 자식노드 전부 삭제 후에 현재 유저를 넣음.
        participantUsers.empty();
        $.each(participantData, function(ind, obj) {
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

            var userBodyHeader = $(document.createElement("div")).addClass(
                    "header");
            var primaryFont = $(document.createElement("strong")).addClass(
                    "primary-font");

            userImgSpan.append(userImg);
            primaryFont.text(obj.username);

            userBodyHeader.append(primaryFont);
            userBody.append(userBodyHeader);

            wrap.append(userImgSpan);
            wrap.append(userBody);

            participantUsers.append(wrap);
        });
    }
})(jQuery);