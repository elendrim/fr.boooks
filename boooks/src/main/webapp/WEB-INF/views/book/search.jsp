<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="util" uri="http://boooks.fr/functions"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<html>
<head>
	<title>Rechercher</title>
</head>
    
<body>

	<h1>Boooks</h1>
	
	<h:bookList page="${bookPage}" />
	
	<c:url var="url" value="book/search.htm">
		<c:if test="${!empty param.q}">
			<c:param name="q">${param.q}</c:param>
		</c:if>
	</c:url>
	
	<h:pagination url="${url}" page="${bookPage}" currentIndex="${currentIndex}" beginIndex="${beginIndex}" endIndex="${endIndex}" /> 
	

</body>
</html>