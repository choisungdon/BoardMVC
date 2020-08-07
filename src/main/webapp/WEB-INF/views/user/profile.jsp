<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>profile</title>
</head>
<style>
	
</style>
<body>
	<div>
 		<img id="img" src="${myProfile != null ? Profile : '/resources/img/base_profile.jpg'}">
 	</div>
 	
 	<div>
 		<form id="frm" method="post" action="/user/profile" enctype="multipart/form-data" >
 			<div>
 				<label>
 					이미지 선택 :
 					<input type="file" name ="uploadProfile" accept="image/*">
 				</label>
 			</div>
 			<div>
 				<button type="submit">저장</button>
 			</div>
 		</form>
 	</div>
</body>
</html>