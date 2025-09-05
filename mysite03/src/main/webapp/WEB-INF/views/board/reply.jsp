<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/reply.css">
</head>
<body>
	<form method="post" action="${pageContext.request.contextPath}/board/reply/${parentPost.no}" class="reply-form">
    <table class="tbl-ex">
        <tr>
            <th colspan="2">답글쓰기</th>
        </tr>
        <tr>
            <td class="label">제목</td>
            <td><input type="text" name="title" value="Re: ${parentPost.title}"></td>
        </tr>
        <tr>
            <td class="label">내용</td>
            <td><textarea name="contents" rows="10" cols="60"></textarea></td>
        </tr>
    </table>
    <div class="bottom">
        <a href="${pageContext.request.contextPath}/board/view/${parentPost.no}">취소</a>
        <input type="submit" value="등록">
    </div>
</form>
</body>
</html>