<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${result>0 }">
	<script>
		alert("회원탈퇴 되었습니다. 홈페이지로 이동합니다.");
		location.href="<%=request.getContextPath()%>/*.do";
	</script>
</c:if>
<c:if test="${result<=0 }">
	<script>
		alert("회원탈퇴 실패하였습니다.");
		history.back();
	</script>
</c:if>