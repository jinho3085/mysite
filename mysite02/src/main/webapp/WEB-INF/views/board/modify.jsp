<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="authUser" value="${sessionScope.authUser}"/>
<!DOCTYPE html>
<html>
<head>
<title>글수정 - Mysite</title>
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
				<form class="board-form" method="post" action="${pageContext.request.contextPath}/board?a=update&id=${post.id}" enctype="multipart/form-data">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글수정</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value="${post.title}"></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td><textarea name="content" rows="10" cols="60">${post.content}</textarea></td>
						</tr>
						<tr>
                        <td class="label">첨부파일</td>
                        <td>
                            <!-- 기존 첨부파일 표시 -->
                            <c:if test="${not empty post.fileName}">
                                현재 파일: 
                                <a href="${pageContext.request.contextPath}/uploads/${post.fileName}" download>
                                    ${post.fileName}
                                </a>
                                <br>
                                <label><input type="checkbox" name="deleteFile" value="true"> 삭제</label>
                            </c:if>
                            <!-- 새 파일 업로드 -->
                            <input type="file" name="uploadFile">
                        </td>
                    </tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board?a=view&id=${post.id}">취소</a>
						<input type="submit" value="수정">
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
			<p>(c)opyright 2025</p>
		</div>
	</div>
</body>
</html>