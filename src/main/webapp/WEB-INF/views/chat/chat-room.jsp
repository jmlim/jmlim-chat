<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<jsp:include page="../fragments/headTag.jsp" />
<script type="text/javascript"
	src="<c:url value="/resources/scripts/jmlim-chat.js" />"></script>
</head>
<body>
	<jsp:include page="../fragments/bodyHeader.jsp" />
	<noscript>
		<h2 style="color: #ff0000">Seems your browser doesn't support
			Javascript! Websockets rely on Javascript being enabled. Please
			enable Javascript and reload this page!</h2>
	</noscript>
	<div class="chat-main">
		<div class="container">
			<div class="row">
				<div class="col-md-9">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="glyphicon glyphicon-comment">
							</span> 정묵이 채팅방
							<div class="btn-group pull-right">
								<button type="button"
									class="btn btn-default btn-xs dropdown-toggle"
									data-toggle="dropdown">
									<span class="glyphicon glyphicon-chevron-down"></span>
								</button>
								<ul class="dropdown-menu slidedown">
									<li>
										<a href="#"><span class="glyphicon glyphicon-refresh"></span>Refresh</a>
									</li>
									<li>
										<a href="#"><span class="glyphicon glyphicon-ok-sign"></span>Available</a>
									</li>
									<li>
										<a href="#"><span class="glyphicon glyphicon-remove"></span>Busy</a>
									</li>
									<li>
										<a href="#"><span class="glyphicon glyphicon-time"></span>Away</a>
									</li>
									<li class="divider"></li>
									<li>
										<a href="#"><span class="glyphicon glyphicon-off"></span>Sign Out</a>
									</li>
								</ul>
							</div>
						</div>
						<div class="panel-body">
							<ul class="chat">
								<!-- <li class="left clearfix">
									<span class="chat-img pull-left">
										<img src="http://placehold.it/50/55C1E7/fff&text=U"
											alt="User Avatar" class="img-circle" />
									</span>
									<div class="chat-body clearfix">
										<div class="header">
											<strong class="primary-font">Masud</strong> 
											<small
												class="pull-right text-muted"> 
												<span class="glyphicon glyphicon-time"></span>12 mins ago
											</small>
										</div>
										<p>Lorem ipsum dolor sit amet, consectetur adipiscing
											elit. Curabitur bibendum ornare dolor, quis ullamcorper
											ligula sodales.
										</p>
									</div>
								</li> -->
							</ul>
						</div>
						<div class="panel-footer">
							<div class="input-group">
								<input id="btn-input" type="text" class="form-control input-sm"
									placeholder="Type your message here..." /> 
								<span class="input-group-btn">
									<button class="btn btn-warning btn-sm" id="btn-chat">Send</button>
								</span>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<span class="glyphicon glyphicon-comment">
							</span> 현재 접속자 목록
						</div>
						<div class="panel-body">
							<ul class="current-users">
								<!-- <li class="left clearfix">
									<span class="chat-img pull-left">
										<img src="http://placehold.it/50/55C1E7/fff&text=U"
											alt="User Avatar" class="img-circle" />
									</span>
									<div class="chat-body clearfix">
										<div class="header">
											<strong class="primary-font">Masud</strong> 
											<small
												class="pull-right text-muted"> 
												<span class="glyphicon glyphicon-time"></span>12 mins ago
											</small>
										</div>
										<p>Lorem ipsum dolor sit amet, consectetur adipiscing
											elit. Curabitur bibendum ornare dolor, quis ullamcorper
											ligula sodales.
										</p>
									</div>
								</li> -->
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../fragments/footer.jsp" />
	</div>
</body>
</html>
