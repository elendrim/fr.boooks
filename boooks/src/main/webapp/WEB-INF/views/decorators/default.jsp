<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"  %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><decorator:title /></title>
	<base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />
	<link rel="icon" type="image/png" href="static/img/glyphicons_071_book.png" />
	<link href="static/css/bootstrap.min.css" rel="stylesheet">
	<link href="static/css/default.css" rel="stylesheet">
    <link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
    <link href="http://fonts.googleapis.com/css?family=Shadows+Into+Light" rel="stylesheet" type="text/css">
	<link href="http://fonts.googleapis.com/css?family=Yanone+Kaffeesatz:400,200,300,700|Cuprum:400,400italic,700,700italic|Francois+One|Mako|Oswald:400,700,300|Ruluko|Cabin+Condensed:400,500,600,700" rel="stylesheet" type="text/css">
	<script src="static/js/jquery-1.8.0.min.js"></script>
	<script src="static/js/bootstrap.min.js"></script>
	

	<decorator:head />
</head>
<body>

	<%@ include file="/WEB-INF/views/menu/menu.jsp" %>
	
	
	<div class="container">
		<br />
		
 		<div class="alert">
	  		<strong>Attention!</strong> Boooks est en version beta. 
		</div> 
		
		<c:if test="${!empty messageSuccess}">
			<div class="alert alert-success">${messageSuccess}</div>
		</c:if>
		
	
	    <decorator:body />
	    
		<hr />
	
	</div> <!-- /container -->

  	<footer>
      	<p>&copy; Boooks 2012 - <a href="#">Mentions légales</a></p>
   	</footer>
	
    
</body>
</html>