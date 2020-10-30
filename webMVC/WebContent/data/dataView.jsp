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
<script>
	$(function(){
		/*
		$("content img").click(function(){
			var url ="/webMVC/data/downloadCount.do";
			var params = "no="+$(this).attr("alt");
			var down = $(this).parent().parent().prev().prev()
			$.ajax({
				url:url,
				data:params,
				success:function(result){
					$(down).children("#downcount").html(result.trim());
				},error:function(e){
					console.log(e.responseText);
				}
			});
		}); 내가푼거 쓸대없는 내용이 많음.
		*/
		$(".down").click(function(){
			var url = "/webMVC/data/downloadCount.do";
			var data = "no="+${vo.no};
			$.ajax({
				url:url,
				data:data,
				success:function(result){
					/* 공백을 douwncount.jsp에서 지우게 바꿈 */
					$("#downcount").text(result);
				},error:function(e){
					console.log("다운로드 회수 증가 에러 발생");
				}
			});
		});
		
	});

	function delCheck(){
		if(confirm("삭제하시겠습니까?")){
			location.href="<%=request.getContextPath()%>/data/dataDel.do?no=${vo.no}";
		}
	}
</script>
</head>
<body>
<div class="container">
	<%@ include file="../inc/top.jspf" %>
	<div id="content">
		<ul>
			<li>번호 : ${vo.no}</li>
			<li>작성자 : ${vo.userid}</li>
			<li>다운로드 횟수 : <span id="downcount">${vo.downcount}</span>, 조회수 : ${vo.hit}</li>
			<li>등록일 : ${vo.writedate}</li>
			<li>첨부파일 : 
			<c:forEach var="file" items="${vo.filename}">
				<c:if test="${file!=null && file!=''}">
					<!-- <a href="/webMVC/upload/${file }" download><img src="<%=request.getContextPath()%>/img/disk.png" title="${file}" alt="${vo.no}"/></a>  내가 푼거-->
					<a href="/webMVC/upload/${file }" download class="down">${file }</a>
				</c:if>			
			</c:forEach>
			</li>
			<li>제목 : ${vo.title}</li>
			<li>글내용<br/>${vo.content}</li>
		</ul>
	</div>
	<div>
	<c:if test="${logStatus!=null && logStatus!='' && logStatus=='Y' && logId==vo.userid}">
		<a href="<%=request.getContextPath()%>/data/dataEdit.do?no=${vo.no}">수정</a>
		<a href="javascript:delCheck();">삭제</a>
	</c:if>
	</div>
</div>
</body>
</html>