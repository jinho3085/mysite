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
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" 
				      action="${pageContext.request.contextPath}/board" 
				      enctype="multipart/form-data">
				    <input type="hidden" name="a" value="write">
				    <table class="tbl-ex">
				        <tr>
				            <th colspan="2">글쓰기</th>
				        </tr>
				        <tr>
				            <td class="label">제목</td>
				            <td><input type="text" name="title" value=""></td>
				        </tr>
				        <tr>
				            <td class="label">내용</td>
				            <td>
				                <textarea id="content" name="content"></textarea>
				            </td>
				        </tr>
				        <tr>
				            <td class="label">첨부파일</td>
				            <td><input type="file" name="uploadFile"></td>
				        </tr>
				    </table>
				    <div class="bottom">
				        <a href="${pageContext.request.contextPath}/board">취소</a>
				        <input type="submit" value="등록">
				    </div>
				</form>
			</div>
		</div>
		<div id="navigation">
			<ul>
				<li><a href="${pageContext.request.contextPath}/main">정진호</a></li>
				<li><a href="${pageContext.request.contextPath}/guestbook">방명록</a></li>
				<li><a href="${pageContext.request.contextPath}/board">게시판</a></li>
			</ul>
		</div>
		<div id="footer">
			<p>(c)2025</p>
		</div>
	</div>
</body>
</html>