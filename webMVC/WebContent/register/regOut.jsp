<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 

		//과제로 내가 한 회원탈퇴
		//과제로 내가 한 회원탈퇴



 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device, initial-scale=1"/> 
<link rel="stylesheet" href="/webMVC/library/bootstrap.css" type="text/css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
<script src="/webMVC/library/bootstrap.js"></script>
<style>
	#content li{
		float:left;
		width:20%;
		padding:5px;
	}
	#content li:nth-child(2n){
		width:80%;
	}
	#content li:last-child{
		width:100%;
		text-align:center;
	}

</style>
</head>
<body>
<div class="container">
	<%@ include file="/inc/top.jspf" %>
	<div id="content">
		<h1>회원탈퇴 폼</h1>
		<form method="post" id="regFrm" action="/webMVC/register/regOutOk.do">
		<ul>
<!-- 		<li>아이디</li>
			아이디 중복검사
			<li><input type="text" name="userid" id="userid" placeholder="아이디입력" value="${regVO.userid}" readonly/> 
				</li> 아이디 안보이게 하는게 좋다고함.-->
			<li>비밀번호</li>
			<li><input type="password" name="userpwd" id="userpwd"/></li>
			<li>
				<input type="submit" value="회원탈퇴"/>
			</li>
		</ul>
		</form>
	</div>
</div>
</body>
</html>