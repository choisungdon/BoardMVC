<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<div>로그인</div>
	<div>${msg}</div>
	<div>
		<form id="frm" action="/user/lgoinPost" method="post">
			<div>
				<input type="text" name="cid" placeholder="ID" value="${writedCid }">
			</div>
			<div>
				<input type="password" name="cpw" placeholder="Password">
			</div>
			<div>
				<input type="submit" value="로그인">
			</div>
			<div>
				<a href="/user/join">회원 가입</a>
			</div>
		</form>
	</div>
</body>
</html>