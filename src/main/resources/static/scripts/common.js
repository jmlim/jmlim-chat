var Constants = (function () {
    var AUTHORIZATION = "Authorization";
    var ACCESS_TOKEN = "access_token";

    return {
        AUTHORIZATION: AUTHORIZATION,
        ACCESS_TOKEN: ACCESS_TOKEN,
    }
})();

var Commons = (function () {


    /**
     * 공통 ajax call 함수
     * @param requestUrl
     * @param callback
     * @private
     */
    var ajaxGet = function (requestUrl, callback) {
        $.ajax({
            type: 'GET',
            url: requestUrl,
            async: false,
            cache: false,
            // data: params,
            dataType: 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(Constants.AUTHORIZATION, Commons.getCookie(Constants.ACCESS_TOKEN));
            },
            success: function(resp) {
               callback(resp);
            },
            error: function (jqXHR, status, error) {
                console.log(jqXHR);
                loginChecker(jqXHR);
           }
        }).done(function() {
             // Commons.offOverlay();
        });
    };

    var ajaxPost = function (requestUrl, params, callback) {
        $.ajax({
            type: 'POST',
            url: requestUrl,
            data: JSON.stringify(params),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(Constants.AUTHORIZATION, Commons.getCookie(Constants.ACCESS_TOKEN));
            },
            success: function(resp) {
               callback(resp);
            },
            error: function (jqXHR, status, error) {
                console.log(jqXHR);
                loginChecker(jqXHR);
           }
        });
    };

    var ajaxDelete = function (requestUrl, params, callback) {
        $.ajax({
            type: 'DELETE',
            url: requestUrl,
            data: JSON.stringify(params),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(Constants.AUTHORIZATION, Commons.getCookie(Constants.ACCESS_TOKEN));
            },
            success: function(resp) {
               callback(resp);
            },
            error: function (jqXHR, status, error) {
               console.log(jqXHR);
               loginChecker(jqXHR);
           }
        });
    };

    var ajaxPut = function (requestUrl, params, callback) {
        $.ajax({
            type: 'PUT',
            url: requestUrl,
            data: JSON.stringify(params),
            contentType: 'application/json',
            dataType: 'json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(Constants.AUTHORIZATION, Commons.getCookie(Constants.ACCESS_TOKEN));
            },
            success: function(resp) {
               callback(resp);
            },
            error: function (jqXHR, status, error) {
               console.log(jqXHR);
               loginChecker(jqXHR);
           }
        });
    };

    var null2Space = function (obj) {
        return isEmpty(obj) ? '' : obj;
    };

    var isEmpty = function (obj) {
        if (typeof obj == 'undefined' || obj === null || obj === '' || obj == {} || obj == '{}') return true;
        if (typeof obj == 'number' && isNaN(obj)) return true;
        if (typeof obj == 'object' && Object.keys(obj).length === 0) return true;
        if (obj instanceof Date && isNaN(Number(obj))) return true;
        return false;
    };

    var isNotEmpty = function (obj) {
        return !isEmpty(obj);
    };

    var initCap = function (str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1, str.length).toLowerCase()
        return str;
    };
    // Unescape HTML in JS
    // https://stackoverflow.com/questions/1912501/unescape-html-entities-in-javascript
    var htmlDecode = function (input) {
        var e = document.createElement('textarea');
        e.innerHTML = input;
        // handle case of empty input
        return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
    }

    var setCookie = function (name, value, hour) {
        // format ex) Sat, 05 Jan 2019 14:53:14
        document.cookie = name + '=' + encodeURIComponent(value) + ';expires=' + moment().add(hour, 'hour').format('ddd, DD MMM YYYY HH:mm:ss') + ';path=/;';
    };

    var getCookie = function (name) {
        var value = decodeURIComponent(document.cookie).match('(^|;) ?' + name + '=([^;]*)(;|$)');
        return value ? value[2] : '{}';
    };

    var removeCookie = function (name) {
        document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/;';
    };

    var loginChecker = function(jqXHR) {
        var accessToken = Commons.getCookie(Constants.ACCESS_TOKEN)
        if(jqXHR
            && jqXHR.responseJSON
            && jqXHR.responseJSON.status == "403") {
            if(Commons.isEmpty(accessToken)) {
                alert("로그인 해주세요.");
                location.href = "/sign/signin";
            } else {
                alert("접근이 거부되었습니다. (권한이 없습니다.)");
            }
        }
    }

    var logout = function () {
        var accessToken = Commons.removeCookie(Constants.ACCESS_TOKEN);
        if(Commons.isEmpty(accessToken)) {
            location.href = "/sign/signin";
        }
    }

    return {
        ajaxGet: ajaxGet,
        ajaxPost: ajaxPost,
        ajaxDelete: ajaxDelete,
        ajaxPut: ajaxPut,
        isEmpty: isEmpty,
        isNotEmpty: isNotEmpty,
        initCap: initCap,
        null2Space: null2Space,
        htmlDecode: htmlDecode,
        setCookie: setCookie,
        getCookie: getCookie,
        removeCookie: removeCookie,
        loginChecker: loginChecker,
        logout: logout
    }
})();