<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="page" required="true" type="org.springframework.data.domain.Page" rtexprvalue="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="util" uri="http://boooks.fr/functions"%>

<c:set var="elementBegin" value="${( (currentIndex -1) * page.size) +1 }" />
<c:set var="elementEnd" value="${elementBegin + page.numberOfElements -1}" />
<c:set var="elementTotal" value="${page.totalElements}" />
<p class="muted"><small>${elementBegin}-${elementEnd} sur ${elementTotal} résultats</small></p>

<c:forEach items="${page.content}" var="book" varStatus="rowCounter">
	  
	<c:if test="${rowCounter.index mod 6 eq 0}">
    	<ul class="thumbnails books">
    </c:if>
    
    <li data-id="${book.id}" class="span2">
    	<div class="thumbnail book">
    		<a href="book/view.htm?id=${book.id}">
	    		<img src="rest/book/cover/${book.id}?w=160" onerror="this.src='static/img/tango/Office-book_medium.png';" width="160" alt="cover">
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
