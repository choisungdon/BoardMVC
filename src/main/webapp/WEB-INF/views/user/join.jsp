<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>join</title>
</head>
<body>
	<div>${msg}</div>
	<div><a href="/user/login">로그인</a></div>
	<form  action="/user/joinPost" method="post" >
		
		<table>
			<tr>
				<th>ID</th>
				<th><input type="text" name="cid" placeholder="ID" value="${data.cid}" ></th> 
			</tr>
			<tr>
				<th>Password</th>
				<th><input type="password" name="cpw" placeholder="Password" ></th>
			</tr>
			<tr>
				<th>Password 확인</th>
				<th><input type="password" name="recpw" placeholder="Password 확인"></th>
			</tr>
			<tr>
				<th>이름</th>
				<th><input type="text" name="nm" placeholder="Name" value = "${data.nm}"></th>
			</tr>
			<tr>
				<th>주소</th>
				<th><input type="text" name="caddr" placeholder="주소" value = ""></th>
			</tr>
			<tr>
				<td colspan="3" align="center"><input type="submit"
					value="회원 가입"></td>
			</tr>
		</table>
	</form>
</body>
</html>