<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Boooks</title>
</head>
<body>

	<!-- Main hero unit for a primary marketing message or call to action -->
	<div class="hero-unit">
		<h1>Hello, world!</h1>
		<p>This is a template for a simple marketing or informational
			website. It includes a large callout called the hero unit and three
			supporting pieces of content. Use it as a starting point to create
			something more unique.</p>
		<p>
			<a class="btn btn-primary btn-large">Learn more &raquo;</a>
		</p>
	</div>

	<!-- Example row of columns -->
	<div class="row">
		<div class="span6">
			<h2>Publier</h2>
			<p>Donec id elit non mi porta gravida at eget metus. Fusce
				dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
				ut fermentum massa justo sit amet risus. Etiam porta sem malesuada
				magna mollis euismod. Donec sed odio dui.</p>
			
			<form class="form-search" action="book/add.htm" method="get">
				<button type="submit" class="btn">Publier</button>
			</form>
			
		</div>
		<div class="span6">
			<h2>Acheter</h2>
			<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in,
				egestas eget quam. Vestibulum id ligula porta felis euismod semper.
				Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum
				nibh, ut fermentum massa justo sit amet risus.</p>
			<form class="form-search" action="book/list.htm">
		    	<div class="input-append">
		    		<input type="text" class="span2 search-query">
		    		<button type="submit" class="btn">Search</button>
		    	</div>
		    </form>
		</div>
	</div>


</body>
</html>