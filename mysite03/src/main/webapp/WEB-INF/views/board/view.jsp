<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="authUser" value="${sessionScope.authUser}"/>

<!DOCTYPE html>
<html>
<head>
    <title>글보기 - MySite</title>
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
        <div id="board" class="board-form">
            <table class="tbl-ex">
                <tr>
                    <th colspan="2">글보기</th>
                </tr>
                <tr>
                    <td class="label">제목</td>
                    <td>${post.title}</td>
                </tr>
                <tr>
                    <td class="label">작성자</td>
                    <td>${post.writer}</td>
                </tr>
                <tr>
                    <td class="label">조회수</td>
                    <td>${post.viewCount}</td>
                </tr>
                <tr>
                    <td class="label">작성일</td>
                    <td><fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
                <tr>
                    <td class="label">내용</td>
                    <td><pre>${post.content}</pre></td>
                </tr>
                <tr>
				    <td class="label">첨부파일</td>
				    <td>
				        <c:if test="${not empty post.fileName}">
				            <a href="${pageContext.request.contextPath}/uploads/${post.fileName}" download>
				                ${post.fileName}
				            </a>
				        </c:if>
				        <c:if test="${empty post.fileName}">
				            첨부파일 없음
				        </c:if>
				    </td>
				</tr>
                
            </table>

            <div class="bottom">
                <a href="${pageContext.request.contextPath}/board">글목록</a>
                <c:if test="${not empty authUser and authUser.name == post.writer}">
                    <a href="${pageContext.request.contextPath}/board?a=updateform&id=${post.id}">글수정</a>
                    <a href="${pageContext.request.contextPath}/board?a=delete&id=${post.id}">삭제</a>
                </c:if>
                <a href="${pageContext.request.contextPath}/board?a=replyform&id=${post.id}">답글</a>
            </div>
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