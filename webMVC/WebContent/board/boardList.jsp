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
	ul, li{
		margin:0;
		padding:0;
		list-style-type:none;	
	}
	#lst>li{
		float:left;
		line-height:35px;
		height:35px;
		border-bottom:1px solid gray;
		width:10%;
		
	}
	#lst>li:nth-of-type(5n+2){width:60%}

	/*wordCut(클래스명이니까 임의로).. 게시판에서 길어서 넘치는 문장 자르기*/
	.wordCut{
		white-space:nowrap; /*줄 바꾸지 말아라. 줄 바꿈 안함*/
		overflow:hidden;/*넘침 숨기기*/
		text-overflow:ellipsis; /*넘친부분을 ...으로 표시하라. 문장 줄이기 */
	}

	/*페이지 매기기*/
	#paging ul{
		height:40px;
		width:100%;
		overflow:auto;
		
	}
	#paging li{
		float:left;
		width:60px;
		height:40px;
		text-align:center;
		font-size:1.3em;
		
	}
</style>
<script>
	$(function(){
		//검색어 폼에서 검색버튼 선택시
		$("#searchFrm").submit(function(){
			if($("#searchWord").val()==""){
				alert("검색어를 입력하세요.");
				return false;
			}
			return true;
		});
	});
</script>
    
</head>
<body>
<div class="container">
	<h1>게시판</h1>
	<div>총레코드 수 : ${pageVO.totalRecord}개</div>
	<div>pages : ${pageVO.nowPage}/${pageVO.totalPage }</div>
	<ul id="lst">
		<li>번호</li>
		<li>제목</li>
		<li>글쓴이</li>
		<li>등록일</li>
		<li>조회수</li>
			<c:forEach var="vo" items="${list }">
			
			<li>${vo.no}</li>
			<li class="wordCut"><a href="<%=request.getContextPath()%>/board/boardView.do?no=${vo.no}&nowPage=${pageVO.nowPage}<c:if test="${pageVO.searchWord!=null}">&searchKey=${pageVO.searchKey}&searchWord=${pageVO.searchWord }</c:if>">${vo.subject}</a></li>
			<li>${vo.userid }</li>
			<li>${vo.writedate}</li>
			<li>${vo.hit }</li>
			</c:forEach>
			
	</ul>
	<div id="paging">
		<ul>
			<!-- 이전페이지 -->
		
			<li>
				<c:if test="${pageVO.nowPage>1}"> <!--														여기부터 검색어 있을때 검색어페이지 넘어가게 작성한것. 밑에도 다 있음	  -->
					<a href="<%=request.getContextPath()%>/board/boardList.do?nowPage=${pageVO.nowPage-1}<c:if test="${pageVO.searchWord!=null}">&searchKey=${pageVO.searchKey}&searchWord=${pageVO.searchWord }</c:if>">Prev</a>
				</c:if>
			</li>
			<!-- 									1						1+10			 -->
			<!-- 									11						11+10			 -->
			<c:forEach var="p" begin="${pageVO.startPageNum}" end="${pageVO.startPageNum+pageVO.onePageRecord-1}">
				<c:if test="${p<=pageVO.totalPage }">
					<li <c:if test="${p==pageVO.nowPage}">style="background-color:lightblue"</c:if>>
						
							<a href="/webMVC/board/boardList.do?nowPage=${p}<c:if test="${pageVO.searchWord!=null}">&searchKey=${pageVO.searchKey}&searchWord=${pageVO.searchWord }</c:if>">${p}</a>
						
					</li>
				</c:if>
			</c:forEach>
			
			<!-- 다음페이지 -->
			<li> 
				<c:if test="${pageVO.nowPage<pageVO.totalPage}">
					<a href="<%=request.getContextPath()%>/board/boardList.do?nowPage=${pageVO.nowPage+1}<c:if test="${pageVO.searchWord!=null}">&searchKey=${pageVO.searchKey}&searchWord=${pageVO.searchWord }</c:if>">Next</a>
				</c:if>
				
			</li>
			
		</ul>
	</div>
	<div>
		<!-- 검색기능 -->
	</div>
	<form method="get" action="/webMVC/board/boardList.do" id="searchFrm">
		<select name="searchKey" id="searchKey">
			<option value="subject">제목</option>
			<option value="content">글내용</option>
			<option value="userid">작성자</option>
		</select>
		<input type="text" name="searchWord" id="searchWord" value="${pageVO.getSearchWord()}"/>
		<input type="submit" value="Search"/> 
	</form>
	<div>
		<a href="<%=request.getContextPath()%>/index.jsp">홈</a>
		<a href="/webMVC/board/boardList.do">전체목록</a>
		<c:if test="${logStatus!=null && logStatus=='Y'}">
		<a href="<%=request.getContextPath()%>/board/boardWrite.do">글쓰기</a>
		</c:if>
	</div>
</div>
</body>
</html>