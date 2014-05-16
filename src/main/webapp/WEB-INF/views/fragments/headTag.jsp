<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>아이큐엠씨 채팅페이지</title>
<link rel="stylesheet"
	href="<c:url value="/resources/jquery/ui/css/ui-darkness/jquery-ui-1.10.4.custom.css" />"
	type="text/css" />
<link rel="stylesheet"
	href="<c:url value="/resources/bootstrap/bootstrap-3.1.1-dist/css/bootstrap.css" />"
	type="text/css" />
<link rel="stylesheet"
	href="<c:url value="/resources/bootstrap/bootstrap-3.1.1-dist/css/bootstrap-theme.css" />"
	type="text/css" />
<link href="<c:url value="/resources/styles/style.css" />"
	rel="stylesheet" type="text/css" />

<script type="text/javascript"
	src="<c:url value="/resources/jquery/jquery-1.10.2.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/jquery/ui/js/jquery-ui-1.10.4.custom.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/bootstrap/bootstrap-3.1.1-dist/js/bootstrap.js" />"></script>
<script type="text/javascript">
	window.jmlim = {};
	jmlim.chat = {};
	jmlim.chat.contextPath = '${pageContext.request.contextPath}';
	jmlim.chat.currentUser = {};
	jmlim.chat.currentUser.id = '${currentUser.uid}';
	jmlim.chat.currentUser.name = '${currentUser.name}';
	jmlim.chat.currentUser.image = '${currentUser.image.id}';
</script>