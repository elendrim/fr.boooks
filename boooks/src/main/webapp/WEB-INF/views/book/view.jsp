<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="util" uri="http://boooks.fr/functions"%>

<html>
<head>
	<title>Book</title>
	<style type="text/css">
		.dl-horizontal dt {
			white-space: normal;
			width: 150px;
		}
		.dl-horizontal dd {
			margin-left: 160px;
		}
				
	</style>
</head>
<body>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<!--Sidebar content-->

				<!-- <img src="static/img/tango/Office-book.svg" width="200" /> -->
				<div class="thumbnail">
					<img src="rest/book/cover/${book.id}?w=160" onerror="this.src='static/img/tango/Office-book.svg';" width="160" alt="cover">
				</div>

			</div>
			<div class="span10">
				<!--Body content-->
				<div class="row-fluid">
					<div class="span9"><h1>${book.title}</h1></div>
				</div>
				<div class="row-fluid">
					<div class="span9"><blockquote>${book.resume}</blockquote></div>
				</div>
			</div>
			
		</div>
		<div >
			<dl class="dl-horizontal">
			  <dt>Auteurs</dt>
			  <dd>
			  	<c:forEach items="${book.authors}" var="author">
			  		<div><a class="author" href="book/author.htm?author=${util:urlEncode(author.name)}">${author.name}</a></div>
			  	</c:forEach>
			  </dd>
			  <dt>Type</dt>
			  <dd>${book.type.liType}</dd>
			  <dt>Genre</dt>
			  <dd>${book.genre.liGenre}</dd>
			  <dt>Date de publication</dt>
			  <dd><fmt:formatDate value="${book.publishDate}" pattern="dd MMMMM yyyy HH:mm:ss"/></dd>
			</dl>
			
		</div>
	    
    	
    	<div class="control-group">
    		<div class="controls">
    			<c:if test="${fn:contains(booksMimeTypeList,'PDF')}">
    				<form action="rest/book/file/${book.id}/PDF" method="get" ><button class="btn btn-success" ><i class="icon-download icon-white"></i> Télécharger au format PDF</button></form>
   				</c:if>
   				<c:if test="${fn:contains(booksMimeTypeList,'EPUB')}">
    				<form action="rest/book/file/${book.id}/EPUB" method="get" ><button class="btn btn-success" ><i class="icon-download icon-white"></i> Télécharger au format EPUB</button></form>
    			</c:if>
    			<c:if test="${fn:contains(booksMimeTypeList,'TEXT')}">
    				<form action="rest/book/file/${book.id}/TEXT" method="get" ><button class="btn btn-success" ><i class="icon-download icon-white"></i> Télécharger au format TEXT</button></form>
    			</c:if>
    		</div>
    	</div>
    	
		
	</div>

</body>
</html>