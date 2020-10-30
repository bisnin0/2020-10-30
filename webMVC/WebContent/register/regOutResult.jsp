<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${result2>0 }">
	<script>
	//과제로 내가 한 회원탈퇴
	//과제로 내가 한 회원탈퇴
	
		alert("회원탈퇴 되었습니다.");
		location.href="<%=request.getContextPath() %>/index.do";
	</script>
</c:if>
<c:if test="${result2==0 }">
	<script>
		alert("회원탈퇴에 실패하였습니다.");
		history.back();
	</script>
</c:if>