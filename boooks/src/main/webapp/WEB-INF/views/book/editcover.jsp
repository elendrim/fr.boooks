<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Modifier la couverture</title>
</head>
<body>

	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li><a href="book/view.htm?id=${bookCoverForm.id}"><i class="icon-book"></i> Visualiser</a></li>
		 	<li class="active dropdown">
		    	<a class="dropdown-toggle" data-toggle="dropdown" href="#">
		        	<i class="icon-edit" ></i> Editer
		        	<b class="caret"></b>
		      	</a>
			    <ul class="dropdown-menu">
			      <li><a href="book/edit.htm?id=${bookCoverForm.id}"><i class="icon-pencil"></i> Modifier les informations</a></li>
			      <li><a href="book/editcover.htm?id=${bookCoverForm.id}"><i class="icon-upload"></i> Modifier la couverture</a></li>
			    </ul>
		  	</li>
		</ul>
	</div>
    
    <form:form id="editBook" cssClass="form-horizontal" commandName="bookCoverForm"  enctype="multipart/form-data" method="POST">
    	<form:hidden path="id"/>
    	
    	<div class="control-group">
    		<label class="control-label" for="fileCover">Couverture</label>
    		<div class="controls">
    			<form:input path="fileCover" type="file"/>
    			<form:errors path="fileCover" cssClass="help-inline"/>
    		</div>
    	</div> 
    	
    	<div class="control-group">
    		<div class="controls">
	    		<form:button class="btn btn-primary" ><i class=" icon-upload icon-white"></i> Modifier</form:button>
    		</div>
    	</div>
    </form:form>
	
</body>
</html>