var Room = (function () {

    // TODO: Overlay
    getListRenderAll();

    function getListRenderAll() {
        var $list = $("#chat-room-list");
        var $row = $("#chat-room-list-template").find("tr");
        $list.empty();

        Commons.ajaxGet("/api/chat/chat-room", function(data) {
            if(Commons.isNotEmpty(data)) {
                $.each(data, function(ind, room) {
                    $row.find(".room-id").text(room.id);
                    $row.find(".room-name a").attr("href", "/chat/chat-room-detail/"+room.id).text(room.name);
                    $row.find(".created-date").text(room.createdDate);
                    $row.find(".modified-date").text(room.modifiedDate);
                    $list.append($row.clone());
                })
            }
        })
    };

    var createRoom = function () {
        var roomName = document.getElementById('room-name').value;
        var params = {name: roomName};
        Commons.ajaxPost("/api/chat/chat-room", params, function(resp) {
            getListRenderAll();
        });
    };
    var updateRoom = function () {
       alert("updateRoom");
    };
    var deleteRoom = function () {
       alert("deleteRoom");
    };
    return {
        getListRenderAll: getListRenderAll,
        createRoom: createRoom,
        updateRoom: updateRoom,
        deleteRoom: deleteRoom
    };
})();