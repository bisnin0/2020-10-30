<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device, initial-scale=1"/> 
<link rel="stylesheet" href="/webMVC/library/bootstrap.css" type="text/css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> 
<script src="/webMVC/library/bootstrap.js"></script>
<script src="<%=request.getContextPath() %>/library/ckeditor/ckeditor.js"></script>
<style>
	#subject{
		width:800px;
	}
	#content{
		width:1000px;
		height:800px;
	}
</style>
<script>
	$(function(){
		CKEDITOR.replace("content");
		
		$("#editFrm").submit(function(){
			if($("#subject").val()==""){
				alert("제목을 입력하세요.")
				$("#subject").focus();
				return false;
			}
			//					이 content는 id가 아니라 name이다. 주의!
			if(CKEDITOR.instances.content.getData()==""){ //CKEDITOR의 val 값 가져오기. 
				alert("내용을 입력하세요");
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
	<h1>글수정하기</h1>
	<form method="post" action="<%=request.getContextPath()%>/board/boardEditOk.do" id="editFrm">
		<input type="hidden" name="no" value="${vo.no }"/> <!-- 안보이게 hidden속성 이용해서 데이터 post방식으로 보내기 -->
		<input type="hidden" name="nowPage" value="${pVo.nowPage }"/>
		<c:if test="${pVo.searchWord!=null}">
			<input type="hidden" name="searchKey" value="${pVo.searchKey }"/>
			<input type="hidden" name="searchWord" value="${pVo.searchWord }"/>
		</c:if>
		
		<ul>
			<li>글제목</li>
			<li><input type="text" name="subject" id="subject" value="${vo.subject }"/></li>
			<li>글내용</li>
			<li><textarea name="content" id="content">${vo.content }</textarea></li>
					
			<li><input type="submit" value="글수정"/>
				<input type="reset" value="다시쓰기"/>
			</li>
		</ul>

	</form>
	<a href="<%=request.getContextPath()%>/index.do">홈</a>
	<a href="<%=request.getContextPath()%>/board/boardList.do?nowPage=${pVo.nowPage}<c:if test="${pVo.searchWord!=null}">&searchKey=${pVo.searchKey}&searchWord=${pVo.searchWord }</c:if>">목록</a>
</div>
</body>
</html>