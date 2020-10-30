<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 풀이. 내가한건 boardWrite에있음. 기능은 구현됐음 -->
<c:if test="${logStatus=='Y'}"><!-- 게시글 등록할때 로그인 상태인지 확인해서 아니면 돌아가게 만들기 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device, initial-scale=1"/> 
<link rel="stylesheet" href="/webMVC/library/bootstrap.css" type="text/css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
<script src="/webMVC/library/bootstrap.js"></script>
<script src="/webMVC/library/ckeditor/ckeditor.js"></script>

<style>
	#subject{width:100%}	
</style>
<script>
	$(function(){
		CKEDITOR.replace("content");
		
		$("#writeFrm").submit(function(){
			if($("#subject").val()==""){
				alert("제목을 입력하세요");
				$("#subject").focus();
				return false;
			}
			if(CKEDITOR.instances.content.getData()==""){
				alert("글내용을 입력하세요.");
				$(CKEDITOR.instances.content).focus();
				return false;
			}
			return true;
		});
		
	});

</script>
</head>
<body>
<div class="container">
	<%@ include file="../inc/top.jspf" %>
	<h1>글쓰기</h1>
	<form method="post" id="writeFrm" action="<%=request.getContextPath()%>/board/boardWriteOk.do">
		<ul>
			<li>제목</li>
			<li><input type="text" name="subject" id="subject"/></li>
			<li>글내용</li>
			<li><textarea name="content" id="content"></textarea></li>
			<li><input type="submit" value="글등록"/>
				<input type="reset" value="다시쓰기"/>
			</li>
		</ul>
	</form>
</div>
</body>
</html>
</c:if>
<c:if test="${logStatus==null || logStatus!='Y'}">
	<script>
		alert("로그인 후 글을 등록할수 있습니다. 로그인 페이지로 이동합니다.");
		location.href="<%=request.getContextPath()%>/login.do";
	</script>
</c:if>