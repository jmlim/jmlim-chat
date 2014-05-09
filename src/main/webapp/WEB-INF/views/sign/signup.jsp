<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

		<c:url value="/sign/processSubmit" var="targetUrl" />
		<form:form action="${targetUrl}" modelattribute="user" method="post"
			commandName="user" class="form-signup">

			<label for="user_id">UserId: </label>
			<form:input path="uid" id="user_id" required="required"
				autofocus="autofocus" class="form-control" placeholder="Your id..." />
			<form:errors path="uid" cssclass="error"></form:errors>
			<br />

			<label for="email">Email: </label>
			<form:input path="email" id="email" required="required"
				autofocus="autofocus" class="form-control"
				placeholder="Your email..." />
			<form:errors path="email" cssclass="error"></form:errors>
			<br />

			<label for="name">Name: </label>
			<form:input path="name" id="name" required="required"
				class="form-control" placeholder="Your name..." />
			<form:errors path="name" cssclass="error"></form:errors>
			<br />

			<label for="password">Password: </label>
			<form:password path="password" id="password" required="required"
				class="form-control" placeholder="Your password..."></form:password>
			<form:errors path="password" cssclass="error"></form:errors>
			<br />
			<input type="submit"
				class="btn btn-lg btn-primary btn-block signup-margin-top"
				value="Submit" />
		</form:form>
	</div>
</body>
</html>