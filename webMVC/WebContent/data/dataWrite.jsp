<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${logStatus==null || logStatus!='Y' }">
	<script>
		location.href="<%=request.getContextPath()%>/login.do";
	</script>
</c:if>
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
	#title{
		width:600px;
	}
</style>
<script>
	$(function(){
		CKEDITOR.replace("content");
		
		//데이터 존재유무 확인
		$("#dataForm").submit(function(){
			if($("#title").val()==""){
				alert("글제목을 입력하세요.");
				$("#title").focus();
				return false;
			}
			if(CKEDITOR.instances.content.getData()==""){
				alert("글내용을 입력하세요.");
				$(CKEDITOR.instances.content).focus();
				return false;
			}
			
			//첨부파일의 수를 구한다.
			var fileCount=0;
			if($("#filename1").val()!=""){
				fileCount++;
			}
			if($("#filename2").val()!=""){
				fileCount++;
			}
			if(fileCount<1){
				alert("첨부파일은 1개이상 반드시 있어야 합니다.");
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
	<div>
	<h2>자료실 글올리기</h2>											<!-- 파일첨부 파일 첨부 파일 업로드 -->
	<form method="post" id="dataForm" action="<%=request.getContextPath()%>/data/dataWriteOk.do" enctype="multipart/form-data"> 
																	<!-- 파일첨부를 할때는 enctype="multipart/form-data" 속성이 꼭 있어야한다. 중요!!-->
		<ul>
			<li>제목</li>
			<li><input type="text" name="title" id="title"></li>
			<li><textarea name="content" id="content"></textarea></li>
			<li>첨부 파일1 : <input type="file" name="filename1" id="filename1"/></li>
			<li>첨부 파일2 : <input type="file" name="filename2" id="filename2"/></li>
			<li><input type="submit" value="자료올리기"/>
				<input type="reset" value="다시쓰기"/>
			</li>
			
		</ul>
	</form>
	</div>
</div>
</body>
</html>