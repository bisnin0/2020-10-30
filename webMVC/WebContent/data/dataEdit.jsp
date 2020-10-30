<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${logStatus==null || logStatus!='Y'}">
	<script>
		location.href="<%=request.getContextPath()%>/login.do";
	</script>
</c:if>
<c:if test="${logId!=vo.userid }">
	<script>
		alert("작성자만 글을 수정할 수 있습니다.");
		location.href="<%=request.getContextPath()%>/data/dataList.do";
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
		
		//첨부파일 있는걸 지우고 다시 넣을때 input박스 나오게 만들기.
		$("b").click(function(){
			$(this).parent().next().attr("type","file"); //숨겨져있던 file input박스를 보이게 만들기
			$(this).parent().next().next().attr("name","delfile"); //파일을 지우기위해서 X를 누르면 name을 세팅해서 리퀘스트 가능하게 만들기
			$(this).parent().remove(); //파일명 찍힌 div 제거
			//이렇게하면 삭제되는 파일 정보와 새로 추가된 파일 정보를 가져갈수있다.
		});
		
		
		
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
	<form method="post" id="dataForm" action="<%=request.getContextPath()%>/data/dataEditOk.do" enctype="multipart/form-data"> 
																	<!-- 파일첨부를 할때는 enctype="multipart/form-data" 속성이 꼭 있어야한다. 중요!!-->
		<ul>
			<input type="hidden" name="no" value="${vo.no }"/>
			<li>제목</li>
			<li><input type="text" name="title" id="title" value="${vo.title }"></li>
			<li><textarea name="content" id="content">${vo.content }</textarea></li>
			<!-- 첫번째 첨부파일 -->
			<li>
				<div>${vo.filename1} <b>X</b></div>
				<input type="hidden" name="filename1" id="filename1"/>
				<input type="hidden" name="" id="delfile1" value="${vo.filename1 }"/>
				
				
			</li>
			<!-- 두번째 첨부파일 -->
			<li>
				<!-- 두번째 첨부파일이 있을때 -->
				<c:if test="${vo.filename2!=null}">
					<div>${vo.filename2} <b>X</b></div>
					<input type="hidden" name="filename2" id="filename2"/>
					<input type="hidden" name="delfile2" value="${vo.filename2 }"/>
				</c:if>
				<c:if test="${vo.filename2==null }">
					<input type="file" name="filename2" id="filename2"/>
				</c:if>
			</li>
			<li><input type="submit" value="자료실 글 수정"/>
				<input type="reset" value="다시쓰기"/>
			</li>
			
		</ul>
	</form>
	</div>
</div>
</body>
</html>