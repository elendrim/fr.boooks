<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
	<title>Publier</title>
</head>
<body>

	<h1>Book</h1>
    
    <form:form id="addBook" cssClass="form-horizontal" commandName="bookForm"  enctype="multipart/form-data" method="POST">
    	<div class="control-group">
    		<label class="control-label" for="title">Titre</label>
    		<div class="controls">
    			<form:input path="title" id="title" cssClass="span7"  />
    		</div>
    	</div>
    	<div class="control-group">
    		<label class="control-label" for="Type">Type</label>
    		<div class="controls">
    			<form:select path="type" items="${typeList}" itemLabel="liType" itemValue="id" cssClass="span4" />
    		</div>
    	</div>
    	<div class="control-group">
    		<label class="control-label" for="Genre">Genre</label>
    		<div class="controls">
    			<form:select path="genre" items="${genreList}" itemLabel="liGenre" itemValue="id" cssClass="span4"/>
    		</div>
    	</div>
    	<div class="control-group">
    		<label class="control-label" for="Resume">Resume</label>
    		<div class="controls">
    			<form:textarea path="resume" rows="6"  cssClass="span7"/>
    		</div>
    	</div>
    	<div class="control-group">
    		<label class="control-label" for="NbPage">Nombre de page</label>
    		<div class="controls">
    			<form:input path="nbPage" />
    		</div>
    	</div>
    	<div class="control-group">
    		<label class="control-label" for="fileData">Fichier</label>
    		<div class="controls">
    			<form:input path="fileData" type="file"/>
    		</div>
    	</div>
    	      
                    
    	
    	<div class="control-group">
    		<div class="controls">
	    		<form:button class="btn" >Publier</form:button>
    		</div>
    	</div>
    </form:form>
	
</body>
</html>