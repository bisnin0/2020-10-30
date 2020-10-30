<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${pVo.searchWord!=null}"> 
	<c:set var="url">&searchKey=${pVo.searchKey}&searchWord=${pVo.searchWord}</c:set>
</c:if>
<c:if test="${cnt<=0}"> 
	<script>
		alert("글수정이 실패하였습니다. 수정페이지로 다시 이동합니다.");
		history.back();
	</script>
</c:if>
<c:if test="${cnt>0 }">
	<script> 
	location.href="<%=request.getContextPath()%>/board/boardView.do?no=${no}&nowPage=${pVo.nowPage}${url}";	
	</script>
</c:if>

<!-- 
	위에 넣으면 에러나서 여기에 주석넣음.
	//태그라이브러리 명령어가 자바스크립트 안에서는 불가능하다.( {}는가능하다.) ex) c:if ...
	// 아래 내용에 c:if test='{pVo.searchWord!=null}'>searchKey= {pVo.searchKey}&searchWord= {pVo.searchWord}/c:if> 이게 안된다는말
	// 달러표시나 <c로 시작하는것등은 주석 안에서도 적용이 안된다. 위에는 삭제한 내용임. 알아서 숙지
	//그래서 아래로 보내기전에 set으로 변수 선언해서 값을 넣은 후 자바스크립트에서 쓴다. 위에 선언했음

	이부분 중요. 자바스크립트 안에서 c:if사용 못하니까 변수로 만들어서 넣어야함
	
	
 -->