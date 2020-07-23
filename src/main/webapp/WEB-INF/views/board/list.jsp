<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list</title>
</head>
<body>
	<a href="/board/write">글 등록</a>
	<table>
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>날짜</th>
		</tr>
	<c:forEach items="${data}" var="item">
		<tr onclick="moveToDeTail(${item.i_board })">
			<td>${item.i_board }</td>
			<td>${item.title }</td>
			<td>${item.r_dt }</td>
		</tr>
	</c:forEach>
		
	</table>
	
	<script>
		function moveToDeTail(i_board){
			location.href="/board/detail?i_board="+i_board;
		}
	</script>
</body>
</html>