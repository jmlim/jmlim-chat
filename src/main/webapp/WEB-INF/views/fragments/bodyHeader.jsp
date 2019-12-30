<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!-- header nav bar -->
<nav class="navbar navbar-inverse" role="navigation">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">SAMPLE</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">사용법</a></li>
				<!--
				<li><a href="#">Link</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Dropdown <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Action</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li class="divider"></li>
						<li><a href="#">Separated link</a></li>
						<li class="divider"></li>
						<li><a href="#">One more separated link</a></li>
					</ul></li> -->
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<c:choose>
					<c:when test="${currentUser != null}">
					<li><a href="<c:url value="/logout"/>">로그아웃</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="<c:url value="/sign/signin"/>">로그인</a></li>
						<li><a href="<c:url value="/sign/signup" />">등록</a></li>
					</c:otherwise>
				</c:choose>
				
				<security:authorize access="hasRole('ROLE_USER')">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle"
							data-toggle="dropdown">회원메뉴 <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="<c:url value="/sign/signup" />">정보수정</a></li>
							<li class="divider"></li>
							<li><a href="#">프로그램 정보</a></li>
							<li><a href="<c:url value="/sign/deleteUser" />">회원탈퇴</a></li>
						</ul>
					</li>
				</security:authorize>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>