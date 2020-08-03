<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="/resources/js/axios.min.js"></script>
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
				<th>Phone : 010 -</th>
				<th><input type="text" name="ph" id="ph" placeholder="핸드폰 번호" value = ""> <button type="button" onclick="sendPHAutoNumber()">인증번호 보내기</button></th>
				<th id="phAuthResult"></th>
			</tr>
			<tr>
				<td>인증번호 </td>
				<td><input type ="text" name="phAuthNumber"> </td> 
			</tr>
			<tr>
				<td colspan="3" align="center"><input type="submit"
					value="회원 가입"></td>
			</tr>
		</table>
	</form>
	
	<script >
		function sendPHAutoNumber(){
			if(ph.value.length < 9){
				alert('Phone 번호를 확인해 주세요.');
				return ;
			}
			axios.get('/user/phAuth', {
			    params: {
			      'ph': ph.value
			    }
			  })
			  .then(function (res) {
			    if(res.data.result == 1){
			    	alert("통신 완료");
			    }else{
			    	alert("에러 발생");
			    }
			  })
			  
		}
	</script>
</body>
</html>