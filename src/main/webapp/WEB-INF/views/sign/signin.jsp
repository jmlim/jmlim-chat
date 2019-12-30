<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<jsp:include page="../fragments/headTag.jsp" />
</head>
<body>
	<jsp:include page="../fragments/bodyHeader.jsp" />

	<div class="signin">
		<form action="<c:url value="/sign/signin" />" method="post"
			class="form-signin">
			<h2 class="form-signin-heading">로그인 해주세요</h2>
			<input id="username" name="username" class="form-control"
				placeholder="Your signin id..." required="required"
				autofocus="autofocus" type="text" /> 
			<input id="password"
				name="password" type="password" class="form-control"
				placeholder="Your password..." required="required" /> 
			<label class="checkbox"> 
				<input value="remember-me" type="checkbox">Remember me</input>
			</label>
			<button class="btn btn-lg btn-primary btn-block" type="submit"
				value="Authenticate">로그인</button>
		</form>
	</div>
</body>
</html>