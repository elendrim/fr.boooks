<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="util" uri="http://boooks.fr/functions"%>

<html>
<head>
	<title>Mes publications</title>
</head>
    
<body>

	<h1>Mes publications</h1>
	
	<c:forEach items="${bookPage.content}" var="book" varStatus="rowCounter">
		  
		<c:if test="${rowCounter.index mod 6 eq 0}">
	    	<ul class="thumbnails books">
	    </c:if>
	    
	    <li data-id="${book.id}" class="span2">
	    	<div class="thumbnail book">
	    		<a href="book/view.htm?id=${book.id}">
		    		<img src="static/img/tango/Office-book.svg" width="160" alt="">
		    	</a>
		    	<div class="details">
		    		<div>
		      			<a class="title" href="book/view.htm?id=${book.id}">${book.title}</a>
		      		</div>
		      		<div>
		      			<c:forEach items="${book.authors}" var="author">
	      					<a class="author" href="book/search.htm?author=${util:urlEncode(author.name)}">${author.name}</a>
	      				</c:forEach>
		      		</div>
	      		</div>
		    </div>
		</li>
		
	    <c:if test="${(rowCounter.index+1) mod 6 eq 0 or rowCounter.last}">
	        </ul>
	    </c:if>
		
	
	</c:forEach>
	
	<c:url var="firstUrl" value="/book/myPublications.htm" >
		<c:param name="p">1</c:param>
	</c:url>
	<c:url var="lastUrl" value="/book/myPublications.htm" >
		<c:param name="p">${bookPage.totalPages}</c:param>
	</c:url>
	<c:url var="prevUrl" value="/book/myPublications.htm" >
		<c:param name="p">${currentIndex - 1}</c:param>
	</c:url>
	<c:url var="nextUrl" value="/book/myPublications.htm" >
		<c:param name="p">${currentIndex + 1}</c:param>
	</c:url>
	
	<div class="pagination">
	    <ul>
	        <c:choose>
	            <c:when test="${currentIndex == 1}">
	                <li class="disabled"><a >&lt;&lt;</a></li>
	                <li class="disabled"><a >&lt;</a></li>
	            </c:when>
	            <c:otherwise>
	                <li><a href="${firstUrl}">&lt;&lt;</a></li>
	                <li><a href="${prevUrl}">&lt;</a></li>
	            </c:otherwise>
	        </c:choose>
	        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
	            <c:url var="pageUrl" value="/book/myPublications.htm" >
					<c:param name="p">${i}</c:param>
				</c:url>
	            <c:choose>
	                <c:when test="${i == currentIndex}">
	                    <li class="active"><a href="${pageUrl}"><c:out value="${i}" /></a></li>
	                </c:when>
	                <c:otherwise>
	                    <li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
	                </c:otherwise>
	            </c:choose>
	        </c:forEach>
	        <c:choose>
	            <c:when test="${currentIndex == bookPage.totalPages}">
	                <li class="disabled"><a >&gt;</a></li>
	                <li class="disabled"><a >&gt;&gt;</a></li>
	            </c:when>
	            <c:otherwise>
	                <li><a href="${nextUrl}">&gt;</a></li>
	                <li><a href="${lastUrl}">&gt;&gt;</a></li>
	            </c:otherwise>
	        </c:choose>
	    </ul>
	</div>
	

</body>
</html>