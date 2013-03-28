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
	<title>Mes achats</title>
</head>
    
<body>

	<h1>Mes achats</h1>
	
	<c:set var="elementBegin" value="${( (currentIndex -1) * bookPage.size) +1 }" />
	<c:set var="elementEnd" value="${elementBegin + bookPage.numberOfElements -1}" />
	<c:set var="elementTotal" value="${bookPage.totalElements}" />
	<p class="muted"><small>${elementBegin}-${elementEnd} sur ${elementTotal} résultats</small></p>
	
	<c:forEach items="${bookPage.content}" var="book" varStatus="rowCounter">
		  
		<c:if test="${rowCounter.index mod 6 eq 0}">
	    	<ul class="thumbnails books">
	    </c:if>
	    
	    <li data-id="${book.id}" class="span2">
	    	<div class="thumbnail book">
	    		<a href="book/view.htm?id=${book.id}">
		    		<img src="rest/book/cover/${book.id}?w=160" onerror="this.src='static/img/tango/Office-book.svg';" width="160" alt="cover">
		    	</a>
		    	<div class="details">
		    		<div>
		      			<a class="title" href="book/view.htm?id=${book.id}">${book.title}</a>
		      		</div>
		      		<div>
		      			<c:forEach items="${book.authors}" var="author">
	      					<a class="author" href="book/author.htm?author=${util:urlEncode(author.name)}">${author.name}</a>
	      				</c:forEach>
		      		</div>
	      		</div>
		    </div>
		</li>
		
	    <c:if test="${(rowCounter.index+1) mod 6 eq 0 or rowCounter.last}">
	        </ul>
	    </c:if>
		
	
	</c:forEach>
	
	<h:pagination url="/book/myPublications.htm" page="${bookPage}" currentIndex="${currentIndex}" beginIndex="${beginIndex}" endIndex="${endIndex}" />
	
</body>
</html>