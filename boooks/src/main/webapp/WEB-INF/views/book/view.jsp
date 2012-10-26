<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Book</title>
</head>
<body>

		<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<!--Sidebar content-->

				<img src="static/img/tango/Office-book.svg" width="200" />

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
		
		<dl class="dl-horizontal">
		  <dt>Type</dt>
		  <dd>${book.type.liType}</dd>
		  <dt>Genre</dt>
		  <dd>${book.genre.liGenre}</dd>
		  <dt>Date de publication</dt>
		  <dd><fmt:formatDate value="${book.publishDate}" pattern="dd MMMMM yyyy HH:mm:ss"/></dd>
		</dl>
	    
    	
    	<div class="control-group">
    		<div class="controls">
    			<form action="rest/book/file/${book.id}" method="get" ><button class="btn" >Télécharger</button></form>
    		</div>
    	</div>
    	
		
	</div>

</body>
</html>