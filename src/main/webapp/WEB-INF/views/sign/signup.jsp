<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<jsp:include page="../fragments/headTag.jsp" />
</head>
<body>
	<jsp:include page="../fragments/bodyHeader.jsp" />
	<div class="signup">

		<c:choose>
			<c:when test="${currentUser != null}">
				<h2 class="form-signup-heading">정보수정</h2>
			</c:when>
			<c:otherwise>
				<h2 class="form-signup-heading">등록 해주세요</h2>
			</c:otherwise>
		</c:choose>

        <form action="/sign/save" method="post" class="form-signup">
            <label for="email">Email: </label>
            <input name="email" id="email" required="required" class="form-control"
                placeholder="Your email..." />
            <br />

            <label for="name">Name: </label>
            <input name="name" id="name" required="required"
                class="form-control" placeholder="Your name..." />
            <br />

            <label for="password">Password: </label>
            <input type="password" id="password" name="password" required="required"
                class="form-control" placeholder="Your password..."/>
            <br />

            <input type="button"
                class="btn btn-lg btn-primary btn-block signup-margin-top signup-btn"
                value="Submit" />
        </form>
	</div>
</body>

<script>
$.fn.serializeObject = function()
{
   var o = {};
   var a = this.serializeArray();
   $.each(a, function() {
       if (o[this.name]) {
           if (!o[this.name].push) {
               o[this.name] = [o[this.name]];
           }
           o[this.name].push(this.value || '');
       } else {
           o[this.name] = this.value || '';
       }
   });
   return o;
};
    /***
     * TODO: Wrapping 필요
     */
    $(document).ready(function () {

        var $signupBtn = $(".signup-btn");
        // enter key
        $("#password").keydown(function (key) {
            if (key.keyCode == 13) {
                $signupBtn.trigger("click");
                return false;
            }
        });

        $signupBtn.click(function () {
            $.ajax({
                url: '/sign/save',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify($('.form-signup').serializeObject()),
                success: function (data) {
                   console.log(data);
                   alert("가입성공");
                   location.href = "/";
                },
                error: function (err) {
                    console.log(err)
                }
            }).done(function() {
            });
        });
    });

</script>

</html>