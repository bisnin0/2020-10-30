<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보수정</title>
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
<script>
	$(function(){
		//우편번호 검색하기
		$("#zipcodeSearch").click(function(){
			window.open('/webMVC/register/zipSearch.do','zipcode','width=600, height=600'); //새창에표시될 내용, 창이름, 옵션
		});
		
		//회원가입폼의 데이터 유효성검사
		$("#regFrm").submit(function(){
			if($("#userpwd").val()==""){
				alert("비밀번호를 입력하세요");
				return false;
			}
			if($("#year").val()=="" || $("#year").val().length<4){
				alert("년도를 잘못 입력하였습니다.")
				return false;
			}
			if($("#tel2").val()=="" || $("#tel3").val()==""){
				alert("전화번호를 입력하세요.")
				return false;
			}
			return true;
			
		});
		
	});
</script>
</head>
<body>
<div class="container">
	<%@ include file="/inc/top.jspf" %>
	<div id="content">
		<h1>회원정보수정 폼</h1>
		<form method="post" id="regFrm" action="/webMVC/register/regEditFormOk.do">
		<ul>
			<li>아이디</li>
			<!-- 아이디 중복검사 -->
			<li><input type="text" name="userid" id="userid" placeholder="아이디입력" value="${regVO.userid}" readonly/> 
				</li>
			<li>비밀번호</li>
			<li><input type="password" name="userpwd" id="userpwd"/></li>
			<li>이름</li>
			<li><input type="text" name="username" id="username" value="${regVO.username}" readonly/></li>
			<li>성별</li>
			<li><input type="radio" name="gender" value="M" <c:if test="${regVO.gender=='M'}">checked</c:if>/>남자
				<input type="radio" name="gender" value="F" <c:if test="${regVO.gender=='F'}">checked</c:if>/>여자</li>
			<li>생년월일</li>
			<li><input type="text" name="year" id="year" size="4" placeholder="2020" maxlength="4" value="${regVO.year}"/>년
				<select name="month" id="month">
					<script>
						for(i=1; i<=12; i++){
							var tag="";
							if(i<10){
								tag +="<option value='0"+i+"'";
								if('${regVO.month}'=='0'+i){ // '0' + i는 문자라서 둘 다 비교하려면 왼쪽도 문자여야함
									tag +=" selected>0"+i+"</option>"
								}else{
									tag +=">0"+i+"</option>"
								}
							}else{
								tag += "<option value='"+i+"'";
								if('${regVO.month}'==i+""){ //둘 다 문자로 해주는게 좋음
									tag += " selected>"+i+"</option>"	
								}else{
									tag += ">"+i+"</option>"
								}
							}
							document.write(tag);
						}
					</script>
				</select>월
				<select name="day" id="day">
					<script>
						var tag="";
						for(i=1; i<=31; i++){
							tag+="<option value='";
							if(i<10){
								if('${regVO.day}'=='0'+i){
									tag += "0"+i+ "' selected";
								}else{
									tag += "0"+i + "'";
								}
								tag +=">"+i+"</option>";
							}else{
								if('${regVO.day}'==i+""){
									tag += i +"' selected";
								}else{
									tag += i +"'";
								}
								tag +=">"+i+"</option>";
							}
						}
						document.write(tag);
					</script>
				</select>일
			<li>전화번호</li>
			<li><select name="tel1" id="tel1">
					<option value="02" <c:if test="${regVO.tel1=='02' }">selected</c:if>>02</option>
					<option value="031" <c:if test="${regVO.tel1=='031' }">selected</c:if>>031</option>
					<option value="032" <c:if test="${regVO.tel1=='032' }">selected</c:if>>032</option>
					<option value="010" <c:if test="${regVO.tel1=='010'}">selected</c:if>>010</option>
				</select>-
				<input type="text" name="tel2" id="tel2" size="4" maxlength="4" value="${regVO.tel2 }"/>-
				<input type="text" name="tel3" id="tel3" size="4" maxlength="4" value="${regVO.tel3 }"/>
			</li>			
			<li>이메일</li>
			<li><input type="text" name="email" id="email" size="20" value="${regVO.email }"/></li>
			<li>우편번호</li>
			<li><input type="text" name="zipcode" id="zipcode" size="5" value="${regVO.zipcode }"/>
				<input type="button" value="우편번호검색" id="zipcodeSearch"/>
				<input type="text" name="addr" id="addr" size="60" value="${regVO.addr }"/>
			</li>
			<li>상세주소</li>
			<li><input type="text" name="addrDetail" id="addrDetail" size="40" value="${regVO.addrDetail }"/></li>
			<li>
				<input type="submit" value="회원정보수정하기"/>
				<input type="reset" value="다시쓰기"/>
			</li>
		</ul>
		</form>
	</div>
</div>
</body>
</html>