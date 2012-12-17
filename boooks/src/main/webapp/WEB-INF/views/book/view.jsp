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


	
	<%-- <div class="container-fluid">
		<ul class="nav nav-tabs">
			<li class="active"><a><i class="icon-book"></i> Visualiser</a></li>
		 	<li class="dropdown">
		    	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
		        	<i class="icon-edit" ></i> Editer
		        	<b class="caret"></b>
		      	</a>
			    <ul class="dropdown-menu">
			      <li><a href="book/edit.htm?id=${book.id}"><i class="icon-pencil"></i> Modifier les informations</a></li>
			      <li><a href="book/editcover.htm?id=${book.id}"><i class="icon-upload"></i> Modifier la couverture</a></li>
			    </ul>
		  	</li>
		</ul>
	</div> --%>
	

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

<%--     	
    	<!-- INFO: The post URL "checkout.java" is invoked when clicked on "Pay with PayPal" button.-->

		<form action="paypal/checkout.htm" METHOD="POST">
			<input type="image" name="paypal_submit" id="paypal_submit"  src="https://www.paypal.com/en_US/i/btn/btn_dg_pay_w_paypal.gif" alt="Pay with PayPal"/>
		</form>
 --%>    	
		
	</div>
	
	
	
	<!-- Add Digital goods in-context experience. Ensure that this script is added before the closing of html body tag -->
	
	<script src="https://www.paypalobjects.com/js/external/dg.js" type="text/javascript"></script>
	
	
	<script>
	
		var dg = new PAYPAL.apps.DGFlow(
		{
			trigger: 'paypal_submit'
			//expType: 'instant'
			 //PayPal will decide the experience type for the buyer based on his/her 'Remember me on your computer' option.
		});
	
	</script>
		

</body>
</html>