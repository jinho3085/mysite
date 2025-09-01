<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("newLine", "\n"); %>

<c:set var="authUser" value="${sessionScope.authUser}"/>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<!-- header -->
		<div id="header">
			<h1>MySite</h1>
			<ul>
				<c:choose>
					<c:when test="${empty authUser}">
						<li><a href="${pageContext.request.contextPath}/user?a=loginform">로그인</a></li>
						<li><a href="${pageContext.request.contextPath}/user?a=joinform">회원가입</a></li>
					</c:when>
					
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/user?a=updateform">회원정보수정</a></li>
						<li><a href="${pageContext.request.contextPath}/user?a=logout">로그아웃</a></li>
						<li>${authUser.name}님 안녕하세요 ^^;</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		
		<!-- content -->
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				
				<!-- 게시판 테이블 -->
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
				<!-- 반복예시 -->	
				<c:forEach var="post" items="${posts}">
					<tr>
						<td>${post.id}</td>
						<td style="text-align:left; padding-left:${(post.depth -1)* 20}px">
						    <c:if test="${post.depth > 1}">
						        <img src="${pageContext.request.contextPath}/assets/images/reply.png">
						    </c:if>
							<a href="${pageContext.request.contextPath}/board?a=view&id=${post.id}">${post.title}</a>
						</td>
						<td>${post.writer}</td>
						<td>${post.viewCount}</td>
						<td><fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						
						<td>
							<!-- 삭제 버튼: 로그인 & 작성자 본인 -->
							<c:if test="${not empty authUser and fn:escapeXml(authUser.name) == fn:escapeXml(post.writer)}">
								<a href="${pageContext.request.contextPath}/board?a=delete&id=${post.id}"
									class="del"
									style='background:url("${pageContext.request.contextPath}/assets/images/recycle.png") no-repeat 0 0;'>삭제</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>		
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<li><a href="">1</a></li>
						<li class="selected">2</li>
						<li><a href="">3</a></li>
						<li>4</li>
						<li>5</li>
						<li><a href="">▶</a></li>
					</ul>
				</div>	
								
				<!-- 글쓰기 버튼: 로그인 한 사람 표시 -->				
				<div class="bottom">
					<c:if test="${not empty authUser}">
						<a href="${pageContext.request.contextPath}/board?a=writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		
		<!-- naviagtion -->
		<div id="navigation">
			<ul>
				<li><a href="${pageContext.request.contextPath}/main">정진호</a></li>
				<li><a href="${pageContext.request.contextPath}/guestbook">방명록</a></li>
				<li><a href="${pageContext.request.contextPath}/board">게시판</a></li>
			</ul>
		</div>
		
		<!-- footer -->
		<div id="footer">
			<p>(c)opyright 2025</p>
		</div>
	</div>
</body>
</html>