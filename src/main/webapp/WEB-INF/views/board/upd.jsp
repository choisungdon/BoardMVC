<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update</title>
</head>
<body>
<form action="/board/upd?i_board=${data.i_board }" method="post">
	<table>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>내용</th>
			<th>등록일시</th>
		</tr>
		
		<tr>
			<td>${data.i_board }</td>
			<td><input type="text" name="title" value="${data.title }"></td>
			<td><textarea name="ctnt" >${data.ctnt }</textarea></td>
			<td>${data.r_dt }</td>
		</tr>
		<tr>
			<td><input type="submit" value="저장 "></td>
		</tr>
		
	</table>
	</form>
</body>
</html>