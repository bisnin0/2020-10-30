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
<style>
	#content li{
		float:left;
		width:10%;
		border-bottom:1px solid gray;
		line-height:40px;
	}
	#content li:nth-of-type(6n+2){
		width:50%;
	}

</style>
<script>
	$(function(){
		$("#content>ul>li>a>img").click(function(){ //#content img 해도 상관없다. 다만 완벽하게 저기만 지정하려면 앞처럼 한다.
			var url = "/webMVC/data/downloadCount.do";
			//레코드번호
			var params = "no="+$(this).attr("alt"); //html의 속성값 구하기 attr
			console.log(params);
			var owner = $(this).parent().parent().next();
			$.ajax({
				url:url,
				data:params,
				success:function(result){
					
					$(owner).html(result.trim());
					
					// $(this).parent().parent().next().html(result); //parent() - 선택자의 부모객체 선택  / next() - 선택자의 다음객체 선택
					//이렇게 하면 될줄알았지만. ajax도 이벤트라 여기서 this하면 파일 다운로드 이벤트가 아니라 ajax가 선택된다. 그래서 실행이 안된다.
					// 그래서 외부로 빼고 변수로 만들어서 넣어준다.
					
				},error:function(e){
					console.log(e.responseText);
				}
			});
		});
	});

</script>
</head>
<body>
<div class="container">
	<%@ include file="../inc/top.jspf" %>
	<h1>자료실 목록</h1>
	<div id="content">
		<ul> <!-- li의 기본적인 스타일은 위에 top.jspf에서 세팅이 되어있다. -->
			<li>번호</li>		
			<li>제목</li>
			<li>작성자</li>
			<li>첨부파일</li>
			<li>다운횟수</li>
			<li>등록일</li>
			<c:forEach var="vo" items="${list}">
				<li>${vo.no}</li>		
				<li><a href="<%=request.getContextPath()%>/data/dataView.do?no=${vo.no}">${vo.title }</a></li>
				<li>${vo.userid }</li>
				<li>
					<!-- 첨부파일 -->
					<c:forEach var="f" items="${vo.filename }">
						<c:if test="${f!=null }">
						<a href="<%=request.getContextPath()%>/upload/${f}" download><img src="<%=request.getContextPath()%>/img/disk.png" title="${f}" alt="${vo.no }"/></a>
						</c:if>
					</c:forEach>
					  
					<!-- 두번째 첨부파일 -->
				</li>
				<li>${vo.downcount }</li>
				<li>${vo.writedate }</li>
			</c:forEach>
		</ul>
	</div><!-- #content -->
	<div>
	<c:if test="${logStatus!=null || logStatus=='Y'}">
	<a href="<%=request.getContextPath()%>/data/dataWriteForm.do">자료올리기</a>
	</c:if>
	</div>
	

</div>
</body>
</html>

<!-- 
	파일 업로드(다운로드는 상관없다.)를 가능하게 하는 프레임워크
	
	http://servlets.com/에서
	왼쪽메뉴에 COS File Upload Library 클릭후
	cos-20.08.zip 다운로드하여 압축을 푼후
	
	cos.jar를 /WEB-INF/lib에 복사한다.
	









 -->