(function($) {
    var stompClient = null;
    var name = null;
    var email = null;
    var roomId = null;
    var myAccess = false;
    var access_token = Commons.getCookie(Constants.ACCESS_TOKEN);

	/**
	 * DOM객체만 로드 후에 socketUrl 업데이트 후 접근.
	 */
	$(document).ready(function() {
	    roomId = $("#roomId").val();
        var socket = new SockJS('/chat/chat-handler');
        stompClient = Stomp.over(socket);

        stompClient.connect({'Authorization': access_token},
            connectionSuccess, connectionClose);

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
        stompClient.subscribe('/topic/chatting.'+roomId, onMessageReceived);

        stompClient.send("/app/"+roomId+"/chat.newUser", {'Authorization': access_token}, JSON.stringify({
            sender : name,
            type : 'newUser'
        }));
	}

	function connectionClose(message) {
    	console.log(message);
	    alert("서버와의 접속이 끊어졌습니다. \n" + message);
	    //location.href = "/logout";
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

            stompClient.send("/app/"+roomId+"/chat.sendMessage", {'Authorization': access_token}, JSON
                    .stringify(chatMessage));
            btnInput.val('');
        }
        event.preventDefault();
    }

    function onMessageReceived(payload) {
        var data = JSON.parse(payload.body);
        var type = data.type;

        console.log('message', data);
        if(["Leave","newUser","CHAT"].indexOf(type) > -1) {
            if(["Leave","newUser"].indexOf(type) > -1) {
                // 중복로그인이 발생한 경우 채팅방에 접속되었던 계정 전부 disconnect 처리
                if("newUser" === type) {
                    if(email === data.email) {
                        stompClient.disconnect(disconnectCallback)
                        return;
                    }
                    if(!myAccess) {
                       email = data.email;
                       myAccess = true;
                    }
                }
                // 채팅방 인원 정보 갱신.
                stompClient.send("/app/"+roomId+"/chat.callParticipants", {'Authorization': access_token}, JSON.stringify({}))
            }

            name =  data.sender;
            var dateTime = moment(data.dateTime).format("YYYY-MM-DD HH:mm:ss");
            printMessage(data.content, data.sender, dateTime);
            return;
        }

        printParticipants(data);
    }

    // disconnect 처리 후 로그아웃
    function disconnectCallback(event) {
          alert("다른 곳에서 로그인되어 접속을 해지합니다.");
          Commons.logout();
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