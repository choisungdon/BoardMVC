<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<title>list</title>
<style>
	.hoverSelected:hover{
		background-color: #ecf0f1;
		cursor: pointer;
	}
</style>
</head>
<body>
	<div>
		${loginUser.nm } 환영 합니다. <a href="/user/logout"><button>로그아웃</button></a>
	</div>

	<a href="/board/write">글 등록</a>
	<div>
		검색 : <input type="search" id="searchText"> <button onclick="search(searchText)">검색 </button></input>
	</div>
	<table >
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>날짜</th>
		</tr>
		<tbody id="contentTable">
		
		</tbody>
	</table>

	<script>
	
	function search(searchText){
		if(searchText.value ==''){
			alert('검색어를 입력해 주세요.');
			searchText.focus();
			return ;
		}
		var ele = document.querySelector('#contentTable');
		ele.innerHTML = '';
		page = 1;
		glovalSearchText =searchText.value;
		getBoardData(page,glovalSearchText);
	}
	
	function addRows(res) {
		res.data.result.forEach(function(item) {
			
			var td1 = document.createElement("td");
			td1.innerHTML = item.i_board
			
			var td2 = document.createElement("td");
			td2.innerHTML = item.title
			
			var td3 = document.createElement("td");
			td3.innerHTML = item.r_dt
			
			var tr = document.createElement('tr')
			tr.appendChild(td1)
			tr.appendChild(td2)
			tr.appendChild(td3)
			
			tr.addEventListener('click', function(){
				location.href = `/board/detail?i_board=\${item.i_board}`
			})
			tr.classList.add("hoverSelected");
			var ele = document.querySelector('#contentTable')					
			ele.appendChild(tr)
		})
	}
			
	function getBoardData(page,searchText) {
		axios.get('/board/getListData', {
			params: {
				page: page,
				searchText: searchText
			}
		}).then(function (res) {
			if(res.data.result.length < 60){
				isBreak = true;
			}
			addRows(res)
		})
	}
	
	var isBreak = false;
	var page = 1;
	var glovalSearchText = '';
	getBoardData(page,glovalSearchText);
	
	window.addEventListener('scroll', function() {
		if(isBreak) {
			return
		}
		if(window.scrollY + window.innerHeight >= document.body.scrollHeight) {
		
			getBoardData(++page,glovalSearchText)	
		}
	})
	
	</script>
</body>
</html>